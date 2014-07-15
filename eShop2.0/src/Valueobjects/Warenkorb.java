package Valueobjects;

import java.util.HashMap;

import exceptions.NichtGenugAufLagerException;
import exceptions.WarenkorbLeerException;

/**
 * Klasse zur Repr�sentation des Warenkorbs.
 * @author Imke Schneider
 *
 */

public class Warenkorb {

	private HashMap<Artikel, Integer> warenkorb = new HashMap<Artikel, Integer>();
/**
 * Die Methode f�gt Artikel zum Warenkorb hinzu und pr�ft ob die ge�nschte Menge noch ausreichend auf Lager ist.	
 * @param a Artikel der hinzugef�gt werden soll.
 * @param gewuenschteMenge Menge die zum Warenkorb hinzugef�gt wird.
 * @param mengeNochDa Bestand des Artikels.
 * @throws NichtGenugAufLagerException wird geworfen, wenn nicht ausreichend auf Lager ist.
 */
	public void artikelHinzufuegen(Artikel a, int gewuenschteMenge, int mengeNochDa) throws NichtGenugAufLagerException{
		// Wenn Artikel schon im Warenkorb vorhanden dann Menge erweitern
		if (warenkorb.containsKey(a)) { // Warenkorb hat hier nichtr den gew�nschten artikel drin
			int alteMenge = warenkorb.get(a);
			if (alteMenge+gewuenschteMenge<=mengeNochDa) {
				System.out.println("Artikel im Warenkorb");
				warenkorb.put(a, alteMenge + gewuenschteMenge);	
			} else{
				NichtGenugAufLagerException e = new NichtGenugAufLagerException(a);
				throw e;
			}
		} else {
		// Ansonsten neu in Liste hinzufuegen
			warenkorb.put(a, gewuenschteMenge);
		}
	}	
	
	/**
	 * Eine Methode zum leeren des Warenkorbs.
	 * @param a Gibt den Artikel wieder der entfernt wurde.
	 * @throws WarenkorbLeerException wird geworfen, wenn kein Artikel zum l�schen im WK ist.
	 */
	public void artikelEntfernen(Artikel a) throws WarenkorbLeerException{
		if(warenkorb.containsKey(a)) {
			warenkorb.remove(a);
		} else{
			WarenkorbLeerException e = new WarenkorbLeerException();
			throw e;
		}
	}
	
	/**
	 * Eine Methode zum leere des Warenkorbs.
	 * @throws WarenkorbLeerException wird geworfen wenn kein Artikel im Warenkorb zum l�schen ist.
	 */
	public void leeren() throws WarenkorbLeerException{
		if(!warenkorb.isEmpty()){
			warenkorb.clear();
		} else {
			WarenkorbLeerException e = new WarenkorbLeerException();
			throw e;
		}
	}
	/**
	 * Die Methode gibt in einer Hashmap den Inhalt des Warenkorbs zur�ck.
	 * @return gibt Hashmap mit Artikeln mit ensprechenden Mengen zur�ck 
	 */
	public HashMap<Artikel, Integer> getInhalt(){
		return warenkorb;
	}
	
	/**
	 * Die Methode setzt den Warenkorb.
	 * @param w Setzt den Warenkorb.
	 */
	public void setWarenkorb(HashMap<Artikel, Integer> w) {
		warenkorb = w;
	}
	
}
