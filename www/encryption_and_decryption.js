module.exports = {
  // Encrypt messages
  encryption: function(key, message, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "encryption_and_decryption", "encryption", [key, message]);
  },

  // Decrypt messages
  decryption: function(key, message, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "encryption_and_decryption", "decryption", [key, message]);
  }
};
