package ba.nwt.ministarstvo.client;


import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.types.VerticalAlignment;  
import com.smartgwt.client.widgets.Canvas;  
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.Window;  
import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.widgets.Canvas;  
import com.smartgwt.client.widgets.IButton;  
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.events.ClickEvent;  
import com.smartgwt.client.widgets.events.ClickHandler;  
import com.smartgwt.client.widgets.form.DynamicForm;  
import com.smartgwt.client.widgets.grid.ListGrid;  
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;  
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;  
import com.smartgwt.client.widgets.layout.VLayout;  


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ministarstvo implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	
	  public static Window createWin(String title, boolean autoSizing, int width, int height, int offsetLeft, String a) {  
	        /*Label label = new Label(  
	                "<b>Ministarstvo</b> - Administracijski dio<br/>Ovaj sistem će omogućavati radnicima u Ministarstvo mogućnost vođenja i administriranja svih uposlenika i sve potrebne dokumentacije<br/><br/>"  
	                        + "<b>Korisnici</b> - Korisnici će biti svi zaposleni u svim ministarstvima<br/><br/>"  
	                        + "<b>Super Admin</b> - To je osoba koja će imati sva prava i koja će imati privilegije za sve<br/>");  
	        */
		  Label label=new Label(a);
	        label.setWidth100();  
	        label.setHeight100();  
	        label.setPadding(5);  
	        label.setValign(VerticalAlignment.TOP);  
	  
	        Window window = new Window();  
	        window.setAutoSize(autoSizing);  
	        window.setTitle(title);  
	        window.setWidth(width);  
	        window.setHeight(height);  
	        window.setLeft(offsetLeft);  
	        window.setCanDragReposition(true);  
	        window.setCanDragResize(true);  
	        window.addItem(label);  
	  
	        return window;  
	    }  
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync servis = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		String uvod="<b>Ministarstvo</b> - Administracijski dio<br/>Ovaj sistem će omogućavati radnicima u Ministarstvo mogućnost vođenja i administriranja svih uposlenika i sve potrebne dokumentacije<br/><br/>"  
	                        + "<b>Korisnici</b> - Korisnici će biti svi zaposleni u svim ministarstvima<br/><br/>"  
	                        + "<b>Super Admin</b> - To je osoba koja će imati sva prava i koja će imati privilegije za sve<br/>";
		String novosti="<b>Ovdje će biti novosti</b>"; 
		Canvas canvasMain = new Canvas();  
	        canvasMain.addChild(createWin("Info panel", true, 300, 200, 0,uvod));  
	        canvasMain.addChild(createWin("Novosti", false, 200, 200, 320,novosti));  
	        canvasMain.draw();  
		servis.Konektujse(new AsyncCallback<String> (){

			@Override
			public void onFailure(Throwable caught) {
				SC.say("Konekcija nije uspjela.");
				
			}

			@Override
			public void onSuccess(String result) {
				SC.say(result);
				
			}
			
		});
		 VLayout layout = new VLayout(15);  
		 
	        Label label = new Label();  
	        label.setHeight(10);  
	        label.setWidth100();  
	        label.setContents("Showing items in Category 'Rollfix Glue");  
	        layout.addMember(label);  
	  
	        final DataSource dataSource = ItemSupplyLocalDS.getInstance();  
	  
	        final DynamicForm form = new DynamicForm();  
	        form.setIsGroup(true);  
	        form.setGroupTitle("Update");  
	        form.setNumCols(4);  
	        form.setDataSource(dataSource);  
	  
	  
	        final ListGrid listGrid = new ListGrid();  
	        listGrid.setWidth100();  
	        listGrid.setHeight(200);  
	        listGrid.setDataSource(dataSource);  
	        listGrid.setAutoFetchData(true);  
	        listGrid.addRecordClickHandler(new RecordClickHandler() {  
	            public void onRecordClick(RecordClickEvent event) {  
	                form.reset();  
	                form.editSelectedData(listGrid);  
	            }  
	        });  
	  
	        layout.addMember(listGrid);  
	        layout.addMember(form);  
	  
	        IButton button = new IButton("Save");  
	        button.addClickHandler(new ClickHandler() {  
	            public void onClick(ClickEvent event) {  
	                form.saveData();                  
	            }  
	        });  
	        layout.addMember(button);  
	        
	        //layout.draw();  
		
	}
}
