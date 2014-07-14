package exceptions;

import Valueobjects.User;

@SuppressWarnings("serial")
public class NichtEingeloggtException extends Exception{
	/**
	 * Wird geworfen, wenn eine Aktion ausgef�hrt werden soll die man nur im eingeloggten Zustand ausf�hren kann.
	 * @param user
	 */
	public NichtEingeloggtException(User user) {
		super("Aktion kann nicht ausgef�hrt werden. " +
			  user.getVorName() + " " + user.getNachName() +
			  " ist nicht eingeloggt!");
	}
	}
