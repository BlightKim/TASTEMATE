package com.tastemate.domain.board;

import com.tastemate.domain.MemberVO;
import com.tastemate.domain.comment.CommentVO;
import java.lang.reflect.Member;
import java.util.Date;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class BoardVO {
    private Integer boardIdx;
    private Integer userIdx;
    private String writer;

    @NotBlank
    @Range(min = 4, max = 8)
    private String boardPassword;

    @NotBlank
    @Range(min = 4, max = 20)
    private String title;

    @NotBlank
    @Max(1000)
    private String content;

    private Integer hits;

    private Date regDate;
    private List<MemberVO> likedUsers;
    private Date updateDate;
    private String oriName;
    private String storeName;
    private Date deleteDate;
    private BoardStatus boardStatus;
    private MultipartFile multipartFile;
    private Integer boardLike;
    private MemberVO memberVO;
    private Integer commentCount;
    private List<CommentVO> commentList;
}