package br.com.acaboumony.account.repository;

import br.com.acaboumony.account.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends JpaRepository<Account,UUID> {
    Optional<Account> findByEmail(String email);

    Optional<Account> findByUuid(UUID uuid);
}
