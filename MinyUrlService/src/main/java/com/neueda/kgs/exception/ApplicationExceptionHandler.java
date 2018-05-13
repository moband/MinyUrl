package com.neueda.kgs.exception;

import com.neueda.kgs.controller.dto.BaseResponse;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;


/**
 * REST controller for managing custom errors
 *
 * @author MohammadReza Alagheband
 */
@ControllerAdvice
public class ApplicationExceptionHandler {
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleWorkerNotFoundException(WorkerNotFoundException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, "Worker identifier not found.", BaseResponse.BAD_REQUEST));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleKeyNotFoundException(KeyNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new BaseResponse(false, "Invalid Address.", BaseResponse.RESOURCE_NOT_FOUND));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleInvalidUrlException(MalformedURLException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, "Invalid Url format.", BaseResponse.BAD_REQUEST));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleInvalidUrlException(URISyntaxException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, "Invalid Url format.", BaseResponse.BAD_REQUEST));
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleInvalidAddressException(InvalidAddressException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, "Invalid Address.", BaseResponse.RESOURCE_NOT_FOUND));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)  // 400
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleUnknownHostException(UnknownHostException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, "Worker identifier not found.", BaseResponse.BAD_REQUEST));
    }


    @ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)  // 507
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleKeyOverFlowException(KeyOverFlowException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, "No key available to allocate.", BaseResponse.INSUFFICIENT_KEY));
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler
    protected ResponseEntity<BaseResponse> handleOptimisticLockingFailureException(OptimisticLockingFailureException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse(false, "Service is too busy,Please try again later. ", BaseResponse.SERVER_TOO_BUSY));
    }

}
