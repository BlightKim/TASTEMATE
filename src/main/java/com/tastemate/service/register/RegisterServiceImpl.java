package com.tastemate.service.register;

import com.tastemate.dao.register.RegisterDao;
import com.tastemate.domain.member.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class RegisterServiceImpl implements RegisterService {

  private final RegisterDao registerDao;

  @Override
  public int add(MemberVO member) {
    return registerDao.insertMember(member);
  }
}
