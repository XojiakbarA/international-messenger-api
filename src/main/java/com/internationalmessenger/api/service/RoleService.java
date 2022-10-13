package com.internationalmessenger.api.service;

import com.internationalmessenger.api.entity.Role;
import com.internationalmessenger.api.entity.enums.ERole;

public interface RoleService {
    Role getByName(ERole name);
    Role saveByName(ERole name);
}
