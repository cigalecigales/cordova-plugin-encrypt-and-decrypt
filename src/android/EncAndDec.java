package plugin.en.de;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class EncAndDec {

	private static final String ZERO = "0";
	private static final int MAX_SIZE = 16;

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
			// Get key's bytes length, and compare to MAX_SIZE.
			int length = MAX_SIZE - key.getBytes("UTF-8").length;

			// If key's bytes length is not MAX_SIZE
			if (length > 0) {
				StringBuffer buf = new StringBuffer(length);
				for (int i = 0; i < length; i++) {
					buf.append(ZERO);
				}
				key = key + buf.toString();
			}else if(length < 0){
				key = new String(key.getBytes("UTF-8"), 0, MAX_SIZE, "UTF-8");
			}

			byte[] byteText = message.getBytes("UTF-8");
			byte[] byteKey = key.getBytes("UTF-8");
			byte[] byteIv = key.getBytes("UTF-8");

			SecretKeySpec enckey = new SecretKeySpec(byteKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(byteIv);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, enckey, iv);

			// Base64 encoding
			encryptedMessage = new String(Base64.getEncoder().encodeToString(cipher.doFinal(byteText)));

			return encryptedMessage;

		} catch (Exception ex) {
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
