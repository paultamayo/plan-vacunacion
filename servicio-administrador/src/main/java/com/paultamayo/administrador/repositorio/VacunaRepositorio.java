package com.paultamayo.administrador.repositorio;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.administrador.entidad.Vacuna;

@Repository
public interface VacunaRepositorio extends CrudRepository<Vacuna, Long> {

	@Modifying
	@Query("UPDATE Vacuna v SET v.cantidad = v.cantidad -1 WHERE v.id = :id")
	void actualizar(Long id);

	@Modifying
	@Query("UPDATE Vacuna v SET v.cantidad = :cantidad WHERE v.id = :id")
	void actualizar(Long id, Long cantidad);
}
