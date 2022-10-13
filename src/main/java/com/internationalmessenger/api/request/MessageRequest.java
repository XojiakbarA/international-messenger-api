package com.internationalmessenger.api.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MessageRequest {
    @NotBlank
    private Long chatId;
    @NotBlank
    private Long senderId;
    @NotBlank
    private Long receiverId;
    @NotBlank
    private String content;
}
