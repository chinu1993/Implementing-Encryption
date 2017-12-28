import java.io.*;
import java.net.*;
import java.security.*;
import java.util.Formatter;

import javax.crypto.*;

public class CipherClient
{
	public static void main(String[] args) throws Exception 
	{
		String message = "The quick brown fox jumps over the lazy dog.";
		String host = "LOCALHOST";
		int port = 7999;
		Socket s = new Socket(host, port);

		// YOU NEED TO DO THESE STEPS:
		// -Generate a DES key.
		// -Store it in a file.
		// -Use the key to encrypt the message above and send it over socket s to the server.
		
		//Generate a DES key
		KeyGenerator keyGen = KeyGenerator.getInstance("DES");
		SecureRandom random = new SecureRandom();
		keyGen.init(random);
		SecretKey secretkey = keyGen.generateKey();
		
	    //store the key in a file
		String fileName = "key.txt";
		ObjectOutputStream outputStream = new ObjectOutputStream( new FileOutputStream(fileName));
		outputStream.writeObject(secretkey);
		
		 // send the key to the server
		ObjectOutputStream sSocket = new ObjectOutputStream(s.getOutputStream());
	    sSocket.writeObject(secretkey);
	    sSocket.flush();
	    
	    Cipher desCipher = Cipher.getInstance("DES");
	    desCipher.init(Cipher.ENCRYPT_MODE, secretkey); // making the encryption with the specified key
	    byte [] byteDataToEncrypt = message.getBytes(); // encode the string into bytes 
	   
	    // building a cipher output stream from the output stream and the desCipher
	    CipherOutputStream cipherOut = new CipherOutputStream(s.getOutputStream(), desCipher);
	    cipherOut.write(byteDataToEncrypt);  //sending through the socket
	    cipherOut.close();
	    
	    s.close();
	    outputStream.close();
	}
}