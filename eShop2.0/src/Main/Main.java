package Main;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import CUI.CUI;
import Domain.ShopVerwaltung;
import GUI.HauptFenster;

/**
 * Die Klasse beinhaltet die Main.
 * @author Imke Schneider
 *
 */
public class Main {
	
	private static  ShopVerwaltung shopVer;

	/**	
	 * Die Main aus der die CUI oder die GUI gestartet werden können.
	 * @param args 
	 */
	public static void main(String[] args){
	
		shopVer = new ShopVerwaltung();
		
		//cuiStarten();
		guiStarten();
		
	}
	/**
	 * Methode zum starten der CUI.
	 */
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
	/**
	 * Methode zum starten der GUI.
	 */
	@SuppressWarnings("unused")
	private static  void guiStarten() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		HauptFenster h = new HauptFenster(shopVer);
	}
}

