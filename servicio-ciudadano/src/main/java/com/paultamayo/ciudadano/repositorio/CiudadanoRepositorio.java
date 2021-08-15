package com.paultamayo.ciudadano.repositorio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.ciudadano.entidad.Ciudadano;
import com.paultamayo.ciudadano.enumerador.EstadoCiudadanoEnum;

@Repository
public interface CiudadanoRepositorio extends CrudRepository<Ciudadano, String> {

	List<Ciudadano> findByEstado(EstadoCiudadanoEnum estado);
}
