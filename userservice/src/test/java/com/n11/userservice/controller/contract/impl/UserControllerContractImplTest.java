package com.n11.userservice.controller.contract.impl;

import com.n11.userservice.data.UserData;
import com.n11.userservice.dto.RecommendationDTO;
import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.request.CreateUserRequest;
import com.n11.userservice.request.UpdateUserRequest;
import com.n11.userservice.service.RecommendationService;
import com.n11.userservice.service.entityservice.UserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserControllerContractImplTest {
    @Mock
    private UserEntityService userService;

    @InjectMocks
    private UserControllerContractImpl userControllerContractImpl;

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor;

    @Test
    void shouldReturnAllUsers(){
        // given
        int page = 0;
        int size = 10;
        String sort = "id";
        String direction = "asc";

        List<User> expectedUsers = UserData.users();
        Page<User> expectedPage = new PageImpl<>(expectedUsers);

        // when
        Mockito.when(userService.findAll(page, size, sort, direction)).thenReturn(expectedPage);
        List<UserDTO> users = userControllerContractImpl.getAllUsers(page, size, sort, direction);

        InOrder inOrder = Mockito.inOrder(userService);
        inOrder.verify(userService).findAll(page, size, sort, direction);
        inOrder.verifyNoMoreInteractions();

        // then
        assertEquals(expectedUsers.size(), users.size());

        for (int i = 0; i < expectedUsers.size(); i++) {
            User expectedUser = expectedUsers.get(i);
            UserDTO actualUserDTO = users.get(i);

            assertEquals(expectedUser.getId(), actualUserDTO.id());
            assertEquals(expectedUser.getName(), actualUserDTO.name());
            assertEquals(expectedUser.getSurname(), actualUserDTO.surname());
            assertEquals(expectedUser.getEmail(), actualUserDTO.email());
            assertEquals(expectedUser.getBirthDate(), actualUserDTO.birthDate());
        }
    }

    @Test
    void shouldReturnUserById(){
        // given
        Long id = 100L;
        User expectedUser = UserData.user();

        // when
        Mockito.when(userService.findByIdWithControl(id)).thenReturn(expectedUser);
        UserDTO user = userControllerContractImpl.getUser(id);

        InOrder inOrder = Mockito.inOrder(userService);
        inOrder.verify(userService).findByIdWithControl(id);
        inOrder.verifyNoMoreInteractions();

        // then
        assertEquals(expectedUser.getId(), user.id());
        assertEquals(expectedUser.getName(), user.name());
        assertEquals(expectedUser.getSurname(), user.surname());
        assertEquals(expectedUser.getEmail(), user.email());
        assertEquals(expectedUser.getBirthDate(), user.birthDate());
    }

    @Test
    void shouldUpdateUser(){
        // given
        Long id = 100L;
        User expectedUser = UserData.user();
        UserDTO expectedUserDTO = UserData.userDTO();

        // when
        Mockito.when(userService.findByIdWithControl(id)).thenReturn(expectedUser);
        Mockito.when(userService.save(expectedUser)).thenReturn(expectedUser);
        UpdateUserRequest request = new UpdateUserRequest(
                expectedUser.getName(),
                expectedUser.getSurname(),
                expectedUser.getUsername(),
                expectedUser.getEmail(),
                expectedUser.getPhoneNumber(),
                expectedUser.getBirthDate(),
                expectedUser.getLatitude(),
                expectedUser.getLongitude(),
                expectedUser.getStatus()
        );
        UserDTO user = userControllerContractImpl.updateUser(id, request);

        InOrder inOrder = Mockito.inOrder(userService);
        inOrder.verify(userService).findByIdWithControl(id);
        inOrder.verify(userService).save(expectedUser);
        inOrder.verifyNoMoreInteractions();

        // then
        assertEquals(expectedUser.getId(), user.id());
        assertEquals(expectedUser.getName(), user.name());
        assertEquals(expectedUser.getSurname(), user.surname());
        assertEquals(expectedUser.getEmail(), user.email());
        assertEquals(expectedUser.getBirthDate(), user.birthDate());
    }

    @Test
    void shouldDeleteUser(){
        // given
        Long id = 100L;

        // when
        userControllerContractImpl.deleteUser(id);

        InOrder inOrder = Mockito.inOrder(userService);
        inOrder.verify(userService).delete(id);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    void shouldCreateUser() {
        // given
        User expectedUser = UserData.user();

        // when
        Mockito.when(userService.save(ArgumentMatchers.any(User.class))).thenReturn(expectedUser);

        CreateUserRequest request = new CreateUserRequest(
                expectedUser.getName(),
                expectedUser.getSurname(),
                expectedUser.getUsername(),
                expectedUser.getEmail(),
                expectedUser.getPhoneNumber(),
                expectedUser.getBirthDate(),
                expectedUser.getLatitude(),
                expectedUser.getLongitude()
        );
        UserDTO user = userControllerContractImpl.createUser(request);

        InOrder inOrder = Mockito.inOrder(userService);
        inOrder.verify(userService).save(ArgumentMatchers.any(User.class));
        inOrder.verifyNoMoreInteractions();

        // then
        assertEquals(expectedUser.getId(), user.id());
        assertEquals(expectedUser.getName(), user.name());
        assertEquals(expectedUser.getSurname(), user.surname());
        assertEquals(expectedUser.getEmail(), user.email());
        assertEquals(expectedUser.getBirthDate(), user.birthDate());
    }


}
