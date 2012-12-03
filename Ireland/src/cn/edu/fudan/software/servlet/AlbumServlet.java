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
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import cn.edu.fudan.software.entity.Album;
import cn.edu.fudan.software.entity.Album_Photo;
import cn.edu.fudan.software.util.FileUtils;
import cn.edu.fudan.software.util.ServletMethodInvoke;
import cn.edu.fudan.software.util.StringEncoder;
import cn.edu.fudan.software.util.StringUtils;

public class AlbumServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String methodName;
	private ServletMethodInvoke servletMethodInvoke = null;

	public AlbumServlet() {
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

	public void getAlbum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM album order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		List<Album> albumList = new ArrayList<Album>();

		Album temp = null;

		try {
			while (rs.next()) {
				temp = new Album();
				temp.setId(rs.getInt("id"));
				temp.setTitle(rs.getString("title"));
				temp.setIntroduction(rs.getString("introduction"));
				temp.setCreatTime(rs.getString("creatTime"));
				temp.setFirstPageURL(getFirstPageURL(rs.getInt("id")));

				albumList.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(albumList);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	// 仅为上面的方法提供调用
	public String getFirstPageURL(int albumId) {
		String firstPageURL = "";
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM album_photo where albumId=" + albumId
				+ " order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);

		try {
			if (rs.next())
				firstPageURL = rs.getString("imageURL");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DBConn.closeDBConnection(conn);

		return firstPageURL;
	}

	public void getPhotoByAlbum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		int albumId = Integer.parseInt(request.getParameter("albumId"));

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM album_photo where albumId=" + albumId;

		ResultSet rs = DBConn.executeQuery(conn, sql);
		List<Album_Photo> photoList = new ArrayList<Album_Photo>();

		Album_Photo temp = null;

		try {
			while (rs.next()) {
				temp = new Album_Photo();
				temp.setId(rs.getInt("id"));
				temp.setAlbumId(rs.getString("albumId"));
				temp.setIntroduction(getAlbumIntroductionByAlbumId(rs
						.getString("albumId")));
				temp.setImageURL("Img/Album/" + rs.getInt("albumId") + "/"
						+ rs.getString("imageURL"));

				photoList.add(temp);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		String json = gson.toJson(photoList);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	// 此函数仅为上面函数提供调用
	public String getAlbumIntroductionByAlbumId(String AlbumId) {
		String intro = "";
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT introduction FROM album where id="
				+ Integer.parseInt(AlbumId);
		ResultSet rs = DBConn.executeQuery(conn, sql);
		try {
			while (rs.next())
				intro = rs.getString("introduction");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return intro;
	}

	// 新建相册
	public void insertAlbum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		String album_title = request.getParameter("album_title");
		String album_introduction = StringEncoder.inDatabase(request
				.getParameter("album_introduction"));

		Date time = new Date();
		String creatTime = DateFormat.getDateInstance().format(time);

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call insertAlbum(?,?,?)}");
			stmt.setString(1, album_title);
			stmt.setString(2, album_introduction);
			stmt.setString(3, creatTime);

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

	public Album getAlbumById(String albumId) {
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM album where id=" + albumId;

		ResultSet rs = DBConn.executeQuery(conn, sql);

		Album album = null;

		try {
			if (rs.next()) {
				album = new Album();
				album.setId(rs.getInt("id"));
				album.setTitle(rs.getString("title"));
				album.setIntroduction(rs.getString("introduction"));
				album.setCreatTime(rs.getString("creatTime"));
				album.setFirstPageURL(getFirstPageURL(rs.getInt("id")));

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		return album;
	}

	// 修改相册
	public void updateAlbum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int album_id = Integer.parseInt(request.getParameter("album_id"));
		String album_title = request.getParameter("album_title");
		String album_introduction = StringEncoder.inDatabase(request
				.getParameter("album_introduction"));

		Date time = new Date();
		String creatTime = DateFormat.getDateInstance().format(time);

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call updateAlbum(?,?,?,?)}");
			stmt.setInt(1, album_id);
			stmt.setString(2, album_title);
			stmt.setString(3, album_introduction);
			stmt.setString(4, creatTime);

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

	public void deleteAlbum(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";

		int albumId = Integer.parseInt(request.getParameter("albumId"));
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/Album/" + albumId + "/";

		int num = 0;// 记录操作成功的个数
		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call deleteAlbum(?)}");
			stmt.setInt(1, albumId);

			int dataRes = stmt.executeUpdate();

			if (dataRes > 0)
				num++;

			stmt = conn.prepareCall("{call deleteAlbum_Photo(?)}");
			stmt.setInt(1, albumId);

			dataRes = stmt.executeUpdate();

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

		// 删除对应的图片目录
		num++;
		File dir = new File(savePath);
		if (dir.exists() && dir.isDirectory()) {
			if (!FileUtils.deleteFolder(dir))
				num--;
		}

		if (num == 2)
			result = "yes";
		else
			result = "no";

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();

	}

	public void uploadify(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/Album/" + "temp/";
		File f1 = new File(savePath);

		if (!f1.exists()) {

			f1.mkdirs();
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
		int i = 0;

		while (it.hasNext()) {
			i++;
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

				name = StringUtils.Md5(name) + ".jpg";

				File saveFile = new File(savePath + name);
				try {

					item.write(saveFile);

				} catch (Exception e) {

					e.printStackTrace();

				}

			}

		}

		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		String json = gson.toJson(name);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void insertPhoto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "ok";
		int albumId = Integer.parseInt(request.getParameter("albumId"));
		String[] photolist = request.getParameter("photolist").split(";");

		String rootPath = this.getServletConfig().getServletContext()
				.getRealPath("");

		String newPath = rootPath + "/Img/Album/" + albumId + "/";

		String oldPath = rootPath + "/Img/Album/temp/";

		File f1 = new File(newPath);

		if (!f1.exists()) {

			f1.mkdirs();

		}

		FileInputStream ins = null;
		FileOutputStream outs = null;
		String resFile = "";
		String objFile = "";

		int dataRes;
		for (int i = 0; i < photolist.length; i++) {
			String oldName = StringUtils.Md5(photolist[i]) + ".jpg";
			String newName = albumId + "_" + oldName;
			resFile = oldPath + oldName;
			objFile = newPath + newName;

			try {
				ins = new FileInputStream(resFile);
				outs = new FileOutputStream(objFile);
				byte[] buffer = new byte[1024 * 512];
				int length;
				while ((length = ins.read(buffer)) != -1) {
					outs.write(buffer, 0, length);
				}
				ins.close();
				outs.flush();
				outs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}

			Connection conn = DBConn.getDBConnection(DBType.ireland);
			CallableStatement stmt = null;
			try {
				stmt = conn.prepareCall("{call insertAlbum_Photo(?,?,?)}");
				stmt.setInt(1, albumId);
				stmt.setString(2, " ");
				stmt.setString(3, newName);
				dataRes = stmt.executeUpdate();

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
		}

		File temp = new File(oldPath);
		if (temp.exists() && temp.isDirectory()) {
			for (File f : temp.listFiles()) {
				if (f.isFile())
					f.delete();
			}
		}

		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void deletePhoto(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";
		int num = 0;

		int photoId = Integer.parseInt(request.getParameter("photoId"));
		int albumId = Integer.parseInt(request.getParameter("albumId"));

		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/Album/" + albumId + "/";
		Connection conn = DBConn.getDBConnection(DBType.ireland);

		String sql = "SELECT * FROM album_photo where id=" + photoId
				+ " and albumId=" + albumId;

		ResultSet rs = DBConn.executeQuery(conn, sql);

		try {
			if (rs.next()) {
				String imageURL = rs.getString("imageURL");
				savePath = savePath + imageURL;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		CallableStatement stmt = null;
		int dataRes = 0;
		try {

			stmt = conn.prepareCall("{call deletePhotoByPhotoId(?)}");
			stmt.setInt(1, photoId);

			dataRes = stmt.executeUpdate();
			if (dataRes > 0)
				num++;
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

		// 删除对应的图片目录
		num++;
		File f = new File(savePath);
		if (f.exists() && f.isFile())
			if (!f.delete())
				num--;

		if (num == 2)
			result = "yes";
		else
			result = "no";

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(result);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();

	}
}
