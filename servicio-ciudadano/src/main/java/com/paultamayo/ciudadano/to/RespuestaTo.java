package com.paultamayo.ciudadano.to;

import java.io.Serializable;

import com.paultamayo.ciudadano.enumerador.EstadoRespuestaEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class RespuestaTo<T> implements Serializable {

	private static final long serialVersionUID = 2668006018009654876L;

	private EstadoRespuestaEnum codigo;

	private String mensaje;

	private T objeto;
}
