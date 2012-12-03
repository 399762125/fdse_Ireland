package test;

import java.util.Date;

public class dateMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Date date = new Date();
		String time = date.toGMTString();
		System.out.println(time);
		time = time.replace(" ", "").replace(":", "");
		System.out.println(time);
	}

}
