package com.tastemate.controller;

import com.tastemate.S3Uploader;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.board.BoardStatus;
import com.tastemate.domain.board.BoardUpdateForm;
import com.tastemate.domain.board.BoardVO;
import com.tastemate.domain.board.BoardWriteForm;
import com.tastemate.domain.comment.CommentVO;
import com.tastemate.domain.board.UploadFileStore;
import com.tastemate.paging.PageHandler;
import com.tastemate.paging.SearchCondition;
import com.tastemate.service.board.BoardService;
import com.tastemate.service.comment.CommentService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

@Controller
@RequestMapping({"/board"})
@Slf4j
public class BoardController {

  private final BoardService boardService;
  private final CommentService commentService;
//  private final S3Uploader s3Uploader;
  private final S3Uploader fileStore;


  public BoardController(BoardService boardService, CommentService commentService,
      S3Uploader fileStore) {
    this.boardService = boardService;
    this.commentService = commentService;
    this.fileStore = fileStore;
  }

  @GetMapping
  public String board(SearchCondition sc, Model model, RedirectAttributes redirectAttributes) {
    log.info("searchCondition: {}", sc);
    int totalCnt = boardService.getResultCnt(sc);
    PageHandler pageHandler = new PageHandler(totalCnt, sc);
    List<BoardVO> boardList = boardService.getAllBoard(sc);
    model.addAttribute("boardList", boardList);
    model.addAttribute("ph", pageHandler);

    return "board/board_list";
  }

  @GetMapping({"/write"})
  public String writeForm(Model model) {
    BoardWriteForm writeForm = new BoardWriteForm();
    model.addAttribute("form", writeForm);
    return "board/board_write";
  }

  @PostMapping({"/write"})
  public String write(@ModelAttribute(name = "form") BoardWriteForm writeForm,
      @SessionAttribute(name = "vo") MemberVO memberVO) throws IOException {
    Integer userIdx = memberVO.getUserIdx();
    String userId = memberVO.getUserId();
        log.info("boardWriteForm: {}", writeForm);
    BoardVO boardVO = writeFormToBoardVO(writeForm, userIdx, userId);
    MultipartFile boardAttachedFile = writeForm.getMultipartFile();
    if (boardAttachedFile != null) {
      String storeName = fileStore.saveFile(boardAttachedFile);
      boardVO.setOriName(boardAttachedFile.getOriginalFilename());
      boardVO.setStoreName(storeName);
    }

    boardService.saveOneBoard(boardVO);
    return "redirect:/board";
  }

  @GetMapping({"/read/{boardIdx}"})
  public String read(@PathVariable("boardIdx") Integer boardIdx, Model model,
      @SessionAttribute(name = "vo") MemberVO memberVO,
      HttpServletRequest request,
      HttpServletResponse response) {
    Integer userIdx = memberVO.getUserIdx();
    boolean checkForLike = boardService.checkForLike(boardIdx, userIdx);
    BoardVO board = boardService.getOnePost(boardIdx);
    viewCountValidation(boardIdx, request, response);
    List<CommentVO> commentList = commentService.getCommentList(boardIdx);
    model.addAttribute("isLiked", checkForLike);
    model.addAttribute("board", board);
    model.addAttribute("commentList", commentList);
    return "board/board_read";
  }

  @ResponseBody
  @PostMapping({"/unlike/{boardIdx}"})
  public String unlike(@PathVariable("boardIdx") Integer boardIdx,
      @SessionAttribute(name = "vo") MemberVO memberVO) {
    Integer userIdx = memberVO.getUserIdx();
    log.info("unlike 호출");
    boolean isLiked = boardService.checkForLike(boardIdx, userIdx);
    if (isLiked) {
      boardService.decreaseLike(boardIdx, userIdx);
    }

    isLiked = boardService.checkForLike(boardIdx, userIdx);
    if(isLiked) {
      return "fail";
    } else {
      return "ok";
    }
  }

  @ResponseBody
  @PostMapping({"/like/{boardIdx}"})
  public String like(@PathVariable("boardIdx") Integer boardIdx,
      @SessionAttribute(name = "vo") MemberVO memberVO) {
    Integer userIdx = memberVO.getUserIdx();
    log.info("like 호출");
    boolean isLiked = boardService.checkForLike(boardIdx, userIdx);
    if (!isLiked) {
      boardService.increaseLike(boardIdx, userIdx);
    }

    isLiked = boardService.checkForLike(boardIdx, userIdx);
    if(isLiked) {
      return "ok";
    } else {
      return "fail";
    }
  }

