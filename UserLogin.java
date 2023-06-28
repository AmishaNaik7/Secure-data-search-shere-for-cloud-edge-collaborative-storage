package poll;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/UserLogin")
public class UserLogin extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public static String impdtd = "30";
	public static String impdtm = "8";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		loginRequest(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		loginRequest(request, response);
	}

	private void loginRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		try {
			String username = request.getParameter("username");
			String passwords = request.getParameter("password");
			String Usertype = request.getParameter("Usertype");
			DateDifferent DF = new DateDifferent();
			Connection con = ConnectionProvider.getConn();
			Statement st = con.createStatement();
			Statement st1 = con.createStatement();

			ResultSet rs = st.executeQuery("select * from user where email='"
					+ username + "'");
			Date dNow = new Date( );
		      SimpleDateFormat ft =new SimpleDateFormat ("dd/MM/yyyy");
				String cdate=ft.format(dNow);
			if (rs.next()) {

				String email = rs.getString("email");
				String user_id = rs.getString("id");
				String fname = rs.getString("fname");
				String p = rs.getString("password");
				// String Usertype = rs.getString("Usertype");

				String regDate = rs.getString("regDate");
				String expire_date = rs.getString("expire_date");
			
				long diff = DF.getCDateDifference(regDate, expire_date);
				 long diff_days=DF.getCDateDifference(cdate, expire_date);
				System.out.println(diff_days);
				String Status = rs.getString("Status");
				
				/*if(diff_days<0)
				{
					response.sendRedirect("User_login.jsp?ex");
				}*/
				
				if (Status.equalsIgnoreCase("1")) 
				{
					
					/*if (diff > 0) */
						if(diff_days>0)
					{
						if (passwords.equalsIgnoreCase(p)) 
						{
							session.setAttribute("email", email);
							session.setAttribute("fname", fname);
							session.setAttribute("userId", user_id);
							session.setAttribute("grupid", rs.getString("GroupID"));
							// new LoadForm();

							if (Usertype.equalsIgnoreCase("User")) 
							{

								session.setAttribute("Usertype", Usertype);
								response.sendRedirect("DataOwnerHome.jsp?day="+diff_days);
							} else {
								session.setAttribute("Usertype", Usertype);
								response.sendRedirect("DataOwnerHome.jsp");

							}

						} else {

							if (Usertype.equalsIgnoreCase("User")) {

								response.sendRedirect("User_login.jsp?user='incorrect'");
							} else if (Usertype.equalsIgnoreCase("TPA")
									&& username.equalsIgnoreCase("tpa")) {
								response.sendRedirect("DataOwnerHome.jsp");

							} else {
								response.sendRedirect("User_login.jsp?user='incorrect'");
							}
						}
					}
					else 
					{
						System.out.println("ex");
						st1.executeUpdate("update user set AuthorisedGroup=1 where id='"+ user_id + "'");
						if (Usertype.equalsIgnoreCase("User")) 
						{
							/*PreparedStatement pp = con.prepareStatement("delete from userfile where uploadedBy='"+user_id+"'");
							pp.executeUpdate();*/
							response.sendRedirect("User_login.jsp?expired='expired'");
						}
						else 
						{
							response.sendRedirect("Dataowner_login.jsp?expired='expired'");

						}
					}

				} else {

					response.sendRedirect("User_login.jsp?Activation='ActivationFailed'");
				}

			}
			if (Usertype.equalsIgnoreCase("TPA")
					&& username.equalsIgnoreCase("tpa")) {
				session.setAttribute("Usertype", Usertype);
				response.sendRedirect("TPAHome.jsp");

			} else {
				response.sendRedirect("User_login.jsp?user='incorrect'");
			}

		} catch (Exception e) {
			out.println(e);
		} finally {

			out.close();
		}

	}

}
