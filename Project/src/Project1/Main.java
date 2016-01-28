package Project1;

import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.InputMismatchException;


public class Main {

	public static void main(String[] args) 
	{
		Scanner file = new Scanner(System.in);
		int option = 0;
		String filename;
		do {

			option = showMenu();

			switch (option) 
			{
				case 1:
					createKeys();
					System.out.println("New Keys Have Been Created.");
					pause();
					break;
				case 2:
					System.out.println("Enter Filename to send:");
					filename = file.nextLine();
					sendFile(filename);
					break;
				case 3:
					System.out.println("Enter Filename to receive:");
					filename = file.nextLine();
					recvFile(filename);
					break;
				case 4:
					System.out.println("Enter FileName to alter a byte: ");
					filename = file.nextLine();
					try 
					{
						alterByte(filename);
					} catch (FileNotFoundException e) 
					{
						System.out.println("File does not exist");
					}
					pause();
					break;
				case -1:
					System.out.println("Program Has Terminated");
					break;
				default:
					System.out.println("Sorry, please enter valid Option");
					pause();
			}
		} while (option != -1);
	
		file.close();
	}

	public static int showMenu()
	{
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);

		int option = 0;

		System.out.println("Menu:");
		System.out.println("1. Create Priv/Pub Keys");
		System.out.println("2. Send File");
		System.out.println("3. Receive File");
		System.out.println("4. Alter a Byte");
		System.out.println("-1. Quit Program");

		System.out.println("Enter Option from above:");
		try
		{
			option = scan.nextInt();
		}
		catch(InputMismatchException e)
		{
			System.out.println("That was not an integer");
		}
		
		return option;

	}
	
	public static void sendFile(String fileName)
	{
		DigitalSignature ds = new DigitalSignature();
		try
		{
			ds.sender(fileName);
			System.out.println(fileName + ".signed was created using privkey.rsa");
			pause();
		}
		catch(Exception e)
		{
			System.out.println("The File you are trying to send can not be found. Please try again");
		}		
	}
	
	public static void recvFile(String fileName)
	{
		DigitalSignature ds = new DigitalSignature();
		try
		{
			ds.receiver(fileName);
			pause();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Error occurred while receiving ! File may have been tampered with!");
			pause();
		}
	}
	
	public static void pause()
	{
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println(" -------------- To Continue press 'Enter' --------------");
		@SuppressWarnings("unused")
		String temp = null;
		temp = input.nextLine();
	}
	
	public static void createKeys()
	{
		KeyGen kg = new KeyGen();
		kg.createKeys();
	}
	

	public static void alterByte(String fileName) throws FileNotFoundException
	{
		ChangeByte.openFileToBin(fileName);
	}
	
}
