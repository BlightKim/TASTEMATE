package com.tastemate.dao.register;

import com.tastemate.domain.member.MemberVO;

public interface RegisterDao {
  public int insertMember(MemberVO member);
}
