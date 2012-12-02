package ba.nwt.ministarstvo.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Prijave implements IsSerializable{
	private Korisnik[] kor;
	private int[] id;
	private String[] konkurs;
	private int broj_uk;
	private int broj;
	
	public Prijave(){}
	public Prijave(int b){
		broj_uk=b;
		kor=new Korisnik[broj_uk];
		konkurs=new String[broj_uk];
		id=new int[broj_uk];
		broj=0;
	}
	
	public void dodaj(Korisnik k, int i, String kon){
		id[broj]=i;
		kor[broj]=k;
		konkurs[broj]=kon;
		broj++;
	}
	
	public int dajint(int i){return id[i];}
	public Korisnik dajkorisnika(int i){return kor[i];}
	public String dajkonkurs(int i){return konkurs[i];}
	public int dajbroj(){return broj_uk;}

}
