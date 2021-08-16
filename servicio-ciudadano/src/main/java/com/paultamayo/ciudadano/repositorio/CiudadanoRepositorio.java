package com.paultamayo.ciudadano.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.base.enumerador.EstadoCiudadanoEnum;
import com.paultamayo.ciudadano.entidad.Ciudadano;

@Repository
public interface CiudadanoRepositorio extends CrudRepository<Ciudadano, String> {

	List<Ciudadano> findByEstado(EstadoCiudadanoEnum estado);
}
