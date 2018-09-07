package com.ahmed.utils.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

public class Barcode {
	
	public static void main(String[] args) throws FileNotFoundException, IOException, WriterException, NotFoundException, ChecksumException, FormatException {
		
		write();
		read();
		System.out.println("Done!");
	}
	
	public static void read() throws IOException, NotFoundException, ChecksumException, FormatException {
		InputStream barCodeInputStream = new FileInputStream("qrcode.gif");  
		BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);  
		  
		LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);  
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));  
		Reader reader = new MultiFormatReader();  
		Result result = reader.decode(bitmap);  
		  
		System.out.println("Barcode text is " + result.getText()); 
	}
	
	public static void write() throws FileNotFoundException, IOException, WriterException {
		String text = "9949698786"; // this is the text that we want to encode  
		  
		int width = 400;  
		int height = 300; // change the height and width as per your requirement  
		  
		// (ImageIO.getWriterFormatNames() returns a list of supported formats)  
		String imageFormat = "gif"; // could be "gif", "tiff", "jpeg"   
		  
		BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);  
		MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, new FileOutputStream(new File("qrcode.gif"))); 
	}

}
