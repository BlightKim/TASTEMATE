package com.tastemate.service.comment;

import com.tastemate.domain.comment.CommentVO;
import java.util.List;

public interface CommentService {
    List<CommentVO> getCommentList(Integer boardIdx);
    Integer writeOneComment(CommentVO commentVO, Integer userIdx);

    CommentVO getOneComment(Integer commentIdx);

    Integer deleteOneComment(Integer commentIdx, Integer boardIdx);

    Integer updateOneComment(Integer commentIdx, CommentVO commentVO);
}