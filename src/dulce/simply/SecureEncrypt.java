/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dulce.simply;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Yonshisoru
 */
public class SecureEncrypt {
    private static final Random random = new SecureRandom();
    private static final String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;
    
    public static String getSalt(int length){
        //Adding salt for mix with password before encrytion
        StringBuilder returnValue = new StringBuilder(length);
        for(int i=0;i<length;i++){
            returnValue.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return new String(returnValue);
    }

public static byte[] hash(char[] password,byte[] salt){
    //Encrypt Method
    PBEKeySpec spec = new PBEKeySpec(password,salt,ITERATIONS,KEY_LENGTH);
Arrays.fill(password,Character.MIN_VALUE);
try{
    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
    return skf.generateSecret(spec).getEncoded();
}catch(NoSuchAlgorithmException | InvalidKeySpecException e){
    throw new AssertionError("Error while hasing a password"+e.getMessage(),e);  
}finally{
    spec.clearPassword();
}
}
public static String generateSecurePassword(String password,String salt){
    String returnValue = null;
    byte[] securePassword = hash(password.toCharArray(), salt.getBytes());
    returnValue = Base64.getEncoder().encodeToString(securePassword);
    return returnValue;
}
    public static boolean verifyUserPassword(String providedPassword,
            String securedPassword, String salt)
    {
        boolean returnValue = false;
        
        // Generate New secure password with the same salt
        String newSecurePassword = generateSecurePassword(providedPassword, salt);
        
        // Check if two passwords are equal
        returnValue = newSecurePassword.equalsIgnoreCase(securedPassword);
        
        return returnValue;
    }
    public static void main(String[] args)
    {
        /*
                String providedPassword = "myPassword123";
                
        // Encrypted and Base64 encoded password read from database
        String securePassword = "HhaNvzTsVYwS/x/zbYXlLOE3ETMXQgllqrDaJY9PD/U=";
        
        // Salt value stored in database 
        String salt = "EqdmPh53c9x33EygXpTpcoJvc4VXLK";
        
        boolean passwordMatch = verifyUserPassword(providedPassword, securePassword, salt);
        
        if(passwordMatch) 
        {
            System.out.println("Provided user password " + providedPassword + " is correct.");
        } else {
            System.out.println("Provided password is incorrect");
        }
        */
    }
 /*      
        String myPassword = "myPassword123";
        
        // Generate Salt. The generated value can be stored in DB. 
        String salt = getSalt(30);
        
        // Protect user's password. The generated value can be stored in DB.
        String mySecurePassword = generateSecurePassword(myPassword, salt);
        
        // Print out protected password 
        System.out.println("My secure password = " + mySecurePassword);
        System.out.println("Salt value = " + salt);
    }*/
}
