package com.paultamayo.administrador.servicio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.paultamayo.administrador.entidad.Vacuna;
import com.paultamayo.administrador.repositorio.VacunaRepositorio;
import com.paultamayo.base.servicio.BaseServicio;

import lombok.AccessLevel;
import lombok.Getter;

@Service
public class VacunaServicio extends BaseServicio<Vacuna, Long> {


	@Getter(value = AccessLevel.PROTECTED)
	@Autowired
	private VacunaRepositorio repositorio;

}
