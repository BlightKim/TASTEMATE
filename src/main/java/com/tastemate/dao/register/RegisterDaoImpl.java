package com.tastemate.dao.register;

import com.tastemate.domain.member.MemberVO;
import com.tastemate.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RegisterDaoImpl implements RegisterDao {
  private final MemberMapper memberMapper;

  @Override
  public int insertMember(MemberVO member) {
    return memberMapper.insertUser(member);
  }
}
