package com.internationalmessenger.api.service;

import com.internationalmessenger.api.entity.User;
import com.internationalmessenger.api.request.RegisterRequest;

public interface UserService {
    User getById(Long id);
    User getByEmail(String email);
    User register(RegisterRequest request);
}
