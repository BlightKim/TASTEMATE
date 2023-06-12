package com.tastemate.domain;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoPayReadyVO {

    private String tid, next_redirect_pc_url;
    private Date created_at;

/*내가 추가*/
    private int kakaoReadyIdx;
    private int userIdx;
    private int storeIdx;
    private int bookingIdx;

    private String partnerOrderId;
    private String pgToken;
    private int totalAmount;

}
