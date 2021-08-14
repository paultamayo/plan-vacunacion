package com.paultamayo.ciudadano.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.ciudadano.entidad.Ciudadano;

@Repository
public interface CiudadanoRepositorio extends CrudRepository<Ciudadano, String> {

}
