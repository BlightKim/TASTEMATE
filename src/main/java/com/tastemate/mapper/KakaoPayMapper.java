package com.tastemate.mapper;

import com.tastemate.domain.KakaoPayReadyVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KakaoPayMapper {

    int kakaoPayReady_insert(KakaoPayReadyVO kakaoPayReadyVO);

}
