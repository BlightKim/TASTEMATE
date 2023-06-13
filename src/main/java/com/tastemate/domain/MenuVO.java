package com.tastemate.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("MenuVO")
public class MenuVO {

    private int menuIdx;
    private int storeIdx;
    private String foodName;
    private int price;


}
