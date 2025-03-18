package com.example.store.config.requests;

import com.example.store.exception.CustomerNotFoundException;
import com.example.store.exception.ProductNotFoundException;
import com.example.store.model.ExceptionResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class BadRequest {

    @ExceptionHandler({CustomerNotFoundException.class})
    @ResponseBody
    public ResponseEntity<ExceptionResponse> customerNotFound(CustomerNotFoundException ex) {
        log.debug("BadRequest_badRequest : " + ex.getMessage());

        ExceptionResponse response = new ExceptionResponse();
        response.setStatus("Bad Request");
        response.setMessage(ex.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ProductNotFoundException.class})
    @ResponseBody
    public ResponseEntity<ExceptionResponse> productNotFound(ProductNotFoundException ex) {
        log.debug("BadRequest_badRequest : " + ex.getMessage());

        ExceptionResponse response = new ExceptionResponse();
        response.setStatus("Bad Request");
        response.setMessage(ex.getMessage());

        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.BAD_REQUEST);
    }
}
