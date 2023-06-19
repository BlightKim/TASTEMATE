package com.tastemate.domain.chatting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class InviteDTO {
    private String senderId;
    private String inviteeId;
}
