package com.tastemate.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.sql.Date;

@Data
@Alias("BookingVO")
public class BookingVO {
    private int bookingIdx;
    private int storeIdx;
    private String storeName;
    private int userIdx;
    private String userId;
    private String userName;
    private int onsitePayment;
    private Date bookingDate;
    private String bookingTime;
    private int menuIdx;
    private String foodName;
    private int price;
    private int tableNo;

}
