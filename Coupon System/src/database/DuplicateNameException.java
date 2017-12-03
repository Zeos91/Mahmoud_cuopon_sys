package database;

public class DuplicateNameException extends Throwable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateNameException(String message){
		super(message);
	}

}
