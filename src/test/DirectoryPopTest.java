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

import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;

import org.junit.*;

import mockJShell.*;
import driver.ExceptionHandler;
import driver.Directory;
import driver.File;
import driver.DirectoryStack;

public class DirectoryPopTest {
	private static MockFileSystem<Directory> fs;
	private static Directory dir1;
	private static Directory dir2;
	private static Directory dir3;
	private static Directory dir4;
	private static MockJShell mockShell = new MockJShell();
	private final ByteArrayOutputStream outContent = new
			ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	
	@Before
	public void setUpStream() {
		// Make a new scanner before every test case
	    System.setOut(new PrintStream(outContent));
	    fs = MockFileSystem.getInstance();
        dir1 = new Directory("Bob");
        dir2 = new Directory("Bobby");
        dir3 = new Directory("Bill");
        dir4 = new Directory("Billy");
        fs.getRoot().addDirectory(dir1);
        dir1.addDirectory(dir2);
        fs.getRoot().addDirectory(dir3);
        dir3.addDirectory(dir4);
	}
	
	@After
	public void tearDown() throws Exception {
		fs.setCurDir(fs.getRoot());
		System.setOut(originalOut);
		MockCommandHistory.clearHistory();
        Field field = (fs.getClass()).getDeclaredField("fs");
        field.setAccessible(true);
        field.set(null, null);
	}
	
	@Test
	public void popDir() {
		fs.setCurDir(dir1);
		DirectoryStack.push(dir3);
		String input = "popd";
		new MockValidator(input);
		assertTrue(dir3 == fs.getCurDir());
	}
	
	@Test
	public void popEmptyException() {
		fs.setCurDir(dir4);
		String input = "popd";
		new MockValidator(input);
		Assert.assertEquals("The Directory Stack is empty, could not pop."
				+ System.getProperty("line.separator"), outContent.toString());
	}
	
	@Test
	public void invalidSyntaxTest() {
		fs.setCurDir(dir2);
		String input = "popd dir1";
		new MockValidator(input);
		Assert.assertEquals("Error: Invalid syntax for popd. Check man popd"
				+ System.getProperty("line.separator"), outContent.toString());
	}

}
