package ba.nwt.ministarstvo.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class Preporuci implements IsSerializable{
	private int[] bazni;
	private int[][] slicnost;
	private double[][] vrij;
	
	public Preporuci(){
		vrij=null;
		bazni=null;
		slicnost=null;
	}
	public void Postavi(int b[], int[][] s){
		bazni=b;
		slicnost=s;
		vrij=new double[slicnost.length][2];
	}
	
	public int Proracunaj(){
		int[] red=new int[slicnost[0].length-1];
		for(int i=0; i<slicnost.length; i++){
			for(int j=1; j<slicnost[0].length; j++){
				red[j-1]=slicnost[i][j];
			}
			vrij[i][0]=slicnost[i][0];
			double suma=0;
			double korijen1=0;
			double korijen2=0;
			
			for(int j=0; j<slicnost[0].length-1; j++){
				suma=suma+bazni[j]*slicnost[i][j+1];
				korijen1=korijen1+bazni[j]*bazni[j]; 
				korijen2=korijen2+slicnost[i][j+1]*slicnost[i][j+1]; 
			}
			double pr=korijen1*korijen2;
			vrij[i][1]=suma/Math.sqrt(pr);
		}
		double max=0;
		int id=0;
		for(int i=0; i<slicnost.length; i++){
			if(vrij[i][1]>max && vrij[i][1]!=1){
				max=vrij[i][1];
				id=(int)vrij[i][0];
			}
		}
		return id;
	}
	

}
