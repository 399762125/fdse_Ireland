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
import cn.edu.fudan.software.entity.Course;
import cn.edu.fudan.software.util.ServletMethodInvoke;
import cn.edu.fudan.software.util.StringEncoder;
import cn.edu.fudan.software.util.StringUtils;

public class CourseServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String methodName;
	private ServletMethodInvoke servletMethodInvoke = null;

	public CourseServlet() {
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

	public void getCourse(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM course order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		List<Course> courseList = new ArrayList<Course>();

		Course temp = null;

		try {
			while (rs.next()) {
				temp = new Course();
				temp.setId(rs.getInt("id"));
				temp.setNumber(rs.getString("number"));
				temp.setName(rs.getString("name"));
				temp.setIntroduction(rs.getString("introduction"));
				temp.setImageURL(rs.getString("imageURL"));

				courseList.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(courseList);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void insertCourse(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int course_id = Integer.parseInt(request.getParameter("course_id"));
		String course_no = request.getParameter("course_no");
		String course_name = request.getParameter("course_name");
		String course_introduction = StringEncoder.inDatabase(request
				.getParameter("course_introduction"));
		String course_imageURL = request.getParameter("course_imageURL");

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call insertCourse(?,?,?,?,?)}");
			stmt.setInt(1, course_id);
			stmt.setString(2, course_no);
			stmt.setString(3, course_name);
			stmt.setString(4, course_introduction);
			stmt.setString(5, course_imageURL);

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

		String sourcePath = savePath + "/Img/temp/" + course_imageURL;
		String desPath = savePath + "/Img/Course/" + course_imageURL;

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

	public void deleteCourse(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int courseId = Integer.parseInt(request.getParameter("courseId"));
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/Course/";

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM course where id=" + courseId;

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
				stmt = conn.prepareCall("{call deleteCourse(?)}");
				stmt.setInt(1, courseId);

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

	public void insertImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/temp/";

		File f1 = new File(savePath);
		if (!f1.exists()) {

			f1.mkdirs();

		}

		int course_id = 1;
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM course order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		try {
			if (rs.next()) {
				course_id = rs.getInt("id") + 1;
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

				// 生成文件名：
				fileName = course_id + "_" + StringUtils.Md5(name) + ".jpg";
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

		response.setCharacterEncoding("utf-8");

		String result = "" + course_id + ";" + fileName;

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public Course getCourseById(String courseId) {
		int id = Integer.parseInt(courseId);
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM course where id=" + id;

		ResultSet rs = DBConn.executeQuery(conn, sql);

		Course course = null;

		try {
			if (rs.next()) {
				course = new Course();
				course.setId(rs.getInt("id"));
				course.setNumber(rs.getString("number"));
				course.setName(rs.getString("name"));
				course.setIntroduction(rs.getString("introduction"));
				course.setImageURL(rs.getString("imageURL"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		return course;
	}

	public void updateImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String course_imageURL = request.getParameter("course_imageURL");

		String result = "yes";

		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		String fullPath = savePath + "/Img/temp/" + course_imageURL;

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

	public void updateCourse(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "yes";

		int course_id = Integer.parseInt(request.getParameter("course_id"));
		String course_no = request.getParameter("course_no");
		String course_name = request.getParameter("course_name");
		String course_introduction = StringEncoder.inDatabase(request
				.getParameter("course_introduction"));
		String course_imageURL = request.getParameter("course_imageURL");

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call updateCourse(?,?,?,?,?)}");
			stmt.setInt(1, course_id);
			stmt.setString(2, course_no);
			stmt.setString(3, course_name);
			stmt.setString(4, course_introduction);
			stmt.setString(5, course_imageURL);

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

		String sourcePath = savePath + "/Img/temp/" + course_imageURL;
		String desPath = savePath + "/Img/Course/" + course_imageURL;

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
