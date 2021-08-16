package com.paultamayo.administrador.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.paultamayo.administrador.entidad.Ciudadano;
import com.paultamayo.base.enumerador.EstadoCiudadanoEnum;

@Repository
public interface CiudadanoRepositorio extends CrudRepository<Ciudadano, String> {

	List<Ciudadano> findByEstado(EstadoCiudadanoEnum estado);

	@Modifying
	@Query("UPDATE Ciudadano v SET v.estado = :estado WHERE v.cedula = :cedula")
	void actualizar(String cedula, EstadoCiudadanoEnum estado);
}
