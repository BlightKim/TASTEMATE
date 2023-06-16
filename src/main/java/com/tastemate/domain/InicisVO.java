package com.tastemate.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

@Data
@Alias("InicisVO")
public class InicisVO {


    private String pg;
    private String imp_uid;
    private String pay_method;
    private String merchant_uid;
    private String name;
    private int amount;
    private String buyer_email;
    private String buyer_name;
    private String buyer_tel;
    private String buyer_addr;
    private String buyer_postcode;


    private String card_no;
    private String refund;
    private Date pay_date;


    /*내가 추가*/
    private int inicisIdx;
    private String token;
    private int userIdx;
    private int storeIdx;
    private int bookingIdx;
    private int status;

    /*join추가*/
    private BookingVO bookingVO;


}
