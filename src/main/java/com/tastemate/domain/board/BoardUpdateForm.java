package com.tastemate.domain.board;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardUpdateForm {
  private String title;
  private String content;
  private MultipartFile multipartFile;
  private String existingFile;
}
