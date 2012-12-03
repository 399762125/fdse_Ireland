package cn.edu.fudan.software.database;

import java.sql.*;

import cn.edu.fudan.software.config.PropertityUtil;

/**
 * This class is used to connect MySql, and execute SQL By wangyongfeng
 */

public class DBConn {

	private static String ireland = "jdbc:mysql://"
			+ PropertityUtil.getPropertity("mysql_db")
			+ "?useUnicode=true&characterEncoding=UTF-8";
	private static String userName = PropertityUtil
			.getPropertity("db_userName");
	private static String password = PropertityUtil
			.getPropertity("db_passWord");

	public DBConn() {

	}

	public static Connection getDBConnection(int DataBaseType) {
		Connection conn = null;
		String url;

		if (DataBaseType == DBType.ireland)
			url = ireland;
		else
			return conn;

		try {
			Class.forName("org.gjt.mm.mysql.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void closeDBConnection(Connection conn) {
		try {
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static ResultSet executeQuery(Connection conn, String sqlQuery) {
		ResultSet rs = null;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlQuery);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rs;

	}

	public static int executeUpdate(Connection conn, String sqlUpdate) {
		Statement stmt = null;
		int resultAffected = -1;
		try {
			stmt = conn.createStatement();
			resultAffected = stmt.executeUpdate(sqlUpdate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultAffected;
	}

	public static void rollBack(Connection conn) {
		try {
			conn.rollback();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws SQLException {
		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "SELECT * FROM user";
		ResultSet rs = DBConn.executeQuery(conn, sql);

		try {
			while (rs.next()) {
				String detail = rs.getString("username");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConn.closeDBConnection(conn);
		}

	}
}