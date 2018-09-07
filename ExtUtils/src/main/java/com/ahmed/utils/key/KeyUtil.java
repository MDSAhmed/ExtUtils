package com.ahmed.utils.key;

import java.security.GeneralSecurityException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class KeyUtil {
	
	public static void main(String[] args) throws GeneralSecurityException, DecoderException {
		String key = "908702DF6FD01D07B309D230782F400C".replaceAll(" ", "");;
		       key = "8A896D4C46255E2A1A75200207A7D35E".replaceAll(" ", "");
		System.out.println(getKcvValue(Hex.decodeHex(key)));
//		System.out.println(getKcvValue(key.getBytes()));
	}
	
	private static String getKcvValue(byte[] key) throws GeneralSecurityException {
	    // Add Bouncy Castle Security Provider
	    Security.addProvider(new BouncyCastleProvider());
	    // Construct a Secret Key from the given key
	    SecretKey skey = new SecretKeySpec(key, "DESede");
	    // Instantiate a DESede Cipher
	    Cipher encrypter = Cipher.getInstance("DESede/ECB/NoPadding", "BC");
	    // Initialize the cipher with the key in Encrypt mode
	    encrypter.init(Cipher.ENCRYPT_MODE, skey);
	    // Encrypt an 8-byte null array with the cipher and return the first 6 Hex digits of the result
	    return Hex.encodeHexString(encrypter.doFinal(new byte[8])).substring(0, 6).toUpperCase();
	}
}

