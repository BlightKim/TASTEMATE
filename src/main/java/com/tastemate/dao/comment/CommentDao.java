package com.tastemate.dao.comment;

import com.tastemate.domain.comment.CommentVO;
import java.util.List;

public interface CommentDao {
  List<CommentVO> selectAllComment(Integer boardIdx);
  int insertOneComment(CommentVO commentVO);

  CommentVO selectOneComment(Integer commentIdx);
}
