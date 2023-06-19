package com.tastemate.domain.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardWriteForm {
  private String title;
  private String writer;
  private String content;
  private String password;
  private MultipartFile multipartFile;
}
