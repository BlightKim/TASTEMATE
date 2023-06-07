package com.tastemate.service.login;

import com.tastemate.dao.login.LoginDao;
import com.tastemate.domain.member.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final LoginDao loginDao;

  @Override
  public MemberVO getMemberById(String memberId) {

    return loginDao.findById(memberId);
  }

  @Override
  public boolean loginCheck(String loginId, String loginPassword) {
    MemberVO findMember = loginDao.findById(loginId);
    if(findMember.getUserPwd().equals(loginPassword)) {
      return true;
    }
    return false;
  }
}