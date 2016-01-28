import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public class Hello 
{
	public static void main(String[] args) throws Exception,IOException
	{
		Scanner in = new Scanner(System.in);
		
		System.out.println("What words do you want to search?");
		List<String> compare = new ArrayList<String>();
		
		for(int i = 0; i < 10; i++)
		{
			compare.add(in.nextLine());
		}		
		in.close();
		
		String fileName = "wordlist.txt";
		List<String> list = new ArrayList<String>();
		
		File test = new File(fileName);
		
		try
		{
			Scanner input = new Scanner(test);
			
			while(input.hasNextLine())
			{
				list.add(input.nextLine());
			}
			input.close();
		}
		catch(Exception E)
		{
			
		}
		List<String> matches = new ArrayList<String>();
		for(int i = 0; i < compare.size(); i++)
		{
			for(int j = 0; j < list.size(); j++)
			{
				
				if(compare.get(i).length() == list.get(j).length())
				{
					if(t(compare.get(i), list.get(j)))
					{
						matches.add(list.get(j));
					}
					
				}
			}
		}
		
		for(int i = 0; i < matches.size(); i++)
		{
			System.out.print(matches.get(i) + ",");
		}
	}
	
	public static boolean t(String a, String b)
	{
		int count = 0;
		for(int i = 0; i < a.length(); i++)
		{
			for(int j = 0; j < b.length(); j++)
			{
				if(a.charAt(i) == b.charAt(j))
				{
					b = b.substring(0, j) + b.substring(j+1);
					count++;
					break;
				}
			}
		}
		if(count == a.length())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
