package test;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import cn.edu.fudan.software.database.DBConn;
import cn.edu.fudan.software.database.DBType;

public class procedureTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection conn = DBConn.getDBConnection(DBType.ireland);
		String sql = "";

		// String
		// name="复旦大学副校长金力、都伯林大学校领导John 'Dunnion先生、爱尔兰驻上海领事馆总领事Conor Riordan先生、复旦大学外事处处长朱畴文、复旦大学软件学院院长臧斌宇教授、都柏林大学教师Henry Mcloughlin、Rem Collier、Fergus Toolan及Neil Hurley等教师出席了毕业典礼。复旦大学副校长金力、都伯林大学校领导John 'Dunnion先生、爱尔兰驻上海领事馆总领事Conor Riordan先生、复旦大学外事处处长朱畴文、复旦大学软件学院院长臧斌宇教授、都柏林大学教师Henry Mcloughlin、Rem Collier、Fergus Toolan及Neil Hurley等教师出席了毕业典礼。复旦大学副校长金力、都伯林大学校领导John 'Dunnion先生、爱尔兰驻上海领事馆总领事Conor Riordan先生、复旦大学外事处处长朱畴文、复旦大学软件学院院长臧斌宇教授、都柏林大学教师Henry Mcloughlin、Rem Collier、Fergus Toolan及Neil Hurley等教师出席了毕业典礼。复旦大学副校长金力、都伯林大学校领导John 'Dunnion先生、爱尔兰驻上海领事馆总领事Conor Riordan先生、复旦大学外事处处长朱畴文、复旦大学软件学院院长臧斌宇教授、都柏林大学教师Henry Mcloughlin、Rem Collier、Fergus Toolan及Neil Hurley等教师出席了毕业典礼。"+"复旦大学副校长金力、都伯林大学校领导John 'Dunnion先生、爱尔兰驻上海领事馆总领事Conor Riordan先生、复旦大学外事处处长朱畴文、复旦大学软件学院院长臧斌宇教授、都柏林大学教师Henry Mcloughlin、Rem Collier、Fergus Toolan及Neil Hurley等教师出席了毕业典礼。复旦大学副校长金力、都伯林大学校领导John 'Dunnion先生、爱尔兰驻上海领事馆总领事Conor Riordan先生、复旦大学外事处处长朱畴文、复旦大学软件学院院长臧斌宇教授、都柏林大学教师Henry Mcloughlin、Rem Collier、Fergus Toolan及Neil Hurley等教师出席了毕业典礼。"+"复旦大学副校长金力、都伯林大学校领导John 'Dunnion先生、爱尔兰驻上海领事馆总领事Conor Riordan先生、复旦大学外事处处长朱畴文、复旦大学软件学院院长臧斌宇教授、都柏林大学教师Henry Mcloughlin、Rem Collier、Fergus Toolan及Neil Hurley等教师出席了毕业典礼。复旦大学副校长金力、都伯林大学校领导John 'Dunnion先生、爱尔兰驻上海领事馆总领事Conor Riordan先生、复旦大学外事处处长朱畴文、复旦大学软件学院院长臧斌宇教授、都柏林大学教师Henry Mcloughlin、Rem Collier、Fergus Toolan及Neil Hurley等教师出席了毕业典礼。";
		// name=name+name+name;
		String name = "2010-2-3";

		String pwd = "2010-2-3";
		CallableStatement stmt = null;
		try {
			stmt = conn.prepareCall("{call insertUser(?,?,?)}");
			// stmt.registerOutParameter(4, Types.INTEGER);
			stmt.setInt(1, 39);
			stmt.setString(2, name);
			stmt.setString(3, pwd);
			int r = stmt.executeUpdate();
			System.out.println(r);
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

}
