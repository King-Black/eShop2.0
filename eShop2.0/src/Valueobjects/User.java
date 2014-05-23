package Valueobjects;

public class User {	
	protected String name;
	protected char[] passwort;
	protected int nr;
	protected String anrede;
	protected String vorName;
	protected String nachName;

	
	public User(String name, char[] passwort, int nr, String anrede, String vorName, String nachName){
		this.name = name;
		this.passwort = passwort;
		this.nr = nr;
		this.anrede = anrede;
		this.vorName = vorName;
		this.nachName = nachName;

	}
	
	public String toString() {
		return null;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public int getNummer(){
		return this.nr;
	}
	
	public void setNummer(int nr){
		this.nr = nr;
	}
	
	public String getAnrede(){
		return this.anrede;
	}
	
	public void setAnrede(String anrede){
		this.anrede = anrede;
	}
	
	public String getVorName(){
		return this.vorName;
	}
	
	public void setVorName(String vorName){
		this.vorName = vorName;
	}
	
	public String getNachName(){
		return this.nachName;
	}
	
	public void setNachName(String nachName){
		this.nachName = nachName;
	}
	
	public char[] getPasswort(){
		return this.passwort;
	}
	
	public void setPasswort(char[] passwort){
		this.passwort = passwort;
	}
}
