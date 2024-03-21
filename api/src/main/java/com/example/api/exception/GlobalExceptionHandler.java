package com.example.api.exception;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);

        List<String> errors = ex.getBindingResult()
                                .getFieldErrors()
                                .stream()
                                .map(FieldError::getDefaultMessage)
                                .collect(Collectors.toList());
        return new ResponseEntity<>(
            Map.of("errors", errors),
            new HttpHeaders(), 
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleRuntimeExceptions(RuntimeException ex) {
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("errors", List.of("erro ao processar solicitação")), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(JWTDecodeException.class)
    private final ResponseEntity<String> jwtException(JWTDecodeException ex){
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>("erro interno", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    private final ResponseEntity<String> jwtException(JWTVerificationException ex){
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>("erro interno", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<String> DataNotFoundException(DataNotFoundException ex) {
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Map<String, List<String>>> handleGeneralExceptions(Exception ex) {
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("errors", Arrays.asList("erro ao processar solicitação")), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public final ResponseEntity<Map<String,String>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        String erro = "campo " + ex.getName() + " deve ser do tipo " + ex.getRequiredType().getSimpleName();
        return new ResponseEntity<>(Map.of("errors",erro), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<?> handleBadRequest(BadRequestException ex){
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>(ex.getMessage(),new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }
}
