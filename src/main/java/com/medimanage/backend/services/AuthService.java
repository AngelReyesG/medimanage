package com.medimanage.backend.services;

import com.medimanage.backend.dtos.LoginRequestDTO;
import com.medimanage.backend.dtos.RegistroRequestDTO;
import com.medimanage.backend.entities.Usuario;
import com.medimanage.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private com.medimanage.backend.security.JwtTokenProvider jwtTokenProvider;

    public String login(LoginRequestDTO dto) {
        //Buscar usuario por correo
        Usuario usuario = usuarioRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales incorrectas (Usuario no encontrado)."));

        //Verificar si la contraseña en texto plano coincide con la encriptada
        if(!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales incorrectas (Contraseña inválida).");
        }
        //Generar y retornar token JWT
        return jwtTokenProvider.generarToken(usuario.getEmail(), usuario.getIdUsuario());
    }
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrarUsuario(RegistroRequestDTO dto) {
        //Validar que el correo no está registrado
        if (usuarioRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico ya está registrado en el sistema.");
        }

        //Crear nuevo Usuario mapeando los datos del DTO
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(dto.getNombre());
        nuevoUsuario.setApellido(dto.getApellido());
        nuevoUsuario.setEmail(dto.getEmail());

        //Encripción de contraseña por BCrypt
        String passwordEncriptada = passwordEncoder.encode(dto.getPassword());
        nuevoUsuario.setPassword(passwordEncriptada);

        //Guardar en BD
        return usuarioRepository.save(nuevoUsuario);
    }
}
