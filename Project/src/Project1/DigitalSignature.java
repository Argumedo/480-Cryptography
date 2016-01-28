package Project1;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.math.BigInteger;
import java.lang.IndexOutOfBoundsException;
import java.security.MessageDigest;
import java.util.*;

public class DigitalSignature 
{
	//Run DigitalSignature.java from the Main.java 
	
	public void sender(String fileName) throws Exception
	{
		//Receives keys d and n from privkey.rsa
		BigInteger keys[] = getPrivateKey();
		BigInteger d = keys[0];
		BigInteger n = keys[1];
		
		//Read File to be sent.
		byte[] msg = readFile(fileName);		
		
		//Create Digest
		MessageDigest t1 = MessageDigest.getInstance("MD5");
		//MD bit by bit.
		for(int i = 0; i < msg.length; i++)
		{
			t1.update(msg[i]);
		}
		
		byte[] message = t1.digest();
		
		//Convert digest into BigInteger with positive sign.
		BigInteger md = new BigInteger(1, message);
		
		//Sign the BigInteger
		BigInteger sign = md.modPow(d, n);
		
		//Create filename with .signed extension.
		createSignedMessage(fileName, sign, msg);
	}

	public void receiver(String fileName) throws Exception
	{
		//reads the .signed file into a list of Objects
		List<Object> received = readMessage(fileName);
		BigInteger signature;
		byte[] message ;
		try
		{
			signature = (BigInteger) received.get(0);
			message = (byte[]) received.get(1);
		}
		catch(IndexOutOfBoundsException e)
		{
			signature = new BigInteger("5");
			message = new byte[] {0x20};
		}
		
		BigInteger[] pubKeys = getPublicKey();
		BigInteger e = pubKeys[0];
		BigInteger n = pubKeys[1];
		
		BigInteger encryptSig = signature.modPow(e, n);
		
		byte[] compare1 = encryptSig.toByteArray();
		
		//removes the sign bit that was given when converted to BigInteger
		if(compare1[0] == 0)
		{
			byte[] temp = new byte[compare1.length-1];
			System.arraycopy(compare1, 1, temp, 0, temp.length);
			compare1 = temp;
		}
		
		//Original Message converted to bytes

		
		MessageDigest md = MessageDigest.getInstance("MD5");
		
		//Converts message into digest bit by bit
		for(int i = 0; i < message.length; i++)
		{
			md.update(message[i]);
		}
	
		byte[] compare2 = md.digest();

		String msg = new String(message);
		if(MessageDigest.isEqual(compare2, compare1))
		{
			System.out.println("-------------- Signature and message both match! -------------- \nMessage is : \n" + msg);	
		}
		else if(!received.isEmpty())
		{
			System.out.println("Signature and Message do not match! Message was tampered with!");
		}
	}
	
	public static List<Object> readMessage(String fileName) throws StreamCorruptedException, IndexOutOfBoundsException
	{

		ObjectInputStream ois = null;
		List<Object> store = new ArrayList<Object>();

		try
		{
			ois = new ObjectInputStream(new FileInputStream(fileName));
			while(ois.available() == 0)
			{
				store.add(ois.readObject());
			}
			
			
			ois.close();
		} 
		catch(FileNotFoundException e)
		{
			System.out.println("File you are trying to receive was not found. Please Try Again.");
		}
		catch(EOFException e) 
		{
//			System.out.println("End of File Reached");
		} 
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("Message and Signature do not match!");
		}
		catch(Exception e) 
		{
			System.out.println("Message and Signature do not match!");
		}
		finally
		{
			try
			{
				if(ois != null)
				{
					ois.close();
				}
			}
			catch(Exception e)
			{
//				e.printStackTrace();
			}
		}
		return store;
	}

	public static void createSignedMessage(String fileName,BigInteger sign, byte[] y)
	{
		try{
			
			String newFile = fileName + ".signed";
	//		System.out.println(newFile);
			FileOutputStream foos = new FileOutputStream(newFile);
			ObjectOutputStream oos = new ObjectOutputStream(foos);
			
			oos.writeObject(sign);
			oos.writeObject(y);
			oos.close();
		} 
		catch(Exception e) 
		{
//			e.printStackTrace();
		}
	}

	public static byte[] readFile(String fileName) throws Exception
	{
 
		File file = new File(fileName);
		byte[] bytes = null;
		
		try(FileInputStream fis = new FileInputStream(file)) 
		{
 
			bytes = new byte[fis.available()];
			int content, i = 0;
			while ((content = fis.read()) != -1) 
			{
				bytes[i] = (byte) content;
				i++;
			}
 
		}
		catch (IOException e) 
		{
//			e.printStackTrace();
		}
		
		return bytes;
		
	}
	
	public static BigInteger[] getPublicKey()
	{
		ObjectInputStream ois = null;
		BigInteger[] keys = new BigInteger[2];
		try
		{
			ois = new ObjectInputStream(new FileInputStream("pubkey.rsa"));
			int i = 0; 
			while(ois.available() == 0)
			{
				keys[i] = (BigInteger) ois.readObject();
				i++;
			}
			ois.close();
		} 
		catch(EOFException e) 
		{
//			System.out.println("End of File Reached");
		} 
		catch(Exception e) 
		{
//			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(ois != null)
				{
					ois.close();
				}
			}
			catch(Exception e)
			{
//				e.printStackTrace();
			}
		}
		return keys;
	}
	

	public static BigInteger[] getPrivateKey()
	{
		ObjectInputStream ois = null;
		BigInteger[] keys = new BigInteger[2];
		try
		{
			ois = new ObjectInputStream(new FileInputStream("privkey.rsa"));
			int i = 0; 
			while(ois.available() == 0)
			{
				keys[i] = (BigInteger) ois.readObject();
				i++;
			}
			ois.close();
		} 
		catch(EOFException e) 
		{
//			System.out.println("End of File Reached");
		} 
		catch(Exception e) 
		{
//			e.printStackTrace();
		}
		finally
		{
			try
			{
				if(ois != null)
				{
					ois.close();
				}
			}
			catch(Exception e)
			{
//				e.printStackTrace();
			}
		}
		return keys;
	}
}
