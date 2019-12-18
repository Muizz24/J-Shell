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
import org.junit.*;
import driver.*;
import java.lang.reflect.Field;


public class GetterTest {

	private static MockFileSystem<Directory> fs = MockFileSystem.getInstance();
	private final ByteArrayOutputStream outContent = 
			new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	private static MockValidator valid;
	private static MockJShell mockMiniJShell = new MockJShell();

	@BeforeClass
	public static void setUpClass() throws Exception {

	}
	@AfterClass
	public static void tearDownClass() throws Exception {
		MockCommandHistory.clearHistory();
		Field field = (fs.getClass()).getDeclaredField("fs");
		field.setAccessible(true);
		field.set(null, null);
	}
	@Before
	public void setUpStream(){
		// Make a new scanner before every test case
		System.setOut(new PrintStream(outContent));
	}
	@After
	public void setDown(){
		System.setOut(originalOut);
	}
	@Test
	public void getCorrectURLContents() {
		String output = ((MockGetter) MockCommand.execute("get")).runCommand
				("http://randomURL.com");
		String expectedOutput = "";
		Assert.assertEquals(expectedOutput , outContent.toString());
	}
	@Test
	public void invalidGetCommand() {
		String input = "get";
		valid = new MockValidator(input);
		String expectedOutput = "Error:  is not a valid command."
				+ System.getProperty("line.separator");
		Assert.assertEquals(expectedOutput, outContent.toString());
	}
	@Test
	public void invalidURLlink() {
		String output = ((MockGetter) MockCommand.execute("get")).runCommand
				("http://randomURL.com");
		String expectedOutput = "";
		Assert.assertEquals(expectedOutput , outContent.toString());
	}

}
