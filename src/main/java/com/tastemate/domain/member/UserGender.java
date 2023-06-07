package com.tastemate.domain.member;

import lombok.Getter;

@Getter
public enum UserGender {

  여자(0), 남자(1);

  private final int genderCode;

  UserGender(int genderCode) {
    this.genderCode = genderCode;
  }

  public static UserGender valueOf(int genderCode) {
    switch(genderCode) {
      case 0: return UserGender.여자;
      case 1: return UserGender.남자;
      default:
        throw new AssertionError("unknowns genderCode : " + genderCode);
    }
  }
}
