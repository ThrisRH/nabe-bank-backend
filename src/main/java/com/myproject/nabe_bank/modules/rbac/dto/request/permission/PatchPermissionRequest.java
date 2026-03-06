package com.myproject.nabe_bank.modules.rbac.dto.request.permission;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchPermissionRequest {
    private String name;
    private String resource;
    private String action;
    private String description;
}
