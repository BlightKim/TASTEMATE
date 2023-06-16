package com.tastemate.domain;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Alias("StoreVO")
public class StoreVO {

  private String storeIdx;
  private String userIdx;
  private String storeName;
  private String category1;
  private String storeAddress;

  private double storeLati;
  private double storeLongi;
  private double distance;

  private String phoneNumber;
  private String storeCount;

  private String filename;
  private MultipartFile oriFilename;

  private int storeStat;


  // join
  private List<MenuVO> menuVO;

  // join
  private List<StarVO> starVO;



}
