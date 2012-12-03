package cn.edu.fudan.software.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertityUtil {

	// 读取prop.properties文件，取出对应的属性的值。
	public static String getPropertity(String key) {
		Properties props = new Properties();
		try {

			String path = RootPath.getInstance().getClassDirectoryPath()
					+ "prop.properties";

			BufferedInputStream ips = new BufferedInputStream(
					new FileInputStream(path));
			props.load(ips);
			String value = props.getProperty(key);
			return value;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	// 该函数返回绝对路径（某个class所在的上级目录的值，包括/）：%tomcat%/Webapps/HGJService/Web-INFO/classes/.../config/
	public static String getClassDirectoryPath() {
		return RootPath.getInstance().getClassDirectoryPath();
	}

	// 获取存放第三方用户制品的文件resource的路径。默认放在:%tomcat%/Webapps/HGJService/Web-INFO/resource/
	public static String getResourcePath() {
		String rootPath = RootPath.getInstance().getConfPath();
		String serverPath = rootPath + "resource/";
		return serverPath;
	}

	// 该函数返回绝对路径：%tomcat%/Webapps/HGJService/Web-INFO
	public static String getConfPath() {
		return RootPath.getInstance().getConfPath();
	}

	public static String test() {
		return RootPath.getInstance().test();
	}

}
