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

  private String nombre;

  private String apellido;

  private String correoElectronico;

  private String cedula;

  @JsonIgnore
  private LocalDateTime fechaRegistro;

  private LocalDate fechaNacimiento;

  private Double peso;

  @Enumerated(EnumType.STRING)
  private EstadoCiudadanoEnum estado;
}
