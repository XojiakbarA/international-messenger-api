package com.internationalmessenger.api.repository;

import com.internationalmessenger.api.entity.Role;
import com.internationalmessenger.api.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
