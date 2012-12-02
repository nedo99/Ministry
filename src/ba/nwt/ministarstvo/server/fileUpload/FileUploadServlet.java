/*
 * Created on Aug 12, 2007
 * 
 * Copyright 2005 CafeSip.org 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *  http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 *
 */
package ba.nwt.ministarstvo.server.fileUpload;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;

/**
 * This servlet provides you a convenient way to handle HTML forms containing
 * file uploads along with standard input elements like text field, text area,
 * checkbox, etc. It is quite common for HTML forms to contain a file upload
 * widget that allows users to upload a file to the server. For example, a music
 * upload site may use such a widget to enable the user to upload music files
 * along with a title of the song, description, etc. GWT provides a class called
 * FileUpload that helps you place such widgets on your GWT application but it
 * does not provide any utilities on the server side. In fact, you lose the
 * convenience of GWT RPC when a file upload needs widget needs to be placed.
 * This class is an attempt to cover such deficiencies.
 * <p>
 * 
 * Once this servlet is added to the server-side deployment, it can work with
 * the GWT client-side FileUpload class to receive HTML forms with one or more
 * file uploads. This class copies the uploaded files to a temporary directory
 * and invoke methods on application-defined "action" classes to notify the
 * receipt of the request. The "action" class can provide the business logic to
 * handle such request. The action class is shielded from any servlet details.
 * As a part of the servlet init params, you can register an action class. An
 * action class must implement the FileUploadAction interface. See {@link
 * org.cafesip.gwtcomp.server.FileUploadAction} for details.
 * <p>
 * 
 * To add the servlet to the deployment, you will need to include the following
 * jar files to the WEB-INF/lib directory of your web archive.
 * <ul class="css">
 * <li>gwtcomp-servlet.jar</li>
 * <li>commons-beanutils.jar</li>
 * <li>commons-collections.jar</li>
 * <li>commons-fileupload-x.x.x.jar</li>
 * <li>commons-io-x.x.jar</li>
 * <li>commons-logging.jar</li>
 * </ul>
 * All of these files are bundled with the gwtcomp distribution.
 * <p>
 * 
 * In addition, to WEB-INF/web.xml file add a segment similar to the following
 * for every action class that you may have:
 * 
 * <pre>
 *      &lt;!-- Change the servlet name and mapping with appropriate name and URL pattern respectively --&gt;
 *      &lt;servlet&gt;
 *           &lt;servlet-name&gt;MusicUploadService&lt;/servlet-name&gt;
 *           &lt;servlet-class&gt;
 *               org.cafesip.gwtcomp.server.FileUploadServlet
 *           &lt;/servlet-class&gt;
 *           
 *           &lt;init-param&gt;
 *               &lt;param-name&gt;action-class&lt;/param-name&gt;
 *               
 *               &lt;!-- Replace the action class name below with the fully qualified name of the action class --&gt;
 *               &lt;param-value&gt;mypackage.MyActionClass&lt;/param-value&gt;
 *           &lt;/init-param&gt;
 *       &lt;/servlet&gt;
 *       ...
 *       &lt;servlet-mapping&gt;
 *           &lt;servlet-name&gt;MusicUploadService&lt;/servlet-name&gt;
 *           &lt;url-pattern&gt;/myservices/musicUploadService&lt;/url-pattern&gt;
 *       &lt;/servlet-mapping&gt;
 * </pre>
 * 
 * To invoke this service from the client side of the application, you will have
 * to do something like the following:
 * 
 * <pre>
 * public class MusicUploadForm extends FormPanel
 * {
 * 
 *     private static final String MUSIC_UPLOAD_ACTION = &quot;/myservices/musicUploadServiceService&quot;;
 * 
 *     public MusicUploadForm() {
 *      setAction(MUSIC_UPLOAD_ACTION); // set the action, must match with the servlet URL pattern
 *      setEncoding(FormPanel.ENCODING_MULTIPART);
 *      setMethod(FormPanel.METHOD_POST);
 *    
 *      VerticalPanel panel = new VerticalPanel();
 *      setWidget(vp);
 *    
 *      panel.add(new Label(&quot;Song name&quot;));
 *      TextBox name = new TextBox();
 *      panel.add(name);
 *    
 *      panel.add(new Label(&quot;File&quot;));
 *      FileUpload upload = new FileUpload();
 *      upload.setName(&quot;musicFile&quot;);
 *      panel.add(upload);
 *    
 *      Button submit = new Button(&quot;Submit&quot;);
 *      submit.addClickListener(new ClickListener()
 *      {
 *        public void onClick(Widget sender) {
 *        submit(); // submit the form
 *      }
 *      });
 *      panel.add(submit)  
 *    }
 * }
 * </pre>
 * 
 * In the above example, the action class must have an attribute called name
 * with corresponding getter/setter for the FileUploadServlet to populate the
 * name field entered in the form.
 * 
 * @author Amit Chatterjee
 * 
 */
public class FileUploadServlet extends HttpServlet
{
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 639643757899122285L;

    private static final String PARAM_ACTION_CLASS = "action-class";

    private static DiskFileItemFactory factory;

    private Class<?> actionClass;

    /**
     * A constructor for this class.
     * 
     */
    public FileUploadServlet()
    {
        super();
    }

    /*
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest,
     *      javax.servlet.http.HttpServletResponse)
     */
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
                sendResponse(response, new FormResponse(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "The servlet can only handle multipart requests."
                                + " This is probably a software bug."));
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

                if (item.isFormField() == true)
                {
                    String param = item.getFieldName();
                    String value = item.getString();
//                    System.out.println(getClass().getName() + ": param="
//                            + param + ", value=" + value);
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
//                    System.out.println("Creating temporary file "
//                            + tempf.getAbsolutePath());
                }
            }

            // populate, invoke the listener, delete files if needed,
            // send response
            FileUploadAction action = (FileUploadAction) actionClass
                    .newInstance();
            BeanUtils.populate(action, params); // populate the object
            action.setFileList(files);

            FormResponse resp = action.onSubmit(this, request);
            if (resp.isDeleteFiles())
            {
                Iterator<Map.Entry<String, File>> j = files.entrySet().iterator();
                while (j.hasNext())
                {
                    Map.Entry<String, File> entry = j.next();
                    File f = entry.getValue();
                    f.delete();
                }
            }

            sendResponse(response, resp);
            return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            sendResponse(response, new FormResponse(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getClass()
                            .getName()
                            + ": " + e.getMessage()));
        }
    }

    private void sendResponse(HttpServletResponse response, FormResponse details)
    {
        // GWT provides very poor support for handling servlet response in the
        // FormPanel and associated classes. Basically, it returns only the body
        // part and nothing else. So, the only way, we can indicate 
        // an error is to embed the message in the HTML. 
        
        try
        {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("text/html");
            
            String msg = (details.getReason() == null?"": details.getReason());
            response.setContentLength(msg.length());
            response.getWriter().print(msg);
            response.getWriter().flush();            
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
     * @see javax.servlet.GenericServlet#init()
     */
    @Override
    public void init() throws ServletException
    {
        factory = new DiskFileItemFactory();

        String clazz = getServletConfig().getInitParameter(PARAM_ACTION_CLASS);
        if (clazz == null)
        {
            throw new ServletException("The parameter " + PARAM_ACTION_CLASS
                    + " must be specified.");
        }

        try
        {
            actionClass = Class.forName(clazz);
        }
        catch (Exception e)
        {
            throw new ServletException(e);
        }
    }
}
