package br.com.acaboumony.account.service;

import br.com.acaboumony.account.model.dto.LoginAccountDTO;
import br.com.acaboumony.account.model.entity.Account;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    public String gerarToken(Account login) {
        return JWT.create()
                .withIssuer("2FA")
                .withSubject(login.getUsername())
                .withClaim("uuid", login.getUuid().toString())
                    .withExpiresAt(Date.from(LocalDateTime.now()
                            .plusMinutes(10)
                            .toInstant(ZoneOffset.of("-03:00"))))
                .sign(Algorithm.HMAC256("squad2"));
    }

    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256("squad2"))
                .withIssuer("2FA")
                .build().verify(token).getSubject();
    }
}
