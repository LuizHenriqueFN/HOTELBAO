package com.example.hotelbao.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> handleValidacaoException(ValidacaoException ex) {
        // Cria um corpo de resposta JSON com a mensagem da nossa exceção
        Map<String, Object> body = Map.of(
                "timestamp", System.currentTimeMillis(),
                "status", HttpStatus.BAD_REQUEST.value(),
                "error", "Validation Error",
                "message", ex.getMessage());

        // Retorna a resposta com o status 400 Bad Request
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}