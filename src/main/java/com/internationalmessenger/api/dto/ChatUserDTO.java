package com.internationalmessenger.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ChatUserDTO {
    private Long id;
    private String name;
    private String email;
    private String image;
    private Date createdAt;
}
