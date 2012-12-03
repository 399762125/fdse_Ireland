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

import cn.edu.fudan.software.database.DBConn;
import cn.edu.fudan.software.database.DBType;
import cn.edu.fudan.software.entity.News;
import cn.edu.fudan.software.util.ServletMethodInvoke;
import cn.edu.fudan.software.util.StringEncoder;
import cn.edu.fudan.software.util.StringUtils;

import com.google.gson.Gson;

public class NewsServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String methodName;
	private ServletMethodInvoke servletMethodInvoke = null;

	public NewsServlet() {
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

	public void getNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM news order by date desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		List<News> newsList = new ArrayList<News>();

		News temp = null;

		try {
			while (rs.next()) {
				temp = new News();
				temp.setId(rs.getInt("id"));
				temp.setTitle(rs.getString("title"));
				temp.setDate(rs.getString("date"));
				temp.setContent(rs.getString("content"));
				temp.setImageURL("Img/News/" + rs.getString("imageURL"));

				newsList.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(newsList);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void deleteNews(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int newId = Integer.parseInt(request.getParameter("newId"));
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/News/";

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM news where id=" + newId;

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
				stmt = conn.prepareCall("{call deleteNews(?)}");
				stmt.setInt(1, newId);

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

		int news_id = 1;
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM news order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		try {
			if (rs.next()) {
				news_id = rs.getInt("id") + 1;
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

				fileName = news_id + "_" + StringUtils.Md5(name) + ".jpg";
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

		String result = "" + news_id + ";" + fileName;

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void insertNews(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int news_id = Integer.parseInt(request.getParameter("news_id"));
		String news_title = request.getParameter("news_title");
		String news_date = request.getParameter("news_date");
		String news_content = StringEncoder.inDatabase(request
				.getParameter("news_content"));
		String news_imageURL = request.getParameter("news_imageURL");

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call insertNews(?,?,?,?,?)}");
			stmt.setInt(1, news_id);
			stmt.setString(2, news_title);
			stmt.setString(3, news_date);
			stmt.setString(4, news_content);
			stmt.setString(5, news_imageURL);

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

		String sourcePath = savePath + "/Img/temp/" + news_imageURL;
		String desPath = savePath + "/Img/News/" + news_imageURL;

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

	public News getNewsById(String newsId) {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM news where id=" + newsId;
		ResultSet rs = DBConn.executeQuery(conn, sql);

		News n = null;

		try {
			while (rs.next()) {
				n = new News();
				n.setId(rs.getInt("id"));
				n.setTitle(rs.getString("title"));
				n.setDate(rs.getString("date"));
				n.setContent(rs.getString("content"));
				n.setImageURL(rs.getString("imageURL"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		return n;
	}

	// 编辑任务页面，上传更新的图片
	public void updateImage(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String news_imageURL = request.getParameter("news_imageURL");

		String result = "yes";

		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		String fullPath = savePath + "/Img/temp/" + news_imageURL;

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

	public void updateNews(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int news_id = Integer.parseInt(request.getParameter("news_id"));
		String news_title = request.getParameter("news_title");
		String news_date = request.getParameter("news_date");
		String news_content = StringEncoder.inDatabase(request
				.getParameter("news_content"));
		String news_imageURL = request.getParameter("news_imageURL");

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call updateNews(?,?,?,?,?)}");
			stmt.setInt(1, news_id);
			stmt.setString(2, news_title);
			stmt.setString(3, news_date);
			stmt.setString(4, news_content);
			stmt.setString(5, news_imageURL);

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

		String sourcePath = savePath + "/Img/temp/" + news_imageURL;
		String desPath = savePath + "/Img/News/" + news_imageURL;

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
