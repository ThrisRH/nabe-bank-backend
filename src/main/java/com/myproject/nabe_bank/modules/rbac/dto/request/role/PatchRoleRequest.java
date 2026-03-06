package com.myproject.nabe_bank.modules.rbac.dto.request.role;

import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PatchRoleRequest {
    private String name;
    private String description;
    private Set<String> permissionKeys;
}
