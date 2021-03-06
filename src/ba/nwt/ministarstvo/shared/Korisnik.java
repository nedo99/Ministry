package ba.nwt.ministarstvo.shared;

import java.sql.Date;

import com.google.gwt.user.client.rpc.IsSerializable;


public class Korisnik implements IsSerializable{
	private int id;
	private String ime;
	private String prezime;
	private String telefon;
	private String jmbg;
	private String mail;
	private String adresa;
	private String grad;
	private Date datum_rodjenja;
	private Date datum_registracije;
	private String username;
	private String pass;
	private String tip;
	public Korisnik(){
		id=0;
		ime="";
		prezime="";
		telefon="";
		jmbg="";
		mail="";
		adresa="";
		grad="";
		datum_rodjenja=null;
		datum_registracije=null;
		username="";
		pass="";
		tip="";
	}
	public void Postavi(int i, String im, String p, String tel, String j, String m, String a, String g, Date dr, Date dreg, String u, String pass1, String ti){
		id=i;
		ime=im;
		prezime=p;
		telefon=tel;
		jmbg=j;
		mail=m;
		adresa=a;
		grad=g;
		datum_rodjenja=dr;
		datum_registracije=dreg;
		username=u;
		pass=p;
		tip=ti;
	}
	public int getid(){return id;}
	public String getime(){return ime;}
	public String getprezime(){return prezime;}
	public String gettelefon(){return telefon;}
	public String getjmbg(){return jmbg;}
	public String getmail(){return mail;}
	public String getadresa(){return adresa;}
	public String getgrad(){return grad;}
	public String gettip(){return tip;}
	public Date getdatum_rodjenja(){return datum_rodjenja;}
	public Date getdatum_registracije(){return datum_registracije;}
	public String getusername(){return username;}
	public String getpass(){return pass;}
	

}
