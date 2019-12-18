// **********************************************************
// Assignment2:
// Student1: Muizz Uddin Ahmed
// UTORID user_name: ahmed323
// UT Student #: 1004160043
// Author:
//
// Student2: Nicholas Michael Gibson Elliott
// UTORID user_name: ellio232
// UT Student #: 1004416713
// Author:
//
// Student3: Anthony Alaimo
// UTORID user_name: alaimoa1
// UT Student #: 1004421814
// Author:
//
// Student4: Millan Singh Khurana
// UTORID user_name: khuran53
// UT Student #: 1004169259
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package mockJShell;

import driver.Directory;
import driver.DirectoryStack;

/**
 * This class corresponds to the popd method, which pops the next directory off
 * of the DirectoryStack when the input "popd" is entered.
 * @author Nicholas Michael Gibson Elliott
 */
public class MockDirectoryPop extends MockCommand {
	private String documentation = "\n Description: \n\n Removes the top entry"
			+ " from the directory stack and makes it the current working"
			+ " directory.\n\n Examples:\n\n popd\n\n Possible Exceptions:"
			+ " invalidSyntaxException, emptyDirectoryStack\n";

	/**
	 * This method calls the popDirectory method in this class, DirectoryPop,
	 * with no parameters. The input should be an empty String, otherwise an
	 * exception must be used.
	 * 
	 * @param input The input entered by the user to be used for the popd
	 * method, should be empty String.
	 * @return empty String
	 */
	public String runCommand(String input) {
		// If the inputed is empty, as wanted (and required)
		if (input.length() == 0) {
			// Call the popDirectory method
			popDirectory();
		}
		// Otherwise, which is an invalid input from the user
		else {
			/* Call an invalidPathException in ExceptionHandler class with the
			 * given String input as its parameter
			 */
			MockExceptionHandler.invalidSyntaxException("popd");;
		}
		return "";
	}

	/**
	 * This method pops off the directory on the top of the DirectoryStack and
	 * sets the that popped directory to be the new current working directory.
	 */
	private void popDirectory() {
		/* Call the pop method in the DirectoryStack class and set it to a new
		 * Directory object called pop
		 */
		Directory pop = DirectoryStack.pop();
		// Set the current working directory to be pop, the popped Directory
		fs.setCurDir(pop);
	}

	/**
	 * This method prints the documentation corresponding to the popd command,
	 * explaining the necessary syntax and what the command does.
	 */
	@Override
	public String printDocumentation() {
		// TODO Auto-generated method stub
	  return(documentation);
	}
}
