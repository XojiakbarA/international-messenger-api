package com.internationalmessenger.api.service;

import com.internationalmessenger.api.entity.Chat;
import com.internationalmessenger.api.entity.User;

import java.util.List;
import java.util.Set;

public interface ChatService {
    Chat getById(Long id);
    List<Chat> getAllByAuthUser();
    Chat saveByUsers(Set<User> users);
}
