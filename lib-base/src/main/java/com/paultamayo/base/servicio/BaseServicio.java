package com.paultamayo.base.servicio;

import java.util.Optional;

import org.apache.commons.lang3.exception.ExceptionUtils;
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

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = LogicaServicioExcepcion.class)
	public T guardar(T t) throws LogicaServicioExcepcion {
		try {
			return getRepositorio().save(t);
		} catch (Exception ex) {
			Throwable e = ExceptionUtils.getRootCause(ex);
			throw new LogicaServicioExcepcion(
					String.format("No se pudo guardar la información. Mensaje[%s]", e.getMessage()), ex);
		}
	}

}
