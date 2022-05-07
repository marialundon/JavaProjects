import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class HRDetector 
{
    int arrayHR []; // Array to hold values representing HR readings
    int maxArraySize; // Max number of values that can be stored in array

    // Constructor - takes constant for max array size and initialises constant & array to store HR values
    public HRDetector(int maxArraySize) 
    {
        this.maxArraySize = maxArraySize;
        this.arrayHR = new int[maxArraySize];
    }

    // Method to take HR reading and add it to the array - returns true if array is full
    public boolean addReading(int reading)
    {
        int counter = 0;

        for (int i = 0; i < arrayHR.length; i++)
            if (arrayHR[i] != 0)
                counter ++;
        
        if (counter < maxArraySize)
        {
            arrayHR[counter] = reading;
            return false;
        }

        else
            return true;
    }

    // Method to return a value at a given position within an array - returns 0 if no value at position provided
    public int returnReading(int position)
    {
        if (position >= 0 && position < arrayHR.length)
            return arrayHR[position];
        else
            return 0;
    }

    // Method to sum all elements in the array and return result
    public int sumReadings()
    {
        int sum = 0;

        for (int i : arrayHR)
            sum += i;
            return sum;
    }

    // Method to return values in the array as string 
    public String toString() 
    { 
        String s = "";       
        for (int i = 0; i < arrayHR.length; i++) 
            s += arrayHR[i] + " ";

        return s;
    }

    // Method to compare HR array with another array - returns true if both arrays are same size and numbers in HR array are less than or equal to those of the array passed to the method
    public boolean passed(int arr[])
    {
        
        if (arr.length != arrayHR.length)
            return false;
        
        for (int i = 0; i < arrayHR.length; i++)
        {
            if (arrayHR[i] > arr[i] )
                return false ;
        }
    
        return true;
    }

    // Method to write numbers in HR array to file passed as parameter to method
    public void recordHRStats(String filename)
    {
        try 
        {
            BufferedWriter bufferedwriter = new BufferedWriter(new FileWriter(filename));

                for( int i = 0; i < arrayHR.length; i++)  
                {
                    bufferedwriter.write(arrayHR[i] + " ");
                }

            bufferedwriter.close();
        }

        catch (IOException e1) 
        {}
    }

    // Method to read int numbers from file passed as parameter into HR array
    public void loadHRStats(String filename)
    {
        try 
        {
            BufferedReader bufferedreader = new BufferedReader(new FileReader(filename));
            Scanner scanner = new Scanner (bufferedreader);
            int i = 0;
            while (scanner.hasNextInt())
            { 
                arrayHR[i++] = scanner.nextInt();
            }

            scanner.close(); 
        } 
    
        catch (FileNotFoundException e2) 
        {
            e2.printStackTrace();
        }
    }

}
