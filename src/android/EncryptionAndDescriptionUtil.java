package plugin.en.de;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;
import java.io.UnsupportedEncodingException;

public final class EncryptionAndDescriptionUtil {

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
		try {
			key = adjustKeyLength(key);

			byte[] byteText = message.getBytes("UTF-8");
			byte[] byteKey = key.getBytes("UTF-8");
			byte[] byteIv = key.getBytes("UTF-8");

			SecretKeySpec enckey = new SecretKeySpec(byteKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(byteIv);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			cipher.init(Cipher.ENCRYPT_MODE, enckey, iv);

			// Return Base64 encoding message.
			return new String(Base64.encode(cipher.doFinal(byteText), Base64.DEFAULT));

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
		try {
			key = adjustKeyLength(key);

			byte[] byteKey = key.getBytes("UTF-8");
			byte[] byteIv = key.getBytes("UTF-8");

			SecretKeySpec deckey = new SecretKeySpec(byteKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(byteIv);

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, deckey, iv);

			String decryptedMessage = new String(cipher.doFinal(Base64.decode(message.getBytes("UTF-8"), Base64.DEFAULT)));

			// Return Decoded message.
			return escape(decryptedMessage);
		} catch(Exception ex){
			return null;
		}
	}

	/**
	 * Adjust key length.
	 *
	 * @param key
	 * @return adjusted key
	 */
	private static String adjustKeyLength(String key) throws UnsupportedEncodingException {
		// Get key's bytes length, and compare to MAX_SIZE.
		int length = MAX_SIZE - key.getBytes("UTF-8").length;

		// If key's bytes length is not MAX_SIZE
		if (length > 0) {
			StringBuffer buf = new StringBuffer(length);
			for (int i = 0; i < length; i++) {
				buf.append(ZERO);
			}
			key = key + buf.toString();
		} else if (length < 0) {
			key = new String(key.getBytes("UTF-8"), 0, MAX_SIZE, "UTF-8");
		}

		return key;
	}

	/**
	 * Escape text.
	 *
	 * @param text
	 */
	private static String escape(String text){
		text.replaceAll("<", "&lt;");
		text = text.replaceAll(">", "&gt;");
	}
}
