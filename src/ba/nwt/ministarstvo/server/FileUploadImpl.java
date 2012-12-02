package ba.nwt.ministarstvo.server;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;


public class FileUploadImpl extends HttpServlet{
	private FileItem uploadedFileItem;
	 private static final long serialVersionUID = 639643757899122285L;

	    private static final String PARAM_ACTION_CLASS = "action-class";

	    private static DiskFileItemFactory factory;

	    private Class<?> actionClass;
	 
	 @SuppressWarnings("unchecked")
	    @Override
	    protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException
	    {
	        try
	        {
	            ServletRequestContext ctx = new ServletRequestContext(request);

	            if (ServletFileUpload.isMultipartContent(ctx) == false)
	            {
	                
	                return;
	            }

	            // Create a new file upload handler
	            ServletFileUpload upload = new ServletFileUpload(factory);
	            
	            // Parse the request
	            List<FileItem> items = upload.parseRequest(request);
	            Iterator<FileItem> i = items.iterator();
	            HashMap<String, String> params = new HashMap<String, String>();
	            HashMap<String, File> files = new HashMap<String, File>();

	            while (i.hasNext() == true)
	            {
	                FileItem item = i.next();

	                if ( item.isFormField() == true)
	                {
	                    String param = item.getFieldName();
	                    String value = item.toString();
//	                    System.out.println(getClass().getName() + ": param="
//	                            + param + ", value=" + value);
	                    params.put(param, value);
	                }
	                else
	                {
	                	
	                	if (item.getSize() == 0)
	                    {
	                        continue; // ignore zero-length files
	                    }

	                    File tempf = File.createTempFile(request.getRemoteAddr()
	                            + "-" + item.getFieldName() + "-", "");
	                    item.write(tempf); 
	                    files.put(item.getFieldName(), tempf);
//	                    System.out.println("Creating temporary file "
//	                            + tempf.getAbsolutePath());
	                }
	            }

	            // populate, invoke the listener, delete files if needed,
	            // send response
	           
	            return;
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	           
	        }
	    }

	public String uploadAttachement(String caseId, String fieldName,
			boolean isNewCase) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteAttachement(String filePath, int caseID,
			String fieldName) {
		// TODO Auto-generated method stub
		return false;
	}

}
