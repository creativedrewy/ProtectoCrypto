import { Injectable } from '@angular/core';
import * as Aes from 'aes-js';
import * as Hash from 'object-hash';
import { sha256 } from 'js-sha256';

@Injectable()
export class ProtectService {

  /**
   * Encrypt the provided data and return as a hex-readable string
   * @param userKey The key string that the user has provided
   * @param text The text to encrypt
   */
  encryptText(userKey: string, text: string): string {
    var engine = this.createEngine(userKey);

    var textBytes = Aes.utils.utf8.toBytes(text);
    var encryptedBytes = engine.encrypt(textBytes);

    return Aes.utils.hex.fromBytes(encryptedBytes);
  }

  /**
   * Decrypt the provided data and return as a hex-readable string
   * @param userKey The key string to use to decrypt
   * @param text The encrypted hex-readable string to decrypt
   */
  decryptText(userKey: string, text: string): string {
    var engine = this.createEngine(userKey);

    var textBytes = Aes.utils.hex.toBytes(text);
    var encryptedBytes = engine.decrypt(textBytes);

    return Aes.utils.utf8.fromBytes(encryptedBytes);
  }

  /**
   * Create the encrypt/decrypt engine based on the user's key
   * @param key the key is sha-256'd for the encryption key and md5'd for the initialization vector
   */
  private createEngine(key: string): Aes.ModeOfOperation {
    var hashResult = sha256.update(key)
    var keyBytes = hashResult.array()

    var initVector = Hash(key, { algorithm: "md5", encoding: "buffer" })

    return new Aes.ModeOfOperation.ofb(keyBytes, initVector);
  }
}
