package com.paultamayo.ciudadano.excepcion;

public class CedulaExcepcion extends Exception {


	private static final long serialVersionUID = -4623934172864591350L;

	public CedulaExcepcion() {
		super();
	}

	public CedulaExcepcion(String message, Throwable cause) {
		super(message, cause);
	}

	public CedulaExcepcion(String message) {
		super(message);
	}
	
	

}
