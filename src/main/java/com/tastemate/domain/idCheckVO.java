package com.tastemate.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;

@Data
public class memberVO {
        private int userIdx;
        private String userId;
        private String userPwd;
        private String userName;
        private int userGender;
        private String userPhone;
        private String userAddress;
        private int userClass;
        private String userLikeFood;
        private String userLoginType;
        private Date userDate;
        private int userStatus;
        private String userProfile;
        private MultipartFile userOriProfile;
        private int userType;
        private String userMbti;
        private String userEmail;
        private String userBirth;
}
