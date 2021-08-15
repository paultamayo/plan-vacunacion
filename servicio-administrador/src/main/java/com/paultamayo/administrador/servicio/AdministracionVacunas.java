package com.paultamayo.administrador.servicio;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.paultamayo.administrador.entidad.ParametrizacionEdadPeso;
import com.paultamayo.administrador.entidad.ParametrizacionEdadVacuna;
import com.paultamayo.administrador.entidad.ParametrizacionTipoEnfermedad;
import com.paultamayo.base.enumerador.ActivoEnum;
import com.paultamayo.base.excepcion.LogicaServicioExcepcion;
import com.paultamayo.base.to.CiudadanoTo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdministracionVacunas {

	@Autowired
	private ParametrizacionEdadPesoServicio edadPesoServicio;

	@Autowired
	private ParametrizacionEdadVacunaServicio edadVacunaServicio;

	@Autowired
	private ParametrizacionTipoEnfermedadServicio tipoEnfermedadServicio;

	private List<ParametrizacionEdadPeso> edadPesos;

	private List<ParametrizacionEdadVacuna> edadVacunas;

	private List<ParametrizacionTipoEnfermedad> tipoEnfermedades;

	@PostConstruct
	public void init() {
		log.info("Cargando datos");
		try {
			edadPesos = edadPesoServicio.buscarPorActivo(ActivoEnum.SI);
			edadVacunas = edadVacunaServicio.buscarPorActivo(ActivoEnum.SI);
			tipoEnfermedades = Lists.newArrayList(tipoEnfermedadServicio.buscarTodos());
		} catch (Exception ex) {
			Throwable root = ExceptionUtils.getRootCause(ex);
			log.error(root.getMessage(), root);
		}
	}

	public List<CiudadanoTo> asignarPriorirdadVacunas(List<CiudadanoTo> ciudadanos) throws LogicaServicioExcepcion {
		if (Objects.isNull(edadPesos) || Objects.isNull(edadVacunas) || Objects.isNull(tipoEnfermedades)) {
			throw new LogicaServicioExcepcion("No se han cargado correctamente los parámetros de asignación.");
		}

		for (CiudadanoTo c : ciudadanos) {
			try {
				ParametrizacionEdadPeso edadPeso = edadPesos.parallelStream()
						.filter(ep -> ep.getMinimo() >= c.getTipoEnfermedadId()
								&& (Objects.isNull(ep.getMaximo()) || c.getEdad() <= ep.getMaximo()))
						.findFirst()
						.orElseThrow(() -> new Exception(
								"No se pudo identificar el rango del tipo de enfermedad para la cedula: "
										+ c.getCedula()));
				ParametrizacionEdadVacuna edadVacuna = edadVacunas.parallelStream()
						.filter(ev -> ev.getMinimo() >= c.getEdad()
								&& (Objects.isNull(ev.getMaximo()) || c.getEdad() <= ev.getMaximo()))
						.findAny().orElseThrow(() -> new Exception(
								"No se pudo identificar un rango de vacuna para la cedula: " + c.getCedula()));
				ParametrizacionTipoEnfermedad tipoEnfermedad = tipoEnfermedades.parallelStream()
						.filter(te -> te.getId().compareTo(c.getTipoEnfermedadId()) == 0).findFirst()
						.orElseThrow(() -> new Exception(
								"No existe un tipo de enfermedad para la cedula: " + c.getCedula()));

				c.setAsignado(true);
				c.setPeso(edadPeso.getValor() + tipoEnfermedad.getValor());
				c.setVacunaId(edadVacuna.getVacunaId());
			} catch (Exception e) {
				Throwable root = ExceptionUtils.getRootCause(e);
				log.error(root.getMessage(), root);
				c.setMensajeError(root.getMessage());
			}
		}
		List<CiudadanoTo> ciudadanoAsignados = ciudadanos.parallelStream().filter(CiudadanoTo::isAsignado)
				.collect(Collectors.toList());
		ciudadanoAsignados.sort(Comparator.comparing(CiudadanoTo::getPeso).thenComparing(CiudadanoTo::getPrioridad));

		return ciudadanoAsignados;
	}
}
