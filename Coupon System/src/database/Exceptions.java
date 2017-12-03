package database;

public class Exceptions extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Exceptions(Throwable e) {
		initCause(e); 
	}

}
