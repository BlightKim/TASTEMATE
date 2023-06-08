package com.tastemate.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkVO {
    private int bookmarkIdx;
    private int userIdx;
    private int storeIdx;
    private String userId;
    private String userName;
    private String storeName;
    private StoreVO storeVO;


}
