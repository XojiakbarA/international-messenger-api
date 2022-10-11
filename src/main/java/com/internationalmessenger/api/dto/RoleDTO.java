package com.internationalmessenger.api.dto;

import com.internationalmessenger.api.entity.enums.ERole;
import lombok.Data;

@Data
public class RoleDTO {
    private Long id;
    private ERole name;
}
