package com.paultamayo.ciudadano.controlador;

import com.paultamayo.ciudadano.entidad.Ciudadano;
import com.paultamayo.ciudadano.servicios.CiudadanoServicio;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CiudadanoControlador {

  @Autowired
  private CiudadanoServicio servicio;

  @GetMapping("/todos")
  public ResponseEntity<List<Ciudadano>> buscarTodos() {
    return new ResponseEntity<>(servicio.buscarTodos(), HttpStatus.OK);
  }

  @ResponseBody
  @PostMapping(path = "/crear", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Ciudadano> crear(@RequestBody Ciudadano ciudadano) {
    return new ResponseEntity<>(servicio.guardar(ciudadano), HttpStatus.OK);
  }

  @GetMapping("/")
  public String index() {
    return "Servicios de Ciudadanos";
  }
}
