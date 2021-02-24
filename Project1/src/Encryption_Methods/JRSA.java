/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption_Methods;
import java.io.RandomAccessFile;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Calendar;
import org.bouncycastle.util.encoders.*;
import javax.crypto.Cipher;
/**
 *
 * @author toxii
 */
public class JRSA {
     public static void main(String[] argv) throws Exception {
         long millisEnStart = 0;
        long millisEnEnd = 0;
        long millisDeStart = 0;
        long millisDeEnd = 0;
        int start = 0;
    RandomAccessFile infile = new RandomAccessFile(argv[2], "r");
    Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

    byte[] input = new byte[8];
    int next = infile.read();
    while(next != -1)
    {
        for(int i = 0; i < 8; i++) // reading the file 8 bytes at a time
        {
            if (next != -1)
            {
                input[i] = (byte)next;
                next = infile.read();
            }
            else
            {
                i = 8;
            }
        }
    if(start <= 1)
    {
        start++;
    }
    Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
    SecureRandom random = new SecureRandom();
    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

    generator.initialize(1024, random);
    KeyPair pair = generator.generateKeyPair();
    Key pubKey = pair.getPublic();
    Key privKey = pair.getPrivate();
    
    if(start <= 1)
    {
        millisEnStart = Calendar.getInstance().getTimeInMillis();
    }
    cipher.init(Cipher.ENCRYPT_MODE, pubKey, random);
    byte[] cipherText = cipher.doFinal(input);
    if(next == -1)
    {
        millisEnEnd = Calendar.getInstance().getTimeInMillis();
    }
    System.out.println("cipher: " + new String(Hex.encode(cipherText)));
    if(start <= 1)
    {
        millisDeStart = Calendar.getInstance().getTimeInMillis();
    }
    cipher.init(Cipher.DECRYPT_MODE, privKey);
    byte[] plainText = cipher.doFinal(cipherText);
    if(next == -1)
    {
        millisDeEnd = Calendar.getInstance().getTimeInMillis();
    }
    System.out.println("plain : " + new String(plainText));
    }
    long totalEnTime = millisEnEnd - millisEnStart;
    long totalDeTime = millisDeEnd - millisDeStart;
    System.out.println("Time to Encrypt is: " + totalEnTime + " milliseconds.");
    System.out.println("Time to Decrypt is: " + totalDeTime + " milliseconds.");
  }
}
