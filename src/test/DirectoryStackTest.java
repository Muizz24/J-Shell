package test;
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

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.*;

import driver.Directory;
import driver.DirectoryStack;

public class DirectoryStackTest {
	private static Directory dir1;
	private static Directory dir2;
	private final ByteArrayOutputStream outContent = new
			ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	@BeforeClass
	public static void setUpClass() {
		// Set dir1 to be a new Directory with name Aaron
		dir1 = new Directory("Aaron");
		// Set dir2 to be a new Directory with name Bobby
		dir2 = new Directory("Bobby");
	}
	
	@Before
	public void setUpStream() {
		// Make a new scanner before every test case
	    System.setOut(new PrintStream(outContent));
	    while (DirectoryStack.peak() != null) {
	      DirectoryStack.pop();
	    }
	}
	
	@After
	public void tearDown() {
		// Destroy the old scanner after every test case
	    System.setOut(originalOut);
	}
	
	@Test
	public void testPush() {
		// Push dir1 to the top of the stack
		DirectoryStack.push(dir1);
		/* Peak at the top element of the stack to make sure its name is Aaron,
		 * as desired
		 */
		assertTrue(DirectoryStack.peak().getName().equals("Aaron"));
	}
	
	@Test
	public void testPopCheck() {
		// Push dir2 to the top of the stack
		DirectoryStack.push(dir2);
		/* Pop off the top element of the stack and make sure its name is
		 * Bobby, as desired
		 */
		assertTrue(DirectoryStack.pop().getName().equals("Bobby"));
	}
	
	@Test
	public void testPopRemainingCheck() {
		// Push dir2 to the top of the stack
		DirectoryStack.push(dir2);
		// Pop off the top element of the stack
		DirectoryStack.pop();
		/* Check to make sure the top element of the stack is null, meaning the
		 * stack is empty, as desired
		 */
		assertTrue(DirectoryStack.peak() == null);
	}
	
	@Test
	public void testPushTwoPopOnePopCheck() {
		// Push dir1 to the top of the stack
		DirectoryStack.push(dir1);
		// Push dir2 to the top of the stack
		DirectoryStack.push(dir2);
		/* Pop off the top element of the stack, and make sure its name is
		 * Bobby, as desired
		 */
		assertTrue(DirectoryStack.pop().getName().equals("Bobby"));
		// Pop from DirectoryStack in order to make it empty
		DirectoryStack.pop();
	}
	
	@Test
	public void testPushTwoPopOneRemainingCheck() {
		// Push dir1 to the top of the stack
		DirectoryStack.push(dir1);
		// Push dir2 to the top of the stack
		DirectoryStack.push(dir2);
		// Pop off the top element of the stack
		DirectoryStack.pop();
		/* Peak at the top element of the stack, and make sure its name is
		 * Aaron, as desired
		 */
		assertTrue(DirectoryStack.peak().getName().equals("Aaron"));
		// Pop from DirectoryStack in order to make it empty
		DirectoryStack.pop();
	}
	
	@Test
	public void testEmptyDirectoryStackException() {
		// Pop the top element from the stack, which is currently empty
		DirectoryStack.pop();
		/* Make sure what is printed is the emptyDirectoryStackException text
		 * in ExceptionHandler
		 */
		Assert.assertEquals("The Directory Stack is empty, could not pop." 
		+ System.getProperty("line.separator"), outContent.toString());
	}
}
