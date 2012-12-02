package ba.nwt.ministarstvo.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;


import ba.nwt.ministarstvo.server.fileUpload.FormResponse;

public class DownloadFile  extends HttpServlet {
	private Connection c=null;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
		Map<String,String[]> parameters = request.getParameterMap();
		
		PrintWriter out = response.getWriter();
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
			
			e.printStackTrace();
		}
		
		if (parameters.containsKey("id")){
			String[] broj = parameters.get("id");
			
			try {
				PreparedStatement ps1 = (PreparedStatement) c.prepareStatement("SELECT file, ime_file FROM prijave WHERE id=?");
				ps1.setInt(1, Integer.parseInt(broj[0]));
				ResultSet rs=ps1.executeQuery();
				while(rs.next()){
					Blob blob = rs.getBlob("file"); 
					InputStream in = blob.getBinaryStream(); 
					String ime=rs.getString("ime_file");
					String duz=rs.getString("file");
					//response.setContentType("text/plain");
					//response.setContentType("application/plain");  
					//response.setHeader("Content-Disposition", "attachment;filename=" + ime); 
					 try{
					ServletOutputStream out1;
					out1 = response.getOutputStream();
					 int len=duz.length();
					 byte [] rb = new byte[len];
					 int index=in.read(rb, 0, len); 
					 System.out.println("index"+index);
					 response.reset();
					  response.setContentType("application/pdf");
					  response.setHeader("Content-Disposition", "attachment;filename=" + ime); 
					  response.getOutputStream().write(rb,0,len);
					  response.getOutputStream().flush();  
					 }
					 catch(Exception e){
						 
					 }
				
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			sendResponse(response, new FormResponse(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "The servlet can only handle multipart requests."
                            + " This is probably a software bug."));
            return;
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

}
