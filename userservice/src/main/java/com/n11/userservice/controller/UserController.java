package com.n11.userservice.controller;

import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.common.RestResponse;
import com.n11.userservice.request.CreateUserRequest;
import com.n11.userservice.request.UpdateUserRequest;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserControllerContract userControllerContract;

    public UserController(UserControllerContract userControllerContract) {
        this.userControllerContract = userControllerContract;
    }

    @GetMapping
    public ResponseEntity<RestResponse<List<UserDTO>>> getAllUsers() {
        List<UserDTO> users = userControllerContract.getAllUsers(0, 10, "id", "asc");
        return ResponseEntity.ok(RestResponse.of(users));
    }

    @PostMapping
    public ResponseEntity<RestResponse<UserDTO>> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserDTO user = userControllerContract.createUser(request);
        return ResponseEntity.ok(RestResponse.of(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestResponse<UserDTO>> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        UserDTO user = userControllerContract.updateUser(id, request);
        return ResponseEntity.ok(RestResponse.of(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse<Void>> deleteUser(@PathVariable Long id) {
        userControllerContract.deleteUser(id);
        return ResponseEntity.ok(RestResponse.of(null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestResponse<UserDTO>> getUser(@PathVariable Long id) {
        UserDTO user = userControllerContract.getUser(id);
        return ResponseEntity.ok(RestResponse.of(user));
    }
}
