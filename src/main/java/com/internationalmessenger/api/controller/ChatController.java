package com.internationalmessenger.api.controller;

import com.internationalmessenger.api.entity.Chat;
import com.internationalmessenger.api.response.ApiResponse;
import com.internationalmessenger.api.service.ChatService;
import com.internationalmessenger.api.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private Mapper mapper;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllChatsByAuthUser() {
        List<Chat> chats = chatService.getAllByAuthUser();
        ApiResponse response = new ApiResponse();
        response.setContent(chats.stream().map(ch -> mapper.convertToChatDTO(ch)).collect(Collectors.toList()));
        return response;
    }
}
