package test.stringmanipulator;

/* @author Joe Nguyen */

import java.util.*;

class SortingString {
    //setting scanner for the whole class
    Scanner scan = new Scanner(System.in);
    
//making a catching error method using try and error
 static int catchingError(String input){
     int convertedInput = 0;
     //converted the string to int
        try {       
        convertedInput = Integer.parseInt(input);
        }
        catch (NumberFormatException e)
            {
                System.out.println("Invalid Input!");
                
            }
        return convertedInput; //return the string that is now an integer       
    }
 public void sort() 
 {
    String mainInput, choiceInput;
   do
   {

       int arraySize;
       do
       {
       //Defining array size and catching any error
        System.out.println("Enter the NUMBER of elements: ");
        String size = scan.nextLine();
        arraySize = catchingError(size);
        }while (arraySize == 0);
        int[] array = new int[arraySize];
       

       //Inputting the elements into the array
        System.out.println("Enter the elements to sort: ");
        for(int i = 0; i < array.length; i++)   
            {     
                try 
                {
                    array[i] = scan.nextInt(); 
                }
                catch (java.util.InputMismatchException e)
                {
                    scan.nextLine(); // this is to prevent infinite loop
                    System.out.println("Invalid Input! Please enter a number");
                    i--; //this is to ensure there is not a space skip in the array
                }
            } 
        scan.nextLine(); //this is to prevent the token returning an empty string in the next call

        System.out.println("What would you like to do with the array of elements?");
        System.out.println("Sort by order (Typed order or o)");
        System.out.println("Separate Even and Odd (Typed separate or s)");
        choiceInput = scan.nextLine();
        choiceInput = choiceInput.toLowerCase();

        if ("order".equals(choiceInput) || "o".equals(choiceInput))
        {
             System.out.println("Would you like to sort ascending (or a) or descending (or d) order?");
             String input = scan.nextLine();
             input = input.toLowerCase();
            if ("ascending".equals(input) || "a".equals(input)) 
            {
                Arrays.sort(array); //sorting the array
                System.out.println("The elements in ascending order: " + Arrays.toString(array));
            }
            else if ("descending".equals(input) || "d".equals(input))
            {
              int last = array.length - 1;
              int middle = array.length / 2;
              Arrays.sort(array); //must sort the array first into ascending order before operate on the array
              for (int i = 0; i <= middle; i++) 
                {
                    // Storing the first half elements temporarily
                    int temp = array[i];  
                    // Assigning the first half to the last half
                    array[i] = array[last - i];  
                    // Assigning the last half to the first half
                    array[last - i] = temp;
                }
              System.out.println("The elements in descending order: " + Arrays.toString(array));              
            }       
        }

        else if ("separate".equals(choiceInput) || "s".equals(choiceInput))
        {
            //Initialize array list for even and odd numbers
            ArrayList<Integer> oddNum = new ArrayList<>(); 
            ArrayList<Integer> evenNum = new ArrayList<>(); 
            //Separating the evens and odds
            for (int j : array) {
                if (j % 2 == 0) //if the remainder is 0 then it is even
                {
                    evenNum.add(j);
                } else {
                    oddNum.add(j);
                }
            }  
            System.out.println("Even Number are: \n" + evenNum);
            System.out.println("Odd Number are : \n" + oddNum);
        }                

    System.out.println("Would you like to continue? (y or yes)");
    mainInput = scan.nextLine();
    mainInput = mainInput.toLowerCase();
    
   }while("yes".equals(mainInput) || "y".equals(mainInput));    
  }
}
