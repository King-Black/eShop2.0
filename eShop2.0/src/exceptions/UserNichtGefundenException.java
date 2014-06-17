package exceptions;

@SuppressWarnings("serial")
public class UserNichtGefundenException extends Exception{
	public UserNichtGefundenException (int userNr){
		super("Der User mit der Usernummer " + userNr + "wurde nicht gefunden." );
	}

}
