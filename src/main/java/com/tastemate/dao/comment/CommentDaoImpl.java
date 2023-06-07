package com.tastemate.dao.comment;

import com.tastemate.domain.comment.CommentVO;
import com.tastemate.mapper.CommentMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentDaoImpl implements CommentDao {

  private final CommentMapper commentMapper;

  @Override
  public CommentVO selectOneComment(Integer commentIdx) {
    return commentMapper.selectOneComment(commentIdx);
  }

  @Override
  public int insertOneComment(CommentVO commentVO) {
    commentMapper.insertComment(commentVO);
    return commentVO.getCommentIdx();
  }

  @Override
  public List<CommentVO> selectAllComment(Integer boardIdx) {
    return commentMapper.selectAllComment(boardIdx);
  }
}
