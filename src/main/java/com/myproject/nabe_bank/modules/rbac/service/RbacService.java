package com.myproject.nabe_bank.modules.rbac.service;

import java.util.List;

import com.myproject.nabe_bank.modules.rbac.dto.request.permission.CreatePermissionRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.permission.PatchPermissionRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.permission.UpdatePermissionRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.role.CreateRoleRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.role.PatchRoleRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.role.UpdateRoleRequest;
import com.myproject.nabe_bank.modules.rbac.dto.response.permission.PermissionResponse;
import com.myproject.nabe_bank.modules.rbac.dto.response.role.RoleResponse;

public interface RbacService {
    // Role
    List<RoleResponse> getRoles();

    RoleResponse createRole(CreateRoleRequest request);

    RoleResponse updateRole(Long id, UpdateRoleRequest request);

    RoleResponse patchRole(Long id, PatchRoleRequest request);

    void deleteRole(Long id);

    // Permission
    List<PermissionResponse> getPermissions();

    PermissionResponse createPermission(CreatePermissionRequest request);

    PermissionResponse updatePermission(Long id, UpdatePermissionRequest request);

    PermissionResponse patchPermission(Long id, PatchPermissionRequest request);

    void deletePermission(Long id);
}
