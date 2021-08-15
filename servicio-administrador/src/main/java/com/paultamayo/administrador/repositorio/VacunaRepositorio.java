package com.paultamayo.administrador.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.administrador.entidad.Vacuna;

@Repository
public interface VacunaRepositorio extends CrudRepository<Vacuna, Long> {

}
