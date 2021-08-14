package com.paultamayo.ciudadano.servicios;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import com.paultamayo.ciudadano.entidad.Ciudadano;
import com.paultamayo.ciudadano.entidad.enumerador.EstadoCiudadanoEnum;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CiudadanoServicio {

  public List<Ciudadano> buscarTodos() {
    String nombre = "Diego";
    String apellido = "Tamayo";
    String correoElectronico = "diego.tamayo@gmail.com";
    String cedula = "0606066606";
    LocalDate fechaNacimiento = LocalDate.of(1986, Month.DECEMBER, 23);
    Double peso = 34.5;
    EstadoCiudadanoEnum estado = EstadoCiudadanoEnum.PENDIENTE;

    Ciudadano c1 = new Ciudadano(
      nombre,
      apellido,
      correoElectronico,
      cedula,
      LocalDateTime.now(),
      fechaNacimiento,
      peso,
      estado
    );
    Ciudadano c2 = new Ciudadano(
      nombre,
      apellido,
      correoElectronico,
      cedula,
      LocalDateTime.now(),
      fechaNacimiento,
      peso,
      estado
    );

    return List.of(c1, c2);
  }

  @Transactional(
    propagation = Propagation.REQUIRES_NEW,
    noRollbackFor = Exception.class
  )
  public Ciudadano guardar(Ciudadano ciudadano) {
    ciudadano.setFechaRegistro(LocalDateTime.now());
    return ciudadano;
  }
}