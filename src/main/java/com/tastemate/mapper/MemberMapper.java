package com.tastemate.mapper;


import com.tastemate.domain.member.MemberVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface MemberMapper {

  MemberVO selectByUserIdx(Integer userIdx);
  MemberVO selectByUserId(String userId);
  int deleteByUserIdx(Integer userIdx);
  int insertUser(MemberVO MemberVO);


    @Autowired
    public List<MemberVO> member_getList();

//    @Autowired
//    public String member_checkId();

    public int user_join(MemberVO vo);

    public Integer checkId(String userId);

    public MemberVO loginId(String userId);

    public int userModify(MemberVO vo);

}