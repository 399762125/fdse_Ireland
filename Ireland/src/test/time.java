package test;

import java.text.DateFormat;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.NEW;

import cn.edu.fudan.software.entity.News;
import cn.edu.fudan.software.util.StringUtils;

import java.security.SecureRandom;
import java.util.Random;

public class time {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Date d = new Date();
		// String s;
		//          
		// /** Date类的格式: Sat Apr 16 13:17:29 CST 2005 */
		// System.out.println(d);
		//          
		// System.out.println("******************************************");
		//       
		// /** getDateInstance() */
		// /** 输出格式: 2005-4-16 */
		// // s = DateFormat.getDateInstance().format(d);
		// System.out.println(d.toString());
		// String t=d.toString().replace(" ", "").replace(":", "");
		// System.out.println(t);

		// String s="我是王永峰";
		// char[] chs = s.toCharArray();
		// for (int i = 0; i < chs.length; i++) {
		// chs[i] = (char) (chs[i] ^ '3');
		// }
		// System.out.println(new String(chs));
		// //
		// String s2="我是王永";
		// char[] chs2 = s.toCharArray();
		// for (int i = 0; i < chs2.length; i++) {
		// chs2[i] = (char) (chs2[i] ^ '0');
		// }
		// System.out.println(chs2.toString());
		//		
		// System.out.println("我是王永峰".toCharArray());
		//        
		// System.out.println("我是王永峰".toCharArray());
		// String s=new String("我是王永峰".toCharArray());
		// System.out.println(s);
		// System.out.println("fsdajlfsad".toCharArray().toString());

		String s = "我是王永峰hahahfadsljfasdfksafjasfadsfasdf发大水交流方式是德国";
		System.out.println(StringUtils.Md5(s));
		System.out.println(StringUtils.Md5(s));

	}

}
