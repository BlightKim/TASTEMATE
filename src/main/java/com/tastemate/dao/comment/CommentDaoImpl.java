package com.tastemate.dao.comment;

import com.tastemate.domain.comment.CommentVO;
import com.tastemate.mapper.CommentMapper;
import java.util.List;
import java.util.Map;
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

  @Override
  public Integer deleteOneComment(Integer commentIdx) {
    return commentMapper.deleteOneComment(commentIdx);
  }

  @Override
  public Integer updateOneComment(Map map) {
    return commentMapper.updateOneComment(map);
  }
}