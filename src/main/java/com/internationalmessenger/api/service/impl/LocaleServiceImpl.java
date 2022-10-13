package com.internationalmessenger.api.service.impl;

import com.internationalmessenger.api.entity.Chat;
import com.internationalmessenger.api.entity.Locale;
import com.internationalmessenger.api.entity.User;
import com.internationalmessenger.api.repository.LocaleRepository;
import com.internationalmessenger.api.request.LocaleRequest;
import com.internationalmessenger.api.service.ChatService;
import com.internationalmessenger.api.service.LocaleService;
import com.internationalmessenger.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LocaleServiceImpl implements LocaleService {
    @Autowired
    private LocaleRepository localeRepository;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @Override
    public Locale getByChatIdAndAuthUser(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return localeRepository.findByChatIdAndUserEmail(id, authentication.getName());
    }

    @Override
    public Locale save(LocaleRequest request) {
        Locale locale = new Locale();
        Chat chat = chatService.getById(request.getChatId());
        User user = userService.getById(request.getUserId());
        locale.setChat(chat);
        locale.setUser(user);
        locale.setLanguage(request.getLanguage());
        return localeRepository.save(locale);
    }
}
