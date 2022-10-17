package com.internationalmessenger.api.service;

import com.internationalmessenger.api.entity.Message;
import com.internationalmessenger.api.request.MessageRequest;

import java.util.List;

public interface MessageService {
    List<Message> getAllByChatId(Long id);
    List<Message> getAllTranslatedByChatId(Long id);
    Message save(MessageRequest request);
}
