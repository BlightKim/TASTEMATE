package com.tastemate.controller;

import com.tastemate.domain.ManageMenuVO;
import com.tastemate.domain.ManageStarVO;
import com.tastemate.domain.ManageStoreVO;
import com.tastemate.domain.ManageMemberVO;
import com.tastemate.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/manage/*")
@Slf4j
public class ManageController {

    @Autowired
    private ManageService service;

    //메인 페이지(관리자)
    @GetMapping("main")
    public String manageMain(Model model, RedirectAttributes redirectAttributes) {
        String title = "관리자 페이지";
        model.addAttribute("title", title);

       return "/manage/main";
    }



    //임시(시험용)
//    @GetMapping("test")
//    public String managePre(){ return "/manage/test"; }



    //맛집 리스트(관리자)
    @GetMapping("storeList")
    public String manageStoreList(Model model) {
        List<ManageStoreVO> manageStoreList = service.manageStoreListGet();
        model.addAttribute("manageStoreList", manageStoreList);

        String title = "관리자 추천";
        model.addAttribute("title", title);
        return "/manage/storeList";
    }

    //맛집 등록 페이지(관리자)
    @GetMapping("storeSaveView")
    public String manageRegister(){
        return "/manage/storeSaveView";
    }

    //맛집등록(관리자)
    @PostMapping("storeSave")
    public String storeSave(ManageStoreVO manageStoreVO, MultipartFile oriFilename) {
        service.manageStoreSave(manageStoreVO, oriFilename);
        return "redirect:/manage/storeList";
    }

    //맛집 상세보기(관리자)
    @RequestMapping("storeView")
    public String storeView(Model model, ManageStoreVO manageStoreVO) {
        ManageStoreVO storeView = service.manageStoreView(manageStoreVO);
        model.addAttribute("storeView", storeView);

        //리뷰 조회
        //selectUserReview(model, manageStarVO);
        return "/manage/storeView";
    }

    //맛집 상세보기(관리자) - 수정페이지
//    @GetMapping("storeUpdateView")
    @PostMapping("storeUpdateView")
    public String storeUpdateView(Model model, ManageStoreVO manageStoreVO){
        //맛집 정보 불러오기
        ManageStoreVO storeUpdateView = service.manageStoreView(manageStoreVO);
        model.addAttribute("storeUpdateView", storeUpdateView);
        return "/manage/storeUpdateView";
    }

    //맛집 삭제(관리자)
//    @GetMapping("deleteManageStore")
    @PostMapping("deleteManageStore")
    public String deleteManageStore(ManageStoreVO manageStoreVO) {
        service.deleteManageStore(manageStoreVO);
        return "redirect:/manage/storeList";
    }

    //맛집 수정(관리자)
    @PostMapping("updateManageStore")
    public String updateManageStore(ManageStoreVO manageStoreVO, MultipartFile oriFilename) {
        service.updateManageStore(manageStoreVO, oriFilename);
        return "redirect:/manage/storeList";
    }



    //맛집 리스트(승인 대기)
    @RequestMapping("storeRegNotList")
    public String storeRegNotList(Model model, ManageStoreVO manageStoreVO) {
        List<ManageStoreVO> storeRegNotList = service.storeRegNotList(manageStoreVO);
        model.addAttribute("storeRegList", storeRegNotList);

        String title = "승인 대기";
        model.addAttribute("title", title);
        return "/manage/storeRegNotList";
    }

    //맛집 리스트(승인)
    @RequestMapping("storeRegOkList")
    public String storeRegOkList(Model model, ManageStoreVO manageStoreVO) {
        List<ManageStoreVO> storeRegOkList = service.storeRegOkList(manageStoreVO);
        model.addAttribute("storeRegList", storeRegOkList);

        String title = "승인 완료";
        model.addAttribute("title", title);
        return "/manage/storeRegOkList";
    }

    //맛집 상세보기(승인 대기)
    @GetMapping("storeRegNotView")
    public String storeRegNotView (Model model, ManageStoreVO manageStoreVO) {
        ManageStoreVO storeRegNotView = service.manageStoreView(manageStoreVO);
        model.addAttribute("storeRegView", storeRegNotView);
        return "/manage/storeRegNotView";
    }

    //맛집 상세보기(승인 대기) - 수정페이지
    @PostMapping("storeRegNotUpdateView")
    public String storeRegNotUpdateView(Model model, ManageStoreVO manageStoreVO) {
        ManageStoreVO storeRegNotUpdateView = service.manageStoreView(manageStoreVO);
        model.addAttribute("storeUpdateView", storeRegNotUpdateView);
        return "/manage/storeRegNotUpdateView";
    }

    //맛집 상세보기(승인)
    @RequestMapping("storeRegOkView")
    public String storeRegOkView (Model model, ManageStoreVO manageStoreVO, ManageStarVO manageStarVO) {
        ManageStoreVO storeRegOkView = service.manageStoreView(manageStoreVO);
        model.addAttribute("storeRegView", storeRegOkView);

        //리뷰 조회
        selectUserReview(model, manageStarVO);
        return "/manage/storeRegOkView";
    }

    //맛집 상세보기(승인) - 수정페이지
    @PostMapping("storeRegOkUpdateView")
    public String storeRegOkUpdateView (Model model, ManageStoreVO manageStoreVO) {
        ManageStoreVO storeRegOkView = service.manageStoreView(manageStoreVO);
        model.addAttribute("storeUpdateView", storeRegOkView);
        return "/manage/storeRegOkUpdateView";
    }

    //맛집 승인
    @PostMapping("storeRegUpdateOK")
    public String storeRegUpdateOK(ManageStoreVO manageStoreVO) {
        service.storeRegUpdateOK(manageStoreVO);
        return "redirect:/manage/storeRegNotList";
    }

    //맛집 수정 후 승인
    @PostMapping("storeRegUpdate")
    public String storeRegUpdate(ManageStoreVO manageStoreVO, MultipartFile oriFilename) {
        service.updateManageStore(manageStoreVO, oriFilename);
        service.storeRegUpdateOK(manageStoreVO);
        return "redirect:/manage/storeRegNotList";
    }

    //맛집 승인 대기(취소)
    @PostMapping("storeRegUpdateNot")
    public String storeRegUpdateNot(ManageStoreVO manageStoreVO) {
        service.storeRegUpdateNot(manageStoreVO);
        return "redirect:/manage/storeRegOkList";
    }

    //승인 맛집 정보수정
    @PostMapping("storeRegOkUpdate")
    public String storeRegOkUpdate(ManageStoreVO manageStoreVO, MultipartFile oriFilename) {
        service.updateManageStore(manageStoreVO, oriFilename);
        return "redirect:/manage/storeRegOkList";
    }



    //맛집 리스트(승인 - 메뉴)
    @RequestMapping("storeRegOkListMenu")
    public String storeRegOkListMenu(Model model, ManageStoreVO manageStoreVO) {
        List<ManageStoreVO> storeRegOkList = service.storeRegOkList(manageStoreVO);
        model.addAttribute("storeRegList", storeRegOkList);

        String title = "맛집 메뉴";
        model.addAttribute("title", title);
        return "/manage/storeRegOkListMenu";
    }

    //맛집 메뉴보기 - 상세 페이지
    @RequestMapping("storeMenuView")
    public String storeMenuView(Model model, ManageMenuVO manageMenuVO, ManageStoreVO manageStoreVO) {
        //맛집 상세보기
        ManageStoreVO storeRegOkView = service.manageStoreView(manageStoreVO);
        model.addAttribute("storeRegView", storeRegOkView);

        //맛집 메뉴보기
        List<ManageMenuVO> storeMenuView = service.storeMenuView(manageMenuVO);
        model.addAttribute("storeMenuView", storeMenuView);
        return "/manage/storeMenuView";
    }

    //메뉴 추가 페이지
    @PostMapping("storeMenuAddView")
    public String storeMenuAddView(Model model, ManageStoreVO manageStoreVO){
        model.addAttribute("storeIdx", manageStoreVO.getStoreIdx());
        return "/manage/storeMenuAddView";
    }

    //메뉴 수정 페이지
    @PostMapping("storeMenuUpdateView")
    public String storeMenuUpdateView(Model model, ManageMenuVO manageMenuVO, ManageStoreVO manageStoreVO) {
        //메뉴 조회(1개)
        ManageMenuVO storeMenuUpdateView = service.storeMenuUpdateView(manageMenuVO);

        model.addAttribute("storeIdx", manageStoreVO.getStoreIdx());
        model.addAttribute("storeMenuUpdateView", storeMenuUpdateView);
        return "/manage/storeMenuUpdateView";
    }

    //메뉴 추가 등록
    @PostMapping("maAddMenu")
    public String maAddMenu(Model model, ManageMenuVO manageMenuVO, ManageStoreVO manageStoreVO) {
        service.maAddMenu(manageMenuVO);
        storeMenuView(model, manageMenuVO, manageStoreVO);
        return "/manage/storeMenuView";
    }

    //메뉴 수정
    @PostMapping("maUpdateMenu")
    public String maUpdateMenu(Model model, ManageMenuVO manageMenuVO, ManageStoreVO manageStoreVO) {
        service.maUpdateMenu(manageMenuVO);
        storeMenuView(model, manageMenuVO, manageStoreVO);
        return "/manage/storeMenuView";
    }

    //메뉴 삭제
    //@RequestMapping("maDeleteMenu")
    //@GetMapping("maDeleteMenu")
    @PostMapping("maDeleteMenu")
    public String maDeleteMenu(ManageMenuVO manageMenuVO, RedirectAttributes redirectAttributes) {
        int result = service.maDeleteMenu(manageMenuVO);

        log.info(String.valueOf(result));
        log.info(String.valueOf(manageMenuVO));
        //storeMenuView(model, manageMenuVO, manageStoreVO);
        //return "/manage/storeMenuView";

        String wow = "complete";
        redirectAttributes.addFlashAttribute("message", wow);
        return "redirect:/manage/storeRegOkListMenu";
    }



    //리뷰 조회
    public String selectUserReview(Model model, ManageStarVO manageStarVO) {
        List<ManageStarVO> manageStarVOList = service.selectUserReview(manageStarVO);
        model.addAttribute("manageStarVOList", manageStarVOList);
        return "/manage/storeView";
    }

    //리뷰 상세보기 - 수정 페이지
    @PostMapping("viewUserReview")
    public String viewUserReview(Model model, ManageStarVO manageStarVO) {
        //리뷰 정보 불러오기
        ManageStarVO viewUserReview = service.viewUserReview(manageStarVO);
        model.addAttribute("viewUserReview", viewUserReview);
        return "/manage/viewUserReview";
    }

    //리뷰 수정
    @PostMapping("updateUserReview")
    public String updateUserReview(Model model, ManageStarVO manageStarVO, ManageStoreVO manageStoreVO) {
        int result = service.updateUserReview(manageStarVO);

        //리뷰 상세
        storeReviewList(model, manageStarVO, manageStoreVO);
        return "/manage/storeReview";
    }

    //리뷰 삭제
//    @GetMapping("deleteUserReview")
    @PostMapping("deleteUserReview")
    public String deleteUserReview(ManageStarVO manageStarVO,
       RedirectAttributes redirectAttributes) {

        int result = service.deleteUserReview(manageStarVO);
        log.info(String.valueOf(manageStarVO));

        String wow = "complete";
        redirectAttributes.addFlashAttribute("message", wow);
        return "redirect:/manage/storeReviewList";
    }

    //리뷰 상세
    @RequestMapping("storeReview")
    public String storeReviewList(Model model, ManageStarVO manageStarVO, ManageStoreVO manageStoreVO) {
        //맛집 상세보기
        ManageStoreVO storeRegOkView = service.manageStoreView(manageStoreVO);
        model.addAttribute("storeRegView", storeRegOkView);

        //리뷰 조회
        selectUserReview(model, manageStarVO);
        return "/manage/storeReview";
    }

    //리뷰 리스트
    @GetMapping("storeReviewList")
    public String storeReviewList(Model model, ManageStoreVO manageStoreVO) {
        List<ManageStoreVO> storeRegOkList = service.storeRegOkList(manageStoreVO);
        model.addAttribute("storeRegOkList", storeRegOkList);

        String title = "맛집 평가";
        model.addAttribute("title", title);

        return "/manage/storeReviewList";
    }

//    public String storeReview (Model model, ManageStoreVO manageStoreVO, ManageStarVO manageStarVO) {
//        ManageStoreVO storeRegOkView = service.manageStoreView(manageStoreVO);
//        model.addAttribute("storeRegView", storeRegOkView);
//
//        //리뷰 조회
//        selectUserReview(model, manageStarVO);
//        return "/manage/storeRegOkView";
//    }



    //클래스 리스트(관리자)
    @GetMapping("classList")
    public String manageClassList(){
        return "/manage/classList";
    }

    //클래스별 수강생 찾기
    @GetMapping("findClass")
    public String findClass(Model model, ManageMemberVO memberVO) {
        List<ManageMemberVO> memberVOList = service.manageFindClass(memberVO);
        model.addAttribute("memberVOList", memberVOList);
        model.addAttribute("userClass", memberVO.getUserClass());
        return "/manage/classMemberList";
    }



    //수강생 정보 조회
    @GetMapping("userInfo")
    public String userInfo(Model model, ManageMemberVO memberVO){
        ManageMemberVO userInfo = service.userInfo(memberVO);
        model.addAttribute("memberVO", userInfo);
        return "/manage/classMember";
    }

    //수강생 계정 활성
    @GetMapping("userInfoActive")
    public String userInfoActive(ManageMemberVO memberVO){
        int result = service.userInfoActive(memberVO);
        return "redirect:/manage/userInfo?userIdx=" + memberVO.getUserIdx();
    }

    //수강생 계정 비활성
    @GetMapping("userInfoInactive")
    public String userInfoInactive(Model model, ManageMemberVO memberVO){
        int result = service.userInfoInactive(memberVO);

        return "redirect:/manage/userInfo?userIdx=" + memberVO.getUserIdx();
    }

    //수강생 계정 강퇴
    @GetMapping("deleteUserInfo")
    public String deleteUserInfo(Model model, ManageMemberVO memberVO) {
        int result = service.deleteUserInfo(memberVO);
        return "redirect:/manage/userInfo?userIdx=" + memberVO.getUserIdx();
    }

}
