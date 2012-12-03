package cn.edu.fudan.software.util;

import java.security.MessageDigest;
import java.util.Date;

public class StringUtils {

	public static String Md5(String plainText) {

		Date date = new Date();
		String time = date.toGMTString().replace(" ", "").replace(":", "")
				.substring(5, 13);

		try {

			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(plainText.getBytes());

			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");

			for (int offset = 0; offset < b.length; offset++) {

				i = b[offset];

				if (i < 0)
					i += 256;

				if (i < 16)

					buf.append("0");

				buf.append(Integer.toHexString(i));

			}
			return buf.toString().substring(8, 24) + time;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
