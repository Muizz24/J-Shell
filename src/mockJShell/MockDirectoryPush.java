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
 * This class corresponds to the pushd method, which pushes the current
 * working directory onto the DirectoryStack, and changes the current working
 * directory to DIR when an input in the format, "popd DIR", is entered.
 * 
 * @author Nicholas Michael Gibson Elliott
 */
public class MockDirectoryPush extends MockCommand {
	private String documentation = "\n Description: \n\n Saves the current"
			+ " directory and changes the given directory to the current"
			+ " working directory.\n\n Examples:\n\n pushd\n\n Possible"
			+ " Exceptions: invalidPathException, invalidSyntaxException\n";

	/**
	 * This method calls the pushDirectory method in this class,
	 * DirectoryPush, using String input to find the desired directory, which
	 * will be used as pushDirectory's only parameter.
	 * 
	 * @param input The input entered by the user to be used for the pushd
	 * command.
	 * @return empty String
	 */
	public String runCommand(String input) {
		// Removes all spaces in the input string
		input = input.trim().replaceAll(" +", "");
		// Find the Directory corresponding to the inputed path
		Directory push = (Directory) fs.pathToDirorFile(input);
		if (input == "") {
			// Call invalidSyntaxExpression
			MockExceptionHandler.invalidSyntaxException("pushd");
		}
		// If the directory was found
		else if (push != null) {
			// Call pushDirectory method with the Directory found
			this.pushDirectory(push);
		}
		return "";
	}

	/**
	 * This method pushes the current working directory onto the
	 * DirectoryStack, and then sets the current working directory to
	 * be the directory given to the method as its input, dir.
	 * 
	 * @param dir The directory found in run_command that is the new desired
	 * current working directory after the current one is pushed on the stack.
	 */
	private void pushDirectory(Directory dir) {
		// Push the current working directory onto the directory stack
		DirectoryStack.push(fs.getCurDir());
		// Set the current directory to the desired directory dir
		fs.setCurDir(dir);
	}

	/**
	 * This method prints the documentation corresponding to the pushd command,
	 * explaining the necessary syntax of inputs and what the command does.
	 */
	@Override
	public String printDocumentation() {
		// TODO Auto-generated method stub
	  return(documentation);
	}
}
