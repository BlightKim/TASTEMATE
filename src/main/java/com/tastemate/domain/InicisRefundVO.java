package com.tastemate.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("InicisRefundVO")
public class InicisRefundVO {


    private String merchant_uid;
    private String imp_uid;
    private String cancel_request_amount;
    private String reason;
    private String token;

    private int bookingIdx;
    private String nowDate;
    private String nowTime;



}
