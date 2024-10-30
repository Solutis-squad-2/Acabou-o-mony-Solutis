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
        ops.set(email, codigo, 5, TimeUnit.MINUTES);
    }

    public boolean verificarCodigo(String email, String codigo) {
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String codigoSalvo = ops.get(email);
        if (codigo.equals(codigoSalvo)) {
            redisTemplate.delete(email);
            return true;
        }
        return false;
    }
    public String obterCodigoParaUsuario(String email) {
        return redisTemplate.opsForValue().get(email);
    }

    public void removerCodigoParaUsuario(String email) {
        redisTemplate.delete(email);
    }
}
