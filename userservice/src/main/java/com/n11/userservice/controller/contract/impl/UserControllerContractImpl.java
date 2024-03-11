package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.controller.contract.UserControllerContract;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.mapper.UserMapper;
import com.n11.userservice.request.CreateUserRequest;
import com.n11.userservice.request.UpdateUserRequest;
import com.n11.userservice.service.entityservice.UserEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserControllerContractImpl implements UserControllerContract {
    private final UserEntityService userEntityService;

    @Override
    public UserDTO createUser(CreateUserRequest request) {
        User user = UserMapper.INSTANCE.convertToUser(request);
        user = userEntityService.save(user);
        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public UserDTO updateUser(Long id, UpdateUserRequest request) {
        User user = userEntityService.findByIdWithControl(id);
        UserMapper.INSTANCE.updateUserFields(user, request);
        user = userEntityService.save(user);
        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public List<UserDTO> getAllUsers(int page, int pageLimit, String sortBy, String sortDir) {
        List<User> users = userEntityService.findAll(page, pageLimit, sortBy, sortDir);
        return UserMapper.INSTANCE.convertToUserDTOList(users);
    }

    @Override
    public UserDTO getUser(Long id) {
        User user = userEntityService.findByIdWithControl(id);
        return UserMapper.INSTANCE.convertToUserDTO(user);
    }

    @Override
    public void deleteUser(Long id) {
        userEntityService.delete(id);
    }
}
