package com.n11.userservice.service.entityservice;

import com.n11.userservice.entity.User;
import com.n11.userservice.common.BaseEntityService;
import com.n11.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserEntityService extends BaseEntityService<User, UserRepository> {
    protected UserEntityService(UserRepository repository) {
        super(repository);
    }
}
