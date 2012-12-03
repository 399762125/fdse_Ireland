package cn.edu.fudan.software.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.fudan.software.database.DBConn;
import cn.edu.fudan.software.database.DBType;
import cn.edu.fudan.software.entity.News;

import com.google.gson.Gson;

public class AnnouncementServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AnnouncementServlet() {
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

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM news order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);

		News latestNews = null;

		try {
			if (rs.next()) {
				latestNews = new News();
				latestNews.setId(rs.getInt("id"));
				latestNews.setTitle(rs.getString("title"));
				latestNews.setDate(rs.getString("date"));
				latestNews.setContent(rs.getString("content"));
				latestNews.setImageURL("Img/News/" + rs.getString("imageURL"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(latestNews);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}
}
