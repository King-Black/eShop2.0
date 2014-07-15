package Valueobjects;

import java.util.HashMap;

import exceptions.NichtGenugAufLagerException;
import exceptions.WarenkorbLeerException;

/**
 * Klasse zur Repräsentation des Warenkorbs.
 * @author Imke Schneider
 *
 */

public class Warenkorb {

	private HashMap<Artikel, Integer> warenkorb = new HashMap<Artikel, Integer>();
/**
 * Die Methode fügt Artikel zum Warenkorb hinzu und prüft ob die geünschte Menge noch ausreichend auf Lager ist.	
 * @param a Artikel der hinzugefügt werden soll.
 * @param gewuenschteMenge Menge die zum Warenkorb hinzugefügt wird.
 * @param mengeNochDa Bestand des Artikels.
 * @throws NichtGenugAufLagerException wird geworfen, wenn nicht ausreichend auf Lager ist.
 */
	public void artikelHinzufuegen(Artikel a, int gewuenschteMenge, int mengeNochDa) throws NichtGenugAufLagerException{
		// Wenn Artikel schon im Warenkorb vorhanden dann Menge erweitern
		if (warenkorb.containsKey(a)) { // Warenkorb hat hier nichtr den gewï¿½nschten artikel drin
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
	 * @throws WarenkorbLeerException wird geworfen, wenn kein Artikel zum löschen im WK ist.
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
	 * @throws WarenkorbLeerException wird geworfen wenn kein Artikel im Warenkorb zum löschen ist.
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
	 * Die Methode gibt in einer Hashmap den Inhalt des Warenkorbs zurück.
	 * @return gibt Hashmap mit Artikeln mit ensprechenden Mengen zurück 
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
