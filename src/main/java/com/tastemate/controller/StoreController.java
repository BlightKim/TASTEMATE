package com.tastemate.controller;

import com.tastemate.domain.*;
import com.tastemate.domain.board.BoardVO;
import com.tastemate.domain.paging.Criteria;
import com.tastemate.domain.paging.PageDTO;
import com.tastemate.service.*;
import com.tastemate.mapper.MemberMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/store/*")
@Slf4j
public class StoreController {

    @Autowired
    private StoreService service;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private StatService statService;



    @GetMapping("/list")
    public void get(Model model
             , @RequestParam(value="cuisineSelect",required = false) String cuisineSelect
             , @RequestParam(value="storeStar",required = false) String storeStar
             , @RequestParam(value="storeCount",required = false) String storeCount
             , @RequestParam(value="storeDistance",required = false) String storeDistance
             , Criteria cri
    ){

        if(cuisineSelect == null || cuisineSelect == ""){
            cuisineSelect = "none";
        }

        if(storeStar == null || storeStar == ""){
            storeStar = "none";
        }

        if(storeCount == null || storeCount == ""){
            storeCount = "none";
        }

        if(storeDistance == null || storeDistance == ""){
            storeDistance = "none";
        }

        if(cri.getKeyword() == null || cri.getKeyword() == ""){
            cri.setKeyword("none");
        }


        Map<String,Object> orderMap = new HashMap<>();
        orderMap.put("cuisineSelect", cuisineSelect);
        orderMap.put("storeStar", storeStar);
        orderMap.put("storeCount", storeCount);
        orderMap.put("storeDistance", storeDistance);
        orderMap.put("cri", cri);


        log.info("orderMap : " + orderMap);

        List<StoreVO> storeVO = service.store_getList(orderMap);
        model.addAttribute("storeList", storeVO);

        /*페이징*/
        int total = service.store_totalCnt(orderMap);
        PageDTO pageMaker = new PageDTO(cri, total);
        model.addAttribute("pageMaker", pageMaker);

        log.info("total : " + total);
        log.info("new PageDTO(cri, total) : " + pageMaker);

    }

    @GetMapping({"/get", "/update"})
    public void get(int storeIdx, Model model, HttpSession session){
        log.info("get 또는 update storeIdx :" + storeIdx);

        StoreVO storeVO = service.store_get(storeIdx);
        StoreVO storeVO1 = service.store_getWithStar(storeIdx);
        StoreVO storeVO2 = service.store_getWithComment(storeIdx);

        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        System.out.println("memberVO = " + memberVO);
        int bookmarkValidate = 0;
        if (memberVO != null ) {
            String userId = memberVO.getUserId();
            bookmarkValidate = bookmarkService.bookmarkValidate(userId, storeIdx);
        }



        model.addAttribute("storeVO", storeVO);
        model.addAttribute("storeVO_star", storeVO1);
        model.addAttribute("storeVO_comment", storeVO2);
        model.addAttribute("bookmarkValidate", bookmarkValidate);

        /*회원정보*/
        MemberVO member = memberMapper.findUserByUserIdx(Integer.parseInt(storeVO.getUserIdx()));
        model.addAttribute("storeVO_member", member);

    }

    @GetMapping("/register")
    public void register(){

    }

    @PostMapping("/register")
    public String registerStoreVO(StoreVO storeVO, MultipartFile oriFilename, RedirectAttributes rttr){

        service.saveFile(storeVO, oriFilename);

        String wow = "complete";
        rttr.addFlashAttribute("message", wow);

        return "redirect:/store/list";
    }



    @PostMapping("/update")
    public String updateStoreVO(StoreVO storeVO, MultipartFile oriFilename, RedirectAttributes rttr){

        log.info("Controller storeVO : " + storeVO);
        service.updateFile(storeVO, oriFilename);

        String wow = "complete";
        rttr.addFlashAttribute("message", wow);


        return "redirect:/store/list";
    }

    @GetMapping("/delete")
    public String delete(int storeIdx){

        log.info("delete storeIdx: " + storeIdx) ;
        int result = service.store_delete(storeIdx);
        log.info("delete 완료 : " + result) ;

        return "redirect:/store/list";
    }


    @RequestMapping(value = "/starComment", produces = "application/json; charset=UTF-8", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> starComment(Model model, HttpSession session, HttpServletRequest request,
                            @RequestParam("bookingIdx") int bookingIdx,
                            @RequestParam("nowDate") String nowDate,
                            @RequestParam("nowTime") String nowTime){

        BookingVO bookingVO = bookingService.bookingToPayShow(bookingIdx);
        System.out.println("bookingVO = " + bookingVO);
        String bookingTime = bookingVO.getBookingTime();
        System.out.println("bookingTime = " + bookingTime);
        System.out.println("nowDate = " + nowDate);
        System.out.println("nowTime = " + nowTime);

        String[] bookingT = bookingTime.split(" ");
        int result = 0;
        for (int i = 0; i < bookingT.length; i++) {
            if (i == 0) {
                String[] str2 = bookingT[i].split("-");
                String[] str3 = nowDate.split("-");
                System.out.println("str2[0] = " + str2[0]);
                System.out.println("str3[0] = " + str3[0]);

                System.out.println("str2[1] = " + str2[1]);
                System.out.println("str3[1] = " + str3[1]);

                System.out.println("str2[2] = " + str2[2]);
                System.out.println("str3[2] = " + str3[2]);
                if (Integer.parseInt(str2[0]) == Integer.parseInt(str3[0]) &&
                    Integer.parseInt(str2[1]) == Integer.parseInt(str3[1]) &&
                    Integer.parseInt(str2[2]) == Integer.parseInt(str3[2]) ) {
                    result++;
                } else if (Integer.parseInt(str2[0]) < Integer.parseInt(str3[0]) ||
                        Integer.parseInt(str2[1]) < Integer.parseInt(str3[1]) ||
                        Integer.parseInt(str2[2]) < Integer.parseInt(str3[2])) {
                    result = 2;
                }
            }
            if (i == 1) {
                String[] str4 = bookingT[i].split(":");
                String[] str5 = nowTime.split(":");

                System.out.println("str4[0] = " + str4[0]);
                System.out.println("str5[0] = " + str5[0]);

                System.out.println("str4[1] = " + str4[1]);
                System.out.println("str5[1] = " + str5[1]);

                System.out.println("str4[2] = " + str4[2]);
                System.out.println("str5[2] = " + str5[2]);
                if (Integer.parseInt(str4[0]) <= Integer.parseInt(str5[0]) &&
                        Integer.parseInt(str4[1]) <= Integer.parseInt(str5[1]) &&
                        Integer.parseInt(str4[2]) <= Integer.parseInt(str5[2]) ) {
                    result++;
                }
            }
        }

        System.out.println("result = " + result);

        Map<String, Integer> resultMap = new HashMap<String, Integer>();
        resultMap.put("result", result);

        return resultMap;
    }

    @GetMapping("/starComment")
    public void starComment(String storeIdx, String inicisIdx,
                            String kakaoApprovalIdx, Model model, int bookingIdx
                            , HttpSession session){


        /* 상대방 별점주기 위해 추가 */
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        log.info("memberVO : " + memberVO);

        int roomIdx = memberMapper.findRoomIdx(memberVO.getUserIdx());
        log.info("roomIdx : " + roomIdx);

        int matchingUserIdx = memberMapper.findMatchingUserIdx(roomIdx, memberVO.getUserIdx());
        log.info("matchingUserIdx : " + matchingUserIdx);



        log.info("storeIdx : "+ storeIdx);
        log.info("inicisIdx : "+ inicisIdx);
        log.info("kakaoApprovalIdx : "+ kakaoApprovalIdx);

        model.addAttribute("storeIdx",storeIdx);
        model.addAttribute("inicisIdx",inicisIdx);
        model.addAttribute("bookingIdx", bookingIdx);
        model.addAttribute("kakaoApprovalIdx",kakaoApprovalIdx);
        model.addAttribute("matchingUserIdx",matchingUserIdx);

    }

    @PostMapping("/starComment")
    public String starCommentStarVO(StarVO starVO, int bookingIdx){
        log.info("starVO : " + starVO);

        int result = service.store_starComment(starVO);
        log.info("starVO result : " + result);

        // 별점 완료하면 바뀔 것들 (결제 / 예약 / roomIdx)
        // 결제 status 2로 변경
        if (starVO.getInicisIdx() != 0){
            //inicisIdx 이용해서 INICIS 테이블 status 변경하기
            int inicisUpdate = paymentService.updateStatus(starVO.getInicisIdx());
            log.info("inicis update : " + inicisUpdate);

        } else if (starVO.getKakaoApprovalIdx() != 0){
            //getKakaoApprovalIdx 이용해서 KAKAOPAYAPPROVAL status 변경하기
            int kakaoUpdate = paymentService.updateStatus2(starVO.getKakaoApprovalIdx());
            log.info("kakao update : " + kakaoUpdate);

        }


        // 예약 status 변경
        bookingService.bookingPayAndStarComplete(bookingIdx);

        // roomIdx 변경



        return "redirect:/store/main";
    }

    @GetMapping("/main")
    public void main(Model model){
        StoreVO storeVO = service.getStoreHighestStar();
        List<BoardVO> adminBoard = statService.adminBoard();
        List<BoardVO> bestBoard = statService.bestBoard();

        model.addAttribute("bestBoard", bestBoard);
        model.addAttribute("adminBoard", adminBoard);
        model.addAttribute("storeVO", storeVO);
    }



    @RequestMapping(value = "/payCheckAjax" , produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> payCheckAjax(HttpSession session, String storeIdx){

        log.info("payCheckAjax Controller 도착");
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        log.info("payCheckAjax memberVO 확인 : " + memberVO);
        log.info("payCheckAjax storeIdx 확인 : " + storeIdx);


        Map<String, Integer> resultMap = new HashMap<>();

        resultMap.put("userIdx", memberVO.getUserIdx());
        resultMap.put("storeIdx", Integer.parseInt(storeIdx));

        log.info("payCheckAjax resultMap 확인 : " + resultMap);

        // 확인
        InicisVO inicisVO = paymentService.findInicis(memberVO.getUserIdx());
        KakaoPayApprovalVO kakaoPayApprovalVO = paymentService.findKakao(memberVO.getUserIdx());

        log.info("inicisVO 확인 : " + inicisVO);
        log.info("kakaoPayApprovalVO 확인 : " + kakaoPayApprovalVO);

        if (inicisVO != null){
            log.info("inicisSuccess 값 확인");
            resultMap.put("inicis", 1);

            return resultMap;

        } else if (kakaoPayApprovalVO != null){
            log.info("mykakaoPaySuccess 값 확인");
            resultMap.put("kakao", 1);
            return resultMap;
        }


        return resultMap;
    }



}
