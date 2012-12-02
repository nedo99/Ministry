package ba.nwt.ministarstvo.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Minis implements IsSerializable{
	private int[] id;
	private String[] naziv;
	private String[] adresa;
	private String[] opis;
	private String[] kontakt;
	private int brojac;
	private int broj;
	
	public Minis(){
		id=null;
		naziv=null;
		adresa=null;
		opis=null;
		kontakt=null;
		brojac=0;
	}
	public Minis(int b){
		broj=b;
		id=new int[broj];
		naziv=new String[broj];
		adresa=new String[broj];
		opis=new String[broj];
		kontakt=new String[broj];
		brojac=0;
	}
	
	public void postavi(int i, String n, String a, String o, String k){
		id[brojac]=i;
		naziv[brojac]=n;
		adresa[brojac]=a;
		opis[brojac]=o;
		kontakt[brojac]=k;
		brojac++;
	}
	public int dajid(int i){return id[i];}
	public String dajnaziv(int i){return naziv[i];}
	public String dajadresu(int i){return adresa[i];}
	public String dajopis(int i){return opis[i];}
	public String dajkontakt(int i){return kontakt[i];}
	public int dajbroj(){return broj;}
	

}
