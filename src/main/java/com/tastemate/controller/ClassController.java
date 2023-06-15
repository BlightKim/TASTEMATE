package com.tastemate.controller;

import com.tastemate.domain.BookmarkVO;
import com.tastemate.domain.MemberMbti;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.StarVO;
import com.tastemate.mapper.BookmarkMapper;
import com.tastemate.mapper.MemberMapper;
import com.tastemate.service.BookmarkService;
import com.tastemate.service.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/class/*")
public class ClassController {

    @Autowired
    private ClassService classService;




    @GetMapping("/get")
    public String get_class(Model model, HttpServletRequest request, HttpSession session) {
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        String userMbti = memberVO.getUserMbti();
        MemberMbti memberMbti = MemberMbti.valueOf(userMbti);
        int userClass = memberVO.getUserClass();

        //List<MemberVO> classList = classService.getClassList(userClass);
        //System.out.println("classList = " + classList);
        //model.addAttribute("classList", classList);
        List<MemberVO> userStar = classService.user_getWithStar(userClass);
        //List<Map<Integer, Integer>> matchNumber = new ArrayList<>();
        Map<Integer, Integer> idxAndMatch = new HashMap<>();
        System.out.println("userStar.size() = " + userStar.size());
        for (int i = 0; i < userStar.size(); i++) {
            idxAndMatch.put(userStar.get(i).getUserIdx(), memberMbti.matchMbti(userMbti, userStar.get(i).getUserMbti()));
        }
        System.out.println(idxAndMatch.toString());

        List<MemberVO> userStar3 = sortBySelectionSort(userStar, memberVO.getUserLikeFood(), memberVO);
        List<MemberVO> userStar2 = sortBySelectionSort(userStar3, idxAndMatch);



        model.addAttribute("userStar", userStar2);
        model.addAttribute("idxAndMatch", idxAndMatch);


        //redirect.addAttribute("userClass", userClass);

        return "/class/myClass";
    }

    // 멤버 리스트를 정렬해주는 메소드
    public static void swap(List<MemberVO> userStar, int idx1, int idx2) {
        MemberVO tmp = userStar.get(idx1);
        userStar.set(idx1, userStar.get(idx2));
        userStar.set(idx2, tmp);
    }
    public static List<MemberVO> sortBySelectionSort(List<MemberVO> userStar, Map<Integer, Integer> idxAndMatch) {
        for (int i = 0; i < userStar.size() - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < userStar.size(); j++) {
                if (idxAndMatch.get(userStar.get(j).getUserIdx()) > idxAndMatch.get(userStar.get(minIdx).getUserIdx())) {
                    minIdx = j;
                }
            }
            swap(userStar, i, minIdx);
        }
        return userStar;
    }

    public static List<MemberVO> sortBySelectionSort(List<MemberVO> userStar, String userLikeFood, MemberVO memberVO) {
        for (int i = 0; i < userStar.size() - 1; i++) {
            int minIdx = i;
            for (int j = i + 1; j < userStar.size(); j++) {
                if (memberVO.getUserLikeFood().equals(userStar.get(j).getUserLikeFood())) {
                    minIdx = j;
                }
            }
            swap(userStar, i, minIdx);
        }
        return userStar;
    }
}
