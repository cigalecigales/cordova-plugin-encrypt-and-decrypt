package plugin.en.de;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import android.util.Base64;
import java.io.UnsupportedEncodingException;

public final class EncryptionAndDescriptionUtil {

	private static final int MAX_SIZE = 16;
	private static final String ENCODE_TYPE = "UTF-8";
	private static final String ENCRYPT_TYPE = "AES";
	private static final String ALGORITHM_NAME = "AES/CBC/PKCS5Padding";

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

			byte[] byteText = message.getBytes(ENCODE_TYPE);
			byte[] byteKey = key.getBytes(ENCODE_TYPE);
			byte[] byteIv = key.getBytes(ENCODE_TYPE);

			SecretKeySpec enckey = new SecretKeySpec(byteKey, ENCRYPT_TYPE);
			IvParameterSpec iv = new IvParameterSpec(byteIv);

			Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);

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

			byte[] byteKey = key.getBytes(ENCODE_TYPE);
			byte[] byteIv = key.getBytes(ENCODE_TYPE);

			SecretKeySpec deckey = new SecretKeySpec(byteKey, ENCRYPT_TYPE);
			IvParameterSpec iv = new IvParameterSpec(byteIv);

			Cipher cipher = Cipher.getInstance(ALGORITHM_NAME);
			cipher.init(Cipher.DECRYPT_MODE, deckey, iv);

			String decryptedMessage = new String(cipher.doFinal(Base64.decode(message.getBytes(ENCODE_TYPE), Base64.DEFAULT)));

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
		int length = MAX_SIZE - key.getBytes(ENCODE_TYPE).length;

		// If key's bytes length is not MAX_SIZE
		if (length > 0) {
			StringBuffer buf = new StringBuffer(length);
			for (int i = 0; i < length; i++) {
				buf.append(String.valueOf(i));
			}
			key = key + buf.toString();
		} else if (length < 0) {
			key = new String(key.getBytes(ENCODE_TYPE), 0, MAX_SIZE, ENCODE_TYPE);
		}

		return key;
	}

	/**
	 * Escape text.
	 *
	 * @param text
	 * @return escaped text
	 */
	private static String escape(String text){
		text = text.replaceAll("<", "&lt;");
		text = text.replaceAll(">", "&gt;");
		return text;
	}
}
