package ba.nwt.ministarstvo.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Konkursi implements IsSerializable{
	private int[] id;
	private String[] naziv;
	private Date[] objava;
	private Date[] rok;
	private String[] opis;
	private String[] min_id;
	int broj_uk;
	int broj;
	
	public Konkursi(){
		
	}
	
	public Konkursi(int b){
		broj_uk=b;
		broj=0;
		id=new int[broj_uk];
		naziv=new String[broj_uk];
		opis=new String[broj_uk];
		min_id=new String[broj_uk];
		objava=new Date[broj_uk];
		rok=new Date[broj_uk];
	}
	public void dodaj(int i, String n, Date o, Date r, String op, String min){
		id[broj]=i;
		naziv[broj]=n;
		objava[broj]=o;
		rok[broj]=r;
		opis[broj]=op;
		min_id[broj]=min;
		broj++;
	}
	public int dajbroj(){return broj_uk;}
	public int dajid(int i){return id[i];}
	public String dajnaziv(int i){return naziv[i];}
	public Date dajobjava(int i){return objava[i];}
	public Date dajrok(int i){ return rok[i];}
	public String dajopis(int i){return opis[i];}
	public String dajmin(int i){return min_id[i];}
	
}
