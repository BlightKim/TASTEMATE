package com.tastemate.domain.comment;

import lombok.Getter;

@Getter
public enum CommentStatus {
  등록("등록"), 삭제("삭제");

  private final String commentStatusCode;

  CommentStatus(String commentStatusCode) {
    this.commentStatusCode = commentStatusCode;
  }
}
