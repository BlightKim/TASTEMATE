package com.tastemate.dao.board;

import com.tastemate.domain.board.BoardVO;
import com.tastemate.mapper.BoardMapper;
import com.tastemate.paging.SearchCondition;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class BoardDaoImpl implements BoardDao{

  private final BoardMapper boardMapper;

  @Override
  public Integer insertLike(Map map) {
    return boardMapper.insertLike(map);
  }

  @Override
  public Integer deleteLike(Map map) {
    return boardMapper.deleteLike(map);
  }

  @Override
  public Integer increaseCommentCount(Integer boardIdx) {
    return boardMapper.increaseCommentCount(boardIdx);
  }

  @Override
  public int insertOne(BoardVO board) {
    log.info("board={}", board);
    return boardMapper.insert(board);
  }

  @Override
  public Integer increaseLike(Integer boardIdx) {
    return boardMapper.increaseLike(boardIdx);
  }

  @Override
  public Integer decreaseLike(Integer boardIdx) {
    return boardMapper.decreaseLike(boardIdx);
  }

  public List<BoardVO> selectAllBoard(SearchCondition sc) {

    List<BoardVO> boardVOS = boardMapper.selectResultPage(sc);
    log.info("boardVOS={}", boardVOS);
    return boardVOS;
  }

  @Override
  public boolean checkForLike(Map<String, Integer> map) {
    Integer result = boardMapper.checkForLike(map);
    boolean isLike = result > 0;
    return isLike;
  }

  public Integer selectResultCnt(SearchCondition sc) {

    return boardMapper.selectResultCnt(sc);
  }

  @Override
  public Integer deleteBoard(Integer boardIdx) {
    return boardMapper.deleteBoard(boardIdx);
  }

  @Override
  public BoardVO selectOneBoardByBoardIdx(Integer boardIdx) {
    return boardMapper.selectOneBoardByBoardIdx(boardIdx);
  }

  @Override
  public Integer increaseHit(Integer boardIdx) {
    return boardMapper.increaseHit(boardIdx);
  }

  @Override
  public Integer updateBoard(BoardVO board) {
    return boardMapper.updateBoard(board);
  }

  @Override
  public Integer decreaseCommentCount(Integer boardIdx) {
    return boardMapper.decreaseCommentCount(boardIdx);
  }
}
