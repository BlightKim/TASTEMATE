

package com.tastemate.controller;

import com.tastemate.domain.board.BoardStatus;
import com.tastemate.domain.board.BoardVO;
import com.tastemate.domain.board.BoardWriteForm;
import com.tastemate.domain.comment.CommentVO;
import com.tastemate.domain.member.UploadFileStore;
import com.tastemate.paging.PageHandler;
import com.tastemate.paging.SearchCondition;
import com.tastemate.service.board.BoardService;
import com.tastemate.service.comment.CommentService;
import com.tastemate.service.login.LoginService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

@Controller
@RequestMapping({"/board"})
public class BoardController {
  private static final Logger log = LoggerFactory.getLogger(BoardController.class);
  private final BoardService boardService;
  private final CommentService commentService;
  private final UploadFileStore fileStore;
  private final LoginService loginService;

  @GetMapping
  public String board(SearchCondition sc, Model model) {
    int totalCnt = this.boardService.getResultCnt(sc);
    PageHandler pageHandler = new PageHandler(totalCnt, sc);
    List<BoardVO> boardList = this.boardService.getAllBoard(sc);
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
  public String write(@ModelAttribute(name = "form") BoardWriteForm writeForm, @SessionAttribute(name = "userIdx") Integer userIdx) throws IOException {
    log.info("writeForm={}", writeForm);
    BoardVO boardVO = this.writeFormToBoardVO(writeForm, userIdx);
    MultipartFile boardAttachedFile = writeForm.getMultipartFile();
    if (boardAttachedFile != null) {
      String storeName = this.fileStore.saveFile(boardAttachedFile);
      boardVO.setOriName(boardAttachedFile.getOriginalFilename());
      boardVO.setStoreName(storeName);
    }

    this.boardService.saveOneBoard(boardVO);
    return "redirect:/board";
  }

  @GetMapping({"/read/{boardIdx}"})
  public String read(@PathVariable("boardIdx") Integer boardIdx, Model model, @SessionAttribute(name = "userIdx") Integer userIdx) {
    boolean checkForLike = this.boardService.checkForLike(boardIdx, userIdx);
    BoardVO board = this.boardService.getOnePost(boardIdx);
    List<CommentVO> commentList = this.commentService.getCommentList(boardIdx);
    model.addAttribute("isLiked", checkForLike);
    model.addAttribute("board", board);
    model.addAttribute("commentList", commentList);
    return "board/board_read";
  }

  @PostMapping({"/unlike/{boardIdx}"})
  public String unlike(@PathVariable("boardIdx") Integer boardIdx, @SessionAttribute(name = "userIdx") Integer userIdx) {
    log.info("unlike 호출");
    this.boardService.unlike(boardIdx, userIdx);
    return "redirect:/board/read/" + boardIdx;
  }

  @PostMapping({"/like/{boardIdx}"})
  public String like(@PathVariable("boardIdx") Integer boardIdx, @SessionAttribute(name = "userIdx") Integer userIdx) {
    log.info("like 호출");
    this.boardService.like(boardIdx, userIdx);
    return "redirect:/board/read/" + boardIdx;
  }

  @PostMapping({"/update/{boardIdx}"})
  public String update(@PathVariable("boardIdx") Integer boardIdx, @ModelAttribute("board") BoardVO boardVO) {
    boardVO.setBoardIdx(boardIdx);
    this.boardService.updateBoard(boardVO);
    return "redirect:/board/read/" + boardIdx;
  }

  @GetMapping({"/download/{storeFileName}"})
  public ResponseEntity<Resource> downloadFile(@PathVariable(name = "storeFileName") Integer boardIdx, HttpServletRequest request) throws MalformedURLException {
    BoardVO findPost = this.boardService.getOnePost(boardIdx);
    String storeFileName = findPost.getStoreName();
    String oriName = findPost.getOriName();
    String var10002 = this.fileStore.getFullPath(storeFileName);
    UrlResource resource = new UrlResource("file:" + var10002);
    log.info("uploadFileName={}", oriName);
    String encodedUploadFileName = UriUtils.encode(oriName, StandardCharsets.UTF_8);
    String contentDisposition = "attachment; fileName=\"" + encodedUploadFileName + "\"";
    return ((ResponseEntity.BodyBuilder)ResponseEntity.ok().header("Content-Disposition", new String[]{contentDisposition})).body(resource);
  }

  private BoardVO writeFormToBoardVO(BoardWriteForm writeForm, Integer userIdx) {
    BoardVO board = new BoardVO();
    board.setUserIdx(userIdx);
    board.setTitle(writeForm.getTitle());
    board.setContent(writeForm.getContent());
    board.setBoardPassword(writeForm.getPassword());
    board.setBoardStatus(BoardStatus.등록);
    return board;
  }

  public BoardController(final BoardService boardService, final CommentService commentService, final UploadFileStore fileStore, final LoginService loginService) {
    this.boardService = boardService;
    this.commentService = commentService;
    this.fileStore = fileStore;
    this.loginService = loginService;
  }
}
