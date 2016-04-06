package plugin.en.de;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncAndDec {

	//static final String ENCRYPT_IV = "abcdefghijklmnopqrstuvwxyz";

	/**
	 * Encrypt message.
	 *
	 * @param key
	 * @param message
	 * @return encrypted message
	 */
	public static String encrypt(String key, String message) {
		String encryptedMessage = null;
		try {
			byte[] byteText = message.getBytes("UTF-8");
			byte[] byteKey = key.getBytes("UTF-8");
			byte[] byteIv = key.getBytes("UTF-8");

			SecretKeySpec enckey = new SecretKeySpec(byteKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(byteIv);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			cipher.init(Cipher.ENCRYPT_MODE, enckey, iv);

			encryptedMessage = new String(cipher.doFinal(byteText));
			// Base64
			//strResult = Base64.encodeBase64String(byteResult);

			return encryptedMessage;

		}catch(Exception ex){
			return null;
		}
	}

	/**
	 * Decrypt message.
	 *
	 * @param key
	 * @param message
	 * @return decrypted message
	 */
	public static String decrypt(String key, String message) {
		String decryptedMessage = null;

		try {
			// Base64
			//byte[] byteText = Base64.decodeBase64(text);

			byte[] byteKey = key.getBytes("UTF-8");
			byte[] byteIv = key.getBytes("UTF-8");

			SecretKeySpec deckey = new SecretKeySpec(byteKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(byteIv);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			cipher.init(Cipher.DECRYPT_MODE, deckey, iv);

			byte[] byteResult = cipher.doFinal(message.getBytes("UTF-8"));

			// バイト配列を文字列へ変換
			decryptedMessage = new String(byteResult, "UTF-8");

			return decryptedMessage;
		} catch(Exception ex){
			return null;
		}
	}
}
