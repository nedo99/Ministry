/*
 * Created on Aug 12, 2007
 * 
 * Copyright 2005 CafeSip.org 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *	http://www.apache.org/licenses/LICENSE-2.0 
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
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 * 
 * Interface that an action class must implement for working with the
 * FileUploadServlet servlet. Once registered with the FileUploadServlet (using
 * init params), the action methods will be invoked by the servlet when a file
 * upload request is received from the client. For details see the,
 * {@link org.cafesip.gwtcomp.server.FileUploadServlet} class.
 * <p>
 * 
 * The class implementing this method can also include addition parameter fields
 * with public getter and setter methods. The FileUploadServlet will
 * automatically populate these parameters if the name of the parameter is the
 * same as the name of the HTML input fields on the client-side. Therefore, you
 * can create a client-side HTML form with one or more input field (text field,
 * password, hidden, etc.) and one or more file upload fields. When the servlet
 * invokes the onSubmit() method (see below), it already has the input fields
 * populated and the upload file list is set (using the setFileList() method,
 * below). In the onSubmit() method (see below), you can add your business logic
 * to implement what your application needs to do to handle the form. It is
 * similar to a JSF bean class in concept.
 * 
 * <p>
 * <u>Examples:</u><br>
 * <ul class="example">
 * <li><a href="../../../../../examples/org/cafesip/gwtcomp/examples/server/FileUploadActionSample.java.html">FileUploadActionSample.java</a></li>
 * </ul>
 * 
 * @author Amit Chatterjee
 * 
 */
public interface FileUploadAction
{
    /**
     * The FileUpload servlet invokes this method to set a list of files that is
     * being uploaded. You can implement this method to receive the list of
     * files.
     * 
     * @param files
     *            list of files. The key property for the HashMap contains the
     *            field name specified in the HTML form and the value contains
     *            the file path name of the file that the FileUploadServlet
     *            copied the file into.
     */
    public void setFileList(HashMap<String, File> files);

    /**
     * The FileUpload servlet invokes this method to notify that a client-side
     * request is received. You can implement this method to handle the event.
     * The method returns a FormResponse object to tell the servlet how to
     * (HTTP) respond to the form request.
     * 
     * 
     * @param servlet
     *            the servlet that handled the request.
     * @param request
     *            the request context. Both of these parameters can be used to
     *            get servlet-specific information like session-scope variables,
     *            etc. using the standard servlet API.
     * @return the FormResponse object
     */
    public FormResponse onSubmit(HttpServlet servlet, HttpServletRequest request);
}
