package com.paultamayo.administrador.servicio;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paultamayo.administrador.entidad.Vacuna;
import com.paultamayo.administrador.repositorio.VacunaRepositorio;
import com.paultamayo.base.excepcion.LogicaServicioExcepcion;
import com.paultamayo.base.servicio.BaseServicio;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class VacunaServicio extends BaseServicio<Vacuna, Long> {

	@Getter(value = AccessLevel.PROTECTED)
	@Autowired
	private VacunaRepositorio repositorio;

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = LogicaServicioExcepcion.class)
	public Vacuna actualizar(Long id, Long cantidad) throws LogicaServicioExcepcion {
		try {
			if (cantidad > 0) {
				Vacuna vacuna = buscarPorId(id);
				Long total = vacuna.getCantidad() + cantidad;

				repositorio.actualizar(id, total);

				return new Vacuna(null, vacuna.getNombre(), total);
			} else {
				throw new Exception("No se puede agregar al stock de vacunas 0 o menos.");
			}
		} catch (Exception ex) {
			Throwable root = ExceptionUtils.getRootCause(ex);
			throw new LogicaServicioExcepcion(root.getMessage());
		}
	}

}
