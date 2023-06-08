package com.tastemate.controller;

import com.tastemate.domain.MemberVO;
import com.tastemate.domain.comment.CommentVO;
import com.tastemate.service.comment.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {

  private final CommentService commentService;

  @GetMapping
  public ResponseEntity<List<CommentVO>> commentList(
      @RequestParam(name = "boardIdx") Integer boardIdx) {
    List<CommentVO> commentList = commentService.getCommentList(boardIdx);
    return new ResponseEntity<>(commentList, HttpStatus.OK);
  }

  @PostMapping("/write")
  public List<CommentVO> writeComment(@RequestBody CommentVO commentVO,
      @SessionAttribute(name = "vo") MemberVO memberVO) {
    Integer userIdx = memberVO.getUserIdx();
    Integer result = commentService.writeOneComment(commentVO, userIdx);
    List<CommentVO> commentList = commentService.getCommentList(commentVO.getBoardIdx());
    log.info("commentList={}", commentList);
    return commentList;
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
