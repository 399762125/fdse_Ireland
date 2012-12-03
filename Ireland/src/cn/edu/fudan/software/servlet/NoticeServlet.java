package cn.edu.fudan.software.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.fudan.software.database.DBConn;
import cn.edu.fudan.software.database.DBType;
import cn.edu.fudan.software.entity.Notice;
import cn.edu.fudan.software.entity.NoticeRoller;
import cn.edu.fudan.software.util.ServletMethodInvoke;
import cn.edu.fudan.software.util.StringEncoder;

import com.google.gson.Gson;

public class NoticeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String methodName;
	private ServletMethodInvoke servletMethodInvoke = null;

	public NoticeServlet() {
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

		methodName = request.getParameter("method");

		if (methodName != null && !methodName.equals("")) {
			servletMethodInvoke = new ServletMethodInvoke(this, methodName);
			servletMethodInvoke.invoke(request, response);
		}
	}

	public void getNotice(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM notice order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		List<Notice> noticeList = new ArrayList<Notice>();

		Notice temp = null;

		try {
			while (rs.next()) {
				temp = new Notice();
				temp.setId(rs.getInt("id"));
				temp.setNotice(rs.getString("notice"));
				temp.setDate(rs.getString("date"));

				noticeList.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		List<NoticeRoller> noticeRollerList = new ArrayList<NoticeRoller>();

		NoticeRoller rollerTemp = null;
		for (int i = 0; i < noticeList.size(); i++) {

			rollerTemp = new NoticeRoller();
			rollerTemp.setTitle(noticeList.get(i).getDate());
			rollerTemp.setHtml("[" + noticeList.get(i).getDate() + "]"
					+ noticeList.get(i).getNotice());

			noticeRollerList.add(rollerTemp);
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(noticeRollerList);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void getNoticeEntity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM notice order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		List<Notice> noticeList = new ArrayList<Notice>();

		Notice temp = null;

		try {
			while (rs.next()) {
				temp = new Notice();
				temp.setId(rs.getInt("id"));
				temp.setNotice(rs.getString("notice"));
				temp.setDate(rs.getString("date"));

				noticeList.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		List<NoticeRoller> noticeRollerList = new ArrayList<NoticeRoller>();

		NoticeRoller rollerTemp = null;
		for (int i = 0; i < noticeList.size(); i++) {

			rollerTemp = new NoticeRoller();
			rollerTemp.setTitle(noticeList.get(i).getDate());
			rollerTemp.setHtml("[" + noticeList.get(i).getDate() + "]"
					+ noticeList.get(i).getNotice());

			noticeRollerList.add(rollerTemp);
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(noticeList);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public Notice getNoticeById(String noticeId) {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM notice where id=" + noticeId;

		ResultSet rs = DBConn.executeQuery(conn, sql);

		Notice notice = null;

		try {
			if (rs.next()) {
				notice = new Notice();
				notice.setId(rs.getInt("id"));
				notice.setNotice(rs.getString("notice"));
				notice.setDate(rs.getString("date"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		return notice;
	}

	public void insertNotice(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";
		String notice_date = request.getParameter("notice_date");

		String notice = StringEncoder
				.inDatabase(request.getParameter("notice"));

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call insertNotice(?,?)}");
			stmt.setString(1, notice);
			stmt.setString(2, notice_date);

			int dataRes = stmt.executeUpdate();

			if (dataRes > 0)
				result = "yes";
			else
				result = "no";
		} catch (SQLException e) {
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

	public void updateNotice(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		String notice_id = request.getParameter("notice_id");
		String notice_content = StringEncoder.inDatabase(request
				.getParameter("notice_content"));
		String notice_date = request.getParameter("notice_date");

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call updateNotice(?,?,?)}");
			stmt.setInt(1, Integer.parseInt(notice_id));
			stmt.setString(2, notice_content);
			stmt.setString(3, notice_date);

			int dataRes = stmt.executeUpdate();

			if (dataRes > 0)
				result = "yes";
			else
				result = "no";
		} catch (SQLException e) {
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

	public void deleteNotice(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int noticeId = Integer.parseInt(request.getParameter("noticeId"));

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call deleteNotice(?)}");
			stmt.setInt(1, noticeId);

			int dataRes = stmt.executeUpdate();

			if (dataRes > 0)
				result = "yes";
			else
				result = "no";
		} catch (SQLException e) {
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
