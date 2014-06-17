package exceptions;

import Valueobjects.Artikel;

@SuppressWarnings("serial")
public class ArtikelNichtGefundenException extends Exception {
	public ArtikelNichtGefundenException (Artikel a){
		super("Der Artikel " + a.getArtikelName() + " wurde nicht gefunden.");
	}
	public ArtikelNichtGefundenException (int artID){
		super("Der Artikel mit der ID " + artID + " wurde nicht gefunden.");
	}
	
}
