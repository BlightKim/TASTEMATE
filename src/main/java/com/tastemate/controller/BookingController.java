package com.tastemate.controller;

import com.tastemate.domain.BookingVO;
import com.tastemate.domain.BookmarkVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.domain.StoreVO;
import com.tastemate.service.BookingService;
import com.tastemate.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Controller
@RequestMapping("/booking/*")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private StoreService storeService;

    @RequestMapping("/bookingList")
    public String getList(Model model) {
        List<BookingVO> bookingList = bookingService.bookingList();
        System.out.println("bookingList = " + bookingList);
        model.addAttribute("bookingList", bookingList);

        return "/booking/bookingList";
    }

    @GetMapping ("/bookingInsert")
    public void booking(Model model, HttpServletRequest request, HttpSession session, int storeIdx) {
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        String userId = memberVO.getUserId();
        String userName = memberVO.getUserName();;
        StoreVO storeVO = storeService.store_get(storeIdx);
        System.out.println("storeVO = " + storeVO);
        model.addAttribute("storeVO", storeVO);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
    }
    //ajax 예약 날짜별 가능 시간
    @RequestMapping(value = "/insertAjax.do", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> insert(Model model, HttpSession session, HttpServletRequest request,
                                      @RequestParam("storeIdx") int storeIdx,
                                      @RequestParam("selectedDate") String selectedDate) {
        //,
        //                                      @RequestParam("selectedTime") String selectedTime
        System.out.println("model = " + model);
        System.out.println("storeIdx : " + storeIdx);
        System.out.println("selectedDate = " + selectedDate);
        //System.out.println("selectedTime = " + selectedTime);
        BookingVO bookingVO = new BookingVO();
        //StoreVO storeVO = storeService.store_get(Integer.parseInt(((StoreVO) model.getAttribute("storeVO")).getStoreIdx()));
        StoreVO storeVO = storeService.store_get(storeIdx);
        bookingVO.setStoreIdx(storeIdx);
        bookingVO.setStoreName(storeVO.getStoreName());
        Map<Integer, String> timeMap = new HashMap<>();
        timeMap.put(1, "12:30:00");
        timeMap.put(2, "12:45:00");
        timeMap.put(3, "13:00:00");
        timeMap.put(4, "13:15:00");
        timeMap.put(5, "13:30:00");
        timeMap.put(6, "13:45:00");
        timeMap.put(7, "14:00:00");

        //로그인 정보 받아오기
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        System.out.println("memberVO = " + memberVO);
        Map<String, String> msg = new HashMap<String, String>();

            int userIdx = memberVO.getUserIdx();
            String userId = memberVO.getUserId();
            String userName = memberVO.getUserName();

            bookingVO.setUserIdx(userIdx);
            bookingVO.setUserId(userId);
            bookingVO.setUserName(userName);


            Map<String, Integer> resultMap = new HashMap<>();
            int result = 0;
            for (int j = 1; j < 8; j++) {

                String dateStr = selectedDate + " " + timeMap.get(j);
                System.out.println("dateStr = " + dateStr);
                bookingVO.setBookingTime(dateStr);
                result = 0;
                for (int i = 1; i <4; i++ ) {
                    bookingVO.setTableNo(i);
                    System.out.println("bookingVO : " + bookingVO);
                    result += bookingService.bookingTableCheck(bookingVO);
                }
                System.out.println("result = " + result);
                switch (j) {
                    case 1 : resultMap.put("a", result);
                    case 2 : resultMap.put("b", result);
                    case 3 : resultMap.put("c", result);
                    case 4 : resultMap.put("d", result);
                    case 5 : resultMap.put("e", result);
                    case 6 : resultMap.put("f", result);
                    case 7 : resultMap.put("g", result);
                }

            }

            System.out.println("resultMap = " + resultMap.toString());

            return resultMap;

    }

    //ajax 예약 날짜별 가능 시간
    @RequestMapping(value = "/insert_Ajax.do", produces = "application/json; charset=UTF-8", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Integer> insertAjax (Model model, HttpSession session, HttpServletRequest request,
                                       @RequestParam("storeIdx") int storeIdx,
                                       @RequestParam("selectedDate") String selectedDate,
                                       @RequestParam("selectedTime") String selectedTime) {

        System.out.println("model = " + model);
        System.out.println("storeIdx : " + storeIdx);
        System.out.println("selectedDate = " + selectedDate);
        //System.out.println("selectedTime = " + selectedTime);
        BookingVO bookingVO = new BookingVO();
        //StoreVO storeVO = storeService.store_get(Integer.parseInt(((StoreVO) model.getAttribute("storeVO")).getStoreIdx()));
        StoreVO storeVO = storeService.store_get(storeIdx);
        bookingVO.setStoreIdx(storeIdx);
        bookingVO.setStoreName(storeVO.getStoreName());

        //로그인 정보 받아오기
        MemberVO memberVO = (MemberVO) session.getAttribute("vo");
        System.out.println("memberVO = " + memberVO);
        Map<String, String> msg = new HashMap<String, String>();

        int userIdx = memberVO.getUserIdx();
        String userId = memberVO.getUserId();
        String userName = memberVO.getUserName();

        bookingVO.setUserIdx(userIdx);
        bookingVO.setUserId(userId);
        bookingVO.setUserName(userName);


        Map<String, Integer> resultMap = new HashMap<>();
        int result = 0;

        String dateStr = selectedDate + " " + selectedTime;
        System.out.println("dateStr = " + dateStr);
        bookingVO.setBookingTime(dateStr);

        for (int i = 1; i < 4; i++ ) {
            bookingVO.setTableNo(i);
            System.out.println("bookingVO : " + bookingVO);
            result = bookingService.bookingTableCheck(bookingVO);

            System.out.println("result = " + result);
            switch (i) {
                case 1 : resultMap.put("a", result);
                case 2 : resultMap.put("b", result);
                case 3 : resultMap.put("c", result);
            }

        }

        System.out.println("resultMap = " + resultMap.toString());

        return resultMap;

    }

    @PostMapping("/bookingInsert")

    public String bookingInsert(Model model, BookingVO bookingVO, RedirectAttributes redirect,
                                @RequestParam String category1, @RequestParam String tableNo,
                                @RequestParam String selectDate, @RequestParam String time, @RequestParam String onsitePayment) {
        System.out.println("date = " + selectDate);
        System.out.println("time = " + time);
        // 문자열
        String dateStr = selectDate + " " + time;
        System.out.println("dateStr = " + dateStr);


        bookingVO.setBookingTime(dateStr);
        System.out.println("bookingVO.getBookingTime() = " + bookingVO.getBookingTime());

        System.out.println("category1 = " + category1);
        String[] info = category1.split("/");
        bookingVO.setMenuIdx(Integer.parseInt(info[0]));
        bookingVO.setFoodName(info[1]);
        bookingVO.setPrice(Integer.parseInt(info[2]));
        bookingVO.setTableNo(Integer.parseInt(tableNo));
        bookingVO.setOnsitePayment(Integer.parseInt(onsitePayment));



        System.out.println("bookingVO = " + bookingVO);


        int result = bookingService.bookingToPay(bookingVO);


        redirect.addAttribute("bookingIdx", bookingVO.getBookingIdx());
        System.out.println("result = " + result);
        return "redirect:/booking/bookingToPay";
    }

    @GetMapping("/bookingToPay")
    public String bookingToPay(Model model, HttpServletRequest request, HttpSession session, @RequestParam int bookingIdx) {
        BookingVO bookingVO = bookingService.bookingToPayShow(bookingIdx);
        StoreVO storeVO = storeService.store_get(bookingVO.getStoreIdx());
        model.addAttribute("bookingIdx",bookingIdx);
        model.addAttribute("bookingVO",bookingVO);
        model.addAttribute("storeVO", storeVO);
        if (bookingVO.getOnsitePayment() == 1) {
            return "booking/bookingToPayOnsite";
        } else {
            return "booking/bookingToPay";
        }
    }


}
