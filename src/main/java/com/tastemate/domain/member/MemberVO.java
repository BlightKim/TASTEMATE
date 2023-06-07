package com.tastemate.domain.member;

import lombok.Data;
import org.apache.catalina.User;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
public class MemberVO {
        private int userIdx;
        private String userId;
        private String userPwd;
        private String userName;
        private UserGender userGender;
        private String userPhone;
        private String userAddress;
        private int userClass;
        private String userLikeFood;
        private String userLoginType;
        private Date userDate;
        private UserStatus userStatus;
        private String userProfile;
//        private MultipartFile userOriProfile;
        private UserType userType;
        private String userMbti;
        private String userEmail;
        private String userBirth;
}
