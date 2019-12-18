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
import mockJShell.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import org.junit.*;
import driver.*;


public class EchoFileTest {
	private static MockFileSystem<Directory> fs = MockFileSystem.getInstance();
	private final ByteArrayOutputStream outContent = 
			new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private static File file;
	private static Directory directory;
	private static MockValidator valid;
	private static MockJShell mockMiniJShell = new MockJShell();

	
	@BeforeClass
	public static void setUp() {
		file = new File("file", "hi");
		directory = new Directory("RandomDir");
		fs.getRoot().addDirectory(directory);
		directory.addFile(file);
		
	}
	@AfterClass
	public static void tearDownClass() throws Exception {
		MockCommandHistory.clearHistory();
		Field field = (fs.getClass()).getDeclaredField("fs");
		field.setAccessible(true);
		field.set(null, null);
	}
	@Before
	public void setUpStream() {
		// Make a new scanner before every test case
		System.setOut(new PrintStream(outContent));
	}
	@After
	public void setDown() {
		System.setOut(originalOut);
	}
	@Test
	public void fileOverwrite() {
		String output = ((MockEchoFile) MockCommand.execute("echo")).runCommand
				("\"hey\" > /RandomDir/file");
	    String expectedOutput = "hey";
	    Assert.assertEquals(expectedOutput, output);
	}
	@Test
	public void printString() {
		String output = ((MockEchoFile)MockCommand.execute("echo")).runCommand
				("\"hello\"");
	    String expectedOutput = "hello";
	    Assert.assertEquals(expectedOutput , output);
	}
	@Test
	public void fileAppend() {
		valid = new MockValidator("echo \"hey\" >> /RandomDir/file");
	    String expectedOutput = "hi\nhey";
	    Assert.assertEquals(expectedOutput, file.getText());
	}
	@Test
	public void invalidStringSyntax() {
		valid = new MockValidator ("echo hey");
	    String expectedOutput = 
	    		"Error: Invalid syntax for echo. Check man echo"
	    		+ System.getProperty("line.separator");
	    Assert.assertEquals(expectedOutput, outContent.toString());
	}
	@Test
	public void newFileExistInCorrectDir() {
		String input = "echo \"hey\" > file5";
		valid = new MockValidator(input);
	    File expectedOutput = 
	    		fs.getCurDir().getFile("file5");
	    Assert.assertTrue(expectedOutput instanceof File);
	}
	@Test
	public void testSpaces() {
		String input = "echo          \"coolStuff\"    >>   /RandomDir/file";
		valid = new MockValidator(input);
	    String expectedOutput = "hi\nhey\ncoolStuff";
	    Assert.assertEquals(expectedOutput, file.getText());
		
	}
}

