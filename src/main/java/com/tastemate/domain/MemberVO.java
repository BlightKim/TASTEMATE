package com.tastemate.domain;

public class MemberVO {
    private  int userIdx;
    public String userName;
    public  String userProfile;

    public  String userLikeFood;
    public  String userMbti;
    public int userClass;

    public int getUserIdx() {
        return userIdx;
    }

    public void setUserIdx(int userIdx) {
        this.userIdx = userIdx;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserLikeFood() {
        return userLikeFood;
    }

    public void setUserLikeFood(String userLikeFood) {
        this.userLikeFood = userLikeFood;
    }

    public String getUserMbti() {
        return userMbti;
    }

    public void setUserMbti(String userMbti) {
        this.userMbti = userMbti;
    }

    public int getUserClass() {
        return userClass;
    }

    public void setUserClass(int userClass) {
        this.userClass = userClass;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "userIdx=" + userIdx +
                ", userName='" + userName + '\'' +
                ", userProfile='" + userProfile + '\'' +
                ", userLikeFood='" + userLikeFood + '\'' +
                ", userMbti='" + userMbti + '\'' +
                ", userClass=" + userClass +
                '}';
    }
}

