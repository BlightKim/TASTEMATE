package com.tastemate.service;

import com.tastemate.domain.MemberVO;
import com.tastemate.mapper.MemberMapper;
import java.lang.reflect.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.relational.core.sql.In;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MemberService {

    @Autowired
    JavaMailSender emailsender;

    private final PasswordEncoder passwordEncoder;
    private MemberMapper mapper;

    private String ePw;

    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    public MemberService(MemberMapper mapper, PasswordEncoder passwordEncoder) {
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    public List<MemberVO> user_get() {

        List<MemberVO> memberList = mapper.member_getList();

        return memberList;
    }

    public void user_join(MemberVO vo, MultipartFile multipartFile) {

        log.info("service도착");
        String savedName = null;

        if(multipartFile.isEmpty()) {
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

        String savedName = null;


        if (multipartFile.getOriginalFilename().equals("")){
            //새 파일 없을때
            savedName = vo.getUserProfile();

        } else if(multipartFile.getOriginalFilename() != null) {

            File saveFile = new File(vo.getUserProfile());

            if (saveFile.exists()){
                saveFile.delete();
            }

            String oriFilename = multipartFile.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();

            String extension = oriFilename.substring(oriFilename.lastIndexOf("."));

            oriFilename = oriFilename.substring(oriFilename.lastIndexOf("\\")+1);

            savedName = uuid + "_" + oriFilename;

            String savedPath = fileDir + "/" +savedName;

            log.info(savedPath);

            saveFile = new File(savedPath);

            try {
                multipartFile.transferTo(saveFile);
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }

        String getVoUserAddress = vo.getUserAddress();
        String userProfile = vo.getUserProfile();

        String[] nullUserAddress = getVoUserAddress.split(",", 0);

        System.out.println("getVoUserAddress = " + getVoUserAddress);


        String userAddress = String.join("/", request.getParameter("userAddress"));

        System.out.println("------------SERVICE---------------");
        String userAddress1 = request.getParameter("userAddress1");
        System.out.println("request.getParameter(\"userAddress1\") = " + request.getParameter("userAddress1"));
        String userAddress2 = request.getParameter("userAddress2");
        System.out.println("userAddress2 = " + userAddress2);
        String userAddress3 = request.getParameter("userAddress3");
        System.out.println("userAddress3 = " + userAddress3);

        if(userAddress1.isEmpty()) {
            userAddress1 = nullUserAddress[0];
        }
        if(userAddress2.isEmpty()) {
            userAddress2 = nullUserAddress[1];
        }
        if(userAddress3.isEmpty()) {
            userAddress3 = nullUserAddress[2];
        }
        
        String userAddressAll = userAddress1 + "," + userAddress2 + "," + userAddress3;
        System.out.println("userAddressAll = " + userAddressAll);

        String[] addressSplit = userAddress2.split(" ");
        System.out.println("addressSplit = " + addressSplit[0]);
        System.out.println("addressSplit = " + addressSplit[1]);



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

        vo.setUserProfile(savedName);

        // userProfile 원래 사진 있으면 default 값으로 들어가게 설정해야함!!!!!!!!!
        //!!!!!!!
        //!!!!!!!
        //!!!!!!!------------------------------------------------


        if (request.getParameter("userAddress1").isEmpty() && request.getParameter("userAddress2").isEmpty() &&
        request.getParameter("userAddress3").isEmpty()) {
            vo.setUserAddress(vo.getUserAddress());
            System.out.println("if문 안에 getVoUserAddress = " + getVoUserAddress);
            vo.setUserAddressSi(addressSplit[0]);
            vo.setUserAddressGu(addressSplit[1]);
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

    public MemberVO findId(String userEmail, HttpServletRequest request, HttpSession session) {
        userEmail = request.getParameter("userEmail");
        MemberVO vo = mapper.findId(userEmail);
        if (vo != null) {
            session.setAttribute("userId", vo.getUserId());
            session.setAttribute("userName", vo.getUserName());
            return vo;

        } else {
            return vo;
        }
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

    public void createChatRoom(MemberVO vo, HttpServletRequest request ,HttpSession session) {
        MemberVO sessionVo = (MemberVO) session.getAttribute("sessionVO");

        vo.setUserIdx(sessionVo.getUserIdx());

        System.out.println("sessionVO = " + sessionVo);

        mapper.createChatRoom(vo);
    }

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
//		System.out.println("보내는 대상 : " + to);
//		System.out.println("인증 번호 : " + ePw);

        MimeMessage message = emailsender.createMimeMessage();

        message.addRecipients(RecipientType.TO, to);// 보내는 대상
        message.setSubject("TASTEMATE 회원가입 이메일 인증");// 제목

        String msgg = "";
        msgg += "<div style='margin:100px;'>";
        msgg += "<h1> 안녕하세요</h1>";
        msgg += "<h1> TASTEMATE 입니다</h1>";
        msgg += "<br>";
        msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
        msgg += "<br>";
        msgg += "<p>항상 당신의 꿈을 응원합니다. 감사합니다!<p>";
        msgg += "<br>";
        msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg += "<div style='font-size:130%'>";
        msgg += "CODE : <strong>";
        msgg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
        msgg += "</div>";
        message.setText(msgg, "utf-8", "html");
        message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
        // 보내는 사람의 이메일 주소, 보내는 사람 이름
        message.setFrom(new InternetAddress("chsh0620@naver.com", "TASTEMATE_Admin"));// 보내는 사람

        return message;
    }

    // 랜덤 인증 코드 전송
    public String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }

        return key.toString();
    }

    // 메일 발송
    // sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
    // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
    // 그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!
    public String sendSimpleMessage(String to) throws Exception {

        ePw = createKey(); // 랜덤 인증번호 생성

        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to); // 메일 발송
        try {// 예외처리
            emailsender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }


        return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
    }


}

