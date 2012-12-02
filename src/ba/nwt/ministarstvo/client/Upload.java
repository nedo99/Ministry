package ba.nwt.ministarstvo.client;

import org.apache.commons.fileupload.FileItem;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.core.client.GWT;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;


public class Upload extends HorizontalPanel{
	
	public Upload(){
		final FormPanel formPanel;
		formPanel = new FormPanel();
		
		formPanel.setAction("/ministarstvo/services/upl");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		 VerticalPanel panel = new VerticalPanel();
		 formPanel.setWidget(panel);
		Button uploadButton = new Button("Upload");
		final FileUpload uploader = new FileUpload();
		uploader.setName("dokument");
		TextBox tekst=new TextBox();
		tekst.setName("broj");
		Label l=new Label("<input type='text' name='broj'>");
		
		
		
		uploadButton.addClickHandler(new ClickHandler() {
		 
		    

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub
				if(uploader.getFilename().contains(".doc"))
				    formPanel.submit();
				else{
					SC.say("Moguće je upload-ovati samo wrod dokumente");
				}
				
			}
		});

		formPanel.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(SubmitEvent event) {
                    if (!"".equalsIgnoreCase(uploader.getFilename())) {
                            GWT.log("UPLOADING FILE????", null);
                                    // NOW WHAT????
                            GWT.log(uploader.getFilename());
                            
                    }
                    else{
                            event.cancel(); // cancel the event
                    }

            }
    });
		formPanel.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
		 
		    @Override
		    public void onSubmitComplete(final SubmitCompleteEvent event) {
			// call upload method from service with the file's name
		    	Window.alert(event.getResults());
			String result = event.getResults();
			if(result.contains("OK")) {
			    String name = uploader.getFilename();
		 
			    // trigger some event that custom file editor will
			     
		 
			 }
		    }
		});
		panel.add(uploader);
		
		panel.add(uploadButton);
		this.add(formPanel);
		//RootPanel.get().add(formPanel);
	}

}
