package fr.eni.encheres;


/**
 * Classe gérant les exceptions de l'application
 * @author Groupe 3
 *
 */
public class BusinessException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	
	public BusinessException() {
		super();
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

}
