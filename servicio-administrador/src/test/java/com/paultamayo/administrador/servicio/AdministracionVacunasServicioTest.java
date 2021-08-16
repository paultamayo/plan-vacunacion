package com.paultamayo.administrador.servicio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.paultamayo.administrador.entidad.AsignacionVacuna;
import com.paultamayo.administrador.entidad.ParametrizacionEdadPeso;
import com.paultamayo.administrador.entidad.ParametrizacionEdadVacuna;
import com.paultamayo.administrador.entidad.ParametrizacionTipoEnfermedad;
import com.paultamayo.administrador.entidad.Vacuna;
import com.paultamayo.administrador.to.AsignacionFechaTo;
import com.paultamayo.base.enumerador.ActivoEnum;
import com.paultamayo.base.excepcion.LogicaServicioExcepcion;
import com.paultamayo.base.to.CiudadanoTo;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class AdministracionVacunasServicioTest {

	@InjectMocks
	private AdministracionVacunasServicio administracion = new AdministracionVacunasServicio();

	@Mock
	private AsignacionVacunaServicio asignacionServicio;

	@Autowired
	private CiudadanoServicio ciudadanoServicio;

	@Mock
	private ParametrizacionEdadPesoServicio edadServicio;

	@Mock
	private ParametrizacionEdadVacunaServicio edadVacunaServicio;

	private List<ParametrizacionEdadPeso> listadoEdades;

	private List<ParametrizacionEdadVacuna> listadoEdadVacunas;

	private List<ParametrizacionTipoEnfermedad> listadoTipoEnfermedad;

	@Mock
	private ParametrizacionTipoEnfermedadServicio tipoEnfermedadServicio;

	@Mock
	private VacunaServicio vacunaServicio;

	@Test
	void asignarVacunas() throws LogicaServicioExcepcion {
		AsignacionVacuna asignacion = new AsignacionVacuna();
		AsignacionFechaTo asignacionFecha = new AsignacionFechaTo(3, LocalDate.of(2021, Month.AUGUST, 1));
		when(asignacionServicio.guardar(Mockito.any())).thenReturn(asignacion);
		when(asignacionServicio.buscarAsignacion()).thenReturn(asignacionFecha);

		Vacuna vacuna = new Vacuna();
		vacuna.setCantidad(10l);
		when(vacunaServicio.buscarVacunaDisponible(Mockito.any())).thenReturn(vacuna);
		when(vacunaServicio.guardarRequerida(Mockito.any())).thenReturn(vacuna);

		LocalDate fechaNacimientoHoy = LocalDate.now();

		CiudadanoTo c0 = new CiudadanoTo("0000000000", fechaNacimientoHoy.minusYears(15), 1l, 3l);
		CiudadanoTo a0 = administracion.asignarPesoVacunas(c0);
		assertThat(a0.getPeso()).isEqualTo(-1);
		assertThat(a0.isAsignado()).isFalse();
		assertThat(a0.getVacunasId()).isNull();

		CiudadanoTo c1 = new CiudadanoTo("0000000001", fechaNacimientoHoy.minusYears(23), 2l, 3l);
		CiudadanoTo a1 = administracion.asignarPesoVacunas(c1);
		assertThat(a1.getPeso()).isEqualTo(5);
		assertThat(a1.getVacunasId()).contains(1l, 2l);
		assertThat(a1.isAsignado()).isTrue();

		CiudadanoTo c2 = new CiudadanoTo("0000000002", fechaNacimientoHoy.minusYears(35), 3l, 2l);
		CiudadanoTo a2 = administracion.asignarPesoVacunas(c2);
		assertThat(a2.getPeso()).isEqualTo(5);
		assertThat(a2.getVacunasId()).contains(2l, 3l);
		assertThat(a2.isAsignado()).isTrue();

		CiudadanoTo c3 = new CiudadanoTo("0000000003", fechaNacimientoHoy.minusYears(48), 4l, 1l);
		CiudadanoTo a3 = administracion.asignarPesoVacunas(c3);
		assertThat(a3.getPeso()).isEqualTo(5);
		assertThat(a3.getVacunasId()).contains(3l, 4l);
		assertThat(a3.isAsignado()).isTrue();

		CiudadanoTo c4 = new CiudadanoTo("0000000004", fechaNacimientoHoy.minusYears(68), 5l, 3l);
		CiudadanoTo a4 = administracion.asignarPesoVacunas(c4);
		assertThat(a4.getPeso()).isEqualTo(2);
		assertThat(a4.getVacunasId()).contains(5l);
		assertThat(a4.isAsignado()).isTrue();
	}

	@BeforeAll
	public void init() {
		listadoEdades = new ArrayList<>();
		listadoEdades.add(new ParametrizacionEdadPeso(ActivoEnum.SI, 1l, 25, 18, 4));
		listadoEdades.add(new ParametrizacionEdadPeso(ActivoEnum.SI, 2l, 35, 26, 3));
		listadoEdades.add(new ParametrizacionEdadPeso(ActivoEnum.SI, 3l, 50, 36, 2));
		listadoEdades.add(new ParametrizacionEdadPeso(ActivoEnum.SI, 4l, null, 51, 1));

		// 1->Sputnik, 2->Sinovac, 3->AstraZeneca, 4->Moderna, 5->Pfizer
		listadoEdadVacunas = new ArrayList<>();
		listadoEdadVacunas.add(new ParametrizacionEdadVacuna(ActivoEnum.SI, 1l, 25, 18, 1l));
		listadoEdadVacunas.add(new ParametrizacionEdadVacuna(ActivoEnum.SI, 2l, 25, 18, 2l));
		listadoEdadVacunas.add(new ParametrizacionEdadVacuna(ActivoEnum.SI, 3l, 35, 26, 2l));
		listadoEdadVacunas.add(new ParametrizacionEdadVacuna(ActivoEnum.SI, 4l, 35, 26, 3l));
		listadoEdadVacunas.add(new ParametrizacionEdadVacuna(ActivoEnum.SI, 5l, 50, 36, 3l));
		listadoEdadVacunas.add(new ParametrizacionEdadVacuna(ActivoEnum.SI, 6l, 50, 36, 4l));
		listadoEdadVacunas.add(new ParametrizacionEdadVacuna(ActivoEnum.SI, 7l, null, 51, 5l));

		listadoTipoEnfermedad = new ArrayList<>();
		listadoTipoEnfermedad.add(new ParametrizacionTipoEnfermedad(1l, "No tiene", 3));
		listadoTipoEnfermedad.add(new ParametrizacionTipoEnfermedad(2l, "Leve", 2));
		listadoTipoEnfermedad.add(new ParametrizacionTipoEnfermedad(3l, "Grave", 1));

		when(edadServicio.buscarPorActivo(ActivoEnum.SI)).thenReturn(listadoEdades);
		when(edadVacunaServicio.buscarPorActivo(ActivoEnum.SI)).thenReturn(listadoEdadVacunas);
		when(tipoEnfermedadServicio.buscarTodos()).thenReturn(listadoTipoEnfermedad);

		administracion.init();
	}
}
