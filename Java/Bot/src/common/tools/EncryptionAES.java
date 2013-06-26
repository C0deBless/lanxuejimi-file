package common.tools;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class EncryptionAES {
	private static final Logger logger = LoggerFactory.getLogger(EncryptionAES.class);
	
	public static String encryptionText( String cipherKey, String plainText ) {
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			generator.init(128, random);
			Key secureKey = new SecretKeySpec( cipherKey.getBytes(), "AES");
			
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secureKey);
			byte[] encryptedData = cipher.doFinal(plainText.getBytes());
			return byteArrayToHex(encryptedData);
			
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("EncryptionAES.encryptionText, " + e.getMessage());
			PrintStackTrace.print(logger, e);
		}
		return null;
	}
	
	public static String decryptionText( String cipherKey, String encryptedData ) {		
		try {
			KeyGenerator generator = KeyGenerator.getInstance("AES");
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			generator.init(128, random);
			
			Key secureKey = new SecretKeySpec( cipherKey.getBytes(), "AES");			
			
			Cipher cipher = Cipher.getInstance("AES");			
			
			cipher.init(Cipher.DECRYPT_MODE, secureKey);
			byte[] plainText = cipher.doFinal( hexToByteArray(encryptedData) );
			return new String(plainText);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			logger.error("EncryptionAES.decryptionText, " + e.getMessage());
			PrintStackTrace.print(logger, e);
		}
		return null;
	}
	
	public static byte[] hexToByteArray(String hex) {
	    if (hex == null || hex.length() == 0) {
	        return null;
	    }
	 
	    byte[] ba = new byte[hex.length() / 2];
	    for (int i = 0; i < ba.length; i++) {
	        ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
	    }
	    return ba;
	}
	
	public static String byteArrayToHex(byte[] ba) {
	    if (ba == null || ba.length == 0) {
	        return null;
	    }
	 
	    StringBuffer sb = new StringBuffer(ba.length * 2);
	    String hexNumber;
	    for (int x = 0; x < ba.length; x++) {
	        hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
	 
	        sb.append(hexNumber.substring(hexNumber.length() - 2));
	    }
	    return sb.toString();
	} 
}
