package com.n11.userservice.controller;

import com.n11.userservice.common.RestResponse;
import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.request.CreateUserRequest;
import com.n11.userservice.request.UpdateUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
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
    public ResponseEntity<RestResponse<List<UserDTO>>> getAllUsers(
            @RequestParam(value = "page", defaultValue = "0")
            @Range(min = 0, max = 1000, message = "Page number must be between 0 and 1000")
            @Schema(description = "Page number", example = "0", minimum = "0", maximum = "1000")
            int page,
            @RequestParam(value = "size", defaultValue = "10")
            @Range(min = 1, max = 100, message = "Page size must be between 1 and 100")
            @Schema(description = "Page size", example = "10", minimum = "1", maximum = "100")
            int size,
            @RequestParam(value = "sortBy", defaultValue = "id")
            @Pattern(regexp = "^(id|name|surname)$", message = "Sort by must be one of 'id', 'name', 'surname'")
            @Schema(description = "Sort by", example = "id", allowableValues = {"id", "name", "surname"})
            String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc")
            @Pattern(regexp = "^(asc|desc)$", message = "Sort direction must be one of 'asc', 'desc'")
            @Schema(description = "Sort direction", example = "asc", allowableValues = {"asc", "desc"})
            String sortDir
    ) {
        List<UserDTO> users = userControllerContract.getAllUsers(page, size, sortBy, sortDir);
        return ResponseEntity.ok(RestResponse.of(users));
    }

    @PostMapping
    @Operation(summary = "Create a user")
    public ResponseEntity<RestResponse<UserDTO>> createUser(
            @Valid @RequestBody CreateUserRequest request
    ) {
        UserDTO user = userControllerContract.createUser(request);
        return ResponseEntity.ok(RestResponse.of(user));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    public ResponseEntity<RestResponse<UserDTO>> updateUser(
            @Positive @PathVariable @Schema(description = "User id", example = "100", type = "number") Long id,
            @Valid @RequestBody UpdateUserRequest request
    ) {
        UserDTO user = userControllerContract.updateUser(id, request);
        return ResponseEntity.ok(RestResponse.of(user));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    public ResponseEntity<RestResponse<Void>> deleteUser(
            @Positive @PathVariable @Schema(description = "User id", example = "100", type = "number") Long id) {
        userControllerContract.deleteUser(id);
        return ResponseEntity.ok(RestResponse.of(null));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a user by id")
    public ResponseEntity<RestResponse<UserDTO>> getUser(
            @Positive @PathVariable @Schema(description = "User id", example = "100", type = "number") Long id
    ) {
        UserDTO user = userControllerContract.getUser(id);
        return ResponseEntity.ok(RestResponse.of(user));
    }
}
