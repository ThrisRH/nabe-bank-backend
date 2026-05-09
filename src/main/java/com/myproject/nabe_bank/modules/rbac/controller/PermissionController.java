package com.myproject.nabe_bank.modules.rbac.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myproject.nabe_bank.core.exception.ApiResponse;
import com.myproject.nabe_bank.core.response.ResponseCode;
import com.myproject.nabe_bank.modules.rbac.dto.request.permission.CreatePermissionRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.permission.PatchPermissionRequest;
import com.myproject.nabe_bank.modules.rbac.dto.request.permission.UpdatePermissionRequest;
import com.myproject.nabe_bank.modules.rbac.dto.response.permission.PermissionResponse;
import com.myproject.nabe_bank.modules.rbac.service.RbacService;

@RestController
@RequestMapping("/api/v1/permission")
public class PermissionController {
    private final RbacService rbacService;

    public PermissionController(RbacService rbacService) {
        this.rbacService = rbacService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getPermissions() {
        List<PermissionResponse> response = rbacService.getPermissions();
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @RequestBody CreatePermissionRequest request) {
        PermissionResponse response = rbacService.createPermission(request);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.CREATED, response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(
            @PathVariable("id") Long id, @RequestBody UpdatePermissionRequest request) {
        PermissionResponse response = rbacService.updatePermission(id, request);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> patchPermission(
            @PathVariable("id") Long id, @RequestBody PatchPermissionRequest request) {
        PermissionResponse response = rbacService.patchPermission(id, request);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePermission(
            @PathVariable("id") Long id) {
        rbacService.deletePermission(id);
        return ResponseEntity.ok(new ApiResponse<>(ResponseCode.SUCCESS, null));
    }
}
