package bruteforce;
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class Cracker {
	
	private static long start; //Used to hold the value of the time passed
	public static void main(String[] args) {
		System.out.println("Enter 1 for Brute Force, or 2 for Dictionary Attack:");
		Scanner userChoice = new Scanner(System.in); //Detects users choice of attack through console
		String attackChoice = userChoice.next();
 
		
		
		
		if (attackChoice.equals("1")){
			bfA();
			
			}

		else if (attackChoice.equals("2")){
			dictA();
			

		}
		else {
			System.out.println("Option not found!");
			System.exit(0);
			
		}
		userChoice.close();
	}
	
	
	public static void bfA() {
		Scanner userBfIn = new Scanner(System.in);
		System.out.println("Enter filepath of hashed passwords:");
		String hpath = userBfIn.next();
		ArrayList<String> hList = new ArrayList<String>();
		loadFile(hpath, hList);
		System.out.println("Enter maximum no of characters in password.");
		int maxLength = userBfIn.nextInt();
		
		
		start = System.currentTimeMillis();
		 
		//Defines the number of characters
				int chars = 95;
				int noP = (int) Math.pow(chars, maxLength); //number of possible permutations calculated by returning the value of chars to the power of maxLength
				String plainText;
				String hash;
				StringBuilder index = new StringBuilder(maxLength);
				ArrayList<String> foundWords = new ArrayList<String>();
				
				System.out.println("\n Brute Force attack in progress.\n");
				
				//A loop that will generate each permutation, hash it, then compare it to the hashes provided
				for (int i = 0; i < noP; i++) {
					index.setLength(0);
				    //Cycles it through the available characters, for each character in the string builder
				    for (int j = 0, k = i; j < maxLength; j++, k /= chars) { 
				    	index.insert(0, (char) (32 + k % chars));
				    	plainText = index.toString();
				    	hash = getMd5(plainText);
				    	
				    	//Checks if the generated hash is in the provided file, and has not already been found
				    	if (hList.contains(hash) && !foundWords.contains(plainText)) {
				    		System.out.println("--------------------------------");
				    		System.out.println("unhashed password = " + plainText);
				    		System.out.println("hashed password = " + hash);
				    		System.out.println("--------------------------------");
				    		foundWords.add(plainText);
				    	}
					} 
				}
				System.out.println("\nAttack complete.");
				
	
				
				System.out.println("\n" + foundWords.size() + " out of " + hList.size() + " matching hashes found.");
	

		
		System.out.println("--------------------------------");
		System.err.println("It took: " + convertmillis(System.currentTimeMillis() - start));
		System.out.println("--------------------------------");
		userBfIn.close();
		System.exit(0);
		}
		
	
		
	
	public static void dictA() {
		
		System.out.println("Enter filepath of dictionary:");
		Scanner userDictIn = new Scanner(System.in);
		String dPath = userDictIn.next();
		ArrayList<String> dList = new ArrayList<String>();
		loadFile(dPath, dList);
		//Finds, then loads the password file
		System.out.println("Enter filepath of hashed passwords:");
		String hpath = userDictIn.next();
		ArrayList<String> hList = new ArrayList<String>();
		loadFile(hpath, hList);
		start = System.currentTimeMillis();
		
		
		int noResults = 0;
		//Hashes each word in the dictionary, then compares it to the hashes in the password file
		for (int i = 0; i < dList.size(); i++) {
			String wordHash = getMd5(dList.get(i));
			if (hList.contains(wordHash)) {
				System.out.println(dList.get(i) + " hashed is " + getMd5(dList.get(i)));
				noResults ++;
			}
		}
		System.out.println("\n" + noResults + " matching hashes found out of " + hList.size() + ".");
		System.err.println("It took: " + convertmillis(System.currentTimeMillis() - start));
		userDictIn.close();
		System.exit(0);
		 
	}
	
	


	
	public static String getMd5(String input)
	 {
		 try {
			 // Static getInstance method is called with hashing MD5
			 MessageDigest md = MessageDigest.getInstance("MD5");
			 // digest() method is called to calculate message digest
			 // of an input digest() return array of byte
			 byte[] messageDigest = md.digest(input.getBytes());
			 // Convert byte array into signum representation
			 BigInteger no = new BigInteger(1, messageDigest);
			 // Convert message digest into hex value
			 String hashtext = no.toString(16);
			 
			 while (hashtext.length() < 32) {
				 hashtext = "0" + hashtext;
			 }
			 return hashtext;
		 }
		 // For specifying wrong message digest algorithms
		 catch (NoSuchAlgorithmException e) {
			 throw new RuntimeException(e);
		 	}
		 } 
		 

	public static void loadFile(String in, ArrayList<String> myList) {
		//Attempts to open and read the provided dictionary file
		try {
			//Creates a File object to contain the .txt and a Scanner object to read it
			File wFile = new File(in);
			Scanner myScanner = new Scanner(wFile);
			//Adds each line (word) to the ArrayList
			while (myScanner.hasNextLine()) {
				String word = myScanner.nextLine();
				myList.add(word);
			}
			myScanner.close();
		}
		//If the file entered cannot be opened, handles the exception
		catch (FileNotFoundException e) {
			System.out.println("File not found.");
			System.exit(0);
		}
	}
	
	public static String convertmillis(long input) {
		int days = 0, hours = 0, minutes = 0, seconds = 0, millis = 0;
			        
		int day = 86400000;
		int hour = 3600000;
		int minute = 60000;
		int second = 1000;
			    
			       
		if(input >= day) {
		     days = (int) (input / day);
		     millis = (int) (input % day);
		} else 
			millis = (int) input;
			           
		if(millis >= hour) {
		     hours = millis / hour;
		     millis = millis% hour;
		}
			       
		if(millis >= minute) {
			 minutes = millis / minute;
			 millis = millis % minute;
		}
		
		if(millis >= second) {
			seconds = millis / second;
			millis = millis % second;
		}
			      
		return (days  + " day(s), " + hours + "h, " + minutes + "min, " + seconds + "s and " + millis + "ms");
	}
	
	
//	public static fileLoader()

}
