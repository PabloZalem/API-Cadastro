package com.building.cadastro_usuario.security;


import com.building.cadastro_usuario.exception.InvalidateToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * Essa classe é um utilitario para trabalhar com JWT (JSON Web Token)
 * Ele cuida de gerar tokens, extrair informações deles e validar se são validos
 */
@Component // Torna a classe um bean gerenciado pelo Spring, ou seja, pode ser injetada em outras partes do sistema.
@Slf4j
public class JWTUtil {

    // Configurado dentro do application.properties
    @Value("${jwt.secret}") // chave secreata usada para assinar/verificar o token
    private String jwtSecret;
    @Value("${jwt.expiration}") // tempo de expiracao do token en milissegundos
    private long jwtExpiration;

    private SecretKey secretKey; // Objeto que representa a chave criptográfica usada para assinar/verificar os tokens.

    /**
     * Método chamado automaticamente depois que o bean é criado.
     * Aqui ele transforma a jwt.secret em uma chave (SecretKey) válida para o algoritmo HMAC.
     */
    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     *
     * @param usuario
     * @return
     * Cria um JWT com usuario como subject
     * Data de emissão (issuedAt)
     * Data de expiracao (expiration)
     * Assinatura usando a chave secreta. Retorna o token em formato de String
     */
    public String generateToken(String usuario) {
        return Jwts.builder()
                .subject(usuario)
                .issuedAt(new Date())
                .expiration(new Date(jwtExpiration))
                .signWith(secretKey)
                .compact();
    }

    // Le o token e extrai o subject  (o nome do usuario que foi colocado dentro dele)
    public String getUserFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    /**
     *
     * @param token
     * @return
     *
     * Verifica se o token é valido
     * Se conseguir parsear e validar a sinatura, retorna true
     * Se der erro (token invalido, expirado, adulterado), registra no log e retorna uma exception tratada
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (InvalidateToken e) {
            log.error("JWT validation error: {}", e.getMessage(), e);
        }
        return false;
    }
}
