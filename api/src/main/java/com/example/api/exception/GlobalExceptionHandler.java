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
import org.springframework.security.core.AuthenticationException;
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

        Map<String, List<String>> errors = ex.getBindingResult()
                                          .getFieldErrors()
                                          .stream()
                                          .collect(Collectors.groupingBy(FieldError::getField))
                                          .entrySet()
                                          .stream()
                                          .collect(
                                            Collectors.toMap(
                                                Map.Entry::getKey, 
                                                value -> value.getValue()
                                                    .stream()
                                                    .map(FieldError::getDefaultMessage)
                                                    .collect(Collectors.toList())
                                            )
                                          );
        return new ResponseEntity<>(
            errors,
            new HttpHeaders(), 
            HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ErrorRuntimeException.class)
    public final ResponseEntity<Map<String, List<String>>> handleErrorRuntimeExceptions(ErrorRuntimeException ex) {
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("errors", List.of(ex.getMessage())), new HttpHeaders(), HttpStatus.BAD_REQUEST);
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

    /*@ExceptionHandler(BadCredentialsException.class)
    private final ResponseEntity<Map<String, List<String>>> badCredentialsException(BadCredentialsException ex){
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("errors", List.of("email ou senha incorretos")), HttpStatus.BAD_REQUEST);
    }*/

    @ExceptionHandler(JWTVerificationException.class)
    private final ResponseEntity<String> jwtException(JWTVerificationException ex){
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>("erro interno", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public final ResponseEntity<Map<String, List<String>>> DataNotFoundException(DataNotFoundException ex) {
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("errors", Arrays.asList(ex.getMessage())), new HttpHeaders(), HttpStatus.UNAUTHORIZED);
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
    public final ResponseEntity<Map<String, List<String>>> handleBadRequest(BadRequestException ex){
        LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        return new ResponseEntity<>(Map.of("errors", Arrays.asList(ex.getMessage())),new HttpHeaders(),HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ AuthenticationException.class })
    public ResponseEntity<?> handleAuthenticationException(Exception ex) {
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }
}
