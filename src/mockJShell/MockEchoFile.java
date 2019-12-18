//**********************************************************
//Assignment2:
//Student1: Muizz Uddin Ahmed
//UTORID user_name: ahmed323
//UT Student #: 1004160043
//Author:
//
//Student2: Nicholas Michael Gibson Elliott
//UTORID user_name: ellio232
//UT Student #: 1004416713
//Author:
//
//Student3: Anthony Alaimo
//UTORID user_name: alaimoa1
//UT Student #: 1004421814
//Author:
//
//Student4: Millan Singh Khurana
//UTORID user_name: khuran53
//UT Student #: 1004169259
//Author:
//
//
//Honor Code: I pledge that this program represents my own
//program code and that I have coded on my own. I received
//help from no one in designing and debugging my program.
//I have also read the plagiarism section in the course info
//sheet of CSC B07 and understand the consequences.
//*********************************************************
package mockJShell;
/**
 * This class represents the echo command, which overwrites the given text of a
 * file with the given text or if no file is given, just prints out the given
 * string
 * @author Anthony Alaimo
 */
public class MockEchoFile extends MockCommand{
  /**
   * String that holds the documentation for the command Echo.
   */
	private String documentation = ("\n Description: \n\n Prints the current"
		      + " working directory (including the whole path).\n\n "
		      + "Examples:\n\n pwd \n\n Possible Exceptions: "
		      + "n\n invalidPathException, invalidSyntaxException \n");
	/**
	 * This method takes a string and prints it back out to the user
	 * 
	 * @param text, a string representing the wanted text of a file
	 */
	// if no file is given, print the text onto the shell
	public String echo(String text) {
		return (text);
	}

	/**
	 * This method divides the echo class into 3 main methods and figures out
	 * which is the necessary method to be ran.
	 * 
	 * @param input, a string representing the input of a user, trying to 
	 * use the echo command.
	 */
	public String runCommand(String input) {
		String text = input.substring(input.indexOf("\"") 
				+ 1, Math.max(0,input.lastIndexOf("\"")));
		// the text can not contain double quotes
		if (text.contains("\"") || input.contains("\"") == false){
			MockExceptionHandler.invalidSyntaxException("echo");
			return "";
		}
		return this.echo(text);
	}

	@Override
	public String printDocumentation() {
		// TODO Auto-generated method stub
	  return(documentation);
	}

}