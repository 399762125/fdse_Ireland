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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import java.util.List;

import cn.edu.fudan.software.database.DBConn;
import cn.edu.fudan.software.database.DBType;
import cn.edu.fudan.software.entity.Teacher;
import cn.edu.fudan.software.util.ServletMethodInvoke;
import cn.edu.fudan.software.util.StringUtils;

public class TeacherServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String methodName;
	private ServletMethodInvoke servletMethodInvoke = null;

	public TeacherServlet() {
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

	public void getTeacher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM teacher order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		List<Teacher> teacherList = new ArrayList<Teacher>();

		Teacher temp = null;

		try {
			while (rs.next()) {
				temp = new Teacher();
				temp.setId(rs.getInt("id"));
				temp.setName(rs.getString("name"));
				temp.setPosition(rs.getString("position"));
				temp.setOffice(rs.getString("office"));
				temp.setTelephone(rs.getString("telephone"));
				temp.setEmail(rs.getString("email"));
				temp.setResearchArea(rs.getString("researchArea"));
				temp.setImageURL("Img/Teacher/" + rs.getString("imageURL"));

				teacherList.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(teacherList);

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

		int teacher_id = 1;
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM teacher order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		try {
			if (rs.next()) {
				teacher_id = rs.getInt("id") + 1;
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

				fileName = "" + teacher_id + "_" + StringUtils.Md5(name)
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

		String result = teacher_id + ";" + fileName;

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();

	}

	public void insertTeacher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int teacher_id = Integer.parseInt(request.getParameter("teacher_id"));
		String teacher_name = request.getParameter("teacher_name");
		String teacher_position = request.getParameter("teacher_position");
		String teacher_office = request.getParameter("teacher_office");
		String teacher_telephone = request.getParameter("teacher_telephone");
		String teacher_email = request.getParameter("teacher_email");
		String teacher_area = request.getParameter("teacher_area");
		String teacher_imageURL = request.getParameter("teacher_imageURL");

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call insertTeacher(?,?,?,?,?,?,?,?)}");
			stmt.setInt(1, teacher_id);
			stmt.setString(2, teacher_name);
			stmt.setString(3, teacher_position);
			stmt.setString(4, teacher_office);
			stmt.setString(5, teacher_telephone);
			stmt.setString(6, teacher_email);
			stmt.setString(7, teacher_area);
			stmt.setString(8, teacher_imageURL);

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

		String sourcePath = savePath + "/Img/temp/" + teacher_imageURL;
		String desPath = savePath + "/Img/Teacher/" + teacher_imageURL;

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

	public void deleteTeacher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int teacherId = Integer.parseInt(request.getParameter("teacherId"));
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/Teacher/";

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM teacher where id=" + teacherId;

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
				stmt = conn.prepareCall("{call deleteTeacher(?)}");
				stmt.setInt(1, teacherId);

				int dataRes = stmt.executeUpdate();
				if (dataRes > 0)
					result = "yes";

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

	public Teacher getTeacherById(String teacherId) {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM teacher where id=" + teacherId;

		ResultSet rs = DBConn.executeQuery(conn, sql);

		Teacher t = null;

		try {
			while (rs.next()) {
				t = new Teacher();
				t.setId(rs.getInt("id"));
				t.setName(rs.getString("name"));
				t.setPosition(rs.getString("position"));
				t.setOffice(rs.getString("office"));
				t.setTelephone(rs.getString("telephone"));
				t.setEmail(rs.getString("email"));
				t.setResearchArea(rs.getString("researchArea"));
				t.setImageURL(rs.getString("imageURL"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		return t;
	}

	// 编辑任务页面，上传更新的图片
	public void updateImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String teacher_imageURL = request.getParameter("teacher_imageURL");

		String result = "yes";

		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		String fullPath = savePath + "/Img/temp/" + teacher_imageURL;

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

	public void updateTeacher(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int teacher_id = Integer.parseInt(request.getParameter("teacher_id"));
		String teacher_name = request.getParameter("teacher_name");
		String teacher_position = request.getParameter("teacher_position");
		String teacher_office = request.getParameter("teacher_office");
		String teacher_telephone = request.getParameter("teacher_telephone");
		String teacher_email = request.getParameter("teacher_email");
		String teacher_area = request.getParameter("teacher_area");
		String teacher_imageURL = request.getParameter("teacher_imageURL");

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call updateTeacher(?,?,?,?,?,?,?,?)}");
			stmt.setInt(1, teacher_id);
			stmt.setString(2, teacher_name);
			stmt.setString(3, teacher_position);
			stmt.setString(4, teacher_office);
			stmt.setString(5, teacher_telephone);
			stmt.setString(6, teacher_email);
			stmt.setString(7, teacher_area);
			stmt.setString(8, teacher_imageURL);

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

		String sourcePath = savePath + "/Img/temp/" + teacher_imageURL;
		String desPath = savePath + "/Img/Teacher/" + teacher_imageURL;

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
