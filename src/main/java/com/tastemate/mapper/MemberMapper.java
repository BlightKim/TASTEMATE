package com.tastemate.mapper;

import com.tastemate.domain.MemberVO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;


@Mapper
public interface MemberMapper {

  @Autowired
  public List<MemberVO> member_getList();


  public int user_join(MemberVO vo);

  public Integer checkId(String userId);

  public MemberVO loginId(String userId);

  public int userModify(MemberVO vo);

  public int userStatus(MemberVO vo);

  public MemberVO findId(String userEmail);

  public Integer check(String userId, String userPwd);

  public MemberVO userGet(String userId, String userEmail);

  public int resetPassword(MemberVO vo);

  public int createChatRoom(MemberVO vo);

  public MemberVO findUserByUserIdx(Integer userIdx);

  public List<MemberVO> member_getClassList(int userClass);

  public List<MemberVO> user_getWithStar(int userClass);


  //별점주기 위해 추가
  int findRoomIdx(int userIdx);

  int findMatchingUserIdx(int roomIdx, int userIdx);
}
