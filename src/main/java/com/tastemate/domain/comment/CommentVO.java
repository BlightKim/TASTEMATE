package com.tastemate.domain.comment;

import com.tastemate.domain.MemberVO;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentVO {
  private Integer commentIdx;
  private Integer commentLevel;
  private Integer parentCommentIdx;
  private MemberVO memberVO;
  private Integer boardIdx;
  private String commentContent;
  private String commenter;
  private Date regDate;
  private Date updateDate;
  private CommentStatus commentStatus;

  public CommentVO() {
  }

}
