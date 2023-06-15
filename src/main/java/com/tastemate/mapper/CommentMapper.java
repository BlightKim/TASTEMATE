//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tastemate.mapper;

import com.tastemate.domain.comment.CommentVO;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
  List<CommentVO> selectAllComment(Integer boardIdx);

  Integer insertComment(CommentVO commentVO);

  CommentVO selectOneComment(Integer commentIdx);

  Integer deleteOneComment(Integer commentIdx);

  Integer updateOneComment(Map map);
}
