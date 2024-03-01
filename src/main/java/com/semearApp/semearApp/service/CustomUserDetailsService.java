package com.semearApp.semearApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.semearApp.semearApp.models.Membro;
import com.semearApp.semearApp.repository.MembroRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private MembroRepository membroRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Membro membro = membroRepository.findByCpf(cpf); // Implemente este método no seu MembroRepository

        if (membro == null) {
            throw new UsernameNotFoundException("Usuário não encontrado com CPF: " + cpf);
        }

        return User.builder()
            .username(membro.getCpf())
            .password(membro.getSenha())
            .roles("USER") // Adicione os papéis do usuário conforme necessário
            .build();
    }
}
