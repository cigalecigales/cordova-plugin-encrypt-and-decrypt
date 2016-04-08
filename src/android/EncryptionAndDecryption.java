package plugin.en.de;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import plugin.en.de.EncryptionAndDescriptionUtil;

public class EncryptionAndDecryption extends CordovaPlugin {

  static final EncryptionAndDescriptionUtil ed = new EncryptionAndDescriptionUtil();

@Override
public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("encryption")) {
                String key = data.getString(0);
                String message = data.getString(1);
                String encryptedMessage = ed.encrypt(key, message);
                callbackContext.success(encryptedMessage);
                return true;
        } else if(action.equals("decryption")) {
                String key = data.getString(0);
                String message = data.getString(1);
                String decryptedMessage = ed.decrypt(key, message);
                callbackContext.success(decryptedMessage);
                return true;
        }else {
                return false;
        }
}
}
