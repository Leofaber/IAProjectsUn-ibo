package it.unibo.ai.didattica.mulino.customExceptions;

public class InvalidPositionException extends Exception {
	
	 
	private static final long serialVersionUID = 1L;

	public InvalidPositionException(String position) {
		super("Position " + position + " does not exist!");
	}
}

 