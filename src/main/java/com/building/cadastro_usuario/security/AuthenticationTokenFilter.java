package com.building.cadastro_usuario.security;

import com.building.cadastro_usuario.exception.TokenAuthenticationException;
import com.building.cadastro_usuario.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 *  Essa classe é um filtro de autenticação que roda em toda requisicao HTTP
 *  Ele verifica se existe um token JWT no cabeçalho da equisicao e, se for válido, coloca o usuario
 *  autenticado no Spring Secuciry context
 *
 *  Requisicao chega -> filtro intercepta
 *  Extrai JWT do cabeçalho
 *  Valida JWT
 *  Se valido -> pega o usaurio -> carrega detalhes -> cria autenticacao -> coloca no contexto do Spring
 *  Se invalido -> Lanca excecao
 */

@Component
@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    public static final String BEARER_ = "Bearer ";

    @Autowired
    public JWTUtil jwtUtil; // Classe utilitária para manipular JWT (validar, extrair usuário etc.).

    @Autowired
    private UsuarioService usuarioService;

    // Ele roda em cada requisição interceptada pelo Spring Security.
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        try {
            String jwt = parseJWT(request); // Extrai o token JWT do cabeçalho Authorization.

            if (jwt != null && jwtUtil.validateToken(jwt)) {
                final String usuario = jwtUtil.getUserFromToken(jwt); // Extrai o username/email do token.
                final UserDetails userDetails = usuarioService.loadUserByUsername(usuario);
                // Carrega os detalhes do usuário (roles, permissões, senha etc.).

                // Ele representa o usuário autenticado, com suas credenciais e permissões.
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                // Adiciona informações extras da requisição (IP, sessão, etc.).
                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                // Coloca o usuário autenticado no contexto de segurança do Spring.
                SecurityContextHolder.getContext()
                        .setAuthentication(usernamePasswordAuthenticationToken);
            }
        } catch (TokenAuthenticationException e) {
            log.error("Não pode fazer autenticação: {}", e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String parseJWT(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith(BEARER_)) {
            return headerAuth.substring(BEARER_.length());
        }

        return null;
    }
}
