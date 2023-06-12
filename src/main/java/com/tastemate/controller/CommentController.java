package com.tastemate.controller;

import com.tastemate.domain.MemberVO;
import com.tastemate.domain.board.BoardVO;
import com.tastemate.domain.comment.CommentVO;
import com.tastemate.service.board.BoardService;
import com.tastemate.service.comment.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

  private final CommentService commentService;
  private final BoardService boardService;

  @ResponseBody
  @GetMapping
  public ResponseEntity<List<CommentVO>> commentList(
      @RequestParam(name = "boardIdx") Integer boardIdx) {
    List<CommentVO> commentList = commentService.getCommentList(boardIdx);
    return new ResponseEntity<>(commentList, HttpStatus.OK);
  }

  @PostMapping("/write")
  public String writeComment(@RequestBody CommentVO commentVO,
      @SessionAttribute(name = "vo") MemberVO memberVO, Model model) {
    Integer userIdx = memberVO.getUserIdx();
    commentService.writeOneComment(commentVO,userIdx);
    List<CommentVO> commentList = commentService.getCommentList(commentVO.getBoardIdx());
    BoardVO boardVO = boardService.getOnePost(commentVO.getBoardIdx());
    model.addAttribute("boardVO", boardVO);
    model.addAttribute("commentList", commentList);
    return "board/comment_container :: #comment-container";
  }
  @DeleteMapping("/comments/delete/{commentIdx}")
  public void deleteComment(@PathVariable(name = "commentIdx") Integer commentIdx,
      @RequestParam(name = "boardIdx") Integer boardIdx) {
    Integer result = commentService.deleteOneComment(commentIdx, boardIdx);

    if(result != 1) {
     throw new RuntimeException("오류가 발생했습니다.");
    }
  }
}
