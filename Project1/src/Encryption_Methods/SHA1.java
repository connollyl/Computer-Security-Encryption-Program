/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Encryption_Methods;
import java.io.RandomAccessFile;
import java.security.Security;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.*;
import java.security.MessageDigest;
import java.util.Calendar;
/**
 *
 * @author toxii
 */
public class SHA1 {
    public static void main(String[] argv) throws Exception {
    long millisStart;
    long millisEnd;
    RandomAccessFile infile = new RandomAccessFile(argv[2], "r");
    Security.addProvider(new BouncyCastleProvider());        
    byte[] input = new byte[(int)infile.length()];
    int next = infile.read();
    for(int i = 0; i < (int)infile.length(); i++)
    {
        if(next != -1)
        {
            input[i] = (byte)next;
            next = infile.read();
        }
        else
        {
            i = (int)infile.length();
        }
    }

      try
      {
            //prepare the input
            millisStart = Calendar.getInstance().getTimeInMillis();
            MessageDigest hash =
               MessageDigest.getInstance("SHA-1", "BC");
            hash.update(input);

            //proceed ....
            
            byte[] digest = hash.digest();
            millisEnd = Calendar.getInstance().getTimeInMillis();

            //show us the result
            System.out.println("input: " +
                   new String(Hex.encode(input)));
            System.out.println("result: " +
                   new String(Hex.encode(digest)));
            long totalTime = millisEnd - millisStart;
            System.out.println("Time to hash is: " + totalTime + " milliseconds.");
      }
      catch (NoSuchAlgorithmException e)
      {
            System.err.println("No such algorithm");
            e.printStackTrace();
      }
      catch (NoSuchProviderException e)
      {
            System.err.println("No such provider");
            e.printStackTrace();
      }
      
  }
}
