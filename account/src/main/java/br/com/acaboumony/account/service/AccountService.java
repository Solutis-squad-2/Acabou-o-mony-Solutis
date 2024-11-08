package br.com.acaboumony.account.service;

import br.com.acaboumony.account.model.dto.AccountDTO;
import br.com.acaboumony.account.model.dto.FindUUIDDto;
import br.com.acaboumony.account.model.dto.GetAccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AccountService {

    Account createAccount(AccountDTO accountDTO);

    GetAccountDTO findAccount(String email);

    FindUUIDDto findUuid(String email);

    String patchAccount(UUID uuid,GetAccountDTO accountDTO);

    void deleteAccount(UUID uuid);

    GetAccountDTO findAccountTeste(String email);

    Page<GetAccountDTO> listAccount(Pageable pageable);

}
