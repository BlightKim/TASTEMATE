package com.tastemate.mapper;

import com.tastemate.domain.ManageStarVO;
import com.tastemate.domain.ManageStoreVO;
import com.tastemate.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManageMapper {

    //맛집 리스트(관리자)
    List<ManageStoreVO> manageStoreListGet();

    //맛집 등록(관리자)
    int manageSaveStore(ManageStoreVO manageStoreVO);

    //맛집 상세보기(관리자)
    ManageStoreVO manageStoreView(ManageStoreVO manageStoreVO);

    //맛집 삭제(관리자)
    int deleteManageStore(ManageStoreVO manageStoreVO);

    //맛집 수정(관리자)
    int updateManageStore(ManageStoreVO manageStoreVO);



    //맛집 리스트(승인대기)
    List<ManageStoreVO> storeRegNotList(ManageStoreVO manageStoreVO);

    //맛집 리스트(승인)
    List<ManageStoreVO> storeRegOkList(ManageStoreVO manageStoreVO);

    //맛집 승인
    int storeRegUpdateOK(ManageStoreVO manageStoreVO);

    //맛집 승인 대기(취소)
    int storeRegUpdateNot(ManageStoreVO manageStoreVO);



    //리뷰 조회
    List<ManageStarVO> selectUserReview(ManageStarVO manageStarVO);

    //리뷰 상세보기
    ManageStarVO viewUserReview(ManageStarVO manageStarVO);

    //리뷰 수정
    int updateUserReview(ManageStarVO manageStarVO);

    //리뷰 삭제
    int deleteUserReview(ManageStarVO manageStarVO);



    //클래스별 수강생 찾기
    List<MemberVO> manageFindClass(MemberVO memberVO);



    //수강생 정보 조회
    MemberVO userInfo (MemberVO memberVO);

    //수강생 계정 활성
    int userInfoActive(MemberVO memberVO);

    //수강생 계정 비활성
    int userInfoInactive(MemberVO memberVO);

    //수강생 계정 강퇴
    int deleteUserInfo(MemberVO memberVO);

}