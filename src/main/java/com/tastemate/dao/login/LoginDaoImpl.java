package com.tastemate.dao.login;

import com.tastemate.domain.member.MemberVO;
import com.tastemate.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class LoginDaoImpl implements LoginDao{
  private final MemberMapper memberMapper;

  @Override
  public MemberVO findById(String userId) {
    return memberMapper.selectByUserId(userId);
  }
}
