package com.tastemate.domain.member;

import lombok.Getter;
import org.springframework.expression.spel.ast.TypeCode;

@Getter
public enum UserType {
  등록(0), 휴면(1);

  private final Integer userTypeCode;

  UserType(Integer userTypeCode) {
    this.userTypeCode = userTypeCode;
  }
  public static UserType valueOf(int userTypeCode) {
    switch(userTypeCode) {
      case 0: return UserType.등록;
      case 1: return UserType.휴면;
      default:
        throw new AssertionError("unknowns userTypeCode : " + userTypeCode);
    }
  }
}
