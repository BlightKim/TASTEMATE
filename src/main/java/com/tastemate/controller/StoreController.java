package com.tastemate.controller;

import com.tastemate.domain.MemberVO;
import com.tastemate.domain.StarVO;
import com.tastemate.domain.StoreVO;
import com.tastemate.domain.paging.Criteria;
import com.tastemate.domain.paging.PageDTO;
import com.tastemate.service.BookmarkService;
import com.tastemate.mapper.MemberMapper;
import com.tastemate.service.StoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
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

    @GetMapping("/starComment")
    public void starComment(String storeIdx, Model model){

        log.info("storeIdx : "+ storeIdx);
        model.addAttribute("storeIdx",storeIdx);

    }

    @PostMapping("/starComment")
    public String starCommentStarVO(StarVO starVO){
        log.info("starVO : " + starVO);

        int result = service.store_starComment(starVO);
        log.info("starVO result : " + result);



        return "redirect:/store/main";  //추후 수정 필요!
    }

    @GetMapping("/main")
    public void main(Model model){
        StoreVO storeVO = service.getStoreHighestStar();
        model.addAttribute("storeVO", storeVO);
    }



}
