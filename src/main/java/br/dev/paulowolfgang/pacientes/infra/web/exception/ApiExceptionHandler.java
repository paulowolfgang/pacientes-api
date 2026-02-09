package br.dev.paulowolfgang.pacientes.infra.web.exception;

import br.dev.paulowolfgang.pacientes.app.exception.CpfAlreadyExistsException;
import br.dev.paulowolfgang.pacientes.app.exception.PacienteNotFoundException;
import br.dev.paulowolfgang.pacientes.domain.exception.DomainException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler
{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiValidationError> handleValidation(MethodArgumentNotValidException ex,
                                                               HttpServletRequest req)
    {

        List<ValidationError> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new ValidationError(fe.getField(), fe.getDefaultMessage()))
                .sorted(Comparator.comparing(ValidationError::field))
                .toList();

        ApiValidationError body = new ApiValidationError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Ocorreu uma falha de validação.",
                req.getRequestURI(),
                errors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }

    @ExceptionHandler(PacienteNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(PacienteNotFoundException ex, HttpServletRequest req)
    {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(CpfAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleConflict(CpfAlreadyExistsException ex, HttpServletRequest req)
    {
        return build(HttpStatus.CONFLICT, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiError> handleBadRequest(DomainException ex, HttpServletRequest req)
    {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex, HttpServletRequest req)
    {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Ocorreu um erro inesperado.", req.getRequestURI());
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String message, String path) {
        ApiError body = new ApiError(
                Instant.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );

        return ResponseEntity.status(status).body(body);
    }
}
