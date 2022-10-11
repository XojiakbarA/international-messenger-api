package com.internationalmessenger.api.util;

import com.internationalmessenger.api.entity.Role;
import com.internationalmessenger.api.entity.User;
import com.internationalmessenger.api.entity.enums.EAuthProvider;
import com.internationalmessenger.api.entity.enums.ERole;
import com.internationalmessenger.api.repository.RoleRepository;
import com.internationalmessenger.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        createRoles();
        createAdmin();
        createUser();
    }

    private void createRoles() {
        Set<Role> roles = new HashSet<>();
        Role adminR = new Role(ERole.ADMIN);
        Role userR = new Role(ERole.USER);
        roles.add(adminR);
        roles.add(userR);
        roleRepository.saveAll(roles);
    }

    private void createAdmin() {
        Role role = roleRepository.findByName(ERole.ADMIN).orElse(null);
        User user = new User();
        user.setName("admin");
        user.setEmail("admin@mail.ru");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRole(role);
        user.setProvider(EAuthProvider.LOCAL);
        userRepository.save(user);
    }

    private void createUser() {
        Role role = roleRepository.findByName(ERole.USER).orElse(null);
        User user = new User();
        user.setName("user");
        user.setEmail("user@mail.ru");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRole(role);
        user.setProvider(EAuthProvider.LOCAL);
        userRepository.save(user);
    }
}
