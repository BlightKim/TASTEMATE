package com.tastemate.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("StarVO")
public class StarVO {

    private int starIdx;
    private int storeIdx;
    private int userIdx;
    private double storeStar;
    private double userStar;

    private String storeComment;

    //user도 추가
    private String userName;

    // 결제관련추가
    private int inicisIdx;
    private int kakaoApprovalIdx;

}
