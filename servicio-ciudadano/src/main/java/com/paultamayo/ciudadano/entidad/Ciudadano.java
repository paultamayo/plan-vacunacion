package com.paultamayo.ciudadano.entidad;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paultamayo.ciudadano.enumerador.EstadoCiudadanoEnum;
import com.paultamayo.ciudadano.restricciones.CedulaEcuador;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@Entity
@NoArgsConstructor
public class Ciudadano {

	private static final String SOLO_LETRAS_ESPACIOS = "^[a-zA-Z ]+$";

	@NotBlank
	@Pattern(regexp = SOLO_LETRAS_ESPACIOS)
	private String apellido;

	@Id
	@NotBlank
	@CedulaEcuador
	private String cedula;

	@Column(name = "correo_electronico")
	@Email
	private String correoElectronico;

	@Enumerated(EnumType.STRING)
	@JsonIgnore
	private EstadoCiudadanoEnum estado;

	@Column(name = "fecha_nacimiento")
	@NotNull
	@Past
	private LocalDate fechaNacimiento;

	@Column(name = "fecha_registro")
	@JsonIgnore
	private LocalDateTime fechaRegistro;

	@NotBlank
	@Pattern(regexp = SOLO_LETRAS_ESPACIOS)
	private String nombre;

	@NotNull
	@Positive
	private Double peso;
}
