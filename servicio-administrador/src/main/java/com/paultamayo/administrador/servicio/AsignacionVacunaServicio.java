package com.paultamayo.administrador.servicio;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.Month;
import java.util.Date;
import java.util.List;
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
		AsignacionFechaTo asignacion = new AsignacionFechaTo(0, LocalDate.of(2021, Month.AUGUST, 1));
		Optional<Object[]> asignacionObject = repositorio.buscarAsignacion();

		if (asignacionObject.isPresent() && asignacionObject.get().length == 1) {
			Object[] obj = (Object[]) asignacionObject.get()[0];
			Date fecha = (Date) obj[0];
			BigInteger cantidad = (BigInteger) obj[1];

			asignacion.setFecha(new java.sql.Date(fecha.getTime()).toLocalDate());
			asignacion.setCantidad(cantidad.intValue());
		}

		return asignacion;
	}
	
	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<AsignacionVacuna> buscarPorFechaProgramada(LocalDate fechaInicio, LocalDate fechaFin){
		return repositorio.findByFechaProgramadaBetween(fechaInicio, fechaFin);
	}

}
