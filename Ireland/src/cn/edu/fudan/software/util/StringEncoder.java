package cn.edu.fudan.software.util;

public class StringEncoder {

	// 把从textarea中字符串转成包含<br>的存进数据库
	public static String inDatabase(String str) {

		// 下面的代码将字符串以正确方式显示（包括回车，换行，空格）
		while (str.indexOf("<") != -1) {

			str = str.substring(0, str.indexOf("<")) + "&lt;"
					+ str.substring(str.indexOf("<") + 1);

		}

		while (str.indexOf(">") != -1) {

			str = str.substring(0, str.indexOf(">")) + "&gt;"
					+ str.substring(str.indexOf(">") + 1);

		}

		while (str.indexOf("\n") != -1) {

			str = str.substring(0, str.indexOf("\n")) + "<br>"
					+ str.substring(str.indexOf("\n") + 1);

		}

		while (str.indexOf(" ") != -1) {

			str = str.substring(0, str.indexOf(" ")) + "&nbsp;"
					+ str.substring(str.indexOf(" ") + 1);

		}

		return str;

	}

	// 和第一步相反，该函数是将第一步中包含<br>的字符串转成\n的形式
	public static String inTextarea(String str) {

		// 下面的代码将字符串以正确方式显示（包括回车，换行，空格）

		while (str.indexOf("<br>") != -1) {

			str = str.substring(0, str.indexOf("<br>")) + "\n"
					+ str.substring(str.indexOf("<br>") + 4);

		}

		while (str.indexOf("&nbsp;") != -1) {

			str = str.substring(0, str.indexOf("&nbsp;")) + " "
					+ str.substring(str.indexOf("&nbsp;") + 6);

		}

		while (str.indexOf("&lt;") != -1) {

			str = str.substring(0, str.indexOf("&lt;")) + "<"
					+ str.substring(str.indexOf("&lt;") + 4);

		}

		while (str.indexOf("&gt;") != -1) {

			str = str.substring(0, str.indexOf("&gt;")) + ">"
					+ str.substring(str.indexOf("&gt;") + 4);

		}
		return str;

	}

	// 和inDatabase函数类似，将包含\r的转成<br>
	public static String inJsp(String str) {

		// 下面的代码将字符串以正确方式显示（包括回车，换行，空格）

		while (str.indexOf("\r") != -1) {

			str = str.substring(0, str.indexOf("\r")) + ""
					+ str.substring(str.indexOf("\r") + 1);

		}
		while (str.indexOf("\"") != -1) {

			str = str.substring(0, str.indexOf("\"")) + "&quot;"
					+ str.substring(str.indexOf("\"") + 1);

		}
		return str;

	}
}
