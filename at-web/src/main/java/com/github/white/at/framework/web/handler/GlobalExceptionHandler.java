package com.github.white.at.framework.web.handler;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.github.white.at.framework.web.ApiResult;
import com.github.white.at.framework.web.ResponseCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult methodArgumentHandler(HttpServletResponse response, MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        BindingResult result = ex.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(error.getDefaultMessage()).append(System.lineSeparator());
        }
        return ApiResult.error(ResponseCode.PARAMETER_ERROR.getCode(), builder.toString());
    }

    @ExceptionHandler(BindException.class)
    public ApiResult bindExceptionHandler(HttpServletResponse response, BindException ex) {
        log.error(ex.getMessage(), ex);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        List<ObjectError> errors = ex.getAllErrors();
        StringBuilder errorMsg = new StringBuilder();
        errors.forEach(x -> errorMsg.append(x.getDefaultMessage()).append(System.lineSeparator()));
        return ApiResult.error(ResponseCode.PARAMETER_ERROR.getCode(), errorMsg.toString());
    }
}
