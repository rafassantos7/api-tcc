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

@ControllerAdvice
public class ResourceExceptionHandler {

    // 1. Trata 404 (Não Encontrado)
    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErroPadrao> entityNotFound(RecursoNaoEncontradoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ErroPadrao err = new ErroPadrao(
                Instant.now(),
                status.value(),
                "Recurso não encontrado",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 2. Trata 409 (Conflito - Email já existe) - ADICIONADO AGORA
    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErroPadrao> emailConflict(EmailJaCadastradoException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;

        ErroPadrao err = new ErroPadrao(
                Instant.now(),
                status.value(),
                "Conflito de Dados",
                e.getMessage(),
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 3. Trata 400 (Erro de Validação dos campos)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadrao> validation(MethodArgumentNotValidException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        Map<String, String> erros = new HashMap<>();
        for (FieldError f : e.getBindingResult().getFieldErrors()) {
            erros.put(f.getField(), f.getDefaultMessage());
        }

        ErroPadrao err = new ErroPadrao(
                Instant.now(),
                status.value(),
                "Erro de Validação",
                erros.toString(), // Retorna algo como: {senha=fraca, email=invalido}
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    // 4. Trata 500 (Erro Genérico)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadrao> database(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ErroPadrao err = new ErroPadrao(
                Instant.now(),
                status.value(),
                "Erro Interno do Servidor",
                "Ocorreu um erro inesperado. Contate o suporte.",
                request.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }
}