package com.tastemate.service;

import com.tastemate.domain.MemberVO;
import com.tastemate.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.UUID;

@Service
@Slf4j
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private MemberMapper mapper;

    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    public MemberService(MemberMapper mapper, PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

//    public List<MemberVO> user_get() {
//
//        List<MemberVO> memberList = mapper.member_getList();
//
//        return memberList;
//    }

    public void user_join(MemberVO vo, MultipartFile multipartFile) {

        log.info("service도착");
        String savedName = null;

        if(multipartFile == null){
            log.info("empty 확인");
            savedName = "tastemate.jpg";

        } else {
            String oriFilename = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String extension = oriFilename.substring(oriFilename.lastIndexOf("."));

            oriFilename = oriFilename.substring(oriFilename.lastIndexOf("\\")+1);

            savedName = uuid + "_" + oriFilename;

            String savedPath = fileDir + "/" +savedName;

            log.info(savedPath);

            File saveFile = new File(savedPath);

            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            } //else 끝

        MemberVO vo1 = new MemberVO();
        String Address = vo.getUserAddress();
        String[] voAddressSplit = vo.getUserAddress().split(",");
        System.out.println(voAddressSplit[1]);

        String[] addressSplit = voAddressSplit[1].split(" ");
        System.out.println(addressSplit[0]);
        System.out.println(addressSplit[1]);


        String userAddress = String.join("/", vo.getUserAddress());

        String userBirth = String.join("/", vo.getUserBirth());
        String encodedPassword = passwordEncoder.encode(vo.getUserPwd());

        System.out.println("vo.getUserAddress = " + vo.getUserAddress());
        System.out.println("Id = " + vo.getUserId());
        System.out.println("pwd = " + vo.getUserPwd());
        System.out.println("encodedPassword = " + encodedPassword);
        System.out.println(vo.getUserName());
        System.out.println(vo.getUserGender());
        System.out.println(vo.getUserPhone());
        System.out.println("userAddress = " + userAddress);
        System.out.println(vo.getUserClass());
        System.out.println(vo.getUserEmail());
        System.out.println(vo.getUserLikeFood());
        System.out.println(vo.getUserBirth());
        System.out.println(vo.getUserMbti());
        System.out.println("savedName = " + savedName);


        vo1.setUserId(vo.getUserId());
        vo1.setUserPwd(encodedPassword);
        vo1.setUserName(vo.getUserName());
        vo1.setUserGender(vo.getUserGender());
        vo1.setUserPhone(vo.getUserPhone());
        vo1.setUserAddress(userAddress);
        vo1.setUserLikeFood(vo.getUserLikeFood());
        vo1.setUserClass(vo.getUserClass());
        vo1.setUserEmail(vo.getUserEmail());
        vo1.setUserMbti(vo.getUserMbti());
        vo1.setUserBirth(userBirth);
        vo1.setUserProfile(savedName);
        vo1.setUserAddressSi(addressSplit[0]);
        vo1.setUserAddressGu(addressSplit[1]);

        int result = mapper.user_join(vo1);
    }

    public Integer checkId(String userId) {
        return mapper.checkId(userId);
    }

    public Integer check(String userId, String userPwd) {
        return mapper.check(userId, userPwd);
    }

    public MemberVO loginId(String userId) {
        MemberVO vo = mapper.loginId(userId);
        return vo;
    }

    public void userModify(MemberVO vo, MultipartFile multipartFile, HttpSession session, HttpServletRequest request) {

        log.info("수정service도착");

        MemberVO sessionVO = (MemberVO) session.getAttribute("sessionVO");

        String getVoUserAddress = vo.getUserAddress();
        String userProfile = vo.getUserProfile();

        System.out.println("getVoUserAddress = " + getVoUserAddress);

        String userAddress = String.join("/", request.getParameter("userAddress"));

        System.out.println("------------SERVICE---------------");
        String userAddress1 = request.getParameter("userAddress1");
        String userAddress2 = request.getParameter("userAddress2");
        String userAddress3 = request.getParameter("userAddress3");
        String userAddressAll = userAddress1 + "," + userAddress2 + "," + userAddress3;

        String[] addressSplit = userAddress2.split(" ");


        System.out.println("수정 전 vo = " + vo);
        System.out.println("sessionVO = " + sessionVO);

        vo.setUserIdx(sessionVO.getUserIdx());

        if (request.getParameter("userName").isEmpty()) {
            vo.setUserName(vo.getUserName());
        } else {
            vo.setUserName(request.getParameter("userName"));
        }

        if (request.getParameter("userEmail").isEmpty()) {
            vo.setUserEmail(vo.getUserEmail());
        } else {
            vo.setUserEmail(request.getParameter("userEmail"));
        }

        if (request.getParameter("userPhone").isEmpty()) {
            vo.setUserPhone(vo.getUserPhone());
        } else {
            vo.setUserPhone(request.getParameter("userPhone"));
        }

        if (request.getParameter("userLikeFood").isEmpty()) {
            vo.setUserLikeFood(vo.getUserLikeFood());
        } else {
            vo.setUserLikeFood(request.getParameter("userLikeFood"));
        }

        if (request.getParameter("userMbti").isEmpty()) {
            vo.setUserMbti(vo.getUserMbti());
        } else {
            vo.setUserMbti(request.getParameter("userMbti"));
        }

        // userProfile 원래 사진 있으면 default 값으로 들어가게 설정해야함!!!!!!!!!
        //!!!!!!!
        //!!!!!!!
        //!!!!!!!------------------------------------------------


        if (request.getParameter("userAddress1").isEmpty()) {
            vo.setUserAddress(vo.getUserAddress());
            System.out.println("if문 안에 getVoUserAddress = " + getVoUserAddress);
        } else {
            vo.setUserAddress(userAddressAll);
            vo.setUserAddressSi(addressSplit[0]);
            vo.setUserAddressGu(addressSplit[1]);
        }

        System.out.println("수정 후 vo = " + vo);

        int result = mapper.userModify(vo);
    }

    public void userStatus(MemberVO vo, HttpSession session) {

        log.info("탈퇴service도착");

        MemberVO sessionVO = (MemberVO) session.getAttribute("sessionVO");

        System.out.println("sessionVO = " + sessionVO);

        vo.setUserIdx(sessionVO.getUserIdx());



        int result = mapper.userStatus(vo);

    }

    public void findId(String userEmail, HttpServletRequest request, HttpSession session) {
        userEmail = request.getParameter("userEmail");
        MemberVO vo = mapper.findId(userEmail);
        session.setAttribute("userId", vo.getUserId());
    }

    public MemberVO userGet(String userId, String userEmail, HttpServletRequest request, HttpSession session) {
        userId = request.getParameter("userId");
        userEmail = request.getParameter("userEmail");
        MemberVO vo = mapper.userGet(userId, userEmail);

        return vo;
    }

    public void reset(MemberVO vo, HttpServletRequest request, HttpSession session) {
//        String userId = (String) session.getAttribute("userId");
//        String userEmail = (String) session.getAttribute("userEmail");
//
//        System.out.println("(reset서비스)userId = " + userId);
//        System.out.println("(reset서비스)userEmail = " + userEmail);

//        vo = mapper.userGet(userId, userEmail);
        String userPwd = request.getParameter("userPwd");
        System.out.println("(service)userPwd = " + userPwd);
        System.out.println("(service_reset)vo = " + vo);

        String encodedPassword = passwordEncoder.encode(userPwd);

        vo.setUserPwd(encodedPassword);


        int result = mapper.resetPassword(vo);

        System.out.println("(reset)vo = " + vo);
    }

}

