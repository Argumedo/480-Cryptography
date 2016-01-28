package Project1;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ChangeByte {
	
	public static void main(String[] args) throws FileNotFoundException
	{
		String filename;
		@SuppressWarnings("resource")
		Scanner scan=new Scanner(System.in);
		System.out.println("Input file name:");
		filename=scan.nextLine();
		openFileToBin(filename);
		
	}
	public static void openFileToBin(String filename) throws FileNotFoundException
	{
		Random random=new Random();
		Scanner input=new Scanner(System.in);

		FileInputStream iFile = new FileInputStream(filename);

			try {
				
				System.out.println("Choose a byte between 0 to " + (iFile.available()-1) + " to change.");
				
				int cByte=input.nextInt();
				ArrayList<Byte> myBytes = new ArrayList<Byte>();
				ArrayList<Byte> tst = new ArrayList<Byte>();


				while(iFile.available()>0){
					myBytes.add(new Byte((byte) iFile.read()));
				}
			
				
				
				try
				{
					myBytes.set(cByte, new Byte((byte)random.nextInt()));
					System.out.println("Byte was altered");
				}
				catch(IndexOutOfBoundsException e)
				{
					System.out.println("Invalid byte selection. No byte was altered");
				}

				
				FileOutputStream oFile = new FileOutputStream(filename);
				
				for(Byte bytes : myBytes){
					oFile.write(bytes.byteValue());
				}	
				oFile.close();
				
				FileInputStream test = new FileInputStream(filename);
				
				while(test.available()>0){
					tst.add(new Byte((byte) test.read()));
				}
				
				test.close();
			} 	catch(EOFException e) 
			{
//				System.out.println("End of File Reached");
			} 
			catch(Exception e) 
			{
				System.out.println("Invalid Entry");
			}
	}
	
	
}
