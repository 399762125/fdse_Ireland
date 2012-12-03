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
import cn.edu.fudan.software.entity.People;
import cn.edu.fudan.software.util.ServletMethodInvoke;
import cn.edu.fudan.software.util.StringUtils;

public class PeopleServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String methodName;
	private ServletMethodInvoke servletMethodInvoke = null;

	public PeopleServlet() {
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

	public void getPeople(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM people order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		List<People> peopleList = new ArrayList<People>();

		People temp = null;

		try {
			while (rs.next()) {
				temp = new People();
				temp.setId(rs.getInt("id"));
				temp.setName(rs.getString("name"));
				temp.setIntroduction(rs.getString("introduction"));
				temp.setImageURL("Img/People/" + rs.getString("imageURL"));

				peopleList.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(peopleList);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void deletePeople(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int peopleId = Integer.parseInt(request.getParameter("peopleId"));
		String savePath = this.getServletConfig().getServletContext()

		.getRealPath("");

		savePath = savePath + "/Img/People/";

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM people where id=" + peopleId;

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
				stmt = conn.prepareCall("{call deletePeople(?)}");
				stmt.setInt(1, peopleId);

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

	public void uploadImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String savePath = this.getServletConfig().getServletContext()

		.getRealPath("");

		savePath = savePath + "/Img/temp/";

		File f1 = new File(savePath);

		if (!f1.exists()) {
			f1.mkdirs();
		}

		int people_id = 1;
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM people order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		try {
			if (rs.next()) {
				people_id = rs.getInt("id") + 1;
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

				fileName = people_id + "_" + StringUtils.Md5(name) + ".jpg";
				fullPath = savePath + fileName;
				// file = new File(savePath + name + extName);
				// file = new File(fullPath);

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

		String result = people_id + ";" + fileName;
		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void insertPeople(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int people_id = Integer.parseInt(request.getParameter("people_id"));
		String people_name = request.getParameter("people_name");
		String people_introduction = request
				.getParameter("people_introduction");
		String people_imageURL = request.getParameter("people_imageURL");

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call insertPeople(?,?,?,?)}");
			stmt.setInt(1, people_id);
			stmt.setString(2, people_name);
			stmt.setString(3, people_introduction);
			stmt.setString(4, people_imageURL);

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

		String sourcePath = savePath + "/Img/temp/" + people_imageURL;
		String desPath = savePath + "/Img/People/" + people_imageURL;

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

	// 编辑任务页面，上传更新的图片
	public void updateImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String people_imageURL = request.getParameter("people_imageURL");

		String result = "yes";

		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		String fullPath = savePath + "/Img/temp/" + people_imageURL;

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

	public void updatePeople(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int people_id = Integer.parseInt(request.getParameter("people_id"));
		String people_name = request.getParameter("people_name");
		String people_introduction = request
				.getParameter("people_introduction");
		String people_imageURL = request.getParameter("people_imageURL");

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call updatePeople(?,?,?,?)}");
			stmt.setInt(1, people_id);
			stmt.setString(2, people_name);
			stmt.setString(3, people_introduction);
			stmt.setString(4, people_imageURL);

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

		String sourcePath = savePath + "/Img/temp/" + people_imageURL;
		String desPath = savePath + "/Img/People/" + people_imageURL;

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

	public People getPeopleById(String id) {
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM people where id=" + id;

		ResultSet rs = DBConn.executeQuery(conn, sql);

		People people = null;

		try {
			if (rs.next()) {
				people = new People();
				people.setId(rs.getInt("id"));
				people.setName(rs.getString("name"));
				people.setIntroduction(rs.getString("introduction"));
				people.setImageURL(rs.getString("imageURL"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		return people;
	}
}
