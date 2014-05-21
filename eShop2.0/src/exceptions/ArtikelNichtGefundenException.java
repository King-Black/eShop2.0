package exceptions;

import Valueobjects.Artikel;

public class ArtikelNichtGefundenException extends Exception {
	public ArtikelNichtGefundenException (Artikel a){
		super("Der Artikel " + a.getArtikelName() + " wurde nicht gefunden.");
	}
	public ArtikelNichtGefundenException (int artID){
		super("Der Artikel mit der ID " + artID + " wurde nicht gefunden.");
	}
	
}
