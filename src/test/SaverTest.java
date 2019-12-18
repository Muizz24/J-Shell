package test;

import static org.junit.Assert.*;
import mockJShell.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.*;
import driver.*;
import java.lang.reflect.Field;

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

public class SaverTest {
	private static MockFileSystem<Directory> fs;
	private final ByteArrayOutputStream outContent = 
			new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private static MockJShell mockMiniJShell = new MockJShell();
	private MockSaver mockSaver = ((MockSaver) MockCommand.execute("save"));
	  private final String[] history = {"testcase", "Jibberish", "mkdir", 
	      "pwd failure", "zzz"};

	@Before
	public void setUpStream() throws Exception{
		// Make a new scanner before every test case
		System.setOut(new PrintStream(outContent));
		fs = MockFileSystem.getInstance();
		// Add some history to commandHistory
	    for (String oldInput : history) {
	      MockCommandHistory.addHistory(oldInput);
	    }
	}
	
	@After
	public void setDown() throws Exception{
		System.setOut(originalOut);
		MockCommandHistory.clearHistory();
        Field field = (fs.getClass()).getDeclaredField("fs");
        field.setAccessible(true);
        field.set(null, null);
	}
	
	@Test
	public void testForValidInput() throws Exception {
		mockSaver.runCommand(" /Users/bob/saveFile");
		// Assume if no error output is given, test case succeed.
		Assert.assertEquals("", outContent.toString());
	}
	
	@Test
	public void testForInvalidInput() throws Exception {
      mockSaver.runCommand(" /User!!!s/bobAin't!saveFile");
      // Assume if no error output is given, test case succeed.
      String expectedOutput = "Error: /User!!!s is not a valid path." +
      System.getProperty("line.separator");
      Assert.assertEquals(expectedOutput, outContent.toString());
    }
	
	@Test
    public void testForNoRedirectionWithSave() throws Exception {
        new MockValidator("save /Users/bob/mySave > OUTFILE");
        String output = ((MockCommand) MockCommand.execute("cat")).
            runCommand("OUTFILE");
        String expectedOutput = "Error: OUTFILE is not a valid path." +
            System.getProperty("line.separator");
        // Assume if no error output is given, test case succeed.
        Assert.assertEquals(expectedOutput, outContent.toString());
    }
	

}
