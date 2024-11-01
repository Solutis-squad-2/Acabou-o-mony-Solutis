package br.com.acaboumony.gateway.teste;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
public class teste {
    @GetMapping("/list")
    @Operation(summary = "Listar Contas", description = "Retorna uma lista paginada da Conta.")
    @ApiResponse(responseCode = "200", description = "Lista das Contas.")
    public ResponseEntity<String> listar() {

        return ResponseEntity.ok("aaaa");
    }
}
