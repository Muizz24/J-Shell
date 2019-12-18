// **********************************************************
// 
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

import java.util.ArrayList;
import mockJShell.MockFileSystem;

/**
 * This class uses an ArrayList of Directories to represent a Directory
 * stack with push and pop methods that follow the usual Last In, First Out
 * (LIFO) behavior of a stack. This class is used by the DirectoryPush and
 * DirectoryPop classes, which are the classes corresponding to pushd and popd,
 * respectively.
 * @author Nicholas Michael Gibson Elliott
 */
public class DirectoryStack {

	// Arraylist of directories used for the stack
	private static ArrayList<Directory> stack = new ArrayList<Directory>();

	/**
	 * This method pushes the directory given as its parameter onto the
	 * Arraylist of Directories, called stack.
	 * 
	 * @param dir The desired directory to be pushed onto the top of the stack.
	 */
	public static void push(Directory dir) {
		// Push the given directory onto the stack at the beginning
		stack.add(dir);
	}

	/**
	 * This method pops off the top Directory from the stack, meaning it
	 * removes it from the stack and returns it.
	 * 
	 * @return Returns the Directory popped from the stack.
	 */
	public static Directory pop() {
		// Create an Directory object named popped
		Directory popped;
		// If the stack is empty
		if (peak() == null) {
			// Create emptyDirectoryStack exception
			ExceptionHandler.emptyDirectoryStack();
			// Set popped to the current working directory
			popped = MockFileSystem.getInstance().getCurDir();
		}
		// Otherwise, meaning the stack is non-empty
		else {
			/* Pop the first directory in the stack off and set its value to
			 * the directory popped
			 */
			popped = stack.remove(stack.size() - 1);
		}
		// Return the new directory
		return popped;
	}
	
	public static Directory peak() {
		// Create a Directory, top, to store the item at the top of the stack
		Directory top;
		// If the size of the stack is 0, meaning the stack is empty
		if (stack.size() == 0) {
			// Set top to null
			top = null;
		}
		// Otherwise, meaning the stack is non empty
		else {
			// Set top to the final item in the list stack, which is its top
			top = stack.get(stack.size() - 1);
		}
		// Return top
		return top;
	}
}
