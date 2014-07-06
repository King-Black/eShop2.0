import CUI.CUI;
import Domain.ShopVerwaltung;


public class Main {
	
	private static  ShopVerwaltung shopVer;

	
public static void main(String[] args){
	
	shopVer = new ShopVerwaltung();
		
		//Thread shopThread = new Thread(new GUI(shopVer));
		//shop.shopVer.fuegeArtikelEin("SECHSSTEIN", 9.99, null, 48, 6);	
		shopVer.fuegeUserEin("Kunde", "123", "Frau", "Regina", "Regenbogen","Elbenweg 3", 13337, "Bruchtal");
		shopVer.fuegeUserEin("Mitarbeiter", "123", "Herr", "Max", "Mustermann");
		shopThread.start();
	
		
		
		
		
		//CUI.guiStarten();
		
	}



}
