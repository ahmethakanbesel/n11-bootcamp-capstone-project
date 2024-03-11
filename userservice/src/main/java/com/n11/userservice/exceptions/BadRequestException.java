package com.n11.userservice.exceptions;

import com.n11.userservice.common.BaseErrorMessage;
import com.n11.userservice.common.N11BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends N11BusinessException {
    public BadRequestException(BaseErrorMessage baseErrorMessage) {
        super(baseErrorMessage);
    }
}

