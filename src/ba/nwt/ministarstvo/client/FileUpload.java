package ba.nwt.ministarstvo.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("File")
public interface FileUpload extends RemoteService{
	
	public String uploadAttachement(String caseId, String fieldName,boolean isNewCase);
	public boolean deleteAttachement(String filePath, int caseID, String fieldName);

}
