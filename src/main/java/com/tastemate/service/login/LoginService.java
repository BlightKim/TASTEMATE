//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tastemate.service.login;

import com.tastemate.domain.member.MemberVO;

public interface LoginService {
  MemberVO getMemberById(String memberId);

  boolean loginCheck(String loginId, String loginPassword);
}
