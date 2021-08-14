package com.paultamayo.ciudadano.servicios;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paultamayo.ciudadano.entidad.Ciudadano;
import com.paultamayo.ciudadano.enumerador.EstadoCiudadanoEnum;
import com.paultamayo.ciudadano.excepcion.LogicaServicioExcepcion;
import com.paultamayo.ciudadano.repositorio.CiudadanoRepositorio;

@Service
public class CiudadanoServicio {

	@Autowired
	private CiudadanoRepositorio repositorio;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Ciudadano buscarPorId(String cedula) throws LogicaServicioExcepcion {
		Optional<Ciudadano> ciudadano = repositorio.findById(cedula);

		if (ciudadano.isEmpty()) {
			throw new LogicaServicioExcepcion("No se ha podido identificar un ciudadano con cédula: " + cedula);
		}

		return ciudadano.get();
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public Iterable<Ciudadano> buscarTodos() {
		return repositorio.findAll();
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor = LogicaServicioExcepcion.class)
	public Ciudadano guardar(Ciudadano ciudadano) throws LogicaServicioExcepcion {
		try {
			if (repositorio.existsById(ciudadano.getCedula())) {
				throw new EntityExistsException("Ya se tiene registrado la cédula: " + ciudadano.getCedula());
			}

			ciudadano.setEstado(EstadoCiudadanoEnum.PENDIENTE);
			ciudadano.setFechaRegistro(LocalDateTime.now());
			repositorio.save(ciudadano);

			return ciudadano;
		} catch (Exception ex) {
			throw new LogicaServicioExcepcion(ex.getMessage(), ex);
		}
	}
}
