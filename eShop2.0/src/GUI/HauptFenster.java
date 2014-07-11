package GUI;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import Domain.ShopVerwaltung;
import Valueobjects.Artikel;
import Valueobjects.User;


@SuppressWarnings("serial")
public class HauptFenster extends JFrame{
	JTabbedPane tabsPanel;
	private ArtikelPanel artikelPanel;
	private WarenkorbPanel warenkorbPanel;
	private VerwaltungsPanel verwaltungsPanel;
	private LagerEreignisPanel lagerPanel;
	
	
	//in Hauptklasse mit getter hier raus nehmen
	public static User benutzer = null;
	public static ShopVerwaltung shopVerwaltung = null;
	public static Artikel artikel = null;
	
	public HauptFenster(ShopVerwaltung esv) {
		super("eShop");
		
		HauptFenster.shopVerwaltung = esv;
		
		this.initialisieren();
		this.pack();
		this.setLocationRelativeTo(this.getParent());
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        this.addWindowListener(new WindowAdapter() {
               public void windowClosing(WindowEvent event) {
                    try {
						shopVerwaltung.speichereDaten();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                    System.exit(1);
               }
           }
        );
	}
	
	private void initialisieren() {
		this.setLayout(new BorderLayout());

		this.centerPanelErzeugen();
		this.northPanelErzeugen();
	}
	
	private void northPanelErzeugen(){
		//initialisiert ein VerwaltungsPanel und fügt es in das Layout ein:
		verwaltungsPanel = new VerwaltungsPanel(this, warenkorbPanel, artikelPanel, lagerPanel);
		
		this.add(verwaltungsPanel, BorderLayout.NORTH);
	}
	
	private void centerPanelErzeugen(){
		//die Bestandteile des JTabbedPane:
		tabsPanel = new JTabbedPane();
		artikelPanel = new ArtikelPanel(this);	
		lagerPanel = new LagerEreignisPanel();
		
		//fügt einen Tab zum JTabbedPane hinzu:
		tabsPanel.addTab("Artikel", artikelPanel);
		
		//fügt das JTabbedPane in das Layout ein:
		this.add(tabsPanel, BorderLayout.CENTER);
	}

}
