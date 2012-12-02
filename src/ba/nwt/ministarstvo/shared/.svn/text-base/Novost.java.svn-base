package ba.nwt.ministarstvo.shared;

import java.sql.Date;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class Novost  implements IsSerializable {
	private int id;
	private String naziv;
	private String opis;
	private Date datum;
	private String ministarstvo;
	public Novost(){
		id=0;
		naziv="";
		opis="";
		datum=null;
		ministarstvo="";
	}
	public Novost(int i, String n, String o,Date d, String m){
		id=i;
		
		naziv=n;
		
		opis=o;
		
		datum=d;
		
		ministarstvo=m;
		
	}
	public void Postavi(int i, String n, String o, Date d, String m){
		id=i;
		naziv=n;
		opis=o;
		datum=d;
		ministarstvo=m;
	}
	
	public int getid(){return id;}
	public String getnaziv(){return naziv;}
	public String getopis(){return opis;}
	public Date getdatum(){return datum;}
	public String getministarstvo(){return ministarstvo;}


}
