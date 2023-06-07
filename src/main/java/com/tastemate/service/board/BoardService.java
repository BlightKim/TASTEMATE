package com.tastemate.service.board;

import com.tastemate.dao.board.BoardDao;
import com.tastemate.domain.board.BoardVO;
import com.tastemate.paging.SearchCondition;
import java.util.HashMap;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
  private final BoardDao boardDao;

  public List<BoardVO> getAllPost(SearchCondition sc) {
    log.info("boardDao={}", boardDao);
    return boardDao.selectAllBoard(sc);
  };


  public Integer saveOneBoard(BoardVO boardVO) {
    return boardDao.insertOne(boardVO);
  }

  public Integer getResultCnt(SearchCondition sc) {
    return boardDao.selectResultCnt(sc);
  }
  public List<BoardVO> getAllBoard(SearchCondition sc) {
    return boardDao.selectAllBoard(sc);
  }

  public BoardVO getOnePost(Integer boardIdx) {
    boardDao.increaseHit(boardIdx);
    return boardDao.selectOneBoardByBoardIdx(boardIdx);
  }

  public Integer updateBoard(BoardVO boardVO) {
    return boardDao.updateBoard(boardVO);
  }

  public boolean checkForLike(Integer boardIdx, Integer userIdx) {
    HashMap map = new HashMap();
    map.put("boardIdx", boardIdx);
    map.put("userIdx", userIdx);
    return boardDao.checkForLike(map);
  }

  @Transactional
  public Integer increaseLike(Integer boardIdx, Integer userIdx) {
    boardDao.insertLike(boardIdx, userIdx);
    return boardDao.increaseLike(boardIdx);
  }
  @Transactional
  public Integer unlike(Integer boardIdx, Integer userIdx) {
    return boardDao.decreaseLike(boardIdx);
  }

  public Integer like(Integer boardIdx, Integer userIdx) {
    return boardDao.increaseLike(boardIdx);
  }
}
