package exceptions;

@SuppressWarnings("serial")
public class UserNichtGefundenException extends Exception{
	/**
	 * Wird geworfen, wenn ein User anhand seiner Usernummer nicht gefunden werden kann.
	 * @param userNr Nummer des gesuchten Users.
	 */
	public UserNichtGefundenException (int userNr){
		super("Der User mit der Usernummer " + userNr + "wurde nicht gefunden." );
	}
	
	/**
	 * Wird geworfen, wenn ein User nicht gefunden werden kann.
	 */
	public UserNichtGefundenException (){
		super("Der User wurde nicht gefunden." );
	}
}
