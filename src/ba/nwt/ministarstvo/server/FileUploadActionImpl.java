
package ba.nwt.ministarstvo.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

import ba.nwt.ministarstvo.server.fileUpload.FileUploadAction;
import ba.nwt.ministarstvo.server.fileUpload.FormResponse;


public class FileUploadActionImpl implements FileUploadAction {
	private HashMap<String, File> fileList;
	private Connection c=null;
	
	@Override
	public FormResponse onSubmit(HttpServlet servlet, HttpServletRequest request) {
	
		String vrati="Konekcija uspjela";
		try {
			Class.forName ("com.mysql.jdbc.Driver").newInstance ();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			c = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/nwt", "root", "niko");
		} catch (SQLException e) {
			vrati=e.getMessage();
			e.printStackTrace();
		}
		
				File avatarFile = (File)fileList.get("dokument"); 
			
				String naziv="doc.doc";
				if (avatarFile instanceof File)
				{
					//String ext = avatarFile.getName().substring(avatarFile.getName().lastIndexOf('.')+1,avatarFile.getName().length());					
					File existingAvatarFile = avatarFile;
					//if (existingAvatarFile.exists())	existingAvatarFile.delete();
					
	
						//Create default profile picture
						File defaultAvatar = new File("default.jpg");
						File avatar = new File( "ime.jpg");
						
						//File copy
						try {
							FileInputStream from = new FileInputStream(existingAvatarFile);
						    FileOutputStream to = new FileOutputStream(avatar);
						    PreparedStatement ps1 = (PreparedStatement) c.prepareStatement("UPDATE konkurs SET file=?, ime_file=?, ext=? WHERE ime_file='postavi'");
						    
						    ps1.setBlob(1, from);
						    ps1.setString(2, "Konkurs2.doc");
						    ps1.setString(3, "msword");
						    ps1.executeUpdate();
							byte[] buffer = new byte[4096];
							int bytesRead;
							while ((bytesRead = from.read(buffer)) != -1)
								to.write(buffer, 0, bytesRead); // write
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
					
					
				}
				

				return new FormResponse(HttpServletResponse.SC_OK, "{\"success\":true}", true);


		//return new FormResponse("{\"success\":false,\"errors\":[{\"id\":\"secondaryEmail\",\"msg\":\"Already exists\"}, {\"id\":\"nickname\",\"msg\":\"Already taken\"}]}", true);
	}

	@Override
	public void setFileList(HashMap<String, File> fileList) {
		this.fileList = fileList;
	}

	public HashMap<String, File> getFileList() {
		return fileList;
	}

}