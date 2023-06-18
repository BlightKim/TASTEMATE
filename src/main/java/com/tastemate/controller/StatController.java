package com.tastemate.controller;

import com.tastemate.domain.InicisVO;
import com.tastemate.domain.KakaoPayApprovalVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.StatVO;
import com.tastemate.service.StatService;
import io.lettuce.core.support.caching.ClientSideCaching;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<StatVO> list = new ArrayList<>();
        Map<Integer, Integer> monthTotalAmount = new HashMap<>();

        for (int i = 1; i <= 12; i++) {
            int totalAmount = 0;
            List<InicisVO> inicisVO = statService.priceAmountInicis(i);
            System.out.println("inicisVO = " + inicisVO);
            Map<Integer, String> inicisStorename = statService.inicisStorename(inicisVO);
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


        Map<Integer, List<StatVO>> statMonthKakao = new HashMap<>();
        List<StatVO> list2 = new ArrayList<>();

        for (int i = 1; i <= 12; i++) {
            int totalAmount = 0;
            List<KakaoPayApprovalVO> kakaoPayApprovalVO = statService.priceAmountKakao(i);
            Map<Integer, String> kakaoStorename = statService.kakaoStorename(kakaoPayApprovalVO);

            for (int j = 0; j < kakaoPayApprovalVO.size(); j++) {
                StatVO statVO = new StatVO();

                statVO.setStoreIdx(kakaoPayApprovalVO.get(j).getStoreIdx());
                statVO.setStoreName(kakaoStorename.get(kakaoPayApprovalVO.get(j).getStoreIdx()));
                statVO.setAmount(kakaoPayApprovalVO.get(j).getAmount2());
                statVO.setCount(kakaoPayApprovalVO.get(j).getUserIdx());

                list2.add(statVO);
                totalAmount += statVO.getAmount();
                statMonthKakao.put(i, list2);
            }
            monthTotalAmount.put(i, (monthTotalAmount.get(i) + totalAmount));

        }

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

        for (int i = 0; i < lister.size(); i++) {
            List<StatVO> test = statMonthInicis.get(lister.get(i).get(1));
            test.remove((int)lister.get(i).get(2));
            statMonthInicis.put((int)lister.get(i).get(1), test);
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
}
