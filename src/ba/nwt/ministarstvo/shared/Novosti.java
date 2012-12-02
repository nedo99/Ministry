package ba.nwt.ministarstvo.shared;



import com.google.gwt.user.client.rpc.IsSerializable;

public class Novosti implements IsSerializable{
	private  Novost[] novosti;
	private int broj;
	public Novosti(){
		novosti=new Novost[100];
		broj=0;
	}
	
	public void dodajnovost(Novost n){
		//novosti[broj]=new Novost();
		novosti[broj]=n;
		broj++;
	}
	public int getbroj(){return broj;}
	public Novost dajnovost(int i){
		return novosti[i];
	}
	public Novost dajnovostpoid(int id){
		Novost n=new Novost();
		for(int i=0; i<broj; i++){
			if(novosti[i].getid()==id){
				n=novosti[i];
				break;
			}
				
		}
		return n;
	}
}