  @PostMapping({"/update/{boardIdx}"})
  public String update(@PathVariable("boardIdx") Integer boardIdx,
      @ModelAttribute("board") BoardUpdateForm updateForm) throws IOException {
    BoardVO boardVO = updateFormToBoardVO(boardIdx, updateForm);
    Integer integer = boardService.updateBoard(boardVO);
    return "redirect:/board/read/" + boardIdx;
  }

  @GetMapping({"/update/{boardIdx}"})
  public String updateForm(@PathVariable("boardIdx") Integer boardIdx,
      Model model) {
    BoardVO board = boardService.getOnePost(boardIdx);
    model.addAttribute("board", board);
    return "/board/board_update";
  }

  @ResponseBody
  @PostMapping("/delete/{boardIdx}")
  public String delete(@PathVariable("boardIdx") Integer boardIdx) {
    Integer result = boardService.deleteBoard(boardIdx);
    if(result == 1) {
      return "success";
    } else {
      return "fail";
    }
  }

/*  @GetMapping({"/download/{storeFileName}"})
  public ResponseEntity<Resource> downloadFile(
      @PathVariable(name = "storeFileName") Integer boardIdx, HttpServletRequest request)
      throws MalformedURLException {
    BoardVO findPost = boardService.getOnePost(boardIdx);
    String storeFileName = findPost.getStoreName();
    String oriName = findPost.getOriName();
    UrlResource resource = new UrlResource("file:" + storeFileName);
    log.info("uploadFileName={}", oriName);
    String encodedUploadFileName = UriUtils.encode(oriName, StandardCharsets.UTF_8);
    String contentDisposition = "attachment; fileName=\"" + encodedUploadFileName + "\"";
    return ((ResponseEntity.BodyBuilder) ResponseEntity.ok()
        .header("Content-Disposition", new String[]{contentDisposition})).body(resource);
  }*/

    @GetMapping({"/download/{storeFileName}"})
  public ResponseEntity<byte[]> downloadFile(
      @PathVariable(name = "storeFileName") Integer boardIdx, HttpServletRequest request)
        throws IOException {
    BoardVO findPost = boardService.getOnePost(boardIdx);
    String storeFileName = findPost.getStoreName();
      log.info("storeFileName={}", storeFileName);
    return fileStore.downloadFile(storeFileName,findPost.getOriName());
  }

  private BoardVO writeFormToBoardVO(BoardWriteForm writeForm, Integer userIdx, String userId) {
    BoardVO board = new BoardVO();
    board.setUserIdx(userIdx);
    board.setWriter(userId);
    board.setTitle(writeForm.getTitle());
    board.setContent(writeForm.getContent());
    board.setBoardPassword(writeForm.getPassword());
    board.setBoardStatus(BoardStatus.등록);
    return board;
  }

  private void viewCountValidation(Integer boardIdx, HttpServletRequest request,
      HttpServletResponse response) {
    Cookie[] cookies = request.getCookies();
    AtomicBoolean isCookie = new AtomicBoolean(false);
    Stream<Cookie> stream = Arrays.stream(cookies);
    stream.filter((cookie -> cookie.getName().equals("boardView"))).findAny().ifPresent(cookie -> {
      if(!cookie.getValue().contains("["+boardIdx+"]")) {
        boardService.increaseHit(boardIdx);
        cookie.setValue(cookie.getValue() + "["+boardIdx+"]");
        } else {
          isCookie.set(true);
        }
    });

    if(!isCookie.get()) {
      Cookie cookie = new Cookie("boardView", "["+boardIdx+"]");
      boardService.increaseHit(boardIdx);
      response.addCookie(cookie);
      long todayEndSecond = LocalDate.now().atTime(LocalTime.MAX).toEpochSecond(ZoneOffset.UTC);
      long currentSecond = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC);
      cookie.setPath("/");
      cookie.setMaxAge((int)(todayEndSecond - currentSecond));
      response.addCookie(cookie);
    }
  }

  private BoardVO updateFormToBoardVO(Integer boardIdx, BoardUpdateForm updateForm)
      throws IOException {
    BoardVO boardVO = boardService.getOnePost(boardIdx);
    boardVO.setBoardIdx(boardIdx);
    boardVO.setTitle(updateForm.getTitle());
    boardVO.setContent(updateForm.getContent());

    MultipartFile boardAttachedFile = updateForm.getMultipartFile();
    String originalFilename = boardAttachedFile.getOriginalFilename();
    String existingFile = updateForm.getExistingFile();
    if (!"".equals(originalFilename)) {
      String storeFileName = fileStore.saveFile(boardAttachedFile);
      boardVO.setOriName(originalFilename);
      boardVO.setStoreName(storeFileName);
    } else {
      if (existingFile == null) {
        boardVO.setOriName(null);
        boardVO.setStoreName(null);
      }
    }
    return boardVO;
  }
}
