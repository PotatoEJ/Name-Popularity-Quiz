////////////////////////////////
//
//   Ethan-John Rasmussen
//   Assignment 9
//   11/30/2017
//
////////////////////////////////
////////////////////////////////////////////////////////////////
//
//   DESCRIPTION: This program reads 1000 lines of text from two seperate files and  
//                assigns the values to an array of type BirthNames, it then quizzes
//                the user as to which names were more popular in the year 2015.
//                It also writed the data as objects to a binary file, then rereads the data
//                and prints the most and least popular names.
//
//   INPUTS: Two text files each containing 1000 names rankings and numBirths.
//           One Binary file with object elements.
//           user input choosing which name they believe is more pupular.  
//
//   OUTPUTS: One binary file with elements of object type.
//            game printed to screen that prompts user to guess popularity.
////////////////////////////////////////////////////////////////
package popularitycontest;

import java.io.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;


public class PopularityContest implements Serializable {

    /*****************************************************************
    *
    * Name: binaryReaderWriter(BirthNames[],)
    * Description: takes in an array of type BirthNames and writes the elements to a .dat file
    *              reads the elements of the .dat file and prints the most and least popular names.
    * Inputs: An array of type BirthNames, one .dat file
    * Output: One .dat file, two lines of text
    * Preconditions: None
    *
    *
    *****************************************************************/
    public static void binaryReaderWriter(BirthNames[] boys) throws IOException {
        // Create a new object output stream
        ObjectOutputStream outStream = null;
        try {
        outStream = new ObjectOutputStream(new FileOutputStream("boynames.dat"));
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found, system exiting");
            outStream.close();
            System.exit(0);
        }
        
        // Use object output stream to write the contentd of the array to a .dat file
        for (int i = 0; i < 1000; ++i ) {
            outStream.writeObject(boys[i]);
        }
        
        // Flush and close file
        outStream.flush();
        outStream.close();
        
        // Create an object input stream to read from the file just created
        ObjectInputStream inStream = null;
        try {
        inStream = new ObjectInputStream(new FileInputStream("boynames.dat"));
        }
        catch(IOException e) {
            System.out.println("IOException program exiting");
            inStream.close();
            System.exit(0);
        }
        
        BirthNames[] boysCopy = new BirthNames[1000];
        
        // Copy contents of the file to the new array
        try {
        for (int i = 0; i < 1000; ++i)
            boysCopy[i] = (BirthNames)inStream.readObject();
        }
        catch(Exception e) {
            System.out.println(e.getMessage() + " program exiting");
            inStream.close();
            System.exit(0);
        }
        
        // Print the most and least popular names.
        System.out.println("Most popular: " + boysCopy[0].getRating() + " " + boysCopy[0].getName() + " " + boysCopy[0].getNumBirths());
        System.out.println("Least popular: " + boysCopy[999].getRating() + " " + boysCopy[999].getName() + " " + boysCopy[999].getNumBirths());
    }

    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        Scanner scnr = new Scanner(System.in);
        Random rand = new Random();
        final int LIST_ELEMENTS = 1000;
        
        // Set up file input streams and catch FIlenotFoundException
        FileInputStream boyNames = null;
        FileInputStream girlNames = null;
        try {
            boyNames = new FileInputStream("boynames2015.txt");
            girlNames = new FileInputStream("girlnames2015.txt");
        } catch (FileNotFoundException e) {
            // If the files have not been read then the program cannot run, must exit.
            System.out.println("File not found, program exiting.");
            boyNames.close();
            girlNames.close();
            System.exit(0);
        } 
        
        // Instatiate new Scanners that take in the file input streams as input.
        Scanner girlScnr = new Scanner(girlNames);
        Scanner boyScnr = new Scanner(boyNames);
        
        
        // Create two array of type Birthnames.
        BirthNames[] boys = new BirthNames[LIST_ELEMENTS];
        BirthNames[] girls = new BirthNames[LIST_ELEMENTS];
        
