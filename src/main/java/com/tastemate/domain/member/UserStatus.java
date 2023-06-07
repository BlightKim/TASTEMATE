package com.tastemate.domain.member;

import lombok.Getter;

@Getter
public enum UserStatus {


  등록(0), 휴면(1);

  private final Integer userStatusCode;

  UserStatus(Integer userStatusCode) {
    this.userStatusCode = userStatusCode;
  }

  public static UserStatus valueOf(Integer userStatusCode) {
    switch(userStatusCode) {
      case 0: return UserStatus.등록;
      case 1: return UserStatus.휴면;
      default:
        throw new AssertionError("unknowns userStatusCode : " + userStatusCode);
    }
  }
}
