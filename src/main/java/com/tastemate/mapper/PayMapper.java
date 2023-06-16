package com.tastemate.mapper;

import com.tastemate.domain.InicisVO;
import com.tastemate.domain.KakaoPayApprovalVO;
import com.tastemate.domain.KakaoPayReadyVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PayMapper {

    int kakaoPayReady_insert(KakaoPayReadyVO kakaoPayReadyVO);

    int kakaoPayApproval_insert(KakaoPayApprovalVO kakaoPayApprovalVO);

    KakaoPayApprovalVO get_KakaoApproval(int userIdx);

    int get_amount(String tid);

    int delete_update(String tid);

    int insert_inicis(InicisVO inicisVO);

    InicisVO get_inicis(int userIdx);

    int cancel_inicis(String merchant_uid);

    int updateStatus(int inicisIdx);

    int updateStatus2(int kakaoApprovalIdx);

    InicisVO findInicis(int userIdx);

    KakaoPayApprovalVO findKakao(int userIdx);
}
