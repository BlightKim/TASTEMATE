package com.tastemate.mapper;

import com.tastemate.domain.KakaoPayApprovalVO;
import com.tastemate.domain.KakaoPayReadyVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface KakaoPayMapper {

    int kakaoPayReady_insert(KakaoPayReadyVO kakaoPayReadyVO);

    int kakaoPayApproval_insert(KakaoPayApprovalVO kakaoPayApprovalVO);

    KakaoPayApprovalVO get_KakaoApproval(int userIdx);

    int get_amount(String tid);

    int delete_update(String tid);
}
