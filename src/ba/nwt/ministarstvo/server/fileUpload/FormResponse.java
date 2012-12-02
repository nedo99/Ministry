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

/**
 * Helper class for FileUploadAction. This class that is used by the
 * FileUploadAction action class to indicate to the servlet how to respond to
 * the form request received from a HTTP client.
 * 
 * @author Amit Chatterjee
 * 
 */
public class FormResponse
{
    private int status;

    private String reason;

    private boolean deleteFiles = false;

    /**
     * A constructor for this class.
     * 
     * @param status
     *            status to be sent as the response. They must be valid HTTP
     *            status value such as 200, 404, 500, etc.
     * 
     * @param reason
     *            optional phrase (or reason string). Example: "Not Found".
     * 
     * @param deleteFiles
     *            true to indicate if the FileUpload servlet must delete the
     *            files after invocation of the FileUploadAction.onSubmit()
     *            method. If set to true, the FileUploadAction servlet will
     *            delete the files that it created from the upload request prior to
     *            invoking the FileUploadAction.onSubmit() method.
     */
    public FormResponse(int status, String reason, boolean deleteFiles)
    {
        this.status = status;
        this.reason = reason;
        this.deleteFiles = deleteFiles;
    }

    /**
     * A constructor for this class with deletFiles parameter set to false.
     * 
     * @param status
     * @param reason
     */
    public FormResponse(int status, String reason)
    {
        this(status, reason, false);
    }

    /**
     * Returns the reason
     * 
     * @return Returns the reason.
     */
    public String getReason()
    {
        return reason;
    }

    /**
     * Sets the reason.
     * 
     * @param reason
     *            The reason to set.
     */
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    /**
     * Returns the status.
     * 
     * @return Returns the status.
     */
    public int getStatus()
    {
        return status;
    }

    /**
     * Sets the status.
     * 
     * @param status
     *            The status to set.
     */
    public void setStatus(int status)
    {
        this.status = status;
    }

    /**
     * Returns if the temporary files are set to be deleted.
     * 
     * @return true if the files are to be deleted.
     */
    public boolean isDeleteFiles()
    {
        return deleteFiles;
    }

    /**
     * Indicates to the servlet whether the temporary files it has created must
     * be deleted.
     * 
     * @param deleteFiles
     *            true if the files are to be deleted..
     */
    public void setDeleteFiles(boolean deleteFiles)
    {
        this.deleteFiles = deleteFiles;
    }
}
