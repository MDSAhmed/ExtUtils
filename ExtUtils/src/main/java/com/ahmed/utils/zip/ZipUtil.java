package com.ahmed.utils.zip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    public static void zipFile(File input, File output) throws IOException {
        FileInputStream in = null;
        ZipOutputStream out = null;
        try {
            in = new FileInputStream(input);
            out = new ZipOutputStream(new FileOutputStream(output));
            out.putNextEntry(new ZipEntry(input.getName()));
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
            out.closeEntry();
            out.finish();
        } finally{
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
    
    @SuppressWarnings("static-access")
	public static void main(String[] args) throws IOException {
    	ZipUtil zipUtil = new ZipUtil();
    	zipUtil.zipFile(new File("sample-in.txt"), new File("sample-out.zip"));
	}
}