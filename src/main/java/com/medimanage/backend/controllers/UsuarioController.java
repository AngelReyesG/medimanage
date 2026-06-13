package com.medimanage.backend.controllers;

import com.medimanage.backend.entities.Usuario;
import com.medimanage.backend.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    //Inyeccion por constructor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    //Registrar un nuevo usuario (Médico o Administrador)
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String usuario = credenciales.get("usuario");
        String password = credenciales.get("password");

        if (usuario == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "El usuario y la contraseña son requeridos"));
        }

        Optional<Usuario> usuarioOpt = usuarioService.autenticar(usuario, password);

        if (usuarioOpt.isPresent()) {
            return ResponseEntity.ok(usuarioOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Credenciales inválidas. Verifica tu correo o contraseña"));
        }
    }
}