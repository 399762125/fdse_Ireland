package cn.edu.fudan.software.util;

import java.io.File;

public class FileUtils {
	public static boolean deleteFolder(File dir) {
		File filelist[] = dir.listFiles();
		int listlen = filelist.length;
		for (int i = 0; i < listlen; i++) {
			if (filelist[i].isDirectory()) {
				deleteFolder(filelist[i]);
			} else {
				if (!filelist[i].delete())
					return false;
			}
		}
		if (!dir.delete())
			return false;// 删除当前目录
		else
			return true;
	}
}
