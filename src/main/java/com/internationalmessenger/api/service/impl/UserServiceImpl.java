package com.internationalmessenger.api.service.impl;

import com.internationalmessenger.api.entity.Role;
import com.internationalmessenger.api.entity.User;
import com.internationalmessenger.api.entity.enums.ERole;
import com.internationalmessenger.api.exception.ResourceNotFoundException;
import com.internationalmessenger.api.repository.UserRepository;
import com.internationalmessenger.api.request.RegisterRequest;
import com.internationalmessenger.api.service.RoleService;
import com.internationalmessenger.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: " + email)
        );
        return UserDetailsImpl.build(user);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + id)
        );
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found with email: " + email)
        );
    }

    @Override
    public User register(RegisterRequest request) {
        User user = new User();
        Role role = roleService.getByName(ERole.USER);
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);
        return userRepository.save(user);
    }
}
