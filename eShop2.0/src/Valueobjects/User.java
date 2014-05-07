package Valueobjects;

public class User {
	private String vorName;
	private String nachName;
	private String passwort;
	
	public User(String vorName, String nachName, String passwort){
		this.vorName = vorName;
		this.nachName = nachName;
		this.passwort = passwort;
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
	
	public void setNachName(){
		this.nachName = nachName;
	}
	
	public String getPasswort(){
		return this.passwort;
	}
	
	public void setPasswort(String passwort){
		this.passwort = passwort;
	}
}
