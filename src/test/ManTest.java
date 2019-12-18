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
import mockJShell.*;

public class ManTest {
  private static MockJShell mockJShell = new MockJShell();
  private static String output = "";
  private static String expectedOutput = "";
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  
    @Before
    public void setUp() throws Exception {
      // Make a new scanner before every test case for catching errors
      System.setOut(new PrintStream(outContent));
      // Empty the output
      output = "";
   }
    
    @After
    public void tearDown() throws Exception {
      // Destroy the old scanner after every test case meant for error catching
      System.setOut(originalOut);
    }

	@Test
	public void manSingleCommand() {
		/*
		 * this test checks that the man command prints the appropriate
		 * documentation for the given command
		 */
	  output = ((MockCommand) MockCommand.execute("man")).runCommand("ls");
	  expectedOutput = 
	    (" Description:\n--------------------------------------------\n\tPrin"
          + "ts the contents of the given directory (or the current directory "
          + "if the user does not input one).\n\n Syntax:\n--------------"
          + "------------------------------\n\t/#: ls [-R] [PATH(S)]\n\t Where"
          + " -R acts as the signal to recursively list inner directories and"
          + " files \n\t and PATH(S) show content in one or more paths dependi"
          + "ng on\n\thow many the user desires to give.\n\n Examples:\n------"
          + "------------------------------------------\n\t/#: ls\n\t/: photos"
          + " songs games \n\t/#: ls /photos/2018\n\t 2018: beach cottage\n\n"
          + "Possible Exceptions:\n-------------------------------------------"
          + "-----\n invalidPathException \n");
	  Assert.assertEquals(expectedOutput, output);
	}
	
	@Test
	public void manMultipleCommands() {
		/*
		 * this test checks that if multiple commands are given at once
		 * they are printed with correct format
		 */
	  output = ((MockCommand) MockCommand.execute("man")).runCommand("cd pwd");
	  expectedOutput = "Error: cd pwd is not a valid command." +
	  System.getProperty("line.separator");
	  Assert.assertEquals(expectedOutput, outContent.toString());
	}
	
	@Test
	public void manIncorrectCommand() {
		/*
		 * this test checks that if an incorrect command is given then an
		 * appropriate error will be shown
		 */
	  output = ((MockCommand) MockCommand.execute("man")).runCommand("you");
	  expectedOutput = "Error: you is not a valid command." +
	      System.getProperty("line.separator");
	  Assert.assertEquals(expectedOutput, outContent.toString());
	}

}
