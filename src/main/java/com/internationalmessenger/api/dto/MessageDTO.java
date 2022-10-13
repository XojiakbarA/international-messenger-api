package com.internationalmessenger.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class MessageDTO {
    private Long id;
    private Boolean isMine;
    private MessageUserDTO receiver;
    private MessageUserDTO sender;
    private String content;
    private Date createdAt;
}
