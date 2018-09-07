package com.ahmed.utils.checksum;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.charset.UnsupportedCharsetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class Checksum {

	private static final String HEXES = "0123456789ABCEDEF";
	private static final String MD5 = "MD5";
	private static final String SHA1 = "SHA1";
	private static final String SHA256 = "SHA-256";

	public static byte[] getChecksum(String algorithm, File file) throws NoSuchAlgorithmException, IOException {
		final MessageDigest md = MessageDigest.getInstance(algorithm);
		try (FileInputStream fis = new FileInputStream(file); FileChannel ch = fis.getChannel()) {
			final ByteBuffer buf = ByteBuffer.allocateDirect(8192);
			int b = ch.read(buf);
			while (b != -1 && b != 0) {
				buf.flip();
				final byte[] bytes = new byte[b];
				buf.get(bytes);
				md.update(bytes, 0, b);
				buf.clear();
				b = ch.read(buf);
			}
			return md.digest();
		}
	}

	public static String getMD5Checksum(File file) throws IOException, NoSuchAlgorithmException {
		final byte[] b = getChecksum(MD5, file);
		return getHex(b);
	}

	public static String getSHA1Checksum(File file) throws IOException, NoSuchAlgorithmException {
		final byte[] b = getChecksum(SHA1, file);
		return getHex(b);
	}

	public static String getSHA256Checksum(File file) throws IOException, NoSuchAlgorithmException {
		final byte[] b = getChecksum(SHA256, file);
		return getHex(b);
	}

	public static String getChecksum(String algorithm, byte[] bytes) {
		final MessageDigest digest = getMessageDigest(algorithm);
		final byte[] b = digest.digest(bytes);
		return getHex(b);
	}

	public static String getMD5Checksum(String text) {
		final byte[] data = stringToBytes(text);
		return getChecksum(MD5, data);
	}

	public static String getSHA1Checksum(String text) {
		final byte[] data = stringToBytes(text);
		return getChecksum(SHA1, data);
	}

	public static String getSHA256Checksum(String text) {
		final byte[] data = stringToBytes(text);
		return getChecksum(SHA256, data);
	}

	private static byte[] stringToBytes(String text) {
		byte[] data;
		try {
			data = text.getBytes(Charset.forName(StandardCharsets.UTF_8.name()));
		} catch (UnsupportedCharsetException ex) {
			data = text.getBytes(Charset.defaultCharset());
		}
		return data;
	}

	public static String getHex(byte[] raw) {
		if (raw == null) {
			return null;
		}
		final StringBuilder hex = new StringBuilder(2 * raw.length);
		for (final byte b : raw) {
			hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt(b & 0x0F));
		}
		return hex.toString();
	}

	private static MessageDigest getMessageDigest(String algorithm) {
		try {
			return MessageDigest.getInstance(algorithm);
		} catch (NoSuchAlgorithmException e) {
			final String msg = String.format("Failed to obtain the %s message digest.", algorithm);
			throw new IllegalStateException(msg, e);
		}
	}
	
	public static void main(String[] args) {		
		System.out.println(getMD5Checksum("ShabeerAhmed"));
		System.out.println(getSHA1Checksum("ShabeerAhmed"));
		System.out.println(getSHA256Checksum("ShabeerAhmed"));
	}
}