package cn.edu.fudan.software.config;

public class RootPath {

	private static RootPath r;
	private int headerSize = 6; // 默认情况下是windows ，大小是6

	private RootPath() {
	}

	public static RootPath getInstance() {
		if (r == null)
			r = new RootPath();
		return r;
	}

	// 该函数返回class的绝对路径：%tomcat%/Webapps/HGJService/Web-INFO/classes
	public String getRootPath() {
		String rootPath = this.getClass().getClassLoader().getResource("/")
				.getPath();
		return rootPath;
	}

	// 该函数返回绝对路径：%tomcat%/Webapps/HGJService/Web-INFO
	public String getConfPath() {
		String className = this.getClass().getName();

		String packageName = this.getClass().getPackage().getName();

		String classFileName = className.substring(packageName.length() + 1)
				+ ".class";
		String classFilePath = this.getClass().getResource(classFileName)
				.toString();
		String filePath = classFilePath.substring(0, classFilePath.length()
				- className.length() - 14);

		if (!isWindowsOS())
			headerSize = 5;
		return filePath.substring(headerSize);
	}

	// 该函数返回绝对路径（某个class所在的上级目录的值，包括/）：%tomcat%/Webapps/HGJService/Web-INFO/classes/.../config/
	public String getClassDirectoryPath() {
		String className = this.getClass().getName();
		String packageName = this.getClass().getPackage().getName();
		String classFileName = className.substring(packageName.length() + 1)
				+ ".class";
		String classFilePath = this.getClass().getResource(classFileName)
				.toString();
		String path = classFilePath.substring(0, classFilePath.length()
				- classFileName.length());

		if (!isWindowsOS())
			headerSize = 5;
		return path.substring(headerSize);
	}

	// 判断tomcat是运行在windows上还是Linux上
	public static boolean isWindowsOS() {
		boolean isWindowsOS = false;
		String osName = System.getProperty("os.name");
		if (osName.toLowerCase().indexOf("windows") > -1) {
			isWindowsOS = true;
		}
		return isWindowsOS;
	}

	public String test() {
		String t = this.getClass().getResource("prop.properties").toString();

		if (!isWindowsOS())
			headerSize = 5;
		return t.substring(headerSize);
	}

}
