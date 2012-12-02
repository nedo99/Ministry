package ba.nwt.ministarstvo.client;




import java.util.Date;

import ba.nwt.ministarstvo.shared.Kalendar;
import ba.nwt.ministarstvo.shared.Korisnik;
import ba.nwt.ministarstvo.shared.Korisnici;
import ba.nwt.ministarstvo.shared.Minis;
import ba.nwt.ministarstvo.shared.Novost;
import ba.nwt.ministarstvo.shared.Novosti;
import ba.nwt.ministarstvo.shared.Preporuci;
import ba.nwt.ministarstvo.shared.Konkursi;
import ba.nwt.ministarstvo.shared.Prijave;

import com.smartgwt.client.widgets.calendar.Calendar;
import com.smartgwt.client.widgets.calendar.CalendarEvent; 
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceDateField;
import com.smartgwt.client.data.fields.DataSourceSequenceField;
import com.smartgwt.client.data.fields.DataSourceTextField;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.DateDisplayFormat;
import com.smartgwt.client.types.ListGridEditEvent;
import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.types.RowEndEditAction;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TimeFormatter;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Canvas;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.IconButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.RichTextEditor;
import com.smartgwt.client.widgets.Window;

import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;

import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.ComboBoxItem;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.grid.ListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;
import com.smartgwt.client.widgets.grid.events.RecordClickEvent;
import com.smartgwt.client.widgets.grid.events.RecordClickHandler;
import com.smartgwt.client.widgets.grid.events.SelectionChangedHandler;
import com.smartgwt.client.widgets.grid.events.SelectionEvent;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.IconMenuButton;
import com.smartgwt.client.widgets.menu.Menu;
import com.smartgwt.client.widgets.toolbar.RibbonBar;
import com.smartgwt.client.widgets.toolbar.RibbonGroup;
 

public class Home extends HorizontalPanel{
	final Calendar calendar = new Calendar();  
	final DataSource eventDS = new DataSource();  
	CalendarEvent[] nek;
	HLayout layout = new HLayout();  
	VLayout vLayout = new VLayout(); 
	HLayout donji = new HLayout();
	HLayout hLayout = new HLayout();
	HLayout main = new HLayout();
	private final GreetingServiceAsync servis = GWT
			.create(GreetingService.class);
	private Novosti n=new Novosti();
	Minis m=new Minis();
	Korisnik k=new Korisnik();
	
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
	public Home(Korisnik kor){
		k=kor;
		layout.setWidth100();  
	    layout.setHeight100();  
	    layout.setMembersMargin(0); 
  
         
        vLayout.setShowEdges(true);  
        vLayout.setWidth(550);  
        vLayout.setMembersMargin(5);  
        vLayout.setLayoutMargin(5);  
         
        layout.addMember(vLayout);  
      
        
          
        hLayout.setShowEdges(true);  
        hLayout.setHeight(100); 
        hLayout.setWidth100();
        hLayout.setMembersMargin(5);  
        hLayout.setLayoutMargin(5);  
        main.setShowEdges(true);  
        main.setHeight100(); 
        main.setMembersMargin(5);  
        main.setLayoutMargin(5);
        donji.setShowEdges(true);  
        donji.setWidth(getOffsetWidth());
        donji.setWidth100();
        donji.setHeight("78%");
        donji.setMembersMargin(5);  
        donji.setLayoutMargin(5);
        donji.setTop(140);
        final Canvas canvasMain = new Canvas();  
        Canvas canvas = new Canvas(); 
        canvasMain.setTop(10);
        String b="<b>Ministarstvo</b> - Administracijski dio<br/>Ovaj sistem će omogućavati radnicima u Ministarstvo mogućnost vođenja i administriranja svih uposlenika i sve potrebne dokumentacije<br/><br/>"  
                + "<b>Korisnici</b> - Korisnici će biti svi zaposleni u svim ministarstvima<br/><br/>"  
                + "<b>Super Admin</b> - To je osoba koja će imati sva prava i koja će imati privilegije za sve<br/>";
        canvasMain.addChild(createWin("Dobrodošli", false, 800, 400, 0,b)); 
        donji.addChild(canvasMain);
        main.addChild(hLayout);  
        main.addChild(donji);
     
        layout.addMember(main);
        dodajmenu();
        dodajkalendar();
        layout.draw();  
		
	}
	public void Postavi(Novosti nov){
		n=nov;
	}
	
	
	
