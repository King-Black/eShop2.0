package Domain;

public class ShopVerwaltung {
	private ArtikelVerwaltung artVer;
	private UserVerwaltung userVer;
	private WarenkorbVerwaltung wakoVer;
	public EreignisVerwaltung erVer;
	
	public ShopVerwaltung(){
		artVer = new ArtikelVerwaltung();
		userVer = new UserVerwaltung();
		wakoVer = new WarenkorbVerwaltung();
		erVer = new EreignisVerwaltung();
	}
	
}
