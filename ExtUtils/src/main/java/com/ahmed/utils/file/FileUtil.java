package com.ahmed.utils.file;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

public class FileUtil {

	public static void main(String[] args) throws IOException {
		readFile("gradlew.bat");
	}

	public static void readFile(String filePath) throws IOException {
		File file = new File(filePath);
		LineIterator it = FileUtils.lineIterator(file, "UTF-8");
		try {
			while (it.hasNext()) {
				String line = it.nextLine();
				System.out.println(line);
			}
		} finally {
			it.close();
		}
	}

}
