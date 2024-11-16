package pedidos.demo.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.auth0.jwt.exceptions.JWTDecodeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private static final String SECRET_KEY = "squad2";


    public String getUuidFromToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getClaim("uuid").asString();
    }
    public String getEmailFromToken(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET_KEY))
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }
}