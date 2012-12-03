package cn.edu.fudan.software.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.fudan.software.database.DBConn;
import cn.edu.fudan.software.database.DBType;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String result = "no";
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call login(?,?)}");
			stmt.setString(1, email);
			stmt.setString(2, password);

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				result = "yes";

				request.getSession().setAttribute("loginUser", email);
				request.getSession().setAttribute("role", rs.getString("role"));
			} else {
				result = "no";
				request.getSession().setAttribute("errorMessage", "用户名或密码错误！");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch (Exception ex) {
				System.out.println("ex : " + ex.getMessage());
			}
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();

	}
}
