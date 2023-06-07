package com.tastemate.service.comment;

import com.tastemate.dao.board.BoardDao;
import com.tastemate.dao.comment.CommentDao;
import com.tastemate.dao.login.LoginDao;
import com.tastemate.dao.register.RegisterDao;
import com.tastemate.domain.comment.CommentVO;
import com.tastemate.domain.member.MemberVO;
import com.tastemate.mapper.CommentMapper;
import com.tastemate.mapper.MemberMapper;
import java.util.List;
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
  @Transactional(rollbackFor = Exception.class)
  public Integer writeOneComment(CommentVO commentVO, Integer userIdx) {
    MemberVO member = memberMapper.selectByUserIdx(userIdx);
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
}
