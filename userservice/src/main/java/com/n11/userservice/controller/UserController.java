package com.n11.userservice.controller;

import com.n11.userservice.common.RestResponse;
import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.request.CreateUserRequest;
import com.n11.userservice.request.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "The User API")
public class UserController {
    private final UserControllerContract userControllerContract;

    @GetMapping
    @Operation(summary = "Get all users")
    public ResponseEntity<RestResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userControllerContract.getAllUsers(0, 10, "id", "asc");
        return ResponseEntity.ok(RestResponse.of(users));
    }

    @PostMapping
    @Operation(summary = "Create a user")
    public ResponseEntity<RestResponse<UserDTO>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO user = userControllerContract.createUser(request);
        return ResponseEntity.ok(RestResponse.of(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    public ResponseEntity<RestResponse<UserDTO>> updateUser(@Positive @PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        UserDTO user = userControllerContract.updateUser(id, request);
        return ResponseEntity.ok(RestResponse.of(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<RestResponse<Void>> deleteUser(@Positive @PathVariable Long id) {
        userControllerContract.deleteUser(id);
        return ResponseEntity.ok(RestResponse.of(null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id")
    public ResponseEntity<RestResponse<UserDTO>> getUser(@Positive @PathVariable Long id) {
        UserDTO user = userControllerContract.getUser(id);
        return ResponseEntity.ok(RestResponse.of(user));
    }
}
