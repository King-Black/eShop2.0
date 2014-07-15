package Main;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import exceptions.EinlagernException;
import CUI.CUI;
import Domain.ShopVerwaltung;
import GUI.HauptFenster;


public class Main {
	
	private static  ShopVerwaltung shopVer;

	
public static void main(String[] args){
	
	shopVer = new ShopVerwaltung();
		
		//cuiStarten();
		guiStarten();
		
	}

	@SuppressWarnings("unused")
	private static void cuiStarten(){
		CUI shop = new CUI();
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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HauptFenster h = new HauptFenster(shopVer);
	}
}

