package com.internationalmessenger.api.controller;

import com.internationalmessenger.api.entity.User;
import com.internationalmessenger.api.request.LoginRequest;
import com.internationalmessenger.api.request.RegisterRequest;
import com.internationalmessenger.api.response.ApiResponse;
import com.internationalmessenger.api.response.JwtResponse;
import com.internationalmessenger.api.security.jwt.JwtUtils;
import com.internationalmessenger.api.service.UserService;
import com.internationalmessenger.api.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private Mapper mapper;

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse me(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if (!userDetails.isAccountNonLocked()) {
            throw new LockedException("User account is locked");
        }
        User user = userService.getByEmail(authentication.getName());
        ApiResponse response = new ApiResponse();
        response.setMessage("OK");
        response.setContent(mapper.convertToUserDTO(user));
        return response;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public JwtResponse login(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateToken(authentication);
        JwtResponse response = new JwtResponse();
        User user = userService.getByEmail(authentication.getName());
        response.setToken(token);
        response.setMessage("You are logged in");
        response.setUser(mapper.convertToUserDTO(user));
        return response;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse register(@Valid @RequestBody RegisterRequest request) {
        User user = userService.register(request);
        ApiResponse response = new ApiResponse();
        response.setMessage("Registration finished successfully. Please Login");
        response.setContent(mapper.convertToUserDTO(user));
        return response;
    }
}
