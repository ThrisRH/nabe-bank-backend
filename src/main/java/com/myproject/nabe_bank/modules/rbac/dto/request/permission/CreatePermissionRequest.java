package com.myproject.nabe_bank.modules.rbac.dto.request.permission;

import jakarta.validation.constraints.NotBlank;

public class CreatePermissionRequest {
    @NotBlank(message = "Key must not be empty")
    private String key;

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Resource must not be empty")
    private String resource;

    @NotBlank(message = "Action must not be empty")
    private String action;

    @NotBlank(message = "Description must not be empty")
    private String description;

    public CreatePermissionRequest() {
    }

    public CreatePermissionRequest(String key, String name, String resource, String action, String description) {
        this.key = key;
        this.name = name;
        this.resource = resource;
        this.action = action;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
