package com.n11.userservice.controller.contract;

import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.request.CreateUserRequest;
import com.n11.userservice.request.UpdateUserRequest;

import java.util.List;

public interface UserControllerContract {
    UserDTO createUser(CreateUserRequest request);

    UserDTO updateUser(Long id, UpdateUserRequest request);

    UserDTO getUser(Long id);

    List<UserDTO> getAllUsers(int page, int pageLimit, String sortBy, String sortDir);

    void deleteUser(Long id);
}
