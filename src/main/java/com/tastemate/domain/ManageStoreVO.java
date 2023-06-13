package com.tastemate.domain;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ManageStoreVO extends ManageMemberVO {

    private int storeIdx;
    private int userIdx;

    private String storeName;
    private String category1;
    private String storeAddress;

    private double storeLati;
    private double storeLongi;

    private String phoneNumber;

    private int storeCount;

    private String filename;
    private MultipartFile oriFilename;

    private int storeStat;

}
