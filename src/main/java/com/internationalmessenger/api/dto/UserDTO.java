package com.internationalmessenger.api.dto;

import com.internationalmessenger.api.entity.enums.EAuthProvider;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String image;
    private Boolean isNonLocked;
    private EAuthProvider provider;
    private RoleDTO role;
}
