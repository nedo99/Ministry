package ba.nwt.ministarstvo.shared;




import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;


public class Kalendar implements IsSerializable{
	private int[] id;
	private String[] naziv;
	private String[] opis;
	private Date[] oddatum;
	private Date[] dodatum;
	private int broj;
	private int brojac;
	
	public Kalendar(){}
	
	public Kalendar(int b){
		broj=b;
		id=new int[broj];
		naziv=new String[broj];
		opis=new String[broj];
		oddatum=new Date[broj];
		dodatum=new Date[broj];
		brojac=0;
		
	}
	public void dodaj(int i, String n, String o, Date date, Date date2){
		id[brojac]=i;
		naziv[brojac]=n;
		opis[brojac]=o;
		oddatum[brojac]=date;
		dodatum[brojac]=date2;
		brojac++;
	}
	public int getid(int i){return id[i];}
	public String getnaziv(int i){return naziv[i];}
	public String getopis(int i){return opis[i];}
	public Date getod(int i){return oddatum[i];}
	public Date getdo(int i){return dodatum[i];}
	public int getbroj(){return broj;}
	

}
