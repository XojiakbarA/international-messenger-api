package com.internationalmessenger.api.service.impl;

import com.internationalmessenger.api.entity.Role;
import com.internationalmessenger.api.entity.enums.ERole;
import com.internationalmessenger.api.repository.RoleRepository;
import com.internationalmessenger.api.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getByName(ERole name) {
        return roleRepository.findByName(name).orElseThrow(
                EntityNotFoundException::new
        );
    }
}
