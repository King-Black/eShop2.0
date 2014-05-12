package exceptions;

import Valueobjects.User;

public class NichtEingeloggtException extends Exception{
	public NichtEingeloggtException(User user) {
		super("Aktion kann nicht ausgef�hrt werden. " +
			  user.getVorName() + " " + user.getNachName() +
			  " ist nicht eingeloggt!");
	}
}
