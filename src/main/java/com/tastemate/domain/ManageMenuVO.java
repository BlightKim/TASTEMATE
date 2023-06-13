package com.tastemate.domain;

import lombok.Data;

@Data
public class ManageMenuVO {
    private int menuIdx;
    private int storeIdx;
    private String foodName;
    private int price;
}
