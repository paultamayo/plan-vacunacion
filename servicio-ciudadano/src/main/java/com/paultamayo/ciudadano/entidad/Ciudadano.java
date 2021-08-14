package com.paultamayo.ciudadano.entidad;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.paultamayo.ciudadano.entidad.enumerador.EstadoCiudadanoEnum;
import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Ciudadano {

  private String apellido;

  private String cedula;

  private String correoElectronico;

  @Enumerated(EnumType.STRING)
  private EstadoCiudadanoEnum estado;

  private LocalDate fechaNacimiento;

  @JsonIgnore
  private LocalDateTime fechaRegistro;

  private String nombre;

  private Double peso;
}
