package cn.edu.fudan.software.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import cn.edu.fudan.software.entity.Album_Photo;
import cn.edu.fudan.software.entity.Intro_Img;
import cn.edu.fudan.software.util.ServletMethodInvoke;
import cn.edu.fudan.software.util.StringEncoder;
import cn.edu.fudan.software.util.StringUtils;

public class IntroductionServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String methodName;
	private ServletMethodInvoke servletMethodInvoke = null;

	public IntroductionServlet() {
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

	public void getIntroduction(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM introduction";

		ResultSet rs = DBConn.executeQuery(conn, sql);
		String introduction = "";

		try {
			while (rs.next()) {
				introduction = rs.getString("introduction");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

		Map map = new HashMap();
		map.put("introduction", introduction);

		response.setCharacterEncoding("utf-8");

		Gson gson = new Gson();
		String json = gson.toJson(map);

		PrintWriter out = response.getWriter();
		out.write(json);
		out.close();
	}

	public void getPhotoList(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM intro_image order by id desc";

		ResultSet rs = DBConn.executeQuery(conn, sql);

		List<Intro_Img> photoList = new ArrayList<Intro_Img>();

		Intro_Img temp = null;

		try {
			while (rs.next()) {
				temp = new Intro_Img();
				temp.setId(rs.getInt("id"));
				temp.setIntroId(rs.getInt("introId"));
				temp
						.setImageURL("Img/Introduction/"
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

	public void updateIntro(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String result = "no";
		String introduction = request.getParameter("introduction");

		introduction = StringEncoder.inDatabase(introduction);

		Connection conn = DBConn.getDBConnection(DBType.ireland);

		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call updateIntroduction(?)}");
			stmt.setString(1, introduction);

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

	public void uploadify(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/Introduction/";
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

				name = StringUtils.Md5(name) + ".PNG";

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

		String result = "yes";
		String[] photolist = request.getParameter("photolist").split(";");
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		CallableStatement stmt = null;
		int dataRes;
		for (int i = 0; i < photolist.length; i++) {

			try {
				stmt = conn.prepareCall("{call insertIntro_image(?,?)}");
				stmt.setInt(1, 1);
				stmt.setString(2, StringUtils.Md5(photolist[i]) + ".PNG");
				dataRes = stmt.executeUpdate();

				if (dataRes <= 0)
					result = "no";
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		try {
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		String savePath = this.getServletConfig().getServletContext()
				.getRealPath("");

		savePath = savePath + "/Img/Introduction/";
		Connection conn = DBConn.getDBConnection(DBType.ireland);

		String sql = "SELECT * FROM intro_image where id=" + photoId;

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

			stmt = conn.prepareCall("{call deleteIntro_image(?)}");
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
