//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tastemate.dao.login;

import com.tastemate.domain.member.MemberVO;

public interface LoginDao {
  MemberVO findById(String memberId);
}
