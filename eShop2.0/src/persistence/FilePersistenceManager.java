package persistence;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import Valueobjects.Artikel;
import Valueobjects.Ereignis;
import Valueobjects.Kunde;
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
		if (titel == null) {
			return null;
		}
		int artikelNr = Integer.parseInt(liesZeile());
		double preis = Double.parseDouble(liesZeile());
		int menge = Integer.parseInt(liesZeile());		
		return new Artikel(artikelNr, titel, menge, preis);
	}	

	
//	public Kunde ladeKunden() throws IOException {
//		// Name einlesen
//		String name = liesZeile();
//		if (name == null) {
//			return null;
//		}
//			//Passwort einlesen
//		String passwort = liesZeile();		
//			// Strasse einlesen ...
//		String str = liesZeile();				
//			//PLZ einlesen
//		String plz = liesZeile();		
//			// Ort einlesen
//		String ort = liesZeile();		
//			//id-nummer einlesen
//		String id = liesZeile();		
//			// neuen Kunden anlegen und zurückgeben
//		return new Kunde(name, passwort, id, new Adresse(str, plz, ort));
//	}
	
	
//	public Mitarbeiter ladeMitarbeiter() throws IOException{
//			// Name einlesen
//		String name = liesZeile();
//		if (name == null) {
//			return null;
//		}
//			//Passwort einlesen
//		String passwort = liesZeile();
//			//id-nummer einlesen
//		String id = liesZeile();
//			//neuen Mitarbeiter Anlegen und zurückgeben
//		return new Mitarbeiter(name, passwort, id);		
//	}
	
	
//	public Ereignis ladeEreignis() throws IOException {
//		String stringDatum = liesZeile();	
//		if (stringDatum == null){
//			return null;
//		}
//		Date df = null;
//		try {
//			df = new SimpleDateFormat("EEE MMM dd kk:mm:ss z yyyy", Locale.ENGLISH).parse(stringDatum);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//
//		String kundenName = liesZeile();
//		String kundenId = liesZeile();
//		String artikelBezeichnung = liesZeile();
//		
//		int veraenderung = Integer.valueOf(liesZeile());
//		int artikelNr = Integer.valueOf(liesZeile());
//		String booleanIstAuslagerung = liesZeile();		
//		boolean istAuslagerung;
//		if(booleanIstAuslagerung.equals("t"))
//			istAuslagerung = true;
//		else 
//			istAuslagerung = false;
//		
//		return null /*new Ereignis(df, kundenName, kundenId, artikelBezeichnung, veraenderung, artikelNr, istAuslagerung)*/;
//	}
	
	
	@Override
	public void speichereArtikel(Artikel a) {
		schreibeZeile(a.getArtikelName());
		schreibeZeile(a.getArtikelNummer() + "");
		schreibeZeile(a.getPreis() + "");
		schreibeZeile(a.getArtikelBestand() + "");	
	}
	
	
	
//	public void speicherKunde(Kunde k)	{
//		// Name, Kundennr, Adresse schreiben
//		schreibeZeile(k.getName());
//		schreibeZeile(k.getPasswort());
//		schreibeZeile(k.getStr());
//		schreibeZeile(k.getPlz());
//		schreibeZeile(k.getOrt());		
//		schreibeZeile(k.getId());		
//	}
	
	
//	public void speicherMitarbeiter(Mitarbeiter m) throws IOException {
//		schreibeZeile(m.getName());	
//		schreibeZeile(m.getPasswort());
//		schreibeZeile(m.getId());		
//	}
	

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
