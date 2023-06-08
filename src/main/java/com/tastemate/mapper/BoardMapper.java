package com.tastemate.mapper;

import com.tastemate.domain.MemberVO;
import com.tastemate.domain.board.BoardVO;
import com.tastemate.paging.SearchCondition;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
  Integer insertLike(Map map);
  Integer deleteLike(Map map);

  Integer deleteByPrimaryKey(Integer boardIdx);

  Integer insert(BoardVO boardVO);

  Integer insertSelective(BoardVO boardVO);

  List<MemberVO> findLikedUsers(Integer boardIdx);

  Integer updateByPrimaryKey(BoardVO boardVO);

  List<BoardVO> selectResultPage(SearchCondition sc);

  void deleteLike(Integer boardIdx, Integer userIdx);
  Integer selectResultCnt(SearchCondition sc);

  BoardVO selectOneBoardByBoardIdx(Integer boardIdx);

  Integer increaseHit(Integer boardIdx);
  Integer increaseCommentCount(Integer boardIdx);
  Integer updateBoard(BoardVO boardVO); // 메서드 추가

  Integer checkForLike(Map<String, Integer> map);

  Integer increaseLike(Integer boardIdx);

  Integer decreaseLike(Integer boardIdx);

  Integer deleteBoard(Integer boardIdx);
  Integer decreaseCommentCount(Integer boardIdx);
}