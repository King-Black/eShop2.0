package persistence;

import java.io.IOException;

import Valueobjects.Artikel;


public interface PersistenceManager {

	public void openForReading(String datenquelle) throws IOException;
	
	public void openForWriting(String datenquelle) throws IOException;
	
	public boolean close();

	
	public Artikel ladeArtikel() throws IOException;
	
	//public Mitarbeiter ladeMitarbeiter() throws IOException;
	
	//public Kunde ladeKunden() throws IOException;
	
	//public Ereignis ladeEreignis() throws IOException;
	
	
	
	public void speichereArtikel(Artikel a) throws IOException;
	
	//public void speicherMitarbeiter(Mitarbeiter m) throws IOException;
	
	//public void speicherKunde(Kunde k) throws IOException;
	
	//public void speichereEreignisse(Ereignis e) throws IOException;
}