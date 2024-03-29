package driver;
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

public class ExceptionHandler {
  private static final String errorText = "Error: ";

  public static void invalidPathException(String invalidPath) {
    String error = (errorText + invalidPath);
    System.out.println(error + " is not a valid path.");
  }
  
  public static void invalidComputerPathException(String invalidPath) {
    String error = (errorText + invalidPath);
    System.out.println(error + " is not a valid path on this"
        + " computer for the save file to be created.");
  }
  
  public static void invalidURLException(String invalidURL) {
    String error = (errorText + invalidURL);
    System.out.println(error + " is not a valid URL link or "
        + "does not contain only plain text.");
  }


  public static void invalidEncodingException(String encoding) {
    System.out.println(errorText + encoding + " is not available on this"
        + " computer, please ensure your laptop is correctly set up.");
  }
  
  public static void invalidNameException(String invalidName) {
    String error = (errorText + invalidName);
    System.out.println(error + " contains special characters.");
  }

  public static void invalidExistenceName(String invalidName) {
    String error = (errorText + invalidName);
    System.out.println(error + " doesn't exist in the FileSystem.");
  }

  public static void nameExistsException(String name) {
    String error = (errorText + name);
    System.out.println(error + " already exists in given path.");
  }

  public static void invalidSyntaxException(String cmd) {
    String error = (errorText + "Invalid syntax for " + cmd);
    System.out.println(error + ". Check man " + cmd);
  }

  public static void invalidFileName(Object invalidName) {
    String error = (errorText + invalidName);
    System.out.println(error + " is not a file inside the FileSystem");
  }

  public static void invalidComputerFileName(Object invalidName) {
    String error = (errorText + invalidName);
    System.out.println(error + " is not a valid file on this computer.");
  }

  public static void parentPathException(String oldPath, String newPath) {
    String error = (errorText + oldPath + " is a parent path of " + newPath);
    System.out.println(error + ". Cannot perform cp/mv.");
  }

  public static void invalidCommand(String cmd) {
    String error = (errorText + cmd);
    System.out.println(error + " is not a valid command.");
  }

  public static void emptyDirectoryStack() {
    System.out.println("The Directory Stack is empty, could not pop.");
  }

  public static void leadingWhiteSpacesException(String input) {
    System.out.println(input + " contains leading whitespaces.");
  }

  public static void invalidLoadCall() {
    System.out.println(errorText + "Load cannot be called once a FileSystem"
        + "has been initialized.");
  }
  
  public static void unreadableFile() {
    System.out.println(errorText + "The given file is not readable, please"
        + " ensure that it contains only text, and is either a .txt or "
        + ".html.");
  }
}
