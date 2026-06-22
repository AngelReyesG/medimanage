package com.medimanage.backend.security;

import com.medimanage.backend.entities.Usuario;
import com.medimanage.backend.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        //Busca en la BD
        return usuarioRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Usuarion no encontrado con email: " + email));
    }
}
