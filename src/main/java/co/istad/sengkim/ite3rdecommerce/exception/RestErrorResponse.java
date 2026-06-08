package co.istad.sengkim.ite3rdecommerce.exception;

import lombok.Builder;

import java.time.Instant;

@Builder
public record RestErrorResponse <T> (
        String message,
        Integer code,
        String status,
        Instant timestamp,
        T errors
){
}
