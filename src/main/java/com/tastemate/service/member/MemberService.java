package com.tastemate.service.member;

import com.tastemate.domain.member.MemberVO;
import com.tastemate.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MemberService {

    private MemberMapper mapper;

    @Value("${file.dir}")
    private String fileDir;

    @Autowired
    public MemberService(MemberMapper mapper) {
        this.mapper = mapper;
    }

    public List<MemberVO> user_get() {

        List<MemberVO> MemberList = mapper.member_getList();

        return MemberList;
    }

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

        String userAddress = String.join("/", vo.getUserAddress());
        String userBirth = String.join("/", vo.getUserBirth());


        System.out.println("Id = " + vo.getUserId());
        System.out.println("pwd = " + vo.getUserPwd());
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
        vo1.setUserPwd(vo.getUserPwd());
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

        int result = mapper.user_join(vo1);
    }

    public Integer checkId(String userId) {
        return mapper.checkId(userId);
    }

    public MemberVO loginId(String userId) {
        MemberVO vo = mapper.loginId(userId);
        return vo;
    }

    public void userModify(MemberVO vo, MultipartFile multipartFile) {
        String userAddress = String.join("/", vo.getUserAddress());

        System.out.println(vo.getUserName());
        System.out.println(vo.getUserPhone());
        System.out.println("userAddress = " + userAddress);
        System.out.println(vo.getUserEmail());
        System.out.println(vo.getUserLikeFood());
        System.out.println(vo.getUserMbti());

        vo.setUserName(vo.getUserName());
        vo.setUserPhone(vo.getUserPhone());
        vo.setUserLikeFood(vo.getUserLikeFood());
        vo.setUserEmail(vo.getUserEmail());
        vo.setUserMbti(vo.getUserMbti());
        vo.setUserAddress(userAddress);
    }

}

