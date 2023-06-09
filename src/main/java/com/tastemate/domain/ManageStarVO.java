package com.tastemate.domain;

import lombok.Data;

@Data
public class ManageStarVO extends ManageMemberVO {

    private int starIdx;
    private int storeIdx;
    private int userIdx;
    private int storeStar;
    private int userStar;
    private String storeComment;

}
