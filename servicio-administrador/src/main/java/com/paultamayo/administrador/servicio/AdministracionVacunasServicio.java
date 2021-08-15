package com.paultamayo.administrador.servicio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.paultamayo.administrador.entidad.AsignacionVacuna;
import com.paultamayo.administrador.entidad.ParametrizacionEdadPeso;
import com.paultamayo.administrador.entidad.ParametrizacionEdadVacuna;
import com.paultamayo.administrador.entidad.ParametrizacionTipoEnfermedad;
import com.paultamayo.administrador.entidad.Vacuna;
import com.paultamayo.administrador.to.AsignacionFechaTo;
import com.paultamayo.base.enumerador.ActivoEnum;
import com.paultamayo.base.excepcion.LogicaServicioExcepcion;
import com.paultamayo.base.to.CiudadanoTo;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class AdministracionVacunasServicio {

	@Autowired
	private AsignacionVacunaServicio asignacionVacunaServicio;

	private List<ParametrizacionEdadPeso> edadPesos;

	@Autowired
	private ParametrizacionEdadPesoServicio edadPesoServicio;

	private List<ParametrizacionEdadVacuna> edadVacunas;

	@Autowired
	private ParametrizacionEdadVacunaServicio edadVacunaServicio;

	private List<ParametrizacionTipoEnfermedad> tipoEnfermedades;

	@Autowired
	private ParametrizacionTipoEnfermedadServicio tipoEnfermedadServicio;

	@Autowired
	private VacunaServicio vacunaServicio;

	public CiudadanoTo asignarPesoVacunas(CiudadanoTo ciudadano) {
		try {
			ciudadano.setPeso(-1);

			Predicate<ParametrizacionEdadPeso> e1 = ep -> Objects.isNull(ep.getMaximo())
					&& ciudadano.getEdad() >= ep.getMinimo();
			Predicate<ParametrizacionEdadPeso> e2 = ep -> Objects.nonNull(ep.getMaximo())
					&& ciudadano.getEdad() >= ep.getMinimo() && ciudadano.getEdad() <= ep.getMaximo();
			ParametrizacionEdadPeso edadPeso = edadPesos.parallelStream().filter(e1.or(e2)).findFirst().orElseThrow(
					() -> new Exception("No se pudo identificar el rango del tipo de enfermedad para la cedula: "
							+ ciudadano.getCedula()));

			Predicate<ParametrizacionEdadVacuna> p3 = ep -> Objects.isNull(ep.getMaximo())
					&& ciudadano.getEdad() >= ep.getMinimo();
			Predicate<ParametrizacionEdadVacuna> p4 = ep -> Objects.nonNull(ep.getMaximo())
					&& ciudadano.getEdad() >= ep.getMinimo() && ciudadano.getEdad() <= ep.getMaximo();
			List<ParametrizacionEdadVacuna> edadVacunasDisponibles = edadVacunas.parallelStream().filter(p3.or(p4))
					.collect(Collectors.toList());

			ParametrizacionTipoEnfermedad tipoEnfermedad = tipoEnfermedades.parallelStream()
					.filter(te -> te.getId().compareTo(ciudadano.getTipoEnfermedadId()) == 0).findFirst()
					.orElseThrow(() -> new Exception(
							"No existe un tipo de enfermedad para la cedula: " + ciudadano.getCedula()));
			if (edadVacunasDisponibles.isEmpty()) {
				throw new Exception("No existe vacunas para su edad: " + ciudadano.getEdad());
			}

			ciudadano.setAsignado(true);
			ciudadano.setPeso(edadPeso.getValor() + tipoEnfermedad.getValor());
			ciudadano.setVacunasId(edadVacunasDisponibles.parallelStream().map(ParametrizacionEdadVacuna::getVacunaId)
					.collect(Collectors.toList()));
		} catch (Exception e) {
			Throwable root = ExceptionUtils.getRootCause(e);
			log.error(root.getMessage());
			ciudadano.setMensajeError(root.getMessage());
		}

		return ciudadano;
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public List<CiudadanoTo> asignarPrioridadVacunas(List<CiudadanoTo> ciudadanos) throws LogicaServicioExcepcion {
		if ((Objects.isNull(edadPesos) || edadPesos.isEmpty()) || (Objects.isNull(edadVacunas) || edadVacunas.isEmpty())
				|| (Objects.isNull(tipoEnfermedades) || tipoEnfermedades.isEmpty())) {
			throw new LogicaServicioExcepcion("No se han cargado correctamente los parámetros de asignación.");
		}

		ciudadanos.forEach(this::asignarPesoVacunas);
		ciudadanos.sort(Comparator.comparing(CiudadanoTo::getPeso).thenComparing(CiudadanoTo::getPrioridad));

		for (CiudadanoTo c : ciudadanos) {
			try {
				if (c.getPeso().compareTo(-1) != 0) {
					guardarCiudadanoAsignado(c);
				}
			} catch (Exception ex) {
				log.error(String.format(ex.getMessage()));
			}
		}

		return ciudadanos;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = LogicaServicioExcepcion.class)
	public void guardarCiudadanoAsignado(CiudadanoTo ciudadano) throws LogicaServicioExcepcion {
		try {
			// Buscar vacuna para asignar
			Vacuna vacuna = vacunaServicio.buscarVacunaDisponible(ciudadano.getVacunasId());
			long stockVacuna = vacuna.getCantidad() - 1;
			if (stockVacuna < 0) {
				throw new Exception("No hay stock de vacunas");
			}

			// Buscar fecha_programada
			AsignacionFechaTo asignacionFecha = asignacionVacunaServicio.buscarAsignacion();
			LocalDate fechaProgramada = asignacionFecha.getCantidad() <= 5 ? asignacionFecha.getFecha()
					: asignacionFecha.getFecha().plusDays(1);
			// Cambiar estado ciudadano

			// Registrar asignacion_vacuna
			AsignacionVacuna asignacion = new AsignacionVacuna();
			asignacion.setCiudadanoId(ciudadano.getCedula());
			asignacion.setVacunaId(vacuna.getId());
			asignacion.setFechaProgramada(fechaProgramada.atTime(LocalTime.now()));
			asignacion.setFechaRegistro(LocalDate.now());

			asignacionVacunaServicio.guardar(asignacion);

			vacuna.setCantidad(stockVacuna);
			vacunaServicio.guardarMandatory(vacuna);
			ciudadano.setProgramado(true);
		} catch (Exception ex) {
			Throwable root = ExceptionUtils.getRootCause(ex);
			ciudadano.setMensajeError(root.getMessage());
			throw new LogicaServicioExcepcion(root.getMessage());
		}
	}

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
}
