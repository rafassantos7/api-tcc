package com.example.levelUp.exception;

import java.time.Instant;

public record ErroPadrao(
        Instant timestamp,
        Integer status,
        String error,
        String message,
        String path) {
}