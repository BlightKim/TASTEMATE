package com.tastemate.service.comment;

import com.tastemate.dao.board.BoardDao;
import com.tastemate.dao.comment.CommentDao;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.comment.CommentVO;
import com.tastemate.mapper.CommentMapper;
import com.tastemate.mapper.MemberMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
  private final CommentDao commentDao;
  private final BoardDao boardDao;
  private final MemberMapper memberMapper;

  @Override
  public Integer updateOneComment(Integer commentIdx, CommentVO commentVO) {
    String commentContent = commentVO.getCommentContent();
    Map map = new HashMap();
    map.put("commentIdx", commentIdx);
    map.put("commentContent", commentContent);
    return commentDao.updateOneComment(map);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer writeOneComment(CommentVO commentVO, Integer userIdx) {
    MemberVO member = memberMapper.findUserByUserIdx(userIdx);
    commentVO.setMemberVO(member);
    boardDao.increaseCommentCount(commentVO.getBoardIdx());
    return commentDao.insertOneComment(commentVO);
  }

  @Override
  public CommentVO getOneComment(Integer commentIdx) {
    return commentDao.selectOneComment(commentIdx);
  }

  @Override
  public List<CommentVO> getCommentList(Integer boardIdx) {
    return commentDao.selectAllComment(boardIdx);
  }

  @Override
  @Transactional(rollbackFor = Exception.class)
  public Integer deleteOneComment(Integer commentIdx, Integer boardIdx) {
    boardDao.decreaseCommentCount(boardIdx);
    return commentDao.deleteOneComment(commentIdx);
  }
}