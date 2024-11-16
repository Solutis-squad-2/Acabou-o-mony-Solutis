package br.com.acaboumony.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CodigoService {

    private final StringRedisTemplate redisTemplate;

    @Autowired
    public CodigoService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void salvarCodigoParaUsuario(String email, String codigo) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set(codigo, email,  5, TimeUnit.MINUTES);
    }

    public String recuperarEmail(String codigo) {
        return redisTemplate.opsForValue().get(codigo);
    }

    public void removerCodigoParaUsuario(String email) {
        redisTemplate.delete(email);
    }
    public boolean codigoExiste(String codigo) {
        return redisTemplate.hasKey(codigo);
    }
}
