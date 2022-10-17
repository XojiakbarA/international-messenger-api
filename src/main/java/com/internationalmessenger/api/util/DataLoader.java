package com.internationalmessenger.api.util;

import com.internationalmessenger.api.entity.*;
import com.internationalmessenger.api.entity.enums.ERole;
import com.internationalmessenger.api.request.LocaleRequest;
import com.internationalmessenger.api.request.MessageRequest;
import com.internationalmessenger.api.request.RegisterRequest;
import com.internationalmessenger.api.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private LocaleService localeService;
    @Autowired
    private MessageService messageService;

    @Override
    public void run(String... args) {
        createRole(ERole.ADMIN);
        createRole(ERole.USER);
        createUser("admin");
        for (int i = 1; i < 11; i++) {
            createUser("user" + i);
        }
        createChat();
        createLocale();
        createMessage();
    }

    private void createRole(ERole name) {
        roleService.saveByName(name);
    }
    private void createUser(String name) {
        RegisterRequest request = new RegisterRequest();
        request.setName(name);
        request.setEmail(name + "@mail.ru");
        request.setPassword("123");
        request.setConfirmPassword("123");
        userService.register(request);
    }

    private void createChat() {
        User admin = userService.getByEmail("admin@mail.ru");
        User user1 = userService.getByEmail("user1@mail.ru");
        User user2 = userService.getByEmail("user2@mail.ru");

        chatService.saveByUsers(Set.of(admin, user1));
        chatService.saveByUsers(Set.of(admin, user2));
        chatService.saveByUsers(Set.of(user1, user2));
    }

    private void createLocale() {
        LocaleRequest request = new LocaleRequest();
        request.setChatId((long) 1);
        request.setUserId((long) 1);
        request.setLanguageName("Russian");
        request.setLanguageCode("ru");
        localeService.save(request);

    }

    private void createMessage() {
        for (int i = 0; i < 15; i++) {
            MessageRequest request1 = new MessageRequest();
            request1.setChatId((long) 1);
            request1.setSenderId((long) 1);
            request1.setReceiverId((long) 2);
            request1.setContent("Hello to user1 from admin");
            messageService.save(request1);
        }

        MessageRequest request2 = new MessageRequest();
        request2.setChatId((long) 2);
        request2.setSenderId((long) 1);
        request2.setReceiverId((long) 3);
        request2.setContent("Hello to user2 from admin");
        messageService.save(request2);

        MessageRequest request3 = new MessageRequest();
        request3.setChatId((long) 3);
        request3.setSenderId((long) 2);
        request3.setReceiverId((long) 3);
        request3.setContent("Hello to user2 from user1");
        messageService.save(request3);
    }
}
