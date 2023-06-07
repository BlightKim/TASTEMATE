package com.tastemate.domain.member;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * The type Profile file.
 *
 * uploadFileName : 유저가 올린 파일이름
 * storeFileName : DB에 올린 파일 이름
 */
@Slf4j
@Getter
@Setter
public class ProfileVO {
    private Integer profileIdx;
    private String uploadFileName;
    private String storeFileName;
    private Date uploadDate;

    public ProfileVO() {
    }

    public ProfileVO(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
