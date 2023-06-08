package com.tastemate.controller;

import com.tastemate.domain.comment.CommentVO;
import com.tastemate.service.comment.CommentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
      Integer userIdx) {
    userIdx = 1;
//    @SessionAttribute(name = "userIdx") Integer userIdx
    Integer result = commentService.writeOneComment(commentVO, userIdx);
    List<CommentVO> commentList = commentService.getCommentList(commentVO.getBoardIdx());
    log.info("commentList={}", commentList);
    return commentList;
  }
}
