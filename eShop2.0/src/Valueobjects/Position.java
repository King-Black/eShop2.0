package Valueobjects;

import java.io.Serializable;

import eshop.client.valueobjects.Artikel;

public class Position {
		
	private Artikel artikel;
	private int menge;
	
	/**
	 * Initialisiert ein Positionsobjekt.
	 * @param artikel Der Artikel, der gekauft werden soll.
	 * @param menge Die Menge des Artikels, der gekauft werden soll.
	 */
	public Position(Artikel artikel, int menge){
		this.artikel = artikel;
		this.menge = menge;
	}
	
	/**
	 * Die Methode gibt den Artikel zurück, der Bestandteil dieser Position ist.
	 * @return Gibt den Artikel zurück, der Bestandteil dieser Position ist.
	 */
	public Artikel getArtikel(){
		return this.artikel;
	}
	
	/**
	 * Die Methode setzt den Artikel der Bestandteil dieser Position.
	 * @param artikel Der Artikel, der in der Position enthalten sein soll.
	 */
	public void setArtikel(Artikel artikel){
		this.artikel = artikel;
	}
	
	/**
	 * Die Methode gibt die Menge des Artikels in der Position zurück.
	 * @return Gibt die Menge des Artikels, der Bestandteil dieser Position ist, zurück.
	 */
	public int getMenge(){
		return this.menge;
	}
	
	/**
	 * Die Methode setzt die Menge des Artikels, der Bestandteil der Position ist.
	 * @param menge Die Menge des Artikels, der Bestandteil dieser Position ist.
	 */
	public void setMenge(int menge){
		this.menge = menge;
	}
}
