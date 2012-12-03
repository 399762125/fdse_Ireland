package test;

import java.io.File;

import cn.edu.fudan.software.util.FileUtils;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// String fileName="1_"+ StringUtils.Md5("2.jpg")+".jpg";
		// System.out.println(fileName);

		FileUtils.deleteFolder(new File("E:/aa/"));
	}

}
