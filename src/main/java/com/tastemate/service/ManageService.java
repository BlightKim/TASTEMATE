package com.tastemate.service;

import com.tastemate.domain.ManageStarVO;
import com.tastemate.domain.ManageStoreVO;
import com.tastemate.domain.MemberVO;
import com.tastemate.mapper.ManageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class ManageService {

    @Autowired private ManageMapper mapper;

    //맛집 리스트(관리자)
    public List<ManageStoreVO> manageStoreListGet(){
        List<ManageStoreVO> manageStoreList = mapper.manageStoreListGet();
        return manageStoreList;
    }

    //맛집등록(관리자)
    public void manageStoreSave(ManageStoreVO manageStoreVO, MultipartFile multipartFile) {

        String savedName = "";

        if (!multipartFile.isEmpty()) {
            String oriFilename = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String extension = oriFilename.substring(oriFilename.lastIndexOf("."));

            oriFilename = oriFilename.substring(oriFilename.lastIndexOf("\\") + 1);

            savedName = uuid + "_" + oriFilename;

//        String savedPath = fileDir + "/" +savedName;
            String savedPath = "C:/upload/" + savedName;

            File saveFile = new File(savedPath);

            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
            }
        }

        ManageStoreVO storeVO = new ManageStoreVO();

        storeVO.setUserIdx(manageStoreVO.getUserIdx());
        storeVO.setStoreName(manageStoreVO.getStoreName());
        storeVO.setCategory1(manageStoreVO.getCategory1());
        storeVO.setStoreAddress(manageStoreVO.getStoreAddress());
        storeVO.setStoreLati(manageStoreVO.getStoreLati());
        storeVO.setStoreLongi(manageStoreVO.getStoreLongi());
        storeVO.setPhoneNumber(manageStoreVO.getPhoneNumber());
        storeVO.setStoreCount(manageStoreVO.getStoreCount());
        storeVO.setStoreStat(manageStoreVO.getStoreStat());

        if(multipartFile.isEmpty()){
            storeVO.setFilename("tastemate.jpg");
        } else {
            storeVO.setFilename(savedName);
        }

        int result = mapper.manageSaveStore(storeVO);
    }

    //맛집 상세보기(관리자)
    public ManageStoreVO manageStoreView(ManageStoreVO manageStoreVO) {
        ManageStoreVO storeInfo = mapper.manageStoreView(manageStoreVO);
        return storeInfo;
    }

    //맛집 삭제(관리자)
    public int deleteManageStore(ManageStoreVO manageStoreVO) {

//        ManageStoreVO deleteInfo = mapper.manageStoreView(manageStoreVO);
//
//        String filePath = "C:/upload/" + deleteInfo.getFilename();
//        File file = new File(filePath);
//
//        if (file.exists() && !manageStoreVO.getFilename().equals("tastemate.jpg")){
//            file.delete();
//        }
        int result = mapper.deleteManageStore(manageStoreVO);
        return result;
    }

    //맛집 수정(관리자)
    public void updateManageStore(ManageStoreVO manageStoreVO, MultipartFile multipartFile) {
        ManageStoreVO storeVO = new ManageStoreVO();
        String savedName = null;

        if (multipartFile.getOriginalFilename().equals("")){
            //새 파일 없을때
            savedName = manageStoreVO.getFilename();

        } else if(multipartFile.getOriginalFilename() != null) {

            String savedFilePath = "C:/upload/" + manageStoreVO.getFilename();
            File saveFile = new File(savedFilePath);

            if (saveFile.exists() && !manageStoreVO.getFilename().equals("tastemate.jpg")){
//            if (saveFile.exists()){
                saveFile.delete();
            }

            String oriFilename = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String extension = oriFilename.substring(oriFilename.lastIndexOf("."));

            oriFilename = oriFilename.substring(oriFilename.lastIndexOf("\\")+1);

            savedName = uuid + "_" + oriFilename;

            String savedPath = "C:/upload/" + savedName;

            saveFile = new File(savedPath);

            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
            }

        }

        storeVO.setFilename(savedName);
        storeVO.setStoreIdx(manageStoreVO.getStoreIdx());
        storeVO.setUserIdx(manageStoreVO.getUserIdx());
        storeVO.setStoreName(manageStoreVO.getStoreName());
        storeVO.setCategory1(manageStoreVO.getCategory1());
        storeVO.setStoreAddress(manageStoreVO.getStoreAddress());
        storeVO.setStoreLati(manageStoreVO.getStoreLati());
        storeVO.setStoreLongi(manageStoreVO.getStoreLongi());
        storeVO.setPhoneNumber(manageStoreVO.getPhoneNumber());

        int result = mapper.updateManageStore(storeVO);
    }



    //맛집 리스트(승인대기)
    public List<ManageStoreVO> storeRegNotList(ManageStoreVO manageStoreVO) {
        List<ManageStoreVO> storeRegNotList = mapper.storeRegNotList(manageStoreVO);
        return storeRegNotList;
    }

    //맛집 리스트(승인)
    public List<ManageStoreVO> storeRegOkList(ManageStoreVO manageStoreVO) {
        List<ManageStoreVO> storeRegOkList = mapper.storeRegOkList(manageStoreVO);
        return storeRegOkList;
    }

    //맛집 승인
    public int storeRegUpdateOK(ManageStoreVO manageStoreVO) {
        int result = mapper.storeRegUpdateOK(manageStoreVO);
        return result;
    }

    //맛집 승인 대기(취소)
    public int storeRegUpdateNot(ManageStoreVO manageStoreVO) {
        int result = mapper.storeRegUpdateNot(manageStoreVO);
        return result;
    }



    //리뷰 조회
    public List<ManageStarVO> selectUserReview(ManageStarVO manageStarVO) {
        List<ManageStarVO> selectUserReview = mapper.selectUserReview(manageStarVO);
        return selectUserReview;
    }

    //리뷰 상세보기
    public ManageStarVO viewUserReview(ManageStarVO manageStarVO) {
        ManageStarVO viewUserReview = mapper.viewUserReview(manageStarVO);
        return viewUserReview;
    }

    //리뷰 수정
    public int updateUserReview (ManageStarVO manageStarVO) {
        int result = mapper.updateUserReview(manageStarVO);
        return result;
    }

    //리뷰 삭제
    public int deleteUserReview(ManageStarVO manageStarVO) {
        int result = mapper.deleteUserReview(manageStarVO);
        return result;
    }



    //클래스별 수강생 찾기
    public List<MemberVO> manageFindClass(MemberVO memberVO) {
        List<MemberVO> manageFindClass = mapper.manageFindClass(memberVO);
        return manageFindClass;
    }

    //수강생 정보 조회
    public MemberVO userInfo (MemberVO memberVO){
        MemberVO userInfo = mapper.userInfo(memberVO);
        return userInfo;
    }
    //수강생 계정 활성
    public int userInfoActive(MemberVO memberVO){
        int result = mapper.userInfoActive(memberVO);
        return result;
    }
    //수강생 계정 비활성
    public int userInfoInactive(MemberVO memberVO){
        int result = mapper.userInfoInactive(memberVO);
        return result;
    }

    //수강생 계정 강퇴
    public int deleteUserInfo(MemberVO memberVO) {
        int result = mapper.deleteUserInfo(memberVO);
        return result;
    }

}
