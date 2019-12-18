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
package test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.*;

import mockJShell.*;
import driver.Directory;
import driver.File;

public class DirectoryChangeTest {
	private MockFileSystem fs;
	private static Directory dir1;
	private static Directory dir2;
	private static Directory dir3;
	private static MockValidator v;
	private static MockJShell js = new MockJShell();
	private final ByteArrayOutputStream outContent = new
			ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@BeforeClass
	public static void setUpClass() {
		dir1 = new Directory("Bob");
		dir2 = new Directory("Bill");
		dir3 = new Directory("Paul");
	}
	@Before
	public void setUp() {
		fs = MockFileSystem.getInstance();
		fs.getRoot().addDirectory(dir1);
		dir1.addDirectory(dir2);
		dir2.addDirectory(dir3);
	}
	
	@Before
	public void setUpStream() {
	    // Make a new scanner before every test case
	    System.setOut(new PrintStream(outContent));
	  }

	@After
	public void tearDown() {
		// Set current working directory to root after each test
		fs.setCurDir(fs.getRoot());
		// Destroy the old scanner after every test case
	    System.setOut(originalOut);
	}
	
	// Test cd on a relative path
	@Test
	public void testCDRelative() {
		fs.setCurDir(dir1);
		String input = "cd Bill/Paul";
		v = new MockValidator(input);
		assertTrue(dir3 == fs.getCurDir());
	}

	// Test cd on a full path
	@Test
	public void testCDFullPath() {
		fs.setCurDir(dir3);
		String input = "cd /Bob/Bill";
		v = new MockValidator(input);
		assertTrue(dir2 == fs.getCurDir());
	}
	
	// Test an invalid path input on cd command for the exception handling
	@Test
	public void testInvalidPathException() {
		fs.setCurDir(dir1);
		String input = "cd John";
		v = new MockValidator(input);
		Assert.assertEquals("Error: John is not a valid path."
		+ System.getProperty("line.separator"), outContent.toString());
	}
	
	@Test
	public void testCDNoPath() {
		String input = "cd";
		v = new MockValidator(input);
		Assert.assertEquals("Error: Invalid syntax for cd. Check man cd"
			+ System.getProperty("line.separator"), outContent.toString());
	}
}
