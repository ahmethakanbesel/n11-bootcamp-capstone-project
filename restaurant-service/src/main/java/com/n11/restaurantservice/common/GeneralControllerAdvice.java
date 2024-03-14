package com.n11.restaurantservice.common;

import com.n11.restaurantservice.exception.ResourceNotFoundException;
import com.n11.restaurantservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class GeneralControllerAdvice extends ResponseEntityExceptionHandler {
    private static final String ERRORS_500_TOPIC = "ERRORS_500";
    private static final String ERRORS_404_TOPIC = "ERRORS_404";
    private static final String ERRORS_400_TOPIC = "ERRORS_400";
    private final KafkaProducerService kafkaProducerService;

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GenericErrorMessage(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_500_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTExceptions(ResourceNotFoundException e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GenericErrorMessage(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_404_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException e, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GenericErrorMessage(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_400_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTExceptions(DataIntegrityViolationException e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GenericErrorMessage(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_400_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTExceptions(ConstraintViolationException e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GenericErrorMessage(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_400_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
}

