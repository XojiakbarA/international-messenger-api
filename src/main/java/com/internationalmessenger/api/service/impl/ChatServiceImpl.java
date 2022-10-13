package com.internationalmessenger.api.service.impl;

import com.internationalmessenger.api.entity.Chat;
import com.internationalmessenger.api.entity.User;
import com.internationalmessenger.api.exception.ResourceNotFoundException;
import com.internationalmessenger.api.repository.ChatRepository;
import com.internationalmessenger.api.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepository chatRepository;

    @Override
    public Chat getById(Long id) {
        return chatRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Chat not found with id:" + id)
        );
    }

    @Override
    public List<Chat> getAllByAuthUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return chatRepository.findAllByUsersEmail(authentication.getName());
    }

    @Override
    public Chat saveByUsers(Set<User> users) {
        Chat chat = new Chat();
        chat.setUsers(users);
        return chatRepository.save(chat);
    }
}
