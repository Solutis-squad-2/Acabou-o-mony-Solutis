package pedidos.demo.GlobalException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        // Criando uma nova instância de ErrorResponse com os detalhes do erro
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(), // status
                ex.getMessage(),              // mensagem de erro
                "ID não encontrado no banco de dados", // descrição do erro
                System.currentTimeMillis()    // timestamp
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
