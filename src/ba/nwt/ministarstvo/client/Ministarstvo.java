package ba.nwt.ministarstvo.client;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ba.nwt.ministarstvo.shared.Korisnik;
import com.google.gwt.user.client.Window.Location;
import java.lang.Object;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.types.VerticalAlignment;  
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Canvas;  
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.Window;  
import com.smartgwt.client.data.DataSource;  
import com.smartgwt.client.widgets.Canvas;  
import com.smartgwt.client.widgets.IButton;  
import com.smartgwt.client.widgets.Label;  
import com.smartgwt.client.widgets.calendar.CalendarEvent;
import com.smartgwt.client.widgets.events.ClickEvent;  
import com.smartgwt.client.widgets.events.ClickHandler;  
import com.smartgwt.client.widgets.form.DynamicForm;  
import com.smartgwt.client.widgets.form.fields.FormItem;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;  
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;  
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;  
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;  


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Ministarstvo implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	  
	  
	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync servis = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Logujse();
		
	}
	
	public void ubaci(){
		
		
	}
	public void Logujse(){
		final HLayout layout = new HLayout(20);  
        layout.setLeft(300);
        final DynamicForm form = new DynamicForm();  
        form.setWidth(250); 
        form.setLeft(800);
           
        final TextItem usernameItem = new TextItem();  
        usernameItem.setTitle("Username");  
        usernameItem.setRequired(true);  
        
  
        final PasswordItem passwordItem = new PasswordItem();  
        passwordItem.setTitle("Password");  
        passwordItem.setRequired(true);  
  
          
  
        form.setFields(new FormItem[] {usernameItem, passwordItem });  
          
        IButton swapButton = new IButton("Log In");  
        swapButton.setLeft(300);  
        swapButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) { 
            	String s=passwordItem.getValueAsString();
            	MessageDigest m = null;
				try {
					m = MessageDigest.getInstance("MD5");
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	m.update(s.getBytes(),0,s.length());
            	s=new BigInteger(1,m.digest()).toString(16);
            	servis.login(usernameItem.getValueAsString(), s, new AsyncCallback<Korisnik> (){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.say("Pogrešan username ili šifra");
					}

					@Override
					public void onSuccess(Korisnik result) {
						// TODO Auto-generated method stub
						if(result==null)
							SC.say("Pogrešan username ili šifra");
						else{
							layout.clear();
							
							RootPanel.get().add(new Home(result));
						}
						
					}
                	
                });
            }  
        });  
       Label rss=new Label("<a href='http://localhost/trunk/rss.php' target='_blank'>RSS Feed</a>");
       
       rss.setAlign(Alignment.CENTER);
       
        
        layout.addMember(form);  
        layout.addMember(swapButton);  
        layout.addMember(rss);
       
       //RootPanel.get().add(new Upload());
        layout.draw();  
	}
}

