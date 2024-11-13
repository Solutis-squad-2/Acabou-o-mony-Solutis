package br.com.acaboumony.account.service.impl;

import br.com.acaboumony.account.exception.AccountException;
import br.com.acaboumony.account.exception.ConflictException;
import br.com.acaboumony.account.exception.NotFoundException;
import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.dto.FindUUIDDto;
import br.com.acaboumony.account.model.dto.GetAccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import br.com.acaboumony.account.repository.AccountRepository;
import br.com.acaboumony.account.service.AccountService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Account createAccount(AccountDTO accountDTO) {
        if(accountRepository.findByEmail(accountDTO.email()).isPresent()){
            throw new ConflictException("Email já existe na nossa base de dados.");
        }
        if(accountRepository.findByCpf(accountDTO.cpf()).isPresent()){
            throw new ConflictException("CPF já registrado!");
        }
        Account account = new Account(accountDTO);
        account.setSenha(passwordEncoder.encode(accountDTO.senha()));
        try {
            return accountRepository.save(account);
        }catch (Exception e){
            throw new AccountException("Erro ao cadastrar a conta: "+ e.getMessage());
        }
    }

    @Override
    public GetAccountDTO findAccount(String email) {
        Account account = existsAccount(email);
        return new GetAccountDTO(account);
    }

    @Override
    public FindUUIDDto findUuid(String email) {
        Account account = existsAccount(email);
        return new FindUUIDDto(account);
    }

    @Override
    public GetAccountDTO findAccountTeste(String email) {
        Optional<Account> account = accountRepository.findByEmail(email);

        if(account.isEmpty()){
            throw new NotFoundException("Este email: "+email+" Não foi encontrado no" +
                    " nosso banco de dados");
        }
        return new GetAccountDTO(account.get());
    }

    @Override
    public String patchAccount(UUID uuid,GetAccountDTO accountDTO) {
        Account account = existsAccountWithUuid(uuid);


        StringBuilder message = new StringBuilder("Parabéns! Dados atualizados com sucesso!");

        String originalEmail = account.getEmail();
        String originalNome = account.getNome();
        String originalTelefone = account.getTelefone();
        String originalCpf = account.getCpf();

        account.patch(accountDTO);

        if (!originalEmail.equals(account.getEmail())) {
            message.append(" Email alterado.");
        }
        if (!originalNome.equals(account.getNome())) {
            message.append(" Nome alterado.");
        }
        if (!originalTelefone.equals(account.getTelefone())) {
            message.append(" Telefone alterado.");
        }
        if (!originalCpf.equals(account.getCpf())) {
            message.append(" CPF alterado.");
        }

        accountRepository.save(account);

        return message.toString();
    }

    @Override
    public void deleteAccount(UUID uuid) {
        Optional<Account> account = accountRepository.findByUuid(uuid);
        accountRepository.delete(account.get());
    }

    @Override
    public Page<GetAccountDTO> listAccount(Pageable pageable) {
        return accountRepository
                .findAll(pageable)
                .map(GetAccountDTO::new);
    }

    private Account existsAccount(String email){
        Optional<Account> account = accountRepository.findByEmail(email);
        if(account.isEmpty()){
            throw new NotFoundException("Este email: "+email+" Não foi encontrado no" +
                    " nosso banco de dados");
        }
        return accountRepository.findByEmail(email).get();

    }
    private Account existsAccountWithUuid(UUID uuid) {
        return accountRepository.findByUuid(uuid).orElseThrow(() ->
                new NotFoundException("A conta com o UUID: " + uuid + " não foi encontrada no banco de dados.")
        );
    }
}
