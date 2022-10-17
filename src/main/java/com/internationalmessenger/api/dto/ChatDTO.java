package com.internationalmessenger.api.dto;

import lombok.Data;

@Data
public class ChatDTO {
    private Long id;
    private ChatUserDTO user;
    private MessageDTO lastMessage;
    private LocaleDTO locale;
}
