package pacman.plantmarket.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandling {
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String,String>> usernameNotFoundExceptionHandler(UsernameNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message",e.getMessage()));
    }

    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseEntity<Map<String,String>> noSuchElementException(NoSuchElementException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("message",e.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String,String>> illegalArgumentException(IllegalArgumentException e){
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(Map.of("message",e.getMessage()));
    }
}
