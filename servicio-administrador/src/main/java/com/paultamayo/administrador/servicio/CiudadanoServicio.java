package com.paultamayo.administrador.servicio;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.paultamayo.administrador.entidad.Ciudadano;
import com.paultamayo.administrador.repositorio.CiudadanoRepositorio;
import com.paultamayo.base.enumerador.EstadoCiudadanoEnum;
import com.paultamayo.base.servicio.BaseServicio;
import com.paultamayo.base.to.CiudadanoTo;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class CiudadanoServicio extends BaseServicio<Ciudadano, String> {

	@Getter(value = AccessLevel.PROTECTED)
	@Autowired
	private CiudadanoRepositorio repositorio;

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<CiudadanoTo> buscarPorEstado(EstadoCiudadanoEnum estado) {
		List<Ciudadano> ciudadanos = repositorio.findByEstado(estado);
		ciudadanos.sort(Comparator.comparing(Ciudadano::getFechaRegistro));

		List<CiudadanoTo> ciudadanosTo = Lists.newArrayListWithCapacity(ciudadanos.size());
		long i = 1;
		for (Ciudadano c : ciudadanos) {
			ciudadanosTo.add(new CiudadanoTo(c.getCedula(), c.getFechaNacimiento(), i++, c.getTipoEnfermedadId()));
		}

		return ciudadanosTo;
	}

	@Transactional
	public void actualizar(String cedula, EstadoCiudadanoEnum estado) {
		repositorio.actualizar(cedula, estado);
	}
	
	

}
