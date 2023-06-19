package com.tastemate.mapper;

import com.tastemate.domain.InicisVO;
import com.tastemate.domain.KakaoPayApprovalVO;
import com.tastemate.domain.MemberMbti;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.board.BoardVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatMapper {

    List<InicisVO> priceAmountInicis(String date);

    List<KakaoPayApprovalVO> priceAmountKakao(String date);

    String storeName(int storeIdx);

    List<MemberVO> memberMbti();

    List<BoardVO> adminBoard();

    List<BoardVO> bestBoard();

    List<BoardVO> bestTwenty();

}