        // Pass over the title information, it is read but not assigned to anything.
        boyScnr.nextLine();
        girlScnr.nextLine();
        
        // Read the contents of the files, split them and assign them to a string array.
        // Call the constructor for each array element with the arguments provided from the file.
        try {
            for (int i = 0; i < 1000; ++i) {
                String[] arrSplit = new String[3];
                arrSplit = boyScnr.nextLine().split(" ");
                boys[i] = new BirthNames(arrSplit[1], Integer.parseInt(arrSplit[0]), Integer.parseInt(arrSplit[2]));
                arrSplit = girlScnr.nextLine().split(" ");
                girls[i] = new BirthNames(arrSplit[1], Integer.parseInt(arrSplit[0]), Integer.parseInt(arrSplit[2]));
            }
        } catch(NumberFormatException e) {
            // Catch an error when converting Strings to integers.
            System.out.println(e.getMessage());
            System.out.println("Program exiting");
            boyNames.close();
            girlNames.close();
            System.exit(0);
        }
        
        
        // Initialize variables to be used in the guessing game
        int randNum1;
        int randNum2;
        int userInt = 0;
        char userIn = ' ';
        
        
        do {
            // This loop finds two indexes for the arrays and continues to find indexes if the numBirths happen to be the same for the boyname and girlname
            do {
            randNum1 = rand.nextInt(1000);
            randNum2 = rand.nextInt(1000);
            } while (boys[randNum2].compareTo(girls[randNum1]) == 0);
            
            // Pose the user the question, have to access private instance variables: name
            do {
                System.out.println("In the year 2015 was the girl name " + girls[randNum1].getName() + 
                        " (1) or the boy name " + boys[randNum2].getName() + " (2) more popular? (enter 1 or 2)");
                try {
                userInt = scnr.nextInt();
                } catch(InputMismatchException e) {
                    // Incase the user enters a char or string when looking for a int, program will exit.
                    System.out.println("Incompatable input, program exiting");
                    boyNames.close();
                    girlNames.close();
                    System.exit(0);
                }
            } while (!((userInt == 1)||(userInt == 2))); // Will prompt user for input until they enter in a 1 or 2
            
            // Logic for if user chooses girl name
            if (userInt == 1) {
                switch (boys[randNum2].compareTo(girls[randNum1])) {
                    case 1:
                        System.out.println("Correct. There were " + girls[randNum1].getNumBirths() + " girls named " + girls[randNum1].getName() 
                                + " and " + boys[randNum2].getNumBirths() + " boys named " + boys[randNum2].getName());
                        break;
                    case -1:
                        System.out.println("Incorrect. There were " + girls[randNum1].getNumBirths() + " girls named " + girls[randNum1].getName() 
                                + " and " + boys[randNum2].getNumBirths() + " boys named " + boys[randNum2].getName());
                        break;
                }
            }
            // Logic for if user chooses boy name
            if (userInt == 2) {
                switch (boys[randNum2].compareTo(girls[randNum1])) {
                    case -1:
                        System.out.println("Correct. There were " + girls[randNum1].getNumBirths() + " girls named " + girls[randNum1].getName() 
                                + " and " + boys[randNum2].getNumBirths() + " boys named " + boys[randNum2].getName());
                        break;
                    case 1:
                        System.out.println("Incorrect. There were " + girls[randNum1].getNumBirths() + " girls named " + girls[randNum1].getName() 
                                + " and " + boys[randNum2].getNumBirths() + " boys named " + boys[randNum2].getName());
                        break;
                }
            }
            // Prompt the user to play again
            System.out.println("Would you like to play again? Enter y to continue, any other button to quit. You have to end this game for my object writer and reader to run.");
            userIn = scnr.next().charAt(0);
        } while (userIn == 'y');
        
        // Close FileInputStreams
        boyNames.close();
        girlNames.close(); 
        
        // Call the Binary output and input practice method
        binaryReaderWriter(boys);
    }   
}
