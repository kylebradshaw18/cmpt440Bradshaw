/**
 * @Author Kyle Bradshaw
 * @Date March 9, 2017
 * @Course Formal Languages
 * @JavaClass driverDFA
 * @Description This file is the driver for the Man Wolf DFA assignment
 *    It will take in a string and send it to the ManWolf.java file for validation
 *    After validating the string the driver will either specify if the string 
 *    was a solution for the DFA or if it was not by printing out a string
 * @Imports none
 */
public class driverDFA {
  /**
   * @MainMethod First function that runs the program
   * @Param args, Takes in the input for the dfa to run
   * @Description This will print out whether or not the input string passed
   *    the validation for the DFA
   */
  public static void main(String[] args) {
    //Check if the user passed in information
    //If they did not then set the local input variable to empty string
    String input = (args.length > 0)?args[0]:"";
    //Create a string which will either be empty or contain the word not
    //Depending on the validation process for the dfa
    String not = (new ManWolf(input).calculateDFAString())?"":"NOT";
    //Print the message to the user depending on the validation of the dfa
    System.out.printf("This is %s a solution! %n", not);
  }
}