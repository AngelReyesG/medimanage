package com.medimanage.backend.controllers;

import com.medimanage.backend.dtos.LoginRequestDTO;
import com.medimanage.backend.dtos.RegistroRequestDTO;
import com.medimanage.backend.entities.Usuario;
import com.medimanage.backend.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@Valid @RequestBody RegistroRequestDTO dto) {
        try {
            Usuario usuarioRegistrado = authService.registrarUsuario(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuarioRegistrado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDTO dto) {
        try {
            String token = authService.login(dto);
            java.util.Map<String, String> response = new java.util.HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
