package com.tastemate.mapper;

import com.tastemate.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper
public interface MemberMapper {

        @Autowired
        public List<MemberVO> member_getList();

        @Autowired
        public String member_checkId();

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


    }
