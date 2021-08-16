package com.paultamayo.oauth.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.paultamayo.oauth.entidades.Usuario;
import com.paultamayo.oauth.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio implements UserDetailsService {

	@Autowired
	private UsuarioRepositorio repositorio;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuarioOptional = repositorio.findByUsername(username);

		if (usuarioOptional.isEmpty()) {
			throw new UsernameNotFoundException(
					String.format("No se ha encontrado el usuario [%s] registrado.", username));
		}

		return usuarioOptional.get();
	}

}
