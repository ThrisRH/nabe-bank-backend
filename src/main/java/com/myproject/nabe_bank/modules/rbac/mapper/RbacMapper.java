package com.myproject.nabe_bank.modules.rbac.mapper;

import org.springframework.stereotype.Component;

import com.myproject.nabe_bank.modules.rbac.dto.response.permission.PermissionResponse;
import com.myproject.nabe_bank.modules.rbac.dto.response.role.RoleResponse;
import com.myproject.nabe_bank.modules.rbac.entity.Permission;
import com.myproject.nabe_bank.modules.rbac.entity.Role;

@Component
public class RbacMapper {
    public RoleResponse toRoleResponse(Role role) {
        if (role == null) {
            return null;
        }

        return new RoleResponse(role.getId(), role.getName(), role.getDescription(), role.getPermissionsKey(),
                role.getCreatedAt(), role.getUpdatedAt());
    }

    public PermissionResponse toPermissionResponse(Permission permission) {
        if (permission == null) {
            return null;
        }

        return new PermissionResponse(permission.getId(), permission.getKey(), permission.getName(),
                permission.getResource(), permission.getAction(), permission.getDescription(),
                permission.getCreatedAt(), permission.getUpdatedAt());
    }
}