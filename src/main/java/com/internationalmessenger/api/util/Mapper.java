package com.internationalmessenger.api.util;

import com.internationalmessenger.api.dto.*;
import com.internationalmessenger.api.entity.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class Mapper {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    public RoleDTO convertToRoleDTO(Role role) {
        return modelMapper.map(role, RoleDTO.class);
    }

    public LocaleDTO convertToLocaleDTO(Locale locale) {
        return modelMapper.map(locale, LocaleDTO.class);
    }

    public MessageDTO convertToMessageDTO(Message message) {
        Converter<User, Boolean> isMineConverter = u -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            return u.getSource().getEmail().equals(email);
        };
        return modelMapper
                .typeMap(Message.class, MessageDTO.class)
                .addMappings(m -> m.using(isMineConverter).map(Message::getSender, MessageDTO::setIsMine))
                .map(message);
    }

    public ChatDTO convertToChatDTO(Chat chat) {
        Converter<Collection<User>, ChatUserDTO> userConverter = c -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            User user = c.getSource().stream().filter(u -> !u.getEmail().equals(email)).findFirst().orElse(null);
            return modelMapper.map(user, ChatUserDTO.class);
        };
        Converter<Collection<Message>, MessageDTO> lastMessageConverter = c -> {
            Message message = c.getSource().stream().reduce((first, second) -> second).orElse(null);
            return modelMapper.map(message, MessageDTO.class);
        };
        return modelMapper
                .typeMap(Chat.class, ChatDTO.class)
                .addMappings(m -> m.using(userConverter).map(Chat::getUsers, ChatDTO::setUser))
                .addMappings(m -> m.using(lastMessageConverter).map(Chat::getMessages, ChatDTO::setLastMessage))
                .map(chat);
    }
}
