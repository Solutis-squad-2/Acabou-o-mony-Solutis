package br.com.acaboumony.account.controller;

import br.com.acaboumony.account.model.dto.*;
import br.com.acaboumony.account.model.entity.Account;
import br.com.acaboumony.account.service.AccountService;
import br.com.acaboumony.account.service.CodigoService;
import br.com.acaboumony.account.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CodigoService codigoService;

    @Autowired
    public AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginAccountDTO login) {
        try {
            GetAccountDTO accountDTO = accountService.findAccountTeste(login.email());
            UsernamePasswordAuthenticationToken user =
                    new UsernamePasswordAuthenticationToken(login.email(), login.password());

            Authentication authentication = this.authenticationManager.authenticate(user);

            var userAuth = (Account) authentication.getPrincipal();
            String codigoVerificacao = String.format("%06d", new Random().nextInt(1000000));
            codigoService.salvarCodigoParaUsuario(userAuth.getEmail(), codigoVerificacao);

            ConfirmacaoCodigoDTO message = new ConfirmacaoCodigoDTO(userAuth.getEmail(), codigoVerificacao);
            rabbitTemplate.convertAndSend("Queue.send.emails", message);

            return ResponseEntity.ok("Código de verificação enviado para o seu e-mail.");
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("");
        }
    }


    @PostMapping("/confirmar-codigo")
    public ResponseEntity<?> confirmarCodigo(@RequestBody CodigoDTO confirmacao) {
        String email = codigoService.obterCodigoParaUsuario(confirmacao.codigo());

        if (email != null ) {
            GetAccountDTO g =  accountService.findAccount(email);
            Account userAuth = new Account(g);
            String token = tokenService.gerarToken(userAuth);

            codigoService.removerCodigoParaUsuario(email);

            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Código de verificação incorreto ou expirou.");
        }
    }

    /*@PostMapping("/login")
    public String login(@RequestBody LoginAccountDTO login){
        UsernamePasswordAuthenticationToken user =
        new UsernamePasswordAuthenticationToken(login.email(),login.password());

        Authentication authentication = this.authenticationManager.authenticate(user);

        var userAuth = (Account) authentication.getPrincipal();
        String token = tokenService.gerarToken(userAuth);

        return token;
    }*/

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
    @ResponseStatus(HttpStatus.CREATED)
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
