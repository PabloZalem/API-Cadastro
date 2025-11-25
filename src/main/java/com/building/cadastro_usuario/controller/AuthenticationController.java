package com.building.cadastro_usuario.controller;

import com.building.cadastro_usuario.entity.Usuario;
import com.building.cadastro_usuario.repository.UsuarioRepository;
import com.building.cadastro_usuario.security.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private AuthenticationManager authenticationManager;
    private UsuarioRepository usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private JWTUtil jwtUtil;

    @Autowired
    public AuthenticationController(AuthenticationManager authenticationManager,
                                    UsuarioRepository repository, PasswordEncoder passwordEncoder,
                                    JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.usuarioRepository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/signin")
    public String authenticateUser(@RequestBody Usuario usuario) {
        Authentication authentication = authenticationManager.authenticate(
                new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
                        usuario.getUsuario(),
                        usuario.getSenha()
                )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtUtil.generateToken(userDetails.getUsername());
    }


    @PostMapping("/signup")
    public String registerUser(@RequestBody Usuario usuario) {
        if (usuarioRepository.existsByUsuario(usuario.getUsuario())) {
            return "Usuario ja existe";
        }

        final Usuario novoUsuario = new Usuario(
                null,
                usuario.getUsuario(),
                passwordEncoder.encode(usuario.getSenha())
        );

        usuarioRepository.save(novoUsuario);
        return "Usuario registrado com sucesso";
    }
}
