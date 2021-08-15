package com.paultamayo.base.excepcion;

public class LogicaServicioExcepcion extends Exception {


	private static final long serialVersionUID = 5933752808118621779L;

	public LogicaServicioExcepcion() {
		super();
	}

	public LogicaServicioExcepcion(String message) {
		super(message);
	}

	public LogicaServicioExcepcion(String message, Throwable cause) {
		super(message, cause);
	}

}
