package br.com.acaboumony.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(Exception.class)
        public ResponseEntity<?> globalExceptionHandler(Exception ex, WebRequest request) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        @ExceptionHandler(AccountException.class)
        public ResponseEntity<?> unauthorizedExceptionHandler(AccountException ex, WebRequest request) {
            return new ResponseEntity<>("Dados invalidos " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
        @ExceptionHandler(ConflictException.class)
        public ResponseEntity<?> handleConflictException(ConflictException ex, WebRequest request) {
            return new ResponseEntity<>("CPF ou EMAIL já cadastrado: " + ex.getMessage(), HttpStatus.CONFLICT);
        }
        @ExceptionHandler(BadCredentialsException.class)
        public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
            return new ResponseEntity<>("Senha incorreta!", HttpStatus.UNAUTHORIZED);
        }
        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<String> handleNotFoundException(NotFoundException ex, WebRequest request) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
}
