package com.example.api.exception;

public class ErrorRuntimeException extends RuntimeException{
    public ErrorRuntimeException(String msg){
        super(msg);
    }
}
