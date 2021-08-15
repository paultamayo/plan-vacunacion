package com.paultamayo.administrador.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.administrador.entidad.AsignacionVacuna;
import com.paultamayo.administrador.entidad.ids.AsignacionVacunaID;

@Repository
public interface AsignacionVacunaRepositorio extends CrudRepository<AsignacionVacuna, AsignacionVacunaID> {


}
