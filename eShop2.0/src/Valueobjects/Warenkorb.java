package Valueobjects;

import java.util.HashMap;

public class Warenkorb {

	private HashMap<Artikel, Integer> warenkorb = new HashMap<Artikel, Integer>();
	
	public void artikelHinzufuegen(Artikel a, int gewuenschteMenge, int mengeNochDa){
		// Wenn Artikel schon im Warenkorb vorhanden dann Menge erweitern
		if (warenkorb.containsKey(a)) { // WArenkorb hat hier nichtr den gewünschten artikel drin
			int alteMenge = warenkorb.get(a);
			if (alteMenge+gewuenschteMenge<=mengeNochDa) {
				System.out.println("Artikel im Warenkorb");
				warenkorb.put(a, alteMenge + gewuenschteMenge);	
			}
		} else {
		// Ansonsten neu in Liste hinzufügen
			warenkorb.put(a, gewuenschteMenge);
		}
	}	
	
	public void artikelEntfernen(Artikel a){
		if(warenkorb.containsKey(a)) {
			warenkorb.remove(a);
		}
	}
	
	public void leeren(){
		if(!warenkorb.isEmpty()){
			warenkorb.clear();
		}
	}
	
	public HashMap<Artikel, Integer> getInhalt(){
		return warenkorb;
	}
	
	public void setWarenkorb(HashMap<Artikel, Integer> w) {
		warenkorb = w;
	}
	
}
