module.exports = {
  // Encrypt messages
  encryption: function(name, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "encryption_and_decryption", "encryption", [key, message]);
  },

  // Decrypt messages
  decryption: function(name, successCallback, errorCallback) {
    cordova.exec(successCallback, errorCallback, "encryption_and_decryption", "decryption", [key, message]);
  }
};
