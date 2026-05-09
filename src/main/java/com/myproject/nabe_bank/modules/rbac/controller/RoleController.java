package com.myproject.nabe_bank.modules.rbac.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.nabe_bank.core.exception.ApiResponse;
import com.myproject.nabe_bank.core.response.ResponseCode;
import com.myproject.nabe_bank.modules.rbac.dto.request.role.CreateRoleRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.role.PatchRoleRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.role.UpdateRoleRequest;
import com.myproject.nabe_bank.modules.rbac.dto.response.role.RoleResponse;
import com.myproject.nabe_bank.modules.rbac.service.RbacService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {
    private final RbacService rbacService;

    public RoleController(RbacService rbacService) {
        this.rbacService = rbacService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getRoles() {
        List<RoleResponse> response = rbacService.getRoles();
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<RoleResponse>> createRole(@Valid @RequestBody CreateRoleRequest request) {
        RoleResponse response = rbacService.createRole(request);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.CREATED, response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> updateRole(@PathVariable("id") Long id,
            @Valid @RequestBody UpdateRoleRequest request) {
        RoleResponse response = rbacService.updateRole(id, request);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> patchRole(@PathVariable("id") Long id,
            @Valid @RequestBody PatchRoleRequest request) {
        RoleResponse response = rbacService.patchRole(id, request);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteRole(@PathVariable Long id) {
        rbacService.deleteRole(id);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, null));
    }
}