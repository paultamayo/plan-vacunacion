package com.paultamayo.administrador.servicio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.paultamayo.administrador.entidad.ParametrizacionEdadPeso;
import com.paultamayo.administrador.entidad.ParametrizacionEdadVacuna;
import com.paultamayo.administrador.entidad.ParametrizacionTipoEnfermedad;
import com.paultamayo.base.enumerador.ActivoEnum;
import com.paultamayo.base.excepcion.LogicaServicioExcepcion;
import com.paultamayo.base.to.CiudadanoTo;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class AdministracionVacunasTest {

	@Mock
	private ParametrizacionEdadPesoServicio edadServicio;

	@Mock
	private ParametrizacionEdadVacunaServicio edadVacunaServicio;

	@Mock
	private ParametrizacionTipoEnfermedadServicio tipoEnfermedadServicio;

	@InjectMocks
	private AdministracionVacunas administracion = new AdministracionVacunas();

	private List<ParametrizacionEdadPeso> listadoEdades;

	private List<ParametrizacionEdadVacuna> listadoEdadVacunas;

	private List<ParametrizacionTipoEnfermedad> listadoTipoEnfermedad;

	@BeforeAll
	public void init() {
		listadoEdades = new ArrayList<>();
		listadoEdadVacunas = new ArrayList<>();
		listadoTipoEnfermedad = new ArrayList<>();

		when(edadServicio.buscarPorActivo(ActivoEnum.SI)).thenReturn(listadoEdades);
		when(edadVacunaServicio.buscarPorActivo(ActivoEnum.SI)).thenReturn(listadoEdadVacunas);
		when(tipoEnfermedadServicio.buscarTodos()).thenReturn(listadoTipoEnfermedad);

		administracion.init();
	}

	@Test
	void asignarPriorirdadVacunas() throws LogicaServicioExcepcion {
		List<CiudadanoTo> ciudadanosPendientes = new ArrayList<>();

		List<CiudadanoTo> ciudadanoAsignados = administracion.asignarPriorirdadVacunas(ciudadanosPendientes);

		assertThat(ciudadanoAsignados).isNotNull();
	}
}
