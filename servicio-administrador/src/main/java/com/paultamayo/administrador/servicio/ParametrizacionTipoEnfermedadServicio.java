package com.paultamayo.administrador.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paultamayo.administrador.entidad.ParametrizacionTipoEnfermedad;
import com.paultamayo.administrador.repositorio.ParametrizacionTipoEnfermedadRepository;
import com.paultamayo.base.servicio.BaseServicio;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class ParametrizacionTipoEnfermedadServicio extends BaseServicio<ParametrizacionTipoEnfermedad, Long> {

	@Autowired
	@Getter(value = AccessLevel.PROTECTED)
	private ParametrizacionTipoEnfermedadRepository repositorio;

}
