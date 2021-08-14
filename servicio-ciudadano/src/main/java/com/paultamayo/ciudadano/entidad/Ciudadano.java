package com.paultamayo.ciudadano.entidad;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paultamayo.ciudadano.entidad.enumerador.EstadoCiudadanoEnum;
import com.paultamayo.ciudadano.restricciones.CedulaEcuador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Ciudadano {

	private static final String SOLO_LETRAS_ESPACIOS = "^[a-zA-Z ]+$";

	@NotBlank
	@Pattern(regexp = SOLO_LETRAS_ESPACIOS)
	private String apellido;

	@NotBlank
	@CedulaEcuador
	private String cedula;

	@Email
	private String correoElectronico;

	@Enumerated(EnumType.STRING)
	private EstadoCiudadanoEnum estado;

	@NotNull
	private LocalDate fechaNacimiento;

	@JsonIgnore
	private LocalDateTime fechaRegistro;

	@NotBlank
	@Pattern(regexp = SOLO_LETRAS_ESPACIOS)
	private String nombre;

	@NotNull
	@Positive
	private Double peso;
}
