package com.tastemate.domain.board;

import lombok.Getter;

@Getter
public enum BoardStatus {
  등록("등록"), 삭제("삭제");

  private final String boardStatusCode;

  BoardStatus(String boardStatusCode) {
    this.boardStatusCode = boardStatusCode;
  }

}
