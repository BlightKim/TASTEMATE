package com.tastemate.controller;

import com.tastemate.domain.InicisVO;
import com.tastemate.domain.KakaoPayApprovalVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.StatVO;
import com.tastemate.domain.board.BoardVO;
import com.tastemate.service.StatService;
import io.lettuce.core.support.caching.ClientSideCaching;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/stat/*")
@Slf4j
public class StatController {

    @Autowired
    private StatService statService;

    @GetMapping("/statMain")
    public void statmain(Model model) {
        String title = "매출";

        Map<Integer, List<StatVO>> statMonthInicis = new HashMap<>();
        Map<Integer, Integer> monthTotalAmount = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            int totalAmount = 0;
            List<InicisVO> inicisVO = statService.priceAmountInicis(i);
            System.out.println("inicisVO = " + inicisVO);
            Map<Integer, String> inicisStorename = statService.inicisStorename(inicisVO);
            List<StatVO> list = new ArrayList<>();
            for (int j = 0; j < inicisVO.size(); j++) {
                StatVO statVO = new StatVO();
                statVO.setStoreIdx(inicisVO.get(j).getStoreIdx());
                statVO.setStoreName(inicisStorename.get(inicisVO.get(j).getStoreIdx()));
                statVO.setAmount(inicisVO.get(j).getAmount());
                statVO.setCount(inicisVO.get(j).getUserIdx());
                list.add(statVO);
                totalAmount += statVO.getAmount();
                statMonthInicis.put(i, list);
            }
            monthTotalAmount.put(i, totalAmount);
        }
        System.out.println("statMonthInicis = " + statMonthInicis);


        Map<Integer, List<StatVO>> statMonthKakao = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            int totalAmount = 0;
            List<KakaoPayApprovalVO> kakaoPayApprovalVO = statService.priceAmountKakao(i);
            Map<Integer, String> kakaoStorename = statService.kakaoStorename(kakaoPayApprovalVO);
            List<StatVO> list2 = new ArrayList<>();

            for (int j = 0; j < kakaoPayApprovalVO.size(); j++) {
                StatVO statVO = new StatVO();
    
                statVO.setStoreIdx(kakaoPayApprovalVO.get(j).getStoreIdx());
                statVO.setStoreName(kakaoStorename.get(kakaoPayApprovalVO.get(j).getStoreIdx()));
                statVO.setAmount(kakaoPayApprovalVO.get(j).getAmount2());
                statVO.setCount(kakaoPayApprovalVO.get(j).getUserIdx());
                System.out.println("statVO = " + statVO);
                list2.add(statVO);
                System.out.println("list2 = " + list2);
                totalAmount += statVO.getAmount();
                statMonthKakao.put(i, list2);
            }
            monthTotalAmount.put(i, (monthTotalAmount.get(i) + totalAmount));

        }
        System.out.println("카카오랑 이니시스까지만 statMonthKakao = " + statMonthKakao);


        Map<Integer, List<StatVO>> statMonthInicisKakao = new HashMap<>();
        List<Integer> remover;
        List<List<Integer>> lister = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            int listerIdx = 0;
            if (statMonthInicis.get(i) != null) {
                for (int j = 0; j < statMonthInicis.get(i).size(); j++) {
                    if (statMonthKakao.get(i) != null) {
                        for (int k = 0; k < statMonthKakao.get(i).size(); k++) {
                            if (statMonthInicis.get(i).get(j).getStoreIdx() == statMonthKakao.get(i).get(k).getStoreIdx()) {
                                List<StatVO> test = new ArrayList<>();
                                test.add(statMonthInicis.get(i).get(j));
                                statMonthInicisKakao.put(i, test);

                                remover = new ArrayList<>();
                                remover.add(listerIdx);
                                remover.add(i);
                                remover.add(j);
                                lister.add(remover);
                                listerIdx++;
                            }
                        }
                    }
                }
            }
        }

        System.out.println("여기!! statMonthInicis = " + statMonthInicis);
        System.out.println("여기!! statMonthKakao = " + statMonthKakao);

        System.out.println("lister = " + lister);
        for (int i = lister.size() -1 ; i >= 0 ; i--) {
                int month = lister.get(i).get(1);
                List<StatVO> test2 = statMonthInicis.get(month);
                int voNum = (int)lister.get(i).get(2);
                test2.remove(voNum);
                System.out.println("test2 = " + test2);
                statMonthInicisKakao.put(month, test2);
        }

        System.out.println("여기!! statMonthInicisKakao = " + statMonthInicisKakao);
        System.out.println("여기!! statMonthInicis = " + statMonthInicis);

        Date date = new Date();
        int thisMonth = date.getMonth() + 1;
        model.addAttribute("statMonthInicis", statMonthInicis);
        model.addAttribute("statMonthKakao", statMonthKakao);
        model.addAttribute("monthTotalAmount", monthTotalAmount);
        model.addAttribute("statMonthInicisKakao", statMonthInicisKakao);
        model.addAttribute("title", title);
        model.addAttribute("thisMonth", thisMonth);

    }


    @GetMapping("/statMember")
    public void statMember(Model model) {
        String title = "수강생 현황";

        List<MemberVO> memberMbti = statService.memberMbti();
        Map<Integer, String> mbtiOrder = new HashMap<>();
        Map<String, Integer> mbti = new HashMap<>();
        for (int i = 0; i < memberMbti.size(); i++) {
            mbtiOrder.put(i, memberMbti.get(i).getUserMbti());
            mbti.put(memberMbti.get(i).getUserMbti(), memberMbti.get(i).getUserStatus());
        }

        model.addAttribute("title", title);
        model.addAttribute("mbtiOrder", mbtiOrder);
        model.addAttribute("mbti", mbti);

    }

    @GetMapping("/honeyBoard")
    public void honeyBoard (Model model){
        List<BoardVO> bestTwenty = statService.bestTwenty();
        System.out.println("bestTwenty = " + bestTwenty);


        model.addAttribute("bestTwenty", bestTwenty);
    }
}
