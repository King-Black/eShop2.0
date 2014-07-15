package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import Valueobjects.Artikel;
import Valueobjects.Kunde;
import Valueobjects.MehrfachArtikel;
import Valueobjects.Mitarbeiter;

/**
 * Realisierung einer Schnittstelle zur persistenten Speicherung von
 * Daten in Textdateien.
 * @see persistence.PersistenceManager
 */

public class FilePersistenceManager implements PersistenceManager {

	private BufferedReader reader = null;
	private PrintWriter writer = null;
	
	public void openForReading(String datei) throws FileNotFoundException {
		reader = new BufferedReader(new FileReader(datei));
	}

	public void openForWriting(String datei) throws IOException {
		writer = new PrintWriter(new BufferedWriter(new FileWriter(datei)));
	}

	public boolean close() {
		if (writer != null)
			writer.close();		
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();				
				return false;
			}
		}
		return true;
	}
	
	
	private String liesZeile() throws IOException {
		if (reader != null)
			return reader.readLine();
		else
			return "";
	}

	private void schreibeZeile(String daten) {
		if (writer != null)
			writer.println(daten);
	}
	
	
	@Override
	public Artikel ladeArtikel() throws IOException {
		String titel = liesZeile();
		if (titel == null || titel.length() == 0) {
			return null;
		}
		int artikelNr = Integer.parseInt(liesZeile());
		double preis = Double.parseDouble(liesZeile());
		int menge = Integer.parseInt(liesZeile());		
		return new Artikel(artikelNr, titel, menge, preis);
	}
	
	public MehrfachArtikel ladeMehrfachArtikel() throws IOException {
		String titel = liesZeile();
		if (titel == null) {
			return null;
		}
		int artikelNr = Integer.parseInt(liesZeile());
		double preis = Double.parseDouble(liesZeile());
		int menge = Integer.parseInt(liesZeile());
		int groesse = Integer.parseInt(liesZeile());
		float stueckpreis = Float.parseFloat(liesZeile());
		return new MehrfachArtikel(artikelNr, titel, menge, preis, groesse, stueckpreis);
	}

	
	public Kunde ladeKunden() throws IOException {
		// Name einlesen
		String name = liesZeile();
		if (name == null) {
			return null;
		}
			//Passwort einlesen
		String passwort = liesZeile();
			// ID einlesen
		int id = Integer.parseInt(liesZeile());
			//Anrede einlesen
		String anrede  = liesZeile();
			//Vorname einlesen
		String vorName = liesZeile();
			//Nachname einlesen
		String nachName = liesZeile();
			// Strasse einlesen ...
		String strasse = liesZeile();				
			//PLZ einlesen
		int plz = Integer.parseInt(liesZeile());		
			// Ort einlesen
		String ort = liesZeile();		
			// neuen Kunden anlegen und zurückgeben
		return new Kunde(name, passwort, id, anrede, vorName, nachName, strasse, plz, ort);
	}
	
	
	public Mitarbeiter ladeMitarbeiter() throws IOException{
			// Name einlesen
		String name = liesZeile();
		if (name == null) {
			return null;
		}
		String passwort = liesZeile();
		int id = Integer.parseInt(liesZeile());
		String anrede  = liesZeile();
		String vorName = liesZeile();
		String nachName = liesZeile();
		return new Mitarbeiter(name, passwort, id, anrede, vorName, nachName);		
	}
	
	
/*	public Ereignis ladeEreignis() throws IOException {
		String stringDatum = liesZeile();	
		if (stringDatum == null){
			return null;
		}
		Date df = null;
		try {
			df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH).parse(stringDatum);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String kundenName = liesZeile();
		String kundenId = liesZeile();
		String artikelBezeichnung = liesZeile();
		
		int veraenderung = Integer.valueOf(liesZeile());
		int artikelNr = Integer.valueOf(liesZeile());
		String booleanIstAuslagerung = liesZeile();		
		boolean istAuslagerung;
		if(booleanIstAuslagerung.equals("t"))
			istAuslagerung = true;
		else 
			istAuslagerung = false;
		
		return null //new Ereignis(df, kundenName, kundenId, artikelBezeichnung, veraenderung, artikelNr, istAuslagerung);
	}
*/
	
	
	@Override
	public void speichereArtikel(Artikel a) {
		schreibeZeile(a.getArtikelName());
		schreibeZeile(a.getArtikelNummer() + "");
		schreibeZeile(a.getPreis() + "");
		schreibeZeile(a.getArtikelBestand() + "");	
	}
	
	public void speichereMehrfachArtikel(MehrfachArtikel m) {
		schreibeZeile(m.getArtikelName());
		schreibeZeile(m.getArtikelNummer() + "");
		schreibeZeile(m.getPreis() + "");
		schreibeZeile(m.getArtikelBestand() + "");
		schreibeZeile(m.getPackungsgroesse() + "");
		schreibeZeile(m.getStueckPreis() + "");
	}
	
	
	
	public void speichereKunde(Kunde k)	{
		// Name, Kundennr, Adresse schreiben
		schreibeZeile(k.getName());
		schreibeZeile(k.getPasswort());
		schreibeZeile(Integer.toString(k.getNummer()));
		schreibeZeile(k.getAnrede());
		schreibeZeile(k.getVorName());
		schreibeZeile(k.getNachName());		
		schreibeZeile(k.getStrasse());
		schreibeZeile(Integer.toString(k.getPlz()));
		schreibeZeile(k.getOrt());
	}
	
	
	public void speichereMitarbeiter(Mitarbeiter m) throws IOException {
		schreibeZeile(m.getName());	
		schreibeZeile(m.getPasswort());
		schreibeZeile(Integer.toString(m.getNummer()));
		schreibeZeile(m.getAnrede());		
		schreibeZeile(m.getVorName());		
		schreibeZeile(m.getNachName());		
	}

	

//	@Override
//	public void speichereEreignisse(Ereignis e) throws IOException {
//		schreibeZeile(String.valueOf(e.getDate().toString()));
//		schreibeZeile(e.getKundenName());
//		schreibeZeile(e.getKundenId());
//		schreibeZeile(e.getArtikelBezeichung());
//		schreibeZeile(String.valueOf(e.getArtikelBestand()));
//		schreibeZeile(String.valueOf(e.getArtikelNr()));
//		if (e.istAuslagerung())
//			schreibeZeile("t");
//		else
//			schreibeZeile("f");		
//	}

}