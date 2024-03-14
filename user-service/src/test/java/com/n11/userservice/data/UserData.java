package com.n11.userservice.data;

import com.n11.userservice.dto.UserDTO;
import com.n11.userservice.entity.User;
import com.n11.userservice.enums.UserStatus;
import com.n11.userservice.mapper.UserMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserData {
    public static User user() {
        User user = new User();
        user.setId(1L);
        user.setName("Ayse");
        user.setSurname("Yilmaz");
        user.setUsername("ayse_yilmaz");
        user.setBirthDate(LocalDate.now().minusYears(1));
        user.setStatus(UserStatus.ACTIVE);
        user.setEmail("ayse@mail.com");
        user.setPhoneNumber("9123456789");
        user.setLatitude(41.0082);
        user.setLongitude(28.9784);
        return user;
    }

    public static List<User> users() {
        List<User> users = new ArrayList<>();
        users.add(user());
        for (int i = 2; i <= 5; i++) {
            User newUser = new User();
            newUser.setId((long) i);
            newUser.setName("User" + i);
            newUser.setSurname("Surname" + i);
            newUser.setUsername("user" + i);
            newUser.setBirthDate(LocalDate.now().minusYears(1));
            newUser.setStatus(UserStatus.ACTIVE);
            newUser.setEmail("user" + i + "@mail.com");
            newUser.setPhoneNumber("912345678" + i);
            newUser.setLatitude(41.0082 + i);
            newUser.setLongitude(28.9784 + i);
            users.add(newUser);
        }
        return users;
    }

    public static UserDTO userDTO() {
        return UserMapper.INSTANCE.convertToUserDTO(user());
    }
}
