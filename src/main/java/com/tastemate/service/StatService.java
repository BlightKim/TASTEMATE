package com.tastemate.service;

import com.tastemate.domain.InicisVO;
import com.tastemate.domain.KakaoPayApprovalVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.mapper.StatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatService {

    @Autowired
    private StatMapper statMapper;

    public List<InicisVO> priceAmountInicis(int month) {
        System.out.println("month = " + month);
        String date = "2023-" + month + "-01";
        System.out.println("date = " + date);
        List<InicisVO> inicisVO = statMapper.priceAmountInicis(date);

        return inicisVO;
    }

    public List<KakaoPayApprovalVO> priceAmountKakao(int month) {
        System.out.println("month = " + month);
        String date = "2023-" + month + "-01";
        System.out.println("date = " + date);
        List<KakaoPayApprovalVO> kakaoPayApprovalVO = statMapper.priceAmountKakao(date);

        return kakaoPayApprovalVO;
    }
    public Map<Integer, String> inicisStorename(List<InicisVO> inicisVO) {
        Map<Integer, String> names = new HashMap<>();
        for (InicisVO vo : inicisVO) {
            names.put(vo.getStoreIdx(), statMapper.storeName(vo.getStoreIdx()));
        }
        return names;
    }
    public Map<Integer, String> kakaoStorename(List<KakaoPayApprovalVO> kakaoPayApprovalVO) {
        Map<Integer, String> names = new HashMap<>();
        for (KakaoPayApprovalVO vo : kakaoPayApprovalVO) {
            names.put(vo.getStoreIdx(), statMapper.storeName(vo.getStoreIdx()));
        }
        return names;
    }

    public List<MemberVO> memberMbti () {
        List<MemberVO> memberMbti = statMapper.memberMbti();



        return memberMbti;
    }


}
