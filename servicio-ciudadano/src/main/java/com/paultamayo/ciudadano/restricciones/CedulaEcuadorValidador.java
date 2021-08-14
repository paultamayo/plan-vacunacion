package com.paultamayo.ciudadano.restricciones;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.paultamayo.ciudadano.excepcion.CedulaExcepcion;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class CedulaEcuadorValidador implements ConstraintValidator<CedulaEcuador, String> {

	@Override
	public boolean isValid(String cedula, ConstraintValidatorContext context) {
		return validar(cedula);
	}

	private static boolean algoritmoModulo10(String digitosIniciales, int digitoVerificador) throws CedulaExcepcion {
		Integer[] arrayCoeficientes = new Integer[] { 2, 1, 2, 1, 2, 1, 2, 1, 2 };

		Integer[] digitosInicialesTMP = new Integer[digitosIniciales.length()];
		int indice = 0;
		for (char valorPosicion : digitosIniciales.toCharArray()) {
			digitosInicialesTMP[indice] = NumberUtils.createInteger(String.valueOf(valorPosicion));
			indice++;
		}

		int total = 0;
		int key = 0;
		for (Integer valorPosicion : digitosInicialesTMP) {
			if (key < arrayCoeficientes.length) {
				valorPosicion = (digitosInicialesTMP[key] * arrayCoeficientes[key]);

				if (valorPosicion >= 10) {
					char[] valorPosicionSplit = String.valueOf(valorPosicion).toCharArray();
					valorPosicion = (Integer.parseInt(String.valueOf(valorPosicionSplit[0])))
							+ (Integer.parseInt(String.valueOf(valorPosicionSplit[1])));

				}
				total = total + valorPosicion;
			}

			key++;
		}
		int residuo = total % 10;
		int resultado;

		if (residuo == 0) {
			resultado = 0;
		} else {
			resultado = 10 - residuo;
		}

		if (resultado != digitoVerificador) {
			throw new CedulaExcepcion("La cédula no es válida.");
		}

		return true;
	}

	private static boolean validarInicial(String numero, int caracteres) throws CedulaExcepcion {
		if (StringUtils.isEmpty(numero)) {
			throw new CedulaExcepcion("La cédula que ha ingresado esta en blanco.");
		}

		if (!NumberUtils.isDigits(numero)) {
			throw new CedulaExcepcion("La cédula ingresada debe contener solo números.");
		}

		if (numero.length() != caracteres) {
			throw new CedulaExcepcion(String.format("La cédula ingresada debe tener %s caracteres.", caracteres));
		}

		return true;
	}

	private static boolean validarTercerDigito(String numero, Integer tipo) throws CedulaExcepcion {
		if (Integer.parseInt(numero) < 0 || Integer.parseInt(numero) > 5) {
			throw new CedulaExcepcion("La cédula no corresponde a una cédula válida.");
		}

		return true;
	}

	private static boolean validarCodigoProvincia(String numero) throws CedulaExcepcion {
		if (Integer.parseInt(numero) < 0 || Integer.parseInt(numero) > 24) {
			throw new CedulaExcepcion("El código de la provincia no se encuentra en el intervalo [1, 24].");
		}

		return true;
	}

	public boolean validar(String cedula) {
		try {
			validarInicial(cedula, 10);
			validarCodigoProvincia(cedula.substring(0, 2));
			validarTercerDigito(String.valueOf(cedula.charAt(2)), 1);
			algoritmoModulo10(cedula, Integer.parseInt(String.valueOf(cedula.charAt(9))));
			return true;
		} catch (CedulaExcepcion ex) {
			log.error(ex.getMessage());
			return false;
		}
	}

}
