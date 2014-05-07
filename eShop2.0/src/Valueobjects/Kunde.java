package Valueobjects;

public class Kunde extends User {
	
	private int kundenId;
	
	public Kunde(int kundenId, String vorName, String nachName, String passwort){
		super(vorName, nachName, passwort);
		this.kundenId = kundenId;
		
	}
	
	public int getKundenId(){
		return this.kundenId;
	}
	
	public void setKundenId(int kundenId){
		this.kundenId = kundenId;
	}

}
