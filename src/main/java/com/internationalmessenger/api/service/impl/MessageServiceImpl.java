package com.internationalmessenger.api.service.impl;

import com.internationalmessenger.api.entity.Chat;
import com.internationalmessenger.api.entity.Locale;
import com.internationalmessenger.api.entity.Message;
import com.internationalmessenger.api.entity.User;
import com.internationalmessenger.api.repository.MessageRepository;
import com.internationalmessenger.api.request.MessageRequest;
import com.internationalmessenger.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private TranslateService translateService;

    @Override
    public List<Message> getAllByChatId(Long id) {
        return messageRepository.findAllByChatId(id);
    }

    @Override
    public List<Message> getAllTranslatedByChatId(Long id) {
        List<Message> messages = getAllByChatId(id);
        Locale locale = localeService.getByChatIdAndAuthUser(id);
        if (locale == null) {
            return messages;
        }
        messages.forEach(m -> {
            String translated = translateService.translate(m.getContent(), locale.getLanguageCode());
            m.setContent(translated);
        });
        return messages;
    }

    @Override
    public Message save(MessageRequest request) {
        Message message = new Message();
        Chat chat = chatService.getById(request.getChatId());
        User sender = userService.getById(request.getSenderId());
        User receiver = userService.getById(request.getReceiverId());
        message.setChat(chat);
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(request.getContent());
        return messageRepository.save(message);
    }
}
