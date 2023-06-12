package com.tastemate.service.board;

import com.tastemate.dao.board.BoardDao;
import com.tastemate.domain.board.BoardUpdateForm;
import com.tastemate.domain.board.BoardVO;
import com.tastemate.domain.board.UploadFileStore;
import com.tastemate.paging.SearchCondition;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
  private final BoardDao boardDao;

  public List<BoardVO> getAllPost(SearchCondition sc) {
    log.info("boardDao={}", boardDao);
    return boardDao.selectAllBoard(sc);
  };

  public Integer deleteBoard(Integer boardIdx) {
    return boardDao.deleteBoard(boardIdx);
  }

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
    return boardDao.selectOneBoardByBoardIdx(boardIdx);
  }

  public Integer updateBoard(BoardVO boardVO) {
    return boardDao.updateBoard(boardVO);
  }
  public Integer increaseHit(Integer boardIdx) {
    return boardDao.increaseHit(boardIdx);
  }
  public boolean checkForLike(Integer boardIdx, Integer userIdx) {
    HashMap map = new HashMap();
    map.put("boardIdx", boardIdx);
    map.put("userIdx", userIdx);
    return boardDao.checkForLike(map);
  }

  @Transactional
  public Integer increaseLike(Integer boardIdx, Integer userIdx) {
    Map<String, Object> map = new HashMap<>();
    map.put("boardIdx", boardIdx);
    map.put("userIdx", userIdx);
    like(map);
    return boardDao.increaseLike(boardIdx);
  }
  @Transactional
  public Integer decreaseLike(Integer boardIdx, Integer userIdx) {
    Map<String, Object> map = new HashMap<>();
    map.put("boardIdx", boardIdx);
    map.put("userIdx", userIdx);
    unlike(map);
    return boardDao.increaseLike(boardIdx);
  }
  private Integer unlike(Map map) {
    return boardDao.deleteLike(map);
  }

  private Integer like(Map map) {
    return boardDao.insertLike(map);
  }

/*  public Integer updateBoard(Integer boardIdx, BoardUpdateForm updateForm) {
    BoardVO boardVO = updateFormToBoardVO(updateForm);

  }*/


}
