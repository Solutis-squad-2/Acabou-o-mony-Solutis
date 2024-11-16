package br.com.acaboumony.account.service;

import br.com.acaboumony.account.model.dto.FindUUIDDto;
import br.com.acaboumony.account.model.dto.LoginAccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;

@Service
public class TokenService {

    @Autowired
    private AccountService accountService;

    public String gerarToken(Account login) {
        FindUUIDDto uuid = accountService.findUuid(login.getEmail());

        return JWT.create()
                .withIssuer("2FA")
                .withSubject(login.getUsername())
                .withClaim("uuid", uuid.uuid())
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusMinutes(50)
                        .toInstant(ZoneOffset.of("-03:00"))))
                .sign(Algorithm.HMAC256("squad2"));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("squad2"))
                .withIssuer("2FA")
                .build().verify(token).getSubject();
    }

   /* public String getEmail(String token) {
        return JWT.require(Algorithm.HMAC256("squad2"))
                .withIssuer("2FA")
                .build().verify(token).getClaim("email").asString();
    }*/
}
