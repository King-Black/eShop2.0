package Valueobjects;

import java.util.Date;
import java.util.HashMap;

/**
 * Klasse zur Repräsentation einer Rechnung.
 * @author Imke Schneider
 *
 */

public class Rechnung {

	private Kunde kunde;
	@SuppressWarnings("unused")
	private Warenkorb warenkorb;
	private Date datum;
	private double gesamtpreis;
	private HashMap<Artikel, Integer> wkorb;
	
	/**
	 * Initalisiert ein Rechnungsobjekt.
	 * @param kunde Kunde, der einkauft.
	 * @param warenkorb Warenkorb der zum Kauf bestätigt wurde.
	 * @param datum Datum, an dem der Kauf stattfindet.
	 */
	public Rechnung(Kunde kunde, Warenkorb warenkorb, Date datum) {
		this.kunde = kunde;
		this.datum = datum;
		System.out.println("Rechnung: \n" +
				"Kunde: " + kunde.getVorName() + kunde.getNachName() +
				" | Datum: " + datum);
		this.warenkorb = warenkorb;
		this.wkorb = warenkorb.getInhalt();
		this.gesamtpreis = 0;
		for (Artikel a : wkorb.keySet()) {
			System.out.println(a.getArtikelName() + " | Anzahl: " + wkorb.get(a) + " | Einzelpreis: " + a.getPreis() + " | Gesamtpreis: " + wkorb.get(a)*a.getPreis());
			gesamtpreis = gesamtpreis + wkorb.get(a)*a.getPreis();			
		}
		System.out.println("Gesamtpreis: " + gesamtpreis);
	}
	
	/**
	 * Die Methode gibt das rechnungsfenster in der Gui aus.
	 * @return Gibt das rechnungsfenster in der Gui aus.
	 */
	public String printRechnung() {
		this.gesamtpreis = 0;
		String str = ""; 
		str += "Rechnung\n\n"; 
		str += "Kunde: \t" + kunde.getVorName() + " " + kunde.getNachName() + "\n"; 
		str += "Datum: \t" + datum + "\n\n"; 
		str += "gekaufte Artikel: \n"; 
		for (Artikel a : wkorb.keySet()) {
			str += a.getArtikelName() + " | Anzahl: " + wkorb.get(a) + " | Einzelpreis: " + a.getPreis() + " | Gesamtpreis: " + wkorb.get(a)*a.getPreis() + "\n";
			gesamtpreis = gesamtpreis + wkorb.get(a)*a.getPreis(); 
		} 
		str += "Rechnungsbetrag: \t" + gesamtpreis; 
		return str; 
	}
	
}
