package ba.nwt.ministarstvo.client;

import ba.nwt.ministarstvo.shared.Kalendar;
import ba.nwt.ministarstvo.shared.Konkursi;
import ba.nwt.ministarstvo.shared.Korisnici;
import ba.nwt.ministarstvo.shared.Korisnik;
import ba.nwt.ministarstvo.shared.Minis;
import ba.nwt.ministarstvo.shared.Novosti;
import ba.nwt.ministarstvo.shared.Preporuci;
import ba.nwt.ministarstvo.shared.Prijave;


import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.smartgwt.client.widgets.calendar.CalendarEvent;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	public String Konektujse();
	public Korisnik login(String user, String pass);
	public Novosti ucitajNovosti();
	public Preporuci ProcitaoVijest(int i, int k);
	public Kalendar dajkalendar(int id);
	public String updatekalendar(Kalendar kal, int id);
	public Minis ucitajministarstva();
	public void ubaciMin(String n, String a, String o, String k);
	public void editMin(int i,String n, String a, String o, String k);
	public void brisiMin(int id);
	public Korisnici ucitajKorisnike();
	public Konkursi ucitajkonkurse();
	public void dodajkonkurs(String n, String rok, String o, String min);
	public void brisikonkurs(int id);
	public void updatekorisnik(int id, String ime, String prezime, String tip);
	public void brisikorisnika(int id);
	public void makeadmin(int id);
	public Prijave ucitajPrijave();
	
}
