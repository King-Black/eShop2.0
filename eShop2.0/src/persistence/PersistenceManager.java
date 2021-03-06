package persistence;

import java.io.IOException;

import Valueobjects.Artikel;
import Valueobjects.EinArtikel;
import Valueobjects.Ereignis;
import Valueobjects.Kunde;
import Valueobjects.MehrfachArtikel;
import Valueobjects.Mitarbeiter;


/**
 * Klasse zum laden und speichern der .txt Dateien.
 * @author King-Black
 *
 */
public interface PersistenceManager {

	public void openForReading(String datenquelle) throws IOException;
	
	public void openForWriting(String datenquelle) throws IOException;
	
	public boolean close();

	
	public EinArtikel ladeEinArtikel() throws IOException;
	
	public MehrfachArtikel ladeMehrfachArtikel() throws IOException;
	
	public Mitarbeiter ladeMitarbeiter() throws IOException;
	
	public Kunde ladeKunden() throws IOException;
	
//	public Ereignis ladeEreignis() throws IOException;
	
	
	
	public void speichereEinArtikel(EinArtikel a);
	
	public void speichereMehrfachArtikel (MehrfachArtikel m);
	
	public void speichereMitarbeiter(Mitarbeiter m) throws IOException;
	
	public void speichereKunde(Kunde k) throws IOException;
	
//	public void speichereEreignisse(Ereignis e) throws IOException;
}