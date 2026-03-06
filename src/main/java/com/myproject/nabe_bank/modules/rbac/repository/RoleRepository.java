package com.myproject.nabe_bank.modules.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.myproject.nabe_bank.modules.rbac.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
