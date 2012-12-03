package cn.edu.fudan.software.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import cn.edu.fudan.software.database.DBConn;
import cn.edu.fudan.software.database.DBType;
import cn.edu.fudan.software.entity.Activity;
import cn.edu.fudan.software.util.ServletMethodInvoke;
import cn.edu.fudan.software.util.StringEncoder;
import cn.edu.fudan.software.util.StringUtils;

public class ActivityServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String methodName;
	private ServletMethodInvoke servletMethodInvoke = null;

	public ActivityServlet() {
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

	public void getActivity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM activity order by id asc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		List<Activity> activityList = new ArrayList<Activity>();

		Activity temp = null;

		try {
			while (rs.next()) {
				temp = new Activity();
				temp.setId(rs.getInt("id"));
				temp.setName(rs.getString("name"));
				temp.setBriefIntroduction(rs.getString("briefIntroduction"));
				temp.setIntroduction(rs.getString("introduction"));
				temp.setImageURL("Img/Activity/" + rs.getString("imageURL"));

				activityList.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		String json = gson.toJson(activityList);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void uploadImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/temp/";

		File f1 = new File(savePath);
		if (!f1.exists()) {
			f1.mkdirs();
		}

		int activity_id = 1;
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM activity order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		try {
			if (rs.next()) {
				activity_id = rs.getInt("id") + 1;
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		DiskFileItemFactory fac = new DiskFileItemFactory();

		ServletFileUpload upload = new ServletFileUpload(fac);

		upload.setHeaderEncoding("utf-8");

		List fileList = null;

		try {

			fileList = upload.parseRequest(request);

		} catch (FileUploadException ex) {

			return;

		}
		Iterator<FileItem> it = fileList.iterator();

		String name = "";

		String extName = "";

		String fileName = "";

		while (it.hasNext()) {

			FileItem item = it.next();

			if (!item.isFormField()) {

				name = item.getName();

				long size = item.getSize();

				String type = item.getContentType();
				if (name == null || name.trim().equals("")) {

					continue;

				}

				// 扩展名格式：

				if (name.lastIndexOf(".") >= 0) {

					extName = name.substring(name.lastIndexOf("."));

				}

				File file = null;

				String fullPath;

				fileName = "" + activity_id + "_" + StringUtils.Md5(name)
						+ ".jpg";
				fullPath = savePath + fileName;
				// file = new File(savePath + name + extName);
				file = new File(fullPath);

				File saveFile = new File(fullPath);
				if (saveFile.isFile() && saveFile.exists())
					saveFile.delete();

				try {

					item.write(saveFile);

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		}

		String result = "" + activity_id + ";" + fileName;

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();

	}

	public void insertActivity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int activity_id = Integer.parseInt(request.getParameter("activity_id"));
		String activity_name = request.getParameter("activity_name");
		String activity_briefIntroduction = StringEncoder.inDatabase(request
				.getParameter("activity_briefIntroduction"));
		String activity_introduction = StringEncoder.inDatabase(request
				.getParameter("activity_introduction"));
		String activity_imageURL = request.getParameter("activity_imageURL");

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call insertActivity(?,?,?,?,?)}");
			stmt.setInt(1, activity_id);
			stmt.setString(2, activity_name);
			stmt.setString(3, activity_briefIntroduction);
			stmt.setString(4, activity_introduction);
			stmt.setString(5, activity_imageURL);

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

		// 将Img/temp中的图片copy过来
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		String sourcePath = savePath + "/Img/temp/" + activity_imageURL;
		String desPath = savePath + "/Img/Activity/" + activity_imageURL;

		File sourceFile = new File(sourcePath);
		File desFile = new File(desPath);

		if (sourceFile.exists() && sourceFile.isFile()) {
			FileInputStream in = new FileInputStream(sourceFile);
			byte[] fileByte = new byte[in.available()];
			in.read(fileByte);
			in.close();

			FileOutputStream out = new FileOutputStream(desFile);
			out.write(fileByte);
			out.close();

			sourceFile.delete();
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void deleteActivity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int activityId = Integer.parseInt(request.getParameter("activityId"));
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/Activity/";

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM activity where id=" + activityId;

		ResultSet rs = DBConn.executeQuery(conn, sql);
		try {
			if (rs.next()) {
				savePath = savePath + rs.getString("imageURL");
				File f = new File(savePath);
				if (f.exists() && f.isFile())
					f.delete();
			}

			CallableStatement stmt = null;
			try {
				stmt = conn.prepareCall("{call deleteActivity(?)}");
				stmt.setInt(1, activityId);

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

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public Activity getActivityById(String activityId) {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM activity where id=" + activityId;

		ResultSet rs = DBConn.executeQuery(conn, sql);

		Activity activity = null;

		try {
			if (rs.next()) {
				activity = new Activity();
				activity.setId(rs.getInt("id"));
				activity.setName(rs.getString("name"));
				activity
						.setBriefIntroduction(rs.getString("briefIntroduction"));
				activity.setIntroduction(rs.getString("introduction"));
				activity.setImageURL(rs.getString("imageURL"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		return activity;
	}

	// 编辑任务页面，上传更新的图片
	public void updateImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String activity_imageURL = request.getParameter("activity_imageURL");

		String result = "yes";

		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		String fullPath = savePath + "/Img/temp/" + activity_imageURL;

		DiskFileItemFactory fac = new DiskFileItemFactory();

		ServletFileUpload upload = new ServletFileUpload(fac);

		upload.setHeaderEncoding("utf-8");

		List fileList = null;

		try {

			fileList = upload.parseRequest(request);

		} catch (FileUploadException ex) {

			result = "no";
			return;

		}

		Iterator<FileItem> it = fileList.iterator();

		String name = "";

		String extName = "";

		String fileName = "";

		while (it.hasNext()) {

			FileItem item = it.next();

			if (!item.isFormField()) {

				name = item.getName();

				long size = item.getSize();

				String type = item.getContentType();

				if (name == null || name.trim().equals("")) {

					continue;

				}

				File saveFile = new File(fullPath);
				if (saveFile.isFile() && saveFile.exists()) {
					saveFile.delete();
				}
				try {

					item.write(saveFile);

				} catch (Exception e) {
					result = "no";
					e.printStackTrace();

				}

			}

		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void updateActivity(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int activity_id = Integer.parseInt(request.getParameter("activity_id"));
		String activity_name = request.getParameter("activity_name");
		String activity_briefIntroduction = StringEncoder.inDatabase(request
				.getParameter("activity_briefIntroduction"));
		String activity_introduction = StringEncoder.inDatabase(request
				.getParameter("activity_introduction"));
		String activity_imageURL = request.getParameter("activity_imageURL");

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call updateActivity(?,?,?,?,?)}");
			stmt.setInt(1, activity_id);
			stmt.setString(2, activity_name);
			stmt.setString(3, activity_briefIntroduction);
			stmt.setString(4, activity_introduction);
			stmt.setString(5, activity_imageURL);

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

		// 将Img/temp中的图片copy过来
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		String sourcePath = savePath + "/Img/temp/" + activity_imageURL;
		String desPath = savePath + "/Img/Activity/" + activity_imageURL;

		File sourceFile = new File(sourcePath);
		File desFile = new File(desPath);

		if (sourceFile.exists() && sourceFile.isFile()) {
			FileInputStream in = new FileInputStream(sourceFile);
			byte[] fileByte = new byte[in.available()];
			in.read(fileByte);
			in.close();

			FileOutputStream out = new FileOutputStream(desFile);
			out.write(fileByte);
			out.close();

			sourceFile.delete();
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}
}
