package com.myproject.nabe_bank.modules.rbac.dto.request.role;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRoleRequest {
    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    private Set<String> permissionKeys;

    public UpdateRoleRequest() {
    }

    public UpdateRoleRequest(String name, String description, Set<String> permissionKeys) {
        this.name = name;
        this.description = description;
        this.permissionKeys = permissionKeys;
    }
}
