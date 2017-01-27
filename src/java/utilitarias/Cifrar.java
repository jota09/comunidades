/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarias;

/**
 *
 * @author ferney.medina
 */

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class Cifrar {
    public static String setEncryp(String key,String value) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
           Key keySpec=new SecretKeySpec(key.getBytes(StandardCharsets.ISO_8859_1),"AES");
           Cipher cipher=Cipher.getInstance("AES");
           cipher.init(Cipher.ENCRYPT_MODE,keySpec);
           byte[] encrypted=cipher.doFinal(value.getBytes(StandardCharsets.ISO_8859_1));
           String s=new String(encrypted,StandardCharsets.ISO_8859_1);
           return s;
        }
        public static String getEncryp(String key,String value) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
           Key keySpec=new SecretKeySpec(key.getBytes(StandardCharsets.ISO_8859_1),"AES");
           Cipher cipher=Cipher.getInstance("AES");
           cipher.init(Cipher.DECRYPT_MODE, keySpec);
           byte[] decrypted=cipher.doFinal(value.getBytes(StandardCharsets.ISO_8859_1));
           String ss=new String(decrypted);
           return ss;
        }
}
