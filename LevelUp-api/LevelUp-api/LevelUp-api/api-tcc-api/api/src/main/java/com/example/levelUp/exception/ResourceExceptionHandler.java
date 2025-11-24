package com.example.levelUp.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice // <--- Diz ao Spring: "Eu cuido dos erros de todos os Controllers"
public class ResourceExceptionHandler {

    // 1. Trata o seu erro personalizado (404 Not Found)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> entityNotFound(RecursoNaoEncontradoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND; // 404

        ErroPadrao err = new ErroPadrao();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Recurso não encontrado");
        err.setMessage(e.getMessage()); // A mensagem que você escreveu no Service vem pra cá
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 2. Trata erros de Validação (@Valid, @NotBlank, @Size) - 400 Bad Request
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST; // 400

        // Lógica para pegar todos os campos inválidos e juntar numa mensagem só
        Map<String, String> erros = new HashMap<>();
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            erros.put(f.getField(), f.getDefaultMessage());
        }

        ErroPadrao err = new ErroPadrao();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Erro de Validação");
        err.setMessage(erros.toString()); // Ex: {senha=senha fraca, email=email inválido}
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 3. Trata erros genéricos (NullPointer, Banco caiu, etc) - 500 Internal Server
    // Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> database(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; // 500

        ErroPadrao err = new ErroPadrao();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Erro Interno do Servidor");
        err.setMessage("Ocorreu um erro inesperado. Contate o suporte.");
        err.setPath(request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}