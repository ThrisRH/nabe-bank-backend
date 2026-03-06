package com.myproject.nabe_bank.modules.rbac.dto.request.permission;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePermissionRequest {
    @NotBlank(message = "Permission name is required")
    private String name;
    @NotBlank(message = "Permission resource is required")
    private String resource;
    @NotBlank(message = "Permission action is required")
    private String action;
    private String description;

    public UpdatePermissionRequest() {
    }

    public UpdatePermissionRequest(String name, String resource, String action, String description) {
        this.name = name;
        this.resource = resource;
        this.action = action;
        this.description = description;
    }
}
