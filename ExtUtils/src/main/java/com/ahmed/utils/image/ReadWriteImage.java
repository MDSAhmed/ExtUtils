package com.ahmed.utils.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

public class ReadWriteImage {

	public static void main(String[] args) throws IOException {

		String dirName = "./";
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1000);
		BufferedImage img = ImageIO.read(new File(dirName, "ishaq.jpg"));
		ImageIO.write(img, "jpg", baos);
		baos.flush();

		String base64String = Base64.getEncoder().encodeToString(baos.toByteArray());
		baos.close();

		System.out.println("data:image/jpeg;base64," + base64String);

		byte[] bytearray = Base64.getDecoder().decode(base64String);

		BufferedImage imag = ImageIO.read(new ByteArrayInputStream(bytearray));
		ImageIO.write(imag, "jpg", new File(dirName, "snap.jpg"));
	}
}