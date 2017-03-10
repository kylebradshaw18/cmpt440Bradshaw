/**
 * @Author Kyle Bradshaw
 * @Date March 9, 2017
 * @Course Formal Languages
 * @JavaClass ManWolf
 * @Description This file is invoked by the driver and an input string is
 *  passed in to run it against the DFA. The Calculate function will either
 *  return true or false depending on if the input solution string passed
 * @Imports Regex and Matcher
 */
import java.util.regex.*;
public class ManWolf {
  /**
   * @Constructor This will create the object for ManWolf depending on the 
   *   input string that is passed in.
   */
  public ManWolf(String input){
    this.input = input;
  }
  /**
   * @Properties These are all of the properties for the ManWolf Class
   *    @currentState Is the current location or state that the DFA is in
   *    @acceptState This is the accepting state for the DFA in order for
   *       the DFA to pass it needs a correct solution
   *    @input This is the string that is passed into the Constructor
   *       and will be used to see if it passed validation or not
   *    @characters This is an array of chars that are all of the possible 
   *       letters of the alphabet that this dfa uses. The order is important
   *       because it goes by index for the transitions property
   *    @transitions This is a multidimensional array of integers which the
   *       the outer array are the states that the DFA can be in and the inner array
   *       are the characters in order that the user can land one from the char 
   *       passed in from the input. A -1 is considered the error state which will
   *       return false because the input string does not satisfy this DFA
   */
  private int currentState = 0, acceptState = 9;
  private String input;
  private final char[] characters = {'c', 'g', 'n', 'w'};
  private final int[][] transitions = {
    {-1, 1,-1,-1},
    {-1, 0, 2,-1},
    { 4,-1, 2, 3},
    {-1, 5,-1, 2},
    { 2, 6,-1,-1},
    { 7, 3,-1,-1},
    {-1, 4,-1, 7},
    { 5,-1, 8, 6},
    {-1, 9, 7,-1},
    {-1,-1, 7,-1}
  };
  /**
   * @Description Takes an array of chars (alphabet) and returns the index
   *   of that character
   * @Param char c is the current character being executed in the input solution
   * @Return This function returns an integer from the position of the character
   */
  private int getIndexOfCharInArray(char c){
    for(int index = 0; index < this.characters.length; index++)
      if(this.characters[index] == c)
        return index;
    return 0;
  }
  /**
   * @Description Boolean function which uses regex to either return true or false 
   *    depending on if the input passes expression
   * @Return true or false depending on regex
   */
  private boolean checkAlphabet(){
    return (Pattern.compile("[cgnw]*").matcher(this.input).matches())?true:false;
  }
  /**
   * @Description Function that calculates the input string and sees if it is a 
   *   proper solution for the DFA
   * @Return true or false depending on if the input string matches the alphabet
   *   and ends with the current state in an accepting state
   */
  public boolean calculateDFAString(){
    if(!checkAlphabet())
      return false;

    //Loop through each character in the string and see if it state does not lead to an error
    for(int index = 0; index < this.input.length(); index++){
      //Set the current state to the result after the transition is made
      this.currentState = this.transitions[this.currentState][this.getIndexOfCharInArray(this.input.charAt(index))];
      //Return false if the current state is -1 because that is an error state
      if(this.currentState == -1)
        return false;
    }
    //Depending on the current state if it matches the accepting state then it succeded
    //else it did not and return false
    return (this.currentState == this.acceptState)?true:false;
  }
}