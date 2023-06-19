package com.tastemate.domain;

import lombok.Data;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.type.Alias;

import java.util.Date;
import java.util.List;

@Data
@Alias("KakaoPayApprovalVO")
public class KakaoPayApprovalVO {

    //response
    private String aid, tid, cid, sid;
    private String partner_order_id, partner_user_id, payment_method_type;

    private AmountVO amount;

    private CardVO card_info;
    private String item_name, item_code, payload;
    private Integer quantity, tax_free_amount, vat_amount;
    private Date created_at, approved_at;

    /* 내가 추가 */
    private int kakaoApprovalIdx;
    private int userIdx;
    private int storeIdx;
    private int bookingIdx;
    private int status;

    //private String partnerOrderId;

    private String pgToken;
    private int amount2;

    //private String paymentMethodType;
    //private String itemName;
    //private Date approvedAt;

    /*booking join*/

    private List<BookingVO> bookingVO;

    





}
