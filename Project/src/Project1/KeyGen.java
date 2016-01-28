package Project1;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Random;

public class KeyGen {
	
	
	public static void main(String[] args)
	{
		//You can either run KeyGen.java separately OR run it from Main.java (option one);
		
		int length = 1024/2;
		Random rand = new Random();
		BigInteger p, q, n, x, e, d;
		
		// Pick p and q to be random primes of some specified length using the appropriate BigInteger constructor for Java.
		//p, q and e as defined above are all 512-bit integers and n  should be ~1024 bits
		p = new BigInteger("19");
		q = new BigInteger("13");

		// Calculate n =  p x q
		n = p.multiply(q);
		System.out.println("n (bitLength = " + n.bitLength() + ") " + n );
		
		//Calculate ø(n) = (p-1)x(q-1)  
		x = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		
		System.out.println("ø(n) (bitLength = " + x.bitLength() + ") " + x );
		
		if(x.compareTo(n) == 0)
		{
			System.out.println("\nx = n\ntrue\n\n");
		}

		//Pick e to be a random prime between 1 and ø(n), such that gcd(e, ø(n)) = 1. e should be similar in (bit) length to p and q, but does not have to be the same length.
		e = BigInteger.probablePrime(5, rand);
		

		while(!(x.compareTo(e) == 1) && !(x.gcd(e) == BigInteger.ONE))
		{
			e=BigInteger.probablePrime(length, rand);	
		
		}
		
		System.out.println("e (bitLength = " + e.bitLength() + ") " + e );
		
		//Calculate  d = e-1 mod ø(n)
		d = e.modInverse(x);
		
		System.out.println("d (bitLength = " + d.bitLength() + ") " + d );

		System.out.println("\nCreating pubkey.rsa and reading objects in file");
		createPublicKey(e,n);
		
		System.out.println("\nCreating privkey.rsa and reading objects in file");
		createPrivateKey(d, n);		
	}
	
	//create public key
	public static void createPublicKey(BigInteger e, BigInteger n)
	{

		ObjectInputStream ois = null;

		try{
			FileOutputStream foos = new FileOutputStream("pubkey.rsa");
			ObjectOutputStream oos = new ObjectOutputStream(foos);
			
			oos.writeObject(e);
			oos.writeObject(n);
			oos.close();

			ois = new ObjectInputStream(new FileInputStream("pubkey.rsa"));
			int i = 0;
			while(ois.available() == 0)
			{	
				if(i == 0)
				{
					System.out.print("Key e : ");
				}
				if(i == 1)
				{
					System.out.print("Key n : " );
				}
				System.out.println(ois.readObject());
				i++;
			}
	
			ois.close();
		} 
		catch(EOFException er) 
		{
//			System.out.println("End of File Reached");
		} 
		catch(Exception er) 
		{
			er.printStackTrace();
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
			catch(Exception er)
			{
				er.printStackTrace();
			}
		}	
	}
	
	public static void createPrivateKey(BigInteger x, BigInteger y)
	{

		ObjectInputStream ois = null;

		try{
			FileOutputStream foos = new FileOutputStream("privkey.rsa");
			ObjectOutputStream oos = new ObjectOutputStream(foos);
			
			oos.writeObject(x);
			oos.writeObject(y);
			oos.close();

			ois = new ObjectInputStream(new FileInputStream("privkey.rsa"));

			int i = 0;
			while(ois.available() == 0)
			{	
				if(i == 0)
				{
					System.out.print("Key d : ");
				}
				if(i == 1)
				{
					System.out.print("Key n : " );
				}
				
				System.out.println(ois.readObject());
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
			e.printStackTrace();
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
				e.printStackTrace();
			}
		}
		
	}

	public void createKeys() {
		
		int length = 1024/2;
		Random rand = new Random();
		BigInteger p, q, n, x, e, d;
		
		p = BigInteger.probablePrime(length, rand);
		q = BigInteger.probablePrime(length, rand);

		// Calculate n =  p x q
		n = p.multiply(q);
		System.out.println("n (bitLength = " + n.bitLength() + ") " + n );
		
		//Calculate ø(n) = (p-1)x(q-1)  
		x = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
		
		System.out.println("ø(n) (bitLength = " + x.bitLength() + ") " + x );
		
		if(x.compareTo(n) == 0)
		{
			System.out.println("\nx = n\ntrue\n\n");
		}

		//Pick e to be a random prime between 1 and ø(n), such that gcd(e, ø(n)) = 1. e should be similar in (bit) length to p and q, but does not have to be the same length.
		e = BigInteger.probablePrime(length, rand);
		

		while(!(x.compareTo(e) == 1) && !(x.gcd(e) == BigInteger.ONE))
		{
			e=BigInteger.probablePrime(length, rand);	
		
		}
		
		System.out.println("e (bitLength = " + e.bitLength() + ") " + e );
		
		//Calculate  d = e-1 mod ø(n)
		d = e.modInverse(x);
		
		System.out.println("d (bitLength = " + d.bitLength() + ") " + d );

		System.out.println("\nCreating pubkey.rsa and reading objects in file");
		createPublicKey(e,n);
		
		System.out.println("\nCreating privkey.rsa and reading objects in file");
		createPrivateKey(d, n);		
		
	}
	
}
