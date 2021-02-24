/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption_Methods;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.Scanner;
 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import org.bouncycastle.util.encoders.*;
/**
 *
 * @author toxii
 */
public class JDES {
    public static void main(String[] argv) {
        long millisEnStart = 0;
        long millisEnEnd = 0;
        long millisDeStart = 0;
        long millisDeEnd = 0;
        int start = 0;
	try{
        // Set up input file, output file 
	RandomAccessFile infile = new RandomAccessFile(argv[2], "r");
        RandomAccessFile outfile = new RandomAccessFile(argv[3],"rw");
        //Scanner scnr = new Scanner(infile);
         
        //Get command line arguments
        byte[] iv = hexStringToByteArray(argv[0]);
        byte[] key = hexStringToByteArray(argv[1]);
		
		// This generate a DES key based on your key
		//String key = "abcdefgh";
	SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DES");
		//DESKeySpec mydeskeyspec = new DESKeySpec(key.getBytes());
        DESKeySpec mydeskeyspec = new DESKeySpec(key);
	SecretKey myDesKey = keyfactory.generateSecret(mydeskeyspec);
			
		// This automatically generate a DES key
		//KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
		//SecretKey myDesKey = keygenerator.generateKey();
			
	System.out.println("My IV: " + new String(Hex.encode(iv)));
	System.out.println("DES Key: " + new String(Hex.encode(myDesKey.getEncoded())));
			
		// Create a cipher instance
	Cipher desCipher;
 
		// Create the cipher 
	desCipher = Cipher.getInstance("DES/ECB/NOPadding");//NoPadding//PKCS5Padding

		// Create a byte array of 8 for encryption
         byte[] text = new byte[8];
          
        // Create a byte array of 8 for encryption in CBC mode
	byte[] cbcchain = new byte[8];
		// initialize it with IV
	cbcchain = iv;   
        byte[] decryptchain = new byte[8];
        decryptchain = iv;
		// While there are more bytes in the input file
        int next = infile.read();
        
        while(next != -1)
        {
            for (int i = 0; i < 8; i++) // reading the file 8 bytes at a time
            {
                if(next != -1)
                {
                    text[i] = (byte)next;
                    next = infile.read();
                }
                else
                {
                    i = 8;
                }
            }
            // Initialize the cipher for encryption
            if(start <= 1)
            {
                start++;
            }
            if(start <= 1)
            {
                millisEnStart = Calendar.getInstance().getTimeInMillis();
            }
            desCipher.init(Cipher.ENCRYPT_MODE, myDesKey);
			// XOR according to CBC
            byte[] xor = new byte[8];
            for (int i = 0; i < 8; i++)
            {
                xor[i] = (byte)(text[i] ^ cbcchain[i]);
            }
		    // Encrypt the block
		    byte[] textEncrypted = desCipher.doFinal(xor);
                    if(next == -1)
                    {
                        millisEnEnd = Calendar.getInstance().getTimeInMillis();
                    }
			// Make a copy for next block's chaining
                    cbcchain = textEncrypted.clone();                   
			// Write the cipher text to the output file
                    outfile.writeUTF("Text Encrypted: ");
                    outfile.writeUTF(Hex.toHexString(textEncrypted)); 
                    outfile.writeUTF(" 8 bytes\n");
		    // Initialize the same cipher for decryption
		    desCipher.init(Cipher.DECRYPT_MODE, myDesKey);
		    // Decrypt the block
                    if(start <= 1)
                    {
                        millisDeStart = Calendar.getInstance().getTimeInMillis();
                    }
		    byte[] textDecrypted = desCipher.doFinal(textEncrypted);
 		    // XOR the block according to CBC
                    byte[] decryptxor = new byte[8];
                    for (int i = 0; i < 8; i++)
                    {
                        decryptxor[i] = (byte)(textDecrypted[i] ^ decryptchain[i]);
                    }
                    if(next == -1)
                    {
                        millisDeEnd = Calendar.getInstance().getTimeInMillis();
                    }
                    // Make a copy for next block's chaining
                    decryptchain = textEncrypted;
                    outfile.writeUTF("Text Decrypted: ");
                    outfile.writeUTF(hexStringtoChar(Hex.toHexString(decryptxor))); 
                    outfile.writeUTF("\n");
        } 
        long totalEnTime = millisEnEnd - millisEnStart;
        long totalDeTime = millisDeEnd - millisDeStart;
        System.out.println("Time to Encrypt is: " + totalEnTime + " milliseconds.");
        System.out.println("Time to Decrypt is: " + totalDeTime + " milliseconds.");
        // end of while loop
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
                catch(FileNotFoundException e){
			e.printStackTrace();
                }
                catch(IOException e){
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
        public static String hexStringtoChar(String hexStr) {
            StringBuilder output = new StringBuilder("");
     
            for (int i = 0; i < hexStr.length(); i += 2) {
                String str = hexStr.substring(i, i + 2);
                output.append((char) Integer.parseInt(str, 16));
            }
     
            return output.toString();
        }
 
}
