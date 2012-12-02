package ba.nwt.ministarstvo.server;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.smartgwt.client.widgets.calendar.CalendarEvent; 
import ba.nwt.ministarstvo.client.GreetingService;
import ba.nwt.ministarstvo.shared.FieldVerifier;
import ba.nwt.ministarstvo.shared.Kalendar;
import ba.nwt.ministarstvo.shared.Konkursi;
import ba.nwt.ministarstvo.shared.Korisnici;
import ba.nwt.ministarstvo.shared.Korisnik;
import ba.nwt.ministarstvo.shared.Minis;
import ba.nwt.ministarstvo.shared.Novost;
import ba.nwt.ministarstvo.shared.Novosti;
import ba.nwt.ministarstvo.shared.Preporuci;
import ba.nwt.ministarstvo.shared.Prijave;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;



/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	private Connection c=null;
	public String greetServer(String input) throws IllegalArgumentException {
		// Verify that the input is valid. 
		if (!FieldVerifier.isValidName(input)) {
			// If the input is not valid, throw an IllegalArgumentException back to
			// the client.
			throw new IllegalArgumentException(
					"Name must be at least 4 characters long");
		}

		String serverInfo = getServletContext().getServerInfo();
		String userAgent = getThreadLocalRequest().getHeader("User-Agent");

		// Escape data from the client to avoid cross-site script vulnerabilities.
		input = escapeHtml(input);
		userAgent = escapeHtml(userAgent);

		return "Hello, " + input + "!<br><br>I am running " + serverInfo
				+ ".<br><br>It looks like you are using:<br>" + userAgent;
	}

	/**
	 * Escape an html string. Escaping data received from the client helps to
	 * prevent cross-site script vulnerabilities.
	 * 
	 * @param html the html string to escape
	 * @return the escaped string
	 */
	private String escapeHtml(String html) {
		if (html == null) {
			return null;
		}
		return html.replaceAll("&", "&amp;").replaceAll("<", "&lt;")
				.replaceAll(">", "&gt;");
	}

	@Override
	public String Konektujse() {
		String vrati="Konekcija uspjela";
		try {
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nwt", "root", "niko");
		} catch (SQLException e) {
			vrati=e.getMessage();
			e.printStackTrace();
		}
		return vrati;
	}
	
	public int brojkorisnika(){
		Konektujse();
		int broj=0;
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) c.prepareStatement("SELECT COUNT(*) FROM clanovi");
		
		ResultSet rs=ps.executeQuery();
		while(rs.next())
			broj=rs.getInt(1);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return broj;
	}
	public Korisnik login(String user, String pass){
		Boolean tacan=null;
		Korisnik k=null;
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT clan_id, tip FROM korisnici WHERE user=? AND pass=? AND (tip='SuperAdmin' OR tip='Moderator')");
			ps.setString(1, user);
			ps.setString(2, pass);
			int broj=0;
			String tip="";
			ResultSet rs=(ResultSet) ps.executeQuery();
			while(((java.sql.ResultSet) rs).next()){
				broj=((java.sql.ResultSet) rs).getInt(1);
				tip=rs.getString(2);
			}
			if(broj!=0){
				ps = (PreparedStatement) c.prepareStatement("SELECT * FROM clanovi WHERE id=?");
				ps.setInt(1, broj);
				rs=(ResultSet) ps.executeQuery();
				while(((java.sql.ResultSet) rs).next()){
					k=new Korisnik();
					int id=rs.getInt(1);
					String ime=rs.getString(2);
					String prezime=rs.getString(3);
					String telefon=rs.getString(4);
					String jmbg=rs.getString(5);
					String mail=rs.getString(6);
					String adresa=rs.getString(7);
					String grad=rs.getString(8);
					Date datum_rodj=null;
					Date datum_reg=null;
					datum_rodj=(Date) rs.getDate(9);
					datum_reg=(Date) rs.getDate(10);
					k.Postavi(id, ime, prezime, telefon, jmbg, mail, adresa, grad, datum_rodj, datum_reg, "", "", tip);
				}
			}
		}
		catch(Exception e) {
			
		}
		return k;
		
	}
	
	public Novosti ucitajNovosti(){
		Konektujse();
		Novosti n=new Novosti();
		int broj=0;
		try {
			
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT n.id, n.naziv, n.opis, n.vrijeme, m.naziv as ministarstvo FROM novosti as n LEFT OUTER JOIN ministarstva as m ON m.id=n.ministarstvo_id");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				
				int id=rs.getInt("id");
				String naziv=rs.getString("naziv");
				String opis=rs.getString("opis");
				Date d=null;
				d=rs.getDate("vrijeme");
				String min=rs.getString("ministarstvo");
				Novost nov=new Novost(id, naziv, opis, d, min);
				n.dodajnovost(nov);
				broj=n.getbroj();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(broj==0)
			n=null;
		return n;
		
	}
	public int brojnovosti(){
		
		int broj=0;
		PreparedStatement ps;
		try {
			ps = (PreparedStatement) c.prepareStatement("SELECT COUNT(*) FROM novosti");
		
		ResultSet rs=ps.executeQuery();
		while(rs.next())
			broj=rs.getInt(1);
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return broj;
	}
	public int[] dajnovostiid(){
		int broj=brojnovosti();
		int[] novosti=new int[broj];
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT id FROM novosti");
			ResultSet rs=ps.executeQuery();
			int b=0;
			while(rs.next()){
				novosti[b]=rs.getInt(1);
				b++;
			}
		}
		catch(Exception e){
			
		}
		return novosti;
		
	}
	public Preporuci ProcitaoVijest(int i, int k){
		Konektujse();
		Preporuci p=new Preporuci();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT id FROM slope_one WHERE clan_id=? AND novost_id=?");
			ps.setInt(1, k);
			ps.setInt(2, i);
			ResultSet rs=ps.executeQuery();
			int br=0;
			while(rs.next())
				br=rs.getInt("id");
			
				PreparedStatement ps1 = (PreparedStatement) c.prepareStatement("UPDATE slope_one SET clan_id=?, novost_id=?, procitao=1 WHERE id=?");
				ps1.setInt(1, k);
				ps1.setInt(2, i);
				ps1.setInt(3, br);
				ps1.executeUpdate();
			
			int broj=brojkorisnika();
			int[] procitano=new int[broj];
			PreparedStatement ps2 = (PreparedStatement) c.prepareStatement("SELECT procitao FROM slope_one WHERE  novost_id=?");
			ps2.setInt(1, i);
			ResultSet rs2=ps2.executeQuery();
			int b=0;
			while(rs2.next()){
				procitano[b]=rs2.getInt(1);
				b++;
			}
			int broj1=brojnovosti();
			int[] novosti=dajnovostiid();
			int[][] sl=new int[broj1][broj+1];
			for(int j=0; j<broj1; j++){
				PreparedStatement ps3 = (PreparedStatement) c.prepareStatement("SELECT procitao FROM slope_one WHERE  novost_id=?");
				ps3.setInt(1, novosti[j]);
				sl[j][0]=novosti[j];
				ResultSet rs3=ps3.executeQuery();
				int duz=1;
				while(rs3.next()){
					sl[j][duz]=rs3.getInt(1);
					duz++;
				}
			}
			
			p.Postavi(procitano, sl);
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return p;
	}
	
	public Kalendar dajkalendar(int id){
		Konektujse();
		Kalendar k=null;
		
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT COUNT(*) FROM kalendar WHERE clan_id=?");
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			int a=0;
			while(rs.next()){
				a=rs.getInt(1);
			}
			k=new Kalendar(a);
			PreparedStatement ps1 = (PreparedStatement) c.prepareStatement("SELECT * FROM kalendar WHERE clan_id=?");
			ps1.setInt(1, id);
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()){
				
				k.dodaj(rs1.getInt("id"), rs1.getString("naziv"), rs1.getString("opis"), rs1.getTimestamp("od_datum"), rs1.getTimestamp("do_datum"));
				
			}
			
		}
		catch(SQLException e){
			
		}
		
		return k;
		
	}
	
	public String updatekalendar(Kalendar kal, int id){
		Konektujse();
		String uradi="Uspjelo";
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("DELETE FROM kalendar WHERE clan_id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			for(int i=0; i<kal.getbroj(); i++){
				PreparedStatement ps1 = (PreparedStatement) c.prepareStatement("INSERT INTO kalendar VALUES (NULL, ?, ?, ?, ?, ?)");
				ps1.setString(1, kal.getnaziv(i));
				ps1.setString(2, kal.getopis(i));
				java.text.SimpleDateFormat sdf =    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(kal.getod(i));
				ps1.setString(3, currentTime);
				currentTime = sdf.format(kal.getdo(i));
				ps1.setString(4, currentTime);
				ps1.setInt(5, id);
				ps1.executeUpdate();
				
			}
		}
		catch(SQLException e){
			uradi=e.getMessage();
		}
		return uradi;
	}
	
	public void dodajkonkurs(String n, String rok, String o, String min){
		Konektujse();
		try{
			PreparedStatement p = (PreparedStatement) c.prepareStatement("select id from ministarstva");
			ResultSet r=p.executeQuery();
			int mini=0;
			while(r.next()){
				mini=r.getInt(1);
				break;
			}
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("INSERT INTO konkurs VALUES(NULL, ?, CURDATE(), ?, ?, NULL, ?, ?, ?)");
			ps.setString(1, n);
			ps.setString(2, rok);
			ps.setString(3, o);
			ps.setString(4, "postavi");
			ps.setString(5, "");
			ps.setInt(6, mini);
			ps.executeUpdate();
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void brisikonkurs(int id){
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("DELETE FROM konkurs WHERE id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			
		}
		catch(SQLException e){
			
		}
	}
	
	public Konkursi ucitajkonkurse(){
		Konkursi k=new Konkursi();
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT COUNT(*) FROM konkurs");
			ResultSet rs=ps.executeQuery();
			int a=0;
			while(rs.next()){
				a=rs.getInt(1);
			}
			k=new Konkursi(a);
			PreparedStatement ps1 = (PreparedStatement) c.prepareStatement("SELECT k.id, k.naziv, k.datum_objave, k.datum_isticanja, k.opis, m.naziv as ministarstvo FROM konkurs as k, ministarstva as m WHERE m.id=k.ministarstvo_id");
			
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()){
				
				k.dodaj(rs1.getInt("id"), rs1.getString("naziv"), rs1.getTimestamp("datum_objave"), rs1.getTimestamp("datum_isticanja"), rs1.getString("opis"), rs1.getString("ministarstvo"));
				
			}
		}
		catch(SQLException e){
			e.printStackTrace();
			k=null;
		}
		return k;
	}
	
	public void ubaciMin(String n, String a, String o, String k){
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("INSERT INTO ministarstva VALUES (NULL, ?, ?, ?, ?)");
			ps.setString(1, n);
			ps.setString(2, a);
			ps.setString(3, o);
			ps.setString(4, k);
			ps.executeUpdate();
		}
		catch(SQLException e){
			
		}
	}
	
	public void brisiMin(int id){
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("DELETE FROM ministarstva WHERE id=?");
			ps.setInt(1, id);
			
			ps.executeUpdate();
		}
		catch(SQLException e){
			
		}
	}
	
	public void editMin(int i,String n, String a, String o, String k){
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("UPDATE ministarstva SET naziv=?, adresa=?, opis=?, kontakt=? WHERE id=?");
			ps.setString(1, n);
			ps.setString(2, a);
			ps.setString(3, o);
			ps.setString(4, k);
			ps.setInt(5, i);
			ps.executeUpdate();
		}
		catch(SQLException e){
			
		}
	}
	
	public Korisnici ucitajKorisnike(){
		Korisnici k=new Korisnici();
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT * FROM clanovi LEFT OUTER JOIN korisnici ON clanovi.id = korisnici.clan_id;");
			ResultSet rs=ps.executeQuery();
			while(rs.next()){
				Korisnik kor=new Korisnik();
				kor.Postavi(rs.getInt("id"), rs.getString("ime"), rs.getString("prezime"), rs.getString("telefon"), "", "", "", "", null, null, rs.getString("username"), "", rs.getString("tip"));
				k.dodajkorisnika(kor);
			}
		}
		catch(SQLException e){
			k=null;
		}
		return k;
	}
	
	public Minis ucitajministarstva(){
		String uspjelo="ok";
		Minis m=null;
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT COUNT(*) FROM ministarstva");
			ResultSet rs=ps.executeQuery();
			int br=0;
			while(rs.next()){
				br=rs.getInt(1);
			}
			m=new Minis(br);
			PreparedStatement ps1 = (PreparedStatement) c.prepareStatement("SELECT * FROM ministarstva");
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()){
				int id=rs1.getInt("id");
				String n=rs1.getString("naziv");
				String a=rs1.getString("adresa");
				String o=rs1.getString("opis");
				String k=rs1.getString("kontakt");
				m.postavi(id, n, a, o, k);
			}
			
			
		}
		catch(SQLException e){
			uspjelo=e.getMessage();
		}
		return m;
	}
	
	public void updatekorisnik(int id, String ime, String prezime, String tip){
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("UPDATE clanovi SET ime=?, prezime=? WHERE id=?");
			ps.setString(1, ime);
			ps.setString(2, prezime);
			ps.setInt(3, id);
			ps.executeUpdate();
			PreparedStatement ps1 = (PreparedStatement) c.prepareStatement("UPDATE korisnici SET tip=? WHERE clan_id=?");
			ps1.setString(1, tip);
			ps1.setInt(2, id);
			ps1.executeUpdate();
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void brisikorisnika(int id){
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("DELETE FROM clanovi WHERE id=?");
			ps.setInt(1, id);
			ps.executeUpdate();
			
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public void makeadmin(int id){
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT username, pass FROM clanovi WHERE id=?");
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			String user="";
			String pass="";
			while(rs.next()){
				user=rs.getString("username");
				pass=rs.getString("pass");
			}
			PreparedStatement ps1 = (PreparedStatement) c.prepareStatement("INSERT INTO korisnici VALUES(NULL,?, ?, 'Moderator',1,?)");
			ps1.setString(1, user);
			ps1.setString(2, pass);
			ps1.setInt(3, id);
			ps1.executeUpdate();
			
			
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public Prijave ucitajPrijave(){
		Prijave p=new Prijave();
		Konektujse();
		try{
			PreparedStatement ps = (PreparedStatement) c.prepareStatement("SELECT COUNT(*) FROM prijave");
			ResultSet rs=ps.executeQuery();
			int br=0;
			while(rs.next()){
				br=rs.getInt(1);
			}
			p=new Prijave(br);
			PreparedStatement ps1 = (PreparedStatement) c.prepareStatement("SELECT p.id as pid, c.id as cid, c.ime, c.prezime, k.naziv FROM prijave as p, clanovi as c, konkurs as k where p.clan_id=c.id and k.id=p.konkurs_id");
			ResultSet rs1=ps1.executeQuery();
			while(rs1.next()){
				Korisnik kor=new Korisnik();
				kor.Postavi(rs1.getInt("cid"), rs1.getString("ime"), rs1.getString("prezime"), "", "", "", "", "", null, null, "", "", "");
				int id=rs1.getInt("pid");
				String n=rs1.getString("naziv");
				p.dodaj(kor, id, n);
			}
			
			
		}
		catch(SQLException e){
			
		}
		return p;
	}
	
	
	
}
