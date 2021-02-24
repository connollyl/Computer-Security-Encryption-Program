/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jdes;

/**

 @author tianb
 */
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.bouncycastle.util.encoders.*;
 
public class JDES
{    
	public static void main(String[] argv) {
 
		try{
 
			// This generates a DES key based on your key
			String key = "abcdefgh";
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
			DESKeySpec mydeskeyspec = new DESKeySpec(key.getBytes());
			SecretKey myDesKey = keyfactory.generateSecret(mydeskeyspec);
			
			// This automatically generates a DES key
		    //KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		    //SecretKey myDesKey = keygenerator.generateKey();
			
			System.out.println("My Key: " + key);
			System.out.println("DES Key: " + new String(Hex.encode(myDesKey.getEncoded())));
			
		    Cipher desCipher;
 
		    // Create the cipher 
		    desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
 
		    // Initialize the cipher for encryption
		    desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
 
		    //sensitive information
		    byte[] text = "12345678".getBytes();
 
		    System.out.println("Text [Hex Format] : " + new String(Hex.encode(text)) + " length = "+text.length);
		    System.out.println("Text : " + new String(text));
 
		    // Encrypt the text
		    byte[] textEncrypted = desCipher.doFinal(text);
 
		    System.out.println("Text Encryted : " + new String(Hex.encode(textEncrypted)));
 
		    // Initialize the same cipher for decryption
		    desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
 
		    // Decrypt the text
		    byte[] textDecrypted = desCipher.doFinal(textEncrypted);
 
		    System.out.println("Text Decryted : " + new String(textDecrypted));
 
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace();
		}catch(NoSuchPaddingException e){
			e.printStackTrace();
		}catch(InvalidKeyException e){
			e.printStackTrace();
		}catch(IllegalBlockSizeException e){
			e.printStackTrace();
		}catch(BadPaddingException e){
			e.printStackTrace();
		} 
		catch(InvalidKeySpecException e){
			e.printStackTrace();
		}
 
	}
	
	public static byte [] hexStringToByteArray (final String s) {
		if (s == null || (s.length () % 2) == 1)
		  throw new IllegalArgumentException ();
		final char [] chars = s.toCharArray ();
		final int len = chars.length;
		final byte [] data = new byte [len / 2];
		for (int i = 0; i < len; i += 2) {
		  data[i / 2] = (byte) ((Character.digit (chars[i], 16) << 4) + Character.digit (chars[i + 1], 16));
		}
		return data;
  }
	
}
