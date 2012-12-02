package ba.nwt.ministarstvo.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Korisnici implements IsSerializable{
	private Korisnik k[];
	private int broj;
	
	public Korisnici(){
		k=new Korisnik[100];
		broj=0;
	}
	public void dodajkorisnika(Korisnik i){
		k[broj]=new Korisnik();
		k[broj]=i;
		broj++;
	}
	public int dajbroj(){return broj;}
	public Korisnik dajkorisnika(int i){return k[i];}

}
