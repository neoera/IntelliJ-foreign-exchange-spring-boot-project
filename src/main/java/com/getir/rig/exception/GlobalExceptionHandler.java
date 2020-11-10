package com.getir.rig.exception;

import com.getir.rig.util.BaseResponse;
import com.getir.rig.util.ValidationInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {
        final ExceptionInfo exceptionInfo = new ExceptionInfo("ERR","Business Exception");

        BaseResponse<?> response = new BaseResponse(false, exceptionInfo);
        response.setValidationInfos(ex.getBindingResult().getFieldErrors().stream().map(x ->
                ValidationInfo.builder().type(x.getField()).message(x.getDefaultMessage()).build()).collect(Collectors.toList()));

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}