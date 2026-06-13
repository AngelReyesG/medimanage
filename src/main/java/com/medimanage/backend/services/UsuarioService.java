package com.medimanage.backend.services;

import com.medimanage.backend.entities.Usuario;
import com.medimanage.backend.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    //Inyección por constructor
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    //Registrar un nuevo usuario
    public Usuario registrarUsuario(Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByNombreUsuario(usuario.getNombreUsuario());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("El usuario ya se encuentra registrado.");
        }

        return usuarioRepository.save(usuario);
    }

    //Login
    public Optional<Usuario> autenticar(String usernameInput, String passwordInput) {
        return usuarioRepository.findByNombreUsuario(usernameInput)
                .filter(u-> u.getPassword().equals(passwordInput));
    }
}
