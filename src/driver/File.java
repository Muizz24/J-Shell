// **********************************************************
// Teammate1: Muizz Uddin Ahmed
//
// Teammate2: Nicholas Michael Gibson Elliott
//
// Teammate3: Anthony Alaimo
//
// Teammate4: Millan Singh Khurana
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package driver;

/**
 * This class represents a File which stores data and/or information used by a program, which can be
 * modified.
 * 
 * @author Anthony Alaimo
 */
public class File {
  // file's name and test variables
  private String name;
  private String text;

  /**
   * this class represents a default file if no parameters are given
   */
  public File() {

  }

  /**
   * This method takes two strings, and creates a File setting its name as one of the strings and
   * the text as the other
   * 
   * @param name, a string representing the File's name
   * @param text, a string representing the text of a File
   */
  public File(String name, String text) {
    this.setName(name);
    setText(text);
  }

  /**
   * This method returns the text inside the File which called this method
   * 
   */
  public String getText() {
    // returns the text stored inside the indicated file
    return text;
  }

  /**
   * This method sets the text of File which called this method with the indicated string
   * 
   */
  public void setText(String text) {
    // set the text for the indicated file
    this.text = text;
  }

  /**
   * This method returns the name of File which called this method with the indicated string
   * 
   */
  public String getName() {
    // returns the files name
    return name;
  }

  /**
   * This method sets the name of File which called this method with the indicated string
   * 
   */
  public void setName(String name) {
    // sets the name of the file
    this.name = name;
  }
}
