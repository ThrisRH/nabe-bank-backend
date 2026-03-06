package com.myproject.nabe_bank.modules.rbac.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.nabe_bank.modules.rbac.entity.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    List<Permission> findByKeyIn(Set<String> permissionKey);
}
