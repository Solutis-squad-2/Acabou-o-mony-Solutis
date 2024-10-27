package br.com.acaboumony.account.controller;

import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.dto.GetAccountDTO;
import br.com.acaboumony.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/{email}")
    @Operation(summary = "Detalhar uma conta", description = "Retorna os detalhes de uma conta específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Conta encontrada."),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada.")
    })
    public ResponseEntity<GetAccountDTO> detalhar(@PathVariable String email) {
        try {
            System.out.println("Buscando conta para o e-mail: " + email);
            GetAccountDTO account = accountService.findAccount(email);
            return ResponseEntity.ok(account);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Retorna 404 se não encontrado
        }
    }
    @GetMapping("/list")
    @Operation(summary = "Listar Contas", description = "Retorna uma lista paginada da Conta.")
    @ApiResponse(responseCode = "200", description = "Lista das Contas.")
    public ResponseEntity<Page<GetAccountDTO>> listar(@PageableDefault(size = 20, sort = {"nome"}) Pageable paginacao) {
        var accounts = accountService.listAccount(paginacao);
        return ResponseEntity.ok(accounts);
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastrar uma nova conta", description = "Cria uma nova conta com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos.")
    })
    public ResponseEntity<?> register(
            @RequestBody @Valid AccountDTO accountDTO
    ) {
        var account = accountService.createAccount(accountDTO);
        GetAccountDTO g = new GetAccountDTO(account);
        return ResponseEntity.status(HttpStatus.CREATED).body("Parabéns! "+ g.nome()
                + " sua conta foi criada com sucesso.");
    }
    @Transactional
    @PatchMapping("/patch/{uuid}")
    @Operation(summary = "Atualizar a conta", description = "Atualizar os dados da conta com os dados fornecidos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta atualizada com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados de atualização inválidos."),
            @ApiResponse(responseCode = "404", description = "A conta não foi encontrada.")
    })
    public ResponseEntity<?> patch(
            @PathVariable UUID uuid,
            @RequestBody @Valid GetAccountDTO accountDTO
    ) {

        var account = accountService.patchAccount(uuid,accountDTO);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma conta da base de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Conta deletada com sucesso."),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada.")
    })
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        accountService.deleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
