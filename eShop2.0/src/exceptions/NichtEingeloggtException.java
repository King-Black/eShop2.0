package exceptions;

import Valueobjects.User;

@SuppressWarnings("serial")
public class NichtEingeloggtException extends Exception{
	/**
	 * Wird geworfen, wenn eine Aktion ausgeführt werden soll die man nur im eingeloggten Zustand ausführen kann.
	 * @param user
	 */
	public NichtEingeloggtException(User user) {
		super("Aktion kann nicht ausgeführt werden. " +
			  user.getVorName() + " " + user.getNachName() +
			  " ist nicht eingeloggt!");
	}
	}
