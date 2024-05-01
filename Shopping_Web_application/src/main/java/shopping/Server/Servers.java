package shopping.Server;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

    public class Servers extends HttpServlet{
		private static final long serialVersionUID = 1L;
		@Override
		public void doGet(HttpServletRequest req,HttpServletResponse res) throws IOException {
			String input=req.getParameter("Input");
			Servers s=new Servers();
			String result=s.Jdbc(input);
			PrintWriter pw=res.getWriter();
			pw.println("<!DOCTYPE html>");
	        pw.println("<html>");
	        pw.println("<head>");
	        pw.println("<title>Output</title>");
	        pw.println("<style>");
	        pw.println("body { font-family: Arial, sans-serif; }");
	        pw.println(".output { padding: 20px; background-color: grey; border-radius: 5px; }");
	        pw.println("</style>");
	        pw.println("</head>");
	        pw.println("<body>");
	        pw.println("<div class='output'>");
	        pw.println("<h2>Output</h2>");
	        pw.println("<p>" + result + "</p>");
	        pw.println("</div>");
	        pw.println("</body>");
	        pw.println("</html>");
		}

    	    	public String Jdbc(String input) {
    	    	StringBuilder result=new StringBuilder();
    	        String url = "jdbc:mysql://localhost:3306/Shopping_Web_Application";
    	        String username = "root";
    	        String password = "152015Uday@";

    	        try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
    	        try (Connection connection = DriverManager.getConnection(url, username, password)) {
    	            String sql = "SELECT * FROM Products WHERE product_name = ?";

    	            try (PreparedStatement statement = connection.prepareStatement(sql)) {
    	                statement.setString(1, input);

    	                try (ResultSet resultSet = statement.executeQuery()) {
    	                	if(!resultSet.isBeforeFirst()) {
    	                		result.append("No matching Records found for input: ").append(input);
    	                	}
    	                	else {
    	                    ResultSetMetaData metaData = resultSet.getMetaData();
    	                    int columnCount = metaData.getColumnCount();

    	                    while (resultSet.next()) {
    	                        for (int i = 1; i <= columnCount; i++) {
    	                            String columnName = metaData.getColumnName(i);
    	                            Object value = resultSet.getObject(i);
    	                            result.append(columnName).append(" : ").append(value).append("\n");
    	                        }
    	                    }
    	                }}
    	            }
    	        } catch (SQLException e) {
    	            e.printStackTrace();
    	            result.append("Error while retreving data");
    	        }
    	        return result.toString();
    	    }
    	}
