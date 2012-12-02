package ba.nwt.ministarstvo.client;

import ba.nwt.ministarstvo.shared.Kalendar;
import ba.nwt.ministarstvo.shared.Konkursi;
import ba.nwt.ministarstvo.shared.Korisnici;
import ba.nwt.ministarstvo.shared.Korisnik;
import ba.nwt.ministarstvo.shared.Minis;
import ba.nwt.ministarstvo.shared.Prijave;

import ba.nwt.ministarstvo.shared.Novosti;
import ba.nwt.ministarstvo.shared.Preporuci;


import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.widgets.calendar.CalendarEvent;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void Konektujse(AsyncCallback<String> callback);

	void login(String user, String pass, AsyncCallback<Korisnik> callback);

	void ucitajNovosti(AsyncCallback<Novosti> callback);

	void ProcitaoVijest(int i, int k, AsyncCallback<Preporuci> callback);

	void dajkalendar(int id, AsyncCallback<Kalendar> callback);

	void updatekalendar(Kalendar kal, int id, AsyncCallback<String> callback);

	void ucitajministarstva(AsyncCallback<Minis> callback);

	void ubaciMin(String n, String a, String o, String k,
			AsyncCallback<Void> callback);

	void editMin(int i, String n, String a, String o, String k,
			AsyncCallback<Void> callback);

	void brisiMin(int id, AsyncCallback<Void> callback);

	void ucitajKorisnike(AsyncCallback<ba.nwt.ministarstvo.shared.Korisnici> asyncCallback);

	void ucitajkonkurse(AsyncCallback<Konkursi> callback);

	

	void dodajkonkurs(String n, String rok, String o, String min,
			AsyncCallback<Void> callback);

	void brisikonkurs(int id, AsyncCallback<Void> callback);

	void updatekorisnik(int id, String ime, String prezime, String tip,
			AsyncCallback<Void> callback);

	void brisikorisnika(int id, AsyncCallback<Void> callback);

	void makeadmin(int id, AsyncCallback<Void> callback);

	void ucitajPrijave(AsyncCallback<Prijave> callback);

	
	
	
}
