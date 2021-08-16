package com.paultamayo.oauth.servicios;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import com.paultamayo.oauth.entidades.Cliente;
import com.paultamayo.oauth.repositorios.ClienteRepositorio;

@Service
public class ClienteServicio implements ClientDetailsService {

	@Autowired
	private ClienteRepositorio repositorio;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		Optional<Cliente> clienteOptional = repositorio.findByClientId(clientId);

		if (clienteOptional.isEmpty()) {
			throw new ClientRegistrationException(
					String.format("No se ha podido encontrar al cliente [%s].", clientId));
		}

		return clienteOptional.get();
	}

}
