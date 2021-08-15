package com.paultamayo.ciudadano.servicios;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paultamayo.base.excepcion.LogicaServicioExcepcion;
import com.paultamayo.base.servicio.BaseServicio;
import com.paultamayo.ciudadano.entidad.Ciudadano;
import com.paultamayo.ciudadano.enumerador.EstadoCiudadanoEnum;
import com.paultamayo.ciudadano.repositorio.CiudadanoRepositorio;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class CiudadanoServicio extends BaseServicio<Ciudadano, String> {

	@Getter(value = AccessLevel.PROTECTED)
	@Autowired
	private CiudadanoRepositorio repositorio;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<Ciudadano> buscarPorEstado(EstadoCiudadanoEnum estado) {
		return repositorio.findByEstado(estado);
	}

	@Override
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
