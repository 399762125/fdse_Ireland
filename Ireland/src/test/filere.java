package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import cn.edu.fudan.software.database.DBConn;

public class filere {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String root = "E:/";
		String newName = "";
		FileInputStream ins = null;
		FileOutputStream outs = null;
		String resFile = "";
		String objFile = "";

		resFile = root + "a.jpg";
		objFile = root + "new/" + "4_a.jpg";

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File temp = new File("E:/new/");
		if (temp.exists() && temp.isDirectory()) {
			for (File f : temp.listFiles()) {
				if (f.isFile())
					f.delete();
			}
		}
	}

}
