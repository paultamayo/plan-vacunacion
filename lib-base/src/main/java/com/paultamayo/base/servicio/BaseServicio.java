package com.paultamayo.base.servicio;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paultamayo.base.excepcion.LogicaServicioExcepcion;

public abstract class BaseServicio<T, K> {


	protected abstract CrudRepository<T, K> getRepositorio();

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Iterable<T> buscarTodos() {
		return getRepositorio().findAll();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public T buscarPorId(K id) throws LogicaServicioExcepcion {
		Optional<T> ciudadano = getRepositorio().findById(id);

		if (ciudadano.isEmpty()) {
			throw new LogicaServicioExcepcion("No se ha podido identificar un ciudadano con cédula: " + id);
		}

		return ciudadano.get();
	}
}
