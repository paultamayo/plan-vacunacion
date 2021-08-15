package com.paultamayo.administrador.servicio;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.paultamayo.administrador.entidad.AsignacionVacuna;
import com.paultamayo.administrador.entidad.ids.AsignacionVacunaID;
import com.paultamayo.administrador.repositorio.AsignacionVacunaRepositorio;
import com.paultamayo.administrador.to.AsignacionFechaTo;
import com.paultamayo.base.servicio.BaseServicio;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class AsignacionVacunaServicio extends BaseServicio<AsignacionVacuna, AsignacionVacunaID> {

	@Autowired
	@Getter(value = AccessLevel.PROTECTED)
	private AsignacionVacunaRepositorio repositorio;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public AsignacionFechaTo buscarAsignacion() {
		Optional<AsignacionFechaTo> asignacion = repositorio.buscarAsignacion();

		if (asignacion.isEmpty()) {
			asignacion = Optional.of(new AsignacionFechaTo(0, LocalDate.of(2021, Month.AUGUST, 1)));
		}

		return asignacion.get();
	}

}
