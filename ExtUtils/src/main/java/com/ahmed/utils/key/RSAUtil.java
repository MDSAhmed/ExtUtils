package com.ahmed.utils.key;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSAUtil {

	public static KeyPair generateKeyPair() throws Exception {
		
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(2048, new SecureRandom());
		KeyPair pair = generator.generateKeyPair();
		return pair;
	}

	public static KeyPair getKeyPairFromKeyStore(String jksPath, String storepass, String keypass, String alias) throws Exception {
		
		// Generated with:
		// keytool -genkeypair -alias mykey -storepass s3cr3t -keypass s3cr3t -keyalg
		// RSA -keystore keystore.jks

		InputStream ins = RSAUtil.class.getResourceAsStream(jksPath);
//		InputStream ins = new FileInputStream(new File(jksPath));

		KeyStore keyStore = KeyStore.getInstance("JCEKS");
		keyStore.load(ins, storepass.toCharArray()); // Keystore password
		KeyStore.PasswordProtection keyPassword = // Key password
				new KeyStore.PasswordProtection(keypass.toCharArray());

		KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry) keyStore.getEntry(alias, keyPassword);

		java.security.cert.Certificate cert = keyStore.getCertificate(alias);
		PublicKey publicKey = cert.getPublicKey();
		PrivateKey privateKey = privateKeyEntry.getPrivateKey();

		return new KeyPair(publicKey, privateKey);
	}

	public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
		Cipher encryptCipher = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKey);

		byte[] cipherText = encryptCipher.doFinal(plainText.getBytes(UTF_8));

		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
		byte[] bytes = Base64.getDecoder().decode(cipherText);

		Cipher decriptCipher = Cipher.getInstance("RSA");
		decriptCipher.init(Cipher.DECRYPT_MODE, privateKey);

		return new String(decriptCipher.doFinal(bytes), UTF_8);
	}

	public static String sign(String plainText, PrivateKey privateKey) throws Exception {
		Signature privateSignature = Signature.getInstance("SHA256withRSA");
		privateSignature.initSign(privateKey);
		privateSignature.update(plainText.getBytes(UTF_8));

		byte[] signature = privateSignature.sign();

		return Base64.getEncoder().encodeToString(signature);
	}

	public static boolean verify(String plainText, String signature, PublicKey publicKey) throws Exception {
		Signature publicSignature = Signature.getInstance("SHA256withRSA");
		publicSignature.initVerify(publicKey);
		publicSignature.update(plainText.getBytes(UTF_8));

		byte[] signatureBytes = Base64.getDecoder().decode(signature);

		return publicSignature.verify(signatureBytes);
	}

	public static void main(String... argv) throws Exception {
		
		String jksPath = "/keystore.jks";
		String storepass = "s3cr3t";
		String keypass = "s3cr3t";
		String alias = "mykey";
		
		// First generate a public/private key pair
//		KeyPair pair = generateKeyPair();
		KeyPair pair = getKeyPairFromKeyStore(jksPath, storepass, keypass, alias);
		// KeyPair pair = getKeyPairFromKeyStore();

		// Our secret message
		String message = "This is our secret message";

		// Encrypt the message
		String cipherText = encrypt(message, pair.getPublic());

		// Now decrypt it
		String decipheredMessage = decrypt(cipherText, pair.getPrivate());

		System.out.println(decipheredMessage);

		// Let's sign our message
		String signature = sign("Shabeer Ahmed", pair.getPrivate());

		// Let's check the signature
		boolean isCorrect = verify("Shabeer Ahmed", signature, pair.getPublic());
		System.out.println("Signature correct: " + isCorrect);
	}
}
