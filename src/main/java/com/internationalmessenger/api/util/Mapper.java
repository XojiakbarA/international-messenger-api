package com.internationalmessenger.api.util;

import com.internationalmessenger.api.dto.RoleDTO;
import com.internationalmessenger.api.dto.UserDTO;
import com.internationalmessenger.api.entity.Role;
import com.internationalmessenger.api.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
