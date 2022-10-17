package com.internationalmessenger.api.controller;

import com.internationalmessenger.api.entity.Message;
import com.internationalmessenger.api.response.ApiResponse;
import com.internationalmessenger.api.service.MessageService;
import com.internationalmessenger.api.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chats")
public class ChatMessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private Mapper mapper;

    @GetMapping("/{id}/messages")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse getAllMessagesByChatId(@PathVariable Long id) {
        List<Message> messages = messageService.getAllTranslatedByChatId(id);
        ApiResponse response = new ApiResponse();
        response.setMessage("OK");
        response.setContent(messages.stream().map(m -> mapper.convertToMessageDTO(m)).collect(Collectors.toList()));
        return response;
    }
}
