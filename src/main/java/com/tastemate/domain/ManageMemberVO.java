package com.tastemate.domain;

import lombok.Data;

@Data
public class ManageMemberVO {
    private int userIdx;
    private String userId;
    private String userPwd;
    private String userName;
    private int userGender;
    private String userPhone;
    private String userAddress;
    private int userClass;
    private String userLikeFood;
    private String userLogintype;
    private String userDate;
    private int userStatus;
    private String userProfile;
    private String userOriProfile;
    private int userType;
    private String userMbti;
    private String userEmail;
    private String userBirth;
}
