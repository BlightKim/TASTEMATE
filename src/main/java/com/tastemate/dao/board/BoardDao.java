package com.tastemate.dao.board;

import com.tastemate.domain.board.BoardVO;
import com.tastemate.paging.SearchCondition;
import java.util.List;
import java.util.Map;

public interface BoardDao {
  public int insertOne(BoardVO board);
  public List<BoardVO> selectAllBoard(SearchCondition sc);

  Integer selectResultCnt(SearchCondition sc);

  BoardVO selectOneBoardByBoardIdx(Integer boardIdx);
  Integer increaseHit(Integer boardIdx);
  Integer increaseCommentCount(Integer boardIdx);

  Integer updateBoard(BoardVO board); // 메서드 추가

  boolean checkForLike(Map<String, Integer> map);

  Integer increaseLike(Integer boardIdx);

  Integer decreaseLike(Integer boardIdx);
  Integer deleteBoard(Integer boardIdx);

  Integer insertLike(Map map);
  Integer deleteLike(Map map);

  Integer decreaseCommentCount(Integer boardIdx);
}
