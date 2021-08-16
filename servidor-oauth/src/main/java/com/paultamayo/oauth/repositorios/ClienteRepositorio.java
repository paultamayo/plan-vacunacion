package com.paultamayo.oauth.repositorios;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.oauth.entidades.Cliente;

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, String> {

	Optional<Cliente> findByClientId(String clientId);

}
