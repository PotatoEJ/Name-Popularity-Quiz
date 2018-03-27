////////////////////////////////
//
//   Ethan-John Rasmussen
//   Assignment 9
//   11/30/2017
//
////////////////////////////////
////////////////////////////////////////////////////////////////
//
//   DESCRIPTION: This class houses private instance variables to be used in   
//                the PopularityContest program. It also defines the 
//                compareTo(Object) method.
//                
//
//   INPUTS: One String and two integers.
//           One Object to be compared to  
//
//   OUTPUTS: Three private instance variables, name, rating and numBirths.
//            1, -1, or 0 depending on how the other objects numBirths compare to this objects numBirths.
//
////////////////////////////////////////////////////////////////
package popularitycontest;

import java.io.Serializable;

public class BirthNames implements Comparable, Serializable {
    private String name;
    private int rating;
    private int numBirths;
    
    /*****************************************************************
    *
    * Name: BirthNames(String, int, int)
    * Description: Constructor that takes the arguments and assigns them to name rating and numBirths
    * Inputs: One String two integers.
    * Output: Class type with three initialized variables.
    * Preconditions: None
    *
    *
    *****************************************************************/   
    BirthNames(String name, int rating, int numBirths) {
        this.name = name;
        this.rating = rating;
        this.numBirths = numBirths;
    }
    
    /*****************************************************************
    *
    * Name: getyName(), getRating(), getNumBirths()
    * Description: getters
    * Inputs: No input
    * Output: values of private fields.
    * Preconditions: None
    *
    *
    *
     * @return name, rating, numBirths
    *****************************************************************/
    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public int getNumBirths() {
        return numBirths;
    }
    
    /*****************************************************************
    *
    * Name: compareTo(Object)
    * Description: This method is an implementation of the Comparable interface
    *              It compares the other object to itself to see if numbirths is greater less than or equal.
    * Inputs: Object and private instance variables.
    * Output: integer of value 1, 0, or -1. 
    * Preconditions: None
    *
    *
    *
     * @return int
    *****************************************************************/
    @Override
    public int compareTo(Object o) {
        
        BirthNames otherObject = (BirthNames)o;
        
        if (otherObject.getNumBirths() > this.numBirths) 
            return 1;
        
        if (otherObject.getNumBirths() < this.numBirths)
            return -1;
        
        return 0;
    }
}
