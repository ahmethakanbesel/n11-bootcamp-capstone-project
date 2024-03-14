package com.n11.userservice.common;

import com.n11.userservice.exceptions.BadRequestException;
import com.n11.userservice.exceptions.ItemNotFoundException;
import com.n11.userservice.exceptions.ResourceNotFoundException;
import com.n11.userservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
public class GeneralControllerAdvice extends ResponseEntityExceptionHandler {
    private final KafkaProducerService kafkaProducerService;
    private static final String ERRORS_500_TOPIC = "ERRORS_500";
    private static final String ERRORS_404_TOPIC = "ERRORS_404";
    private static final String ERRORS_400_TOPIC = "ERRORS_400";

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllExceptions(Exception e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_500_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleAllExceptions(TransactionSystemException e, WebRequest request) {
        var originalException = e.getOriginalException();
        if (originalException == null) {
            return handleAllExceptions(e, request);
        }

        String message = originalException.getCause().getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_500_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTExceptions(ItemNotFoundException e, WebRequest request) {
        String message = e.getBaseErrorMessage().getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_404_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTExceptions(N11BusinessException e, WebRequest request) {
        String message = e.getBaseErrorMessage().getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_500_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTExceptions(BadRequestException e, WebRequest request) {
        String message = e.getBaseErrorMessage().getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_400_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTExceptions(ResourceNotFoundException e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_404_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTExceptions(DataIntegrityViolationException e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_400_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTExceptions(ConstraintViolationException e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_400_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public final ResponseEntity<Object> handleRTEExceptions(jakarta.validation.ConstraintViolationException e, WebRequest request) {
        String message = e.getMessage();
        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), message, description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_400_TOPIC, message);

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {
        List<Map<String, String>> errorList = e.getBindingResult().getFieldErrors().stream()
                .map(fieldError ->
                {
                    Map<String, String> errorMap = new HashMap<>();
                    errorMap.put(fieldError.getField(),
                            fieldError.getDefaultMessage());
                    return errorMap;
                }).toList();

        String description = request.getDescription(false);

        var generalErrorMessages = new GeneralErrorMessages(LocalDateTime.now(), errorList.toString(), description);
        var restResponse = RestResponse.error(generalErrorMessages);

        kafkaProducerService.sendMessage(ERRORS_400_TOPIC, e.getMessage());

        return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }

}
