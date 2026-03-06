package com.myproject.nabe_bank.modules.rbac.dto.request.role;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;

public class CreateRoleRequest {
    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Description must not be empty")
    private String description;

    private Set<String> permissionKeys;

    public CreateRoleRequest() {
    }

    public CreateRoleRequest(String name, String description, Set<String> permissionKeys) {
        this.name = name;
        this.description = description;
        this.permissionKeys = permissionKeys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getPermissionKeys() {
        return permissionKeys;
    }

    public void setPermissionKeys(Set<String> permissionKeys) {
        this.permissionKeys = permissionKeys;
    }
}