	public void dodajkalendar(){
		DataSourceSequenceField eventIdField = new DataSourceSequenceField("eventId");  
	    eventIdField.setPrimaryKey(true);  

	    DataSourceTextField nameField = new DataSourceTextField("name");  
	    DataSourceTextField descField = new DataSourceTextField("description");  
	    DataSourceDateField startDateField = new DataSourceDateField("startDate");  
	    DataSourceDateField endDateField = new DataSourceDateField("endDate"); 
	    eventDS.setFields(eventIdField, nameField, descField, startDateField, endDateField);  
	    eventDS.setClientOnly(true);
	  
	    //use 24 hr based times  
        calendar.setTimeFormatter(TimeFormatter.TOSHORT24HOURTIME);  
  
        //use european based DD/MM/YYYY date formats  
        calendar.setDateFormatter(DateDisplayFormat.TOEUROPEANSHORTDATE);  
	    servis.dajkalendar(k.getid(), new AsyncCallback<Kalendar>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.say(caught.getMessage());
			}

			@Override
			public void onSuccess(Kalendar result) {
				// TODO Auto-generated method stub
				if(result==null) SC.say("Došlo je do greške prilikom učitavanja podataka");
				else{
					
					
					calendar.setData(postavikalendar(result));
					 //eventDS.setTestData((CalendarEent)postavikalendar(result));
				}
			}
	    	
	    });
	    

	     
	      
       
        //calendar.setDataSource(eventDS);  
        
		
        calendar.setAutoFetchData(true);  
        
        vLayout.addChild(calendar); 
       
	}
	
	
	
	public CalendarEvent[] postavikalendar(Kalendar kal){
		CalendarEvent[] cal=new CalendarEvent[kal.getbroj()];
		
		for(int i=0; i<kal.getbroj(); i++){
			
			Date daj=new Date();
			daj.setTime(kal.getod(i).getTime());
			Date d1=new Date();
			d1.setTime(kal.getdo(i).getTime());
			
			CalendarEvent c=new CalendarEvent(kal.getid(i),kal.getnaziv(i), kal.getopis(i), daj,d1);
			cal[i]=c;
			/*nek[i].setName(kal.getnaziv(i));
			nek[i].setDescription(kal.getopis(i));
			nek[i].setStartDate(kal.getod(i));
			nek[i].setEndDate(kal.getdo(i));*/
		}
		
		
		return cal;
	}
	
	public void dodajmenu(){
		RibbonBar ribbonBar = new RibbonBar();  
        ribbonBar.setLeft(0);  
        ribbonBar.setTop(75);  
        ribbonBar.setWidth100();  
  
        ribbonBar.setMembersMargin(2);  
        ribbonBar.setLayoutMargin(0);  
  
        Menu menu = new Menu();  
  
        RibbonGroup fileGroup = new RibbonGroup();  
        fileGroup.setTitle("Menu");  
        fileGroup.setTitleAlign(Alignment.CENTER);  
        fileGroup.setNumRows(1);  
        fileGroup.setRowHeight(76); 
        
        IconButton korisnici=new IconButton();
        korisnici=getIconButton("Korisnici", "star_yellow", true);
        fileGroup.addControl(korisnici); 
        IconButton open = new IconButton();
        open=getIconButton("Novosti", "star_yellow", true);
        fileGroup.addControl(open);
        IconButton sacuvajkalendar = new IconButton();
        sacuvajkalendar=getIconButton("Sacuvaj kalendar", "pawn_red", true);
        fileGroup.addControl(sacuvajkalendar);  
        IconButton min = new IconButton();
        min=getIconButton("Ministarstva", "cube_green", true);
        fileGroup.addControl(min);  
        IconButton konkursi=new IconButton();
        konkursi=getIconButton("Konkursi", "piece_blue", true);
        fileGroup.addControl(konkursi); 
        IconButton prijave=new IconButton();
        prijave=getIconButton("Prijave", "piece_blue", true);
        fileGroup.addControl(prijave); 
        
        prijave.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				prijave();
			}
        	
        });
        
        konkursi.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Konkursi();
			}
        	
        });
        sacuvajkalendar.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				 
				CalendarEvent[] kal=calendar.getData();				
				
				
			    Kalendar kl=new Kalendar(kal.length);
			    for(int i=0; i<kal.length; i++){
			    	
			    	String n=kal[i].getName();
			    	String o=kal[i].getDescription();
			    	Date d=kal[i].getStartDate();
			    	Date d1=kal[i].getEndDate();
			    	kl.dodaj(1,n , o ,d, d1);
			    }
			   
				servis.updatekalendar(kl, k.getid(), new AsyncCallback<String> (){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						
						SC.say(caught.getMessage());
					}

					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						SC.say(result);
						
					}
					
				});
				
			}
        	
        });
        min.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Ministarstva();
			}
        	
        });
        korisnici.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				korisnici();
			}
        	
        });
        open.addClickHandler(new ClickHandler(){

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				Novosti();
			}
        	
        });
         
        
  
        ribbonBar.addMember(fileGroup);  
        
        hLayout.addMember(ribbonBar);
        //ribbonBar.draw();  
	}
	
	public void prijave(){
		donji.destroy();
		donji=new HLayout();
		donji.setShowEdges(true);  
        donji.setWidth(getOffsetWidth());
        donji.setWidth100();
        donji.setHeight("78%");
        donji.setMembersMargin(5);  
        donji.setLayoutMargin(5);
        donji.setTop(140);
		main.addChild(donji);
		servis.ucitajPrijave(new AsyncCallback<Prijave>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Prijave result) {
				// TODO Auto-generated method stub
				int y=0;
				for(int i=0; i<result.dajbroj(); i++){
					Korisnik k=result.dajkorisnika(i);
					try{
						String b="<strong>"+k.getime()+" "+k.getprezime()+"</strong> Konkurs:"+result.dajkonkurs(i)+"  <a href='http://localhost/trunk/prijava.php?id="+Integer.toString(result.dajint(i))+"' target='_blank'><strong>Skini aplikaciju</strong></a>";
						Label prijava=new Label(b);
						 prijava.setTop(y);
						 prijava.setWidth100();
						 donji.addChild(prijava);
						 y=y+40;
					}
					catch(Exception e){
						
					}
					 
				}
			}
			
		});
		
	}
	
	private IconButton getIconButton(String title, String iconName, boolean vertical) {  
        IconButton button = new IconButton(title);  
        button.setTitle(title);  
        if (iconName == null) iconName = "cube_blue";  
        button.setIcon("/images/" + iconName + ".png");  
        button.setLargeIcon("/images/" + iconName + ".png");  
        if (vertical == true) button.setOrientation("vertical");  
        return button;  
    }  
  
    private IconMenuButton getIconMenuButton(String title, String iconName, Menu menu, boolean vertical) {  
        IconMenuButton button = new IconMenuButton();  
        button.setTitle(title);  
        if (iconName == null) iconName = "cube_blue";  
        button.setIcon("/images/" + iconName + ".png");  
        button.setLargeIcon("/images/" + iconName + ".png");  
        if (vertical == true) button.setOrientation("vertical");  
        if (menu != null) button.setMenu(menu);  
  
        button.setShowMenuIcon(true);  
        return button;  
    }  
    
    
	public void Novosti(){
		donji.destroy();
		donji=new HLayout();
		donji.setShowEdges(true);  
        donji.setWidth(getOffsetWidth());
        donji.setWidth100();
        donji.setHeight("78%");
        donji.setMembersMargin(5);  
        donji.setLayoutMargin(5);
        donji.setTop(140);
		main.addChild(donji);
		//donji.draw();
		final ListGrid countryGrid = new ListGrid();  
        countryGrid.setWidth(700);  
        countryGrid.setHeight(224);  
        countryGrid.setShowAllRecords(true);  
        countryGrid.setSelectionType(SelectionStyle.SINGLE);  
  
        
        ListGridField id = new ListGridField("id", "ID");  
        ListGridField nameField = new ListGridField("naziv", "Novost");  
        ListGridField capitalField = new ListGridField("datum", "Vrijeme objave");  
        ListGridField continentField = new ListGridField("ministarstvo", "Ministarstvo");  
        countryGrid.setFields(id, nameField, capitalField, continentField);  
        
        final Canvas canvasMain = new Canvas();  
        Canvas canvas = new Canvas(); 
        canvasMain.setTop(250);
        
        servis.ucitajNovosti(new AsyncCallback<Novosti> (){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.say("Doslo je do greske!");
			}

			@Override
			public void onSuccess(Novosti result) {
				// TODO Auto-generated method stub
				if(result==null)
					SC.say("nema nista");
				else{
					n=result;
					Postavi(result);
					for(int i=0;i<n.getbroj(); i++){
						ListGridRecord r1=new ListGridRecord();
						Novost n1=new Novost();
				        n1=n.dajnovost(i);
				        r1.setAttribute("id", n1.getid());
				        r1.setAttribute("naziv", n1.getnaziv());
				        r1.setAttribute("datum", n1.getdatum().toString());
				        r1.setAttribute("ministarstvo", n1.getministarstvo());
				        countryGrid.addData(r1);
					}       		        
			        
			       
					
				}
			}

			
        	
        });
        
        countryGrid.addSelectionChangedHandler(new SelectionChangedHandler() {  
            public void onSelectionChanged(SelectionEvent event) {
            	int id=0;
            	try{
            		ListGridRecord r=countryGrid.getSelectedRecord();
                    id=Integer.parseInt(r.getAttributeAsString("id"));
                    Novost nov=new Novost();
                    nov=n.dajnovostpoid(id);
                    //canvasMain.clear();
                    canvasMain.addChild(createWin(r.getAttributeAsString("naziv"), false, 600, 200, 0,nov.getopis()));  
            	}
            	catch(Exception e){
            		
            	}
            	
                servis.ProcitaoVijest(id, k.getid(), new AsyncCallback<Preporuci>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.say("Doslo je do grekse prilikom upisa pregleda vijesti");
					}

					@Override
					public void onSuccess(Preporuci result) {
						// TODO Auto-generated method stub
						int vrij=result.Proracunaj();
						Novost nov=new Novost();
						nov=n.dajnovostpoid(vrij);
						
						final VLayout layout = new VLayout();  
				        layout.setMembersMargin(10);  
				        if(nov.getnaziv().equals(""))
				        	SC.say("Preporučujemo vijest: Vijest broj 1");
				        else
				        	SC.say("Preporučujemo vijest: "+nov.getnaziv());
				        
				  
				            
				        
				  
				        HLayout hLayout = new HLayout();  
				        hLayout.setMembersMargin(10);  
				         
				         
				        layout.addMember(hLayout);  
				        layout.setLeft(610);
				        layout.setTop(250);
				        layout.draw();  
					}
                	
                });
            }  
        });  
  
        canvas.addChild(countryGrid);  
        
  
         
          
		
		String novosti="<b>Kliknite na novost da bi se prikazala</b>"; 
		
	
		canvasMain.addChild(createWin("Novosti", false, 600, 200, 0,novosti));  
		//canvasMain.draw();  
		
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
		donji.addChild(canvas);
		donji.addChild(canvasMain);
	
	}
	
	public void korisnici(){
		donji.destroy();
		donji=new HLayout();
		donji.setShowEdges(true);  
        donji.setWidth(getOffsetWidth());
        donji.setWidth100();
        donji.setHeight("78%");
        donji.setMembersMargin(5);  
        donji.setLayoutMargin(5);
        donji.setTop(140);
		main.addChild(donji);
		Canvas canvas = new Canvas();
		final ListGrid countryGrid = new ListGrid();  
        countryGrid.setWidth(700);  
        countryGrid.setHeight(224);  
        countryGrid.setCellHeight(22);
        ListGridField idField = new ListGridField("id", "ID"); 
        ListGridField nameField = new ListGridField("ime", "Ime");  
        ListGridField continentField = new ListGridField("prezime", "Prezime");  
        ListGridField memberG8Field = new ListGridField("tip", "Tip");  
        ListGridField populationField = new ListGridField("user", "Username"); 
        countryGrid.setFields(idField,nameField,continentField, memberG8Field, populationField); 
        countryGrid.setAutoFetchData(true);  
        countryGrid.setCanEdit(true);  
        countryGrid.setModalEditing(true);  
        countryGrid.setEditEvent(ListGridEditEvent.CLICK);  
        countryGrid.setListEndEditAction(RowEndEditAction.NEXT);  
        countryGrid.setAutoSaveEdits(false);
        
        servis.ucitajKorisnike(new AsyncCallback<Korisnici>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.say(caught.getMessage());
			}

			@Override
			public void onSuccess(Korisnici result) {
				// TODO Auto-generated method stub
				if(result==null){
					SC.say("Došlo je do greške prilikom učitavanja korisnika");
				}
				else{
					for(int i=0; i<result.dajbroj(); i++){
						ListGridRecord r1=new ListGridRecord();
						Korisnik korisnik=new Korisnik();
						korisnik=result.dajkorisnika(i);
						r1.setAttribute("id", korisnik.getid());
						r1.setAttribute("ime", korisnik.getime());
						r1.setAttribute("prezime", korisnik.getprezime());
						r1.setAttribute("tip", korisnik.gettip());
						r1.setAttribute("user", korisnik.getusername());
						countryGrid.addData(r1);
					}
				}
			}
        	
        });
         
        IButton admin = new IButton("Admin");  
        admin.setTop(250);  
         
        admin.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                countryGrid.saveAllEdits();  
                ListGridRecord r=countryGrid.getSelectedRecord();
                int id=r.getAttributeAsInt("id");
                servis.makeadmin(id, new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.say(caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						SC.say("Korisnik je sada administrator");
					}
                	
                });
                
               
            }  
        });  
        canvas.addChild(admin);
        IButton saveButton = new IButton("Save");  
        saveButton.setTop(250);  
        saveButton.setLeft(110);  
        saveButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                countryGrid.saveAllEdits();  
                ListGridRecord r=countryGrid.getSelectedRecord();
                int id=r.getAttributeAsInt("id");
                String i=r.getAttributeAsString("ime");
                String p=r.getAttributeAsString("prezime");
                String tip=r.getAttributeAsString("tip");
                
                servis.updatekorisnik(id, i, p, tip, new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.say(caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						SC.say("Uspješno sačuvano");
					}
                	
                });
            }  
        });  
        canvas.addChild(saveButton);  
  
        IButton discardButton = new IButton("Discard");  
        discardButton.setTop(250);  
        discardButton.setLeft(220);  
        discardButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	ListGridRecord r1=countryGrid.getSelectedRecord();
            	
            	int id=r1.getAttributeAsInt("id");  
            	servis.brisikorisnika(id, new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.say(caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						SC.say("Uspešno izbrisan korisnik");
					}
            		
            	});
            }  
        });  
        canvas.addChild(discardButton); 
        canvas.addChild(countryGrid);  
        donji.addChild(canvas);
	}
	
	public void Ministarstva(){
		donji.destroy();
		donji=new HLayout();
		donji.setShowEdges(true);  
        donji.setWidth(getOffsetWidth());
        donji.setWidth100();
        donji.setHeight("78%");
        donji.setMembersMargin(5);  
        donji.setLayoutMargin(5);
        donji.setTop(140);
		main.addChild(donji);
		Canvas canvas = new Canvas();  
		final TextAreaItem messageItem = new TextAreaItem(); 
		final TextItem subjectItem = new TextItem();  
        subjectItem.setTitle("Naziv");  
        subjectItem.setWidth("*"); 
        final TextItem adresa = new TextItem();  
        adresa.setTitle("Adresa");  
        adresa.setWidth("*"); 
        final TextItem kontakt = new TextItem();  
        kontakt.setTitle("Kontakt");  
        kontakt.setWidth("*"); 
        final ListGrid countryGrid = new ListGrid();  
        countryGrid.setWidth(600);  
        countryGrid.setHeight(224);  
        countryGrid.setCellHeight(22);  
        
        ListGridField idField = new ListGridField("id", "ID");  
        ListGridField nameField = new ListGridField("naziv", "Ministarstvo");  
        ListGridField continentField = new ListGridField("adresa", "Adresa");  
        ListGridField memberG8Field = new ListGridField("opis", "Opis");  
        ListGridField populationField = new ListGridField("kontakt", "Kontakt");  
        
        
        countryGrid.addSelectionChangedHandler(new SelectionChangedHandler(){

			@Override
			public void onSelectionChanged(SelectionEvent event) {
				// TODO Auto-generated method stub
				ListGridRecord r1=new ListGridRecord();
				
				r1=countryGrid.getSelectedRecord();
				messageItem.setValue(r1.getAttributeAsString("opis"));
				subjectItem.setValue(r1.getAttributeAsString("naziv"));
				adresa.setValue(r1.getAttributeAsString("adresa"));
				kontakt.setValue(r1.getAttributeAsString("kontakt"));
			}
        	
        });
        countryGrid.setFields(idField,nameField,continentField, memberG8Field, populationField);  
  
        //countryGrid.setAutoFetchData(true);  
        //countryGrid.setCanEdit(true);  
        //countryGrid.setModalEditing(true);  
        //countryGrid.setEditEvent(ListGridEditEvent.CLICK);  
        //countryGrid.setListEndEditAction(RowEndEditAction.NEXT);  
        countryGrid.setAutoSaveEdits(false);  
        canvas.addChild(countryGrid);  
        servis.ucitajministarstva(new AsyncCallback<Minis>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.say(caught.getMessage());
			}

			@Override
			public void onSuccess(Minis result) {
				// TODO Auto-generated method stub
				if(result==null)
					SC.say("Došlo je do greške prilikom učitavanja");
				else{
					for(int i=0; i<result.dajbroj(); i++){
						ListGridRecord r1=new ListGridRecord();
						r1.setAttribute("id", result.dajid(i));
						r1.setAttribute("naziv", result.dajnaziv(i));
						r1.setAttribute("adresa", result.dajadresu(i));
						r1.setAttribute("opis", result.dajopis(i));
						r1.setAttribute("kontakt", result.dajkontakt(i));
						countryGrid.addData(r1);
					}
					Postavimin(result);
				}
			}
        	
        });
  
        IButton editButton = new IButton("Edit New");  
        editButton.setTop(250);  
        editButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                if(subjectItem.getValueAsString().equals("") || adresa.getValueAsString().equals("") || kontakt.getValueAsString().equals("")) 
                	SC.say("Jedno od polja je prazno");
                else{
                	servis.ubaciMin(subjectItem.getValueAsString(), adresa.getValueAsString(), messageItem.getValueAsString(), kontakt.getValueAsString(), new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							SC.say(caught.getMessage());
						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							ListGridRecord r1=new ListGridRecord();
							r1.setAttribute("id", 4);
							r1.setAttribute("naziv", subjectItem.getValueAsString());
							r1.setAttribute("adresa", adresa.getValueAsString());
							r1.setAttribute("opis", messageItem.getValueAsString());
							r1.setAttribute("kontakt", kontakt.getValueAsString());
							countryGrid.addData(r1);
							subjectItem.setValue("");
							adresa.setValue("");
							kontakt.setValue("");
							messageItem.setValue("");
							SC.say("Ministarstvo dodano");
						}
                		
                	});
                }
            }  
        });  
        canvas.addChild(editButton);  
  
        
        
  
        IButton discardButton = new IButton("Discard");  
        discardButton.setTop(250);  
        discardButton.setLeft(110);  
        discardButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	ListGridRecord r1=countryGrid.getSelectedRecord();
            	int id=r1.getAttributeAsInt("id");
            	servis.brisiMin(id, new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.say(caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						countryGrid.removeSelectedData();
						
						subjectItem.setValue("");
						adresa.setValue("");
						kontakt.setValue("");
						messageItem.setValue("");
						SC.say("Ministarstvo uspješno izbrisano");
					}
            		
            	});
            }  
        });  
        canvas.addChild(discardButton);  
        final DynamicForm form = new DynamicForm();  
        form.setWidth(600);  
        form.setHeight(180); 
        form.setTop(280);
        messageItem.setShowTitle(false);  
        messageItem.setLength(5000);  
        messageItem.setColSpan(2);  
        messageItem.setWidth("*");  
        messageItem.setHeight("*");  
  
        
        form.setFields(subjectItem, adresa, kontakt,messageItem);
        
         
  
        donji.addChild(form);
        donji.addChild(canvas);
	}
	
	public void Konkursi(){
		donji.destroy();
		donji=new HLayout();
		donji.setShowEdges(true);  
        donji.setWidth(getOffsetWidth());
        donji.setWidth100();
        donji.setHeight("78%");
        donji.setMembersMargin(5);  
        donji.setLayoutMargin(5);
        donji.setTop(140);
		main.addChild(donji);
		final Canvas canvas = new Canvas();  
		final DynamicForm form = new DynamicForm();  
		final TextAreaItem messageItem = new TextAreaItem(); 
		
		final TextItem subjectItem = new TextItem();  
        subjectItem.setTitle("Naziv");  
        subjectItem.setWidth("*"); 
        
        final TextItem rok = new TextItem();  
        rok.setTitle("Rok (YYYY-MM-DD)");  
        //subjectItem.setWidth("*"); 
        
        final ListGrid countryGrid = new ListGrid();  
        countryGrid.setWidth(700);  
        countryGrid.setHeight(224);  
        countryGrid.setCellHeight(22); 
        final ComboBoxItem divisionItem = new ComboBoxItem();  
        divisionItem.setTitle("Ministarstvo");  
        divisionItem.setValueMap("Ministarstvo obrazovanja", "Ministarstvo saobracaja", "Ministarstvo kulture");  
        divisionItem.setDefaultValue("Ministarstvo obrazovanja"); 
        
        final Canvas canvas1 = new Canvas();
		 canvas1.setTop(300);
        
        ListGridField idField = new ListGridField("id", "ID");  
        ListGridField nameField = new ListGridField("naziv", "Konkurs");  
        ListGridField continentField = new ListGridField("objavljeno", "Objavljeno");  
        ListGridField memberG8Field = new ListGridField("rok", "Rok");  
        ListGridField populationField = new ListGridField("opis", "Opis");  
        ListGridField min = new ListGridField("min", "ministarstvo");
        
        
        countryGrid.addSelectionChangedHandler(new SelectionChangedHandler(){

			@Override
			public void onSelectionChanged(SelectionEvent event) {
				// TODO Auto-generated method stub
				try{
					
					donji.removeChild(form);
ListGridRecord r1=countryGrid.getSelectedRecord();
	            	
	            	int id=r1.getAttributeAsInt("id");
	            	
					String id1=Integer.toString(id);
					
					canvas1.addChild(new Upload());
					String b="<a href='http://localhost/trunk/dokument.php?id="+id1+"' target='_blank'><strong>Skini obrazac</strong></a>";
					
					 Label obrazac=new Label(b);
					 obrazac.setTop(80);
					 canvas1.addChild(obrazac);
					donji.addChild(canvas1);
				}
				catch(Exception e){
					
				}
				
				
			}
        	
        });
        countryGrid.setFields(idField,nameField,continentField, memberG8Field, populationField,min);  
  
        //countryGrid.setAutoFetchData(true);  
        //countryGrid.setCanEdit(true);  
        //countryGrid.setModalEditing(true);  
        //countryGrid.setEditEvent(ListGridEditEvent.CLICK);  
        //countryGrid.setListEndEditAction(RowEndEditAction.NEXT);  
        countryGrid.setAutoSaveEdits(false);  
        canvas.addChild(countryGrid);  
        servis.ucitajkonkurse(new AsyncCallback<Konkursi>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				SC.say(caught.getMessage());
			}

			

			@Override
			public void onSuccess(Konkursi result) {
				// TODO Auto-generated method stub
				if(result==null)
					SC.say("Došlo je do greške prilikom učitavanja");
				else{
					for(int i=0; i<result.dajbroj(); i++){
						try{
							ListGridRecord r1=new ListGridRecord();
							r1.setAttribute("id", result.dajid(i));
							r1.setAttribute("naziv", result.dajnaziv(i));
							r1.setAttribute("objavljeno", result.dajobjava(i).toString());
							r1.setAttribute("rok", result.dajrok(i).toString());
							r1.setAttribute("opis", result.dajopis(i));
							r1.setAttribute("min", result.dajmin(i));
							countryGrid.addData(r1);
						}
						catch(Exception e){
							
						}
						
					}
				}
			}
        	
        });
  
        IButton editButton = new IButton("Add new");  
        editButton.setTop(250);  
        editButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
                if(subjectItem.getValueAsString().equals("") ) 
                	SC.say("Jedno od polja je prazno");
                else{
                	servis.dodajkonkurs(subjectItem.getValueAsString(), rok.getValueAsString(), messageItem.getValueAsString(),divisionItem.getValueAsString(), new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							SC.say(caught.getMessage());
						}

						@Override
						public void onSuccess(Void result) {
							// TODO Auto-generated method stub
							ListGridRecord r1=new ListGridRecord();
							r1.setAttribute("id", 0);
							r1.setAttribute("naziv", subjectItem.getValueAsString());
							r1.setAttribute("objavljeno", "danas");
							r1.setAttribute("rok", rok.getValueAsString());
							r1.setAttribute("opis", messageItem.getValueAsString());
							r1.setAttribute("min", divisionItem.getValueAsString());
							countryGrid.addData(r1);
							donji.removeChild(form);
							
							SC.say("Konkurs dodan. Da bi se proces zavrsio, potrebno je da kliknete da konkurs u listi i da pošaljete file");
						}
                		
                	});
                }
            }  
        });  
        canvas.addChild(editButton);  
  
        
        IButton refresh = new IButton("Osvježi");  
        refresh.setTop(250);  
        refresh.setLeft(220);  
        refresh.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	donji.removeChild(canvas1);
            	donji.addChild(form);
            }  
        });  
        canvas.addChild(refresh);
  
        IButton discardButton = new IButton("Discard");  
        discardButton.setTop(250);  
        discardButton.setLeft(110);  
        discardButton.addClickHandler(new ClickHandler() {  
            public void onClick(ClickEvent event) {  
            	ListGridRecord r1=countryGrid.getSelectedRecord();
            	
            	int id=r1.getAttributeAsInt("id");
            	
            	servis.brisikonkurs(id, new AsyncCallback<Void>(){

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						SC.say(caught.getMessage());
					}

					@Override
					public void onSuccess(Void result) {
						// TODO Auto-generated method stub
						countryGrid.removeSelectedData();
						
						
						SC.say("Konkurs uspješno izbrisan");
					}
            		
            	});
            }  
        });  
        canvas.addChild(discardButton);  
        
        form.setWidth(600);  
        form.setHeight(180); 
        form.setTop(300);
        messageItem.setShowTitle(false);  
        messageItem.setLength(5000);  
        messageItem.setColSpan(2);  
        messageItem.setWidth("*");  
        messageItem.setHeight("*");  
  
       
        form.setFields(subjectItem,divisionItem, messageItem, rok);
        
         
  
        donji.addChild(form);
        donji.addChild(canvas);
	}
	
	
	public void Postavimin(Minis min){
		m=min;
	}
}
