package com.stockmanager.stockmanager_be.exception;

import com.stockmanager.stockmanager_be.constant.CommonMessageConstant;
import com.stockmanager.stockmanager_be.dto.response.ResponseEntityDto;
import com.stockmanager.stockmanager_be.type.ResponseStatus;
import com.stockmanager.stockmanager_be.util.MessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final MessageUtil messageUtil;
    private final HttpServletRequest request;

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ResponseEntityDto> handleCategoryNotFoundException(CategoryNotFoundException e) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_ERROR_CATEGORY_NOT_FOUND);
        log.error(message, e);

        return new ResponseEntity<>(
                new ResponseEntityDto(ResponseStatus.UNSUCCESSFUL, message), status
        );
    }

    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<ResponseEntityDto> handleCategoryAlreadyExistsException(DuplicateCategoryException e) {

        HttpStatus status = HttpStatus.CONFLICT;
        String message = messageUtil.getMessage(CommonMessageConstant.COMMON_ERROR_CATEGORY_ALREADY_EXISTS);
        log.error(message, e);

        return new ResponseEntity<>(
                new ResponseEntityDto(ResponseStatus.UNSUCCESSFUL, message), status
        );
    }


}
