package ba.nwt.ministarstvo.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface FileUploadAsync {

	void uploadAttachement(String caseId, String fieldName, boolean isNewCase,
			AsyncCallback<String> callback);

	void deleteAttachement(String filePath, int caseID, String fieldName,
			AsyncCallback<Boolean> callback);

}
