package com.paultamayo.ciudadano.servicios;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.paultamayo.ciudadano.entidad.Ciudadano;
import com.paultamayo.ciudadano.enumerador.EstadoCiudadanoEnum;
import com.paultamayo.ciudadano.excepcion.LogicaServicioExcepcion;
import com.paultamayo.ciudadano.repositorio.CiudadanoRepositorio;

@SpringBootTest
class CiudadanoServicioTests {

	@Mock
	private CiudadanoRepositorio repositorio;

	@InjectMocks
	private CiudadanoServicio servicio = new CiudadanoServicio();

	@Test
	void evaluarBuscarPorCedula() throws LogicaServicioExcepcion {
		String cedula = "0000000000";

		Ciudadano ciudadano = new Ciudadano();
		ciudadano.setApellido("Apellido de Prueba");
		ciudadano.setCedula(cedula);
		ciudadano.setCorreoElectronico("prueba@algo.com");
		ciudadano.setEstado(EstadoCiudadanoEnum.PENDIENTE);
		ciudadano.setFechaNacimiento(LocalDate.now());
		ciudadano.setFechaRegistro(LocalDateTime.now());
		ciudadano.setNombre("Nombre de Prueba");
		ciudadano.setPeso(55.0);

		when(repositorio.findById(cedula)).thenReturn(Optional.of(ciudadano));

		Ciudadano c = servicio.buscarPorId(cedula);

		assertThat(c).isNotNull();
	}

	@Test
	void evaluarBuscarPorCedulaException() throws LogicaServicioExcepcion {
		String cedula = "0000000000";

		when(repositorio.findById(cedula)).thenReturn(Optional.empty());

		assertThatExceptionOfType(LogicaServicioExcepcion.class).isThrownBy(() -> servicio.buscarPorId(cedula))
				.withMessage("No se ha podido identificar un ciudadano con cédula: " + cedula);
	}

	@Test
	void evaluarBuscarTodos() {
		when(repositorio.findAll()).thenReturn(List.of());

		Iterable<Ciudadano> iterable = servicio.buscarTodos();

		assertThat(iterable).isNotNull();
	}

	@Test
	void evaluarCreacionCiudadano() throws LogicaServicioExcepcion {
		Ciudadano ciudadano = new Ciudadano();

		Ciudadano ciudadanoGuardado = new Ciudadano();
		ciudadanoGuardado.setFechaRegistro(LocalDateTime.now());
		ciudadanoGuardado.setEstado(EstadoCiudadanoEnum.PENDIENTE);

		when(repositorio.save(ciudadano)).thenReturn(ciudadanoGuardado);

		Ciudadano c = servicio.guardar(ciudadano);

		assertThat(c.getEstado()).isNotNull();
		assertThat(c.getFechaRegistro()).isNotNull();
	}

}
