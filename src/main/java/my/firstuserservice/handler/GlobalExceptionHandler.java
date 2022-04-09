package my.firstuserservice.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = {NoSuchElementException.class})
    public ResponseEntity<?> handleException1(Exception e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .body(List.of(Map.of("message", e.getMessage())));
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<?> handleException2(Exception e) {
        return ResponseEntity
                .badRequest()
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .body(List.of(Map.of("message", e.getMessage())));
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<?> handleException3(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
                e.getFieldErrors().stream()
                        .map(f -> Map.of(
                                "message",
                                f.getDefaultMessage()
                        ))
                        .collect(Collectors.toList()));
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<?> handleException4(Exception e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                .body(Map.of("message", e.getMessage()));
    }
}
