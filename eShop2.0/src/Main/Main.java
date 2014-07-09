package Main;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import exceptions.EinlagernException;
import CUI.CUI;
import Domain.ShopVerwaltung;
import GUI.HauptFenster;


public class Main {
	
	private static  ShopVerwaltung shopVer;
	private static CUI cui;

	
public static void main(String[] args){
	
	shopVer = new ShopVerwaltung();
		
		/*Thread shopThread = new Thread(new GUI(shopVer));
		//shop.shopVer.fuegeArtikelEin("SECHSSTEIN", 9.99, null, 48, 6);	
		shopVer.fuegeUserEin("Kunde", "123", "Frau", "Regina", "Regenbogen","Elbenweg 3", 13337, "Bruchtal");
		shopVer.fuegeUserEin("Mitarbeiter", "123", "Herr", "Max", "Mustermann");
		cui.run();*/
	
		//cuiStarten();
		guiStarten();
		
	}

	private static void cuiStarten(){
		CUI shop = new CUI();
		try {
			shopVer.fuegeArtikelEin("Hose", 48, 6);
		} catch (EinlagernException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		shopVer.fuegeUserEin("Kunde", "123", "Frau", "Regina", "Regenbogen","Elbenweg 3", 13337, "Bruchtal");
		shopVer.fuegeUserEin("Mitarbeiter", "123", "Herr", "Max", "Mustermann");
		try {
			shop.run();
		}
		catch (Exception e) {
			System.out.println("Fehler bei der Eingabe");
			e.printStackTrace();
		}		
	}
	@SuppressWarnings("unused")
	private static  void guiStarten() {
		shopVer.fuegeUserEin("Kunde", "123", "Frau", "Regina", "Regenbogen","Elbenweg 3", 13337, "Bruchtal");
		shopVer.fuegeUserEin("Mitarbeiter", "123", "Herr", "Max", "Mustermann");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		@SuppressWarnings("unused")
		HauptFenster h = new HauptFenster(shopVer);
	}
}

