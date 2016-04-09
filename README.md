# :full_moon_with_face: cordova-plugin-encrypt-and-decrypt :new_moon_with_face:

## Encrypt message and Decrypt message Cordova Plugin

## How to use
### Add plugin to your project.

```bash
$ cordova plugin add https://github.com/cigalecigales/cordova-plugin-encrypt-and-decrypt.git
```


### JavaScript
`key` is encryption or decryption key.<br>
`message` is encryption or decryption target.<br>
You must set same key when you decrypt message.<br>

```js
// encrypt message
en_de.encryption(key, message, success, error);
// decrypt message
en_de.decryption(key, message, success, error);

function success(data){
	alert(data);
}

function error(){
  alert("Error !!!");
}
```
