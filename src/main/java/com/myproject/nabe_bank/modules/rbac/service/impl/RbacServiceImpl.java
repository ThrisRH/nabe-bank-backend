package com.myproject.nabe_bank.modules.rbac.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.myproject.nabe_bank.modules.rbac.dto.request.permission.CreatePermissionRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.permission.PatchPermissionRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.permission.UpdatePermissionRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.role.CreateRoleRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.role.PatchRoleRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.role.UpdateRoleRequest;
import com.myproject.nabe_bank.modules.rbac.dto.response.permission.PermissionResponse;
import com.myproject.nabe_bank.modules.rbac.dto.response.role.RoleResponse;
import com.myproject.nabe_bank.modules.rbac.entity.Permission;
import com.myproject.nabe_bank.modules.rbac.entity.Role;
import com.myproject.nabe_bank.modules.rbac.mapper.RbacMapper;
import com.myproject.nabe_bank.modules.rbac.repository.PermissionRepository;
import com.myproject.nabe_bank.modules.rbac.repository.RoleRepository;
import com.myproject.nabe_bank.modules.rbac.service.RbacService;

@Service
public class RbacServiceImpl implements RbacService {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    private final RbacMapper rbacMapper;

    public RbacServiceImpl(RoleRepository roleRepository, PermissionRepository permissionRepository,
            RbacMapper rbacMapper) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.rbacMapper = rbacMapper;
    }

    @Override
    public List<RoleResponse> getRoles() {
        List<Role> roles = roleRepository.findAll();

        if (roles.isEmpty()) {
            throw new IllegalArgumentException("No roles found!");
        }
        return roles.stream().map(role -> rbacMapper.toRoleResponse(role)).collect(Collectors.toList());
    }

    @Override
    public RoleResponse createRole(CreateRoleRequest request) {
        Role role = new Role();

        if (request.getPermissionKeys() != null && !request.getPermissionKeys().isEmpty()) {
            List<Permission> permissions = permissionRepository.findByKeyIn(request.getPermissionKeys());

            if (permissions.isEmpty()) {
                throw new IllegalArgumentException("No permissions found!");
            }
        }

        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setPermissionsKey(request.getPermissionKeys());
        role = roleRepository.save(role);
        return rbacMapper.toRoleResponse(role);
    }

    @Override
    public RoleResponse updateRole(Long id, UpdateRoleRequest request) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));

        List<Permission> permissions = permissionRepository.findByKeyIn(request.getPermissionKeys());

        if (permissions.isEmpty()) {
            throw new IllegalArgumentException("No permissions found!");
        }

        if (permissions.size() != request.getPermissionKeys().size()) {
            throw new IllegalArgumentException("Some permissions not found!");
        }

        role.setName(request.getName());
        role.setDescription(request.getDescription());
        role.setPermissionsKey(request.getPermissionKeys());
        role = roleRepository.save(role);

        return rbacMapper.toRoleResponse(role);
    }

    @Override
    public RoleResponse patchRole(Long id, PatchRoleRequest request) {
        System.out.printf("Patch Request: %s", request.getPermissionKeys());
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));

        if (request.getName() != null) {
            role.setName(request.getName());
        }

        if (request.getDescription() != null) {
            role.setDescription(request.getDescription());
        }

        if (request.getPermissionKeys() != null) {
            List<Permission> permissions = permissionRepository.findByKeyIn(request.getPermissionKeys());

            if (permissions.isEmpty()) {
                throw new IllegalArgumentException("No permissions found!");
            }

            if (permissions.size() != request.getPermissionKeys().size()) {
                throw new IllegalArgumentException("Some permissions not found!");
            }

            role.setPermissionsKey(request.getPermissionKeys());
        }

        role = roleRepository.save(role);
        return rbacMapper.toRoleResponse(role);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Role not found"));
        roleRepository.delete(role);
    }

    @Override
    public List<PermissionResponse> getPermissions() {
        List<Permission> permissions = permissionRepository.findAll();

        if (permissions.isEmpty()) {
            throw new IllegalArgumentException("No permissions found!");
        }
        return permissions.stream().map(permission -> rbacMapper.toPermissionResponse(permission))
                .collect(Collectors.toList());
    }

    @Override
    public PermissionResponse createPermission(CreatePermissionRequest request) {
        Permission permission = new Permission();

        permission.setKey(request.getResource() + "_" + request.getAction());
        permission.setName(request.getName());
        permission.setResource(request.getResource());
        permission.setAction(request.getAction());
        permission.setDescription(request.getDescription());
        permission = permissionRepository.save(permission);

        return rbacMapper.toPermissionResponse(permission);
    }

    @Override
    public PermissionResponse updatePermission(Long id, UpdatePermissionRequest request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));

        permission.setKey(request.getResource() + "_" + request.getAction());
        permission.setName(request.getName());
        permission.setResource(request.getResource());
        permission.setAction(request.getAction());
        permission.setDescription(request.getDescription());
        permission = permissionRepository.save(permission);

        return rbacMapper.toPermissionResponse(permission);
    }

    @Override
    public void deletePermission(Long id) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));
        permissionRepository.delete(permission);
    }

    @Override
    public PermissionResponse patchPermission(Long id, PatchPermissionRequest request) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found"));

        if (request.getName() != null) {
            permission.setName(request.getName());
        }

        if (request.getResource() != null) {
            permission.setResource(request.getResource());
            permission.setKey(request.getResource() + "_" + permission.getAction());
        }

        if (request.getAction() != null) {
            permission.setAction(request.getAction());
            permission.setKey(permission.getResource() + "_" + request.getAction());
        }

        if (request.getDescription() != null) {
            permission.setDescription(request.getDescription());
        }

        permission = permissionRepository.save(permission);
        return rbacMapper.toPermissionResponse(permission);
    }

}
