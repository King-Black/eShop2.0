package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import Valueobjects.Artikel;
import Valueobjects.Ereignis;
import Valueobjects.Kunde;
import Valueobjects.MehrfachArtikel;
import Valueobjects.Mitarbeiter;
import Valueobjects.User;

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
	
	
	/**
	 * Methode, die eine Zeile ließt, in der ein String gespeichert wurde.
	 * @return String
	 * @throws IOException
	 */
	private String liesZeile() throws IOException {
		if (reader != null)
			return reader.readLine();
		else
			return "";
	}

	/**
	 * Methode, die in einer Zeile einen String schreibt.
	 * @param daten
	 */
	private void schreibeZeile(String daten) {
		if (writer != null)
			writer.println(daten);
	}
	
/*	private void schreibeZeile(Date daten) {
		if (writer != null)
			writer.println(daten);
	}
	
	private void schreibeZeile(Artikel daten) {
		if (writer != null)
			writer.println(daten);
	}
	
	private void schreibeZeile(User daten) {
		if (writer != null)
			writer.println(daten);
	}*/
	
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
	
	
	/*public Ereignis ladeEreignis() throws IOException {
		Date datum = Date.parseDate(liesZeile());	

		String artikel = liesZeile();
		int menge = Integer.valueOf(liesZeile());
		String aktion = liesZeile();
		String user = liesZeile();
		
		
		return new Ereignis(datum, artikel, menge, aktion, user);
	}*/
	
	
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

	

/*	@Override
	public void speichereEreignisse(Ereignis e) throws IOException {
		schreibeZeile(e.getDate());
		schreibeZeile(e.getArtikel());
		schreibeZeile(Integer.toString(e.getMenge()));
		schreibeZeile(e.getAktion());
		schreibeZeile(e.getNutzer());
	}*/

}