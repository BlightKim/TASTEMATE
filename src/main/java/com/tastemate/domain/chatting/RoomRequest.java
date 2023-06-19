package com.tastemate.domain.chatting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@ToString
@NoArgsConstructor
public class RoomRequest {
  private Integer roomIdx;
  private String senderId;
  private String inviteeId;
}
