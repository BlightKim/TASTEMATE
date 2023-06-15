package com.tastemate.dao.comment;

import com.tastemate.domain.comment.CommentVO;
import java.util.List;
import java.util.Map;

public interface CommentDao {
    List<CommentVO> selectAllComment(Integer boardIdx);
    int insertOneComment(CommentVO commentVO);

    CommentVO selectOneComment(Integer commentIdx);

    Integer deleteOneComment(Integer commentIdx);

    Integer updateOneComment(Map map);
}