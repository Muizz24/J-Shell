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

public class ViewFileTest {
	private static MockFileSystem<Directory> fs = MockFileSystem.getInstance();
	private final ByteArrayOutputStream outContent = 
			new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	//
	private static File file;
	private static File file2;
	private static File file3;
	private static Directory directory;
	private static MockValidator valid;
	private static MockJShell mockMiniJShell = new MockJShell();

	@BeforeClass
	public static void setUpClass() throws Exception {
		file = new File("file1", "hey");
		file2 = new File("file2", "hi");
		file3 = new File("file3", "hello");
		directory = new Directory("RandomDir");
		fs.getRoot().addDirectory(directory);
		directory.addFile(file);
		directory.addFile(file2);
		directory.addFile(file3);

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
	public void testFileContent() {
		MockFileSystem.getInstance().setCurDir(directory);
		String output = ((MockViewFile) MockCommand.execute("cat")).runCommand
				("/RandomDir/file2");
		String expectedOutput = "file2: " 
				+ fs.getCurDir().getFile("file2").getText();
		Assert.assertEquals(expectedOutput , output);
	}
	@Test
	public void testMultipleFileContent() {
		String output = ((MockViewFile) MockCommand.execute("cat")).runCommand
				("file1 file2 file3");
		String expectedOutput = "file1: " + file.getText()
		+"\n\n\nfile2: " + file2.getText()
		+"\n\n\nfile3: " + file3.getText();
		Assert.assertEquals(expectedOutput, output);
	}
	@Test
	public void testInvalidFileName() {
		String input = "cat file";
		valid = new MockValidator(input);
		String expectedOutput = "Error: file is not a valid path."
				+ System.getProperty("line.separator");
		Assert.assertEquals(expectedOutput, outContent.toString());

	}
	@Test
	public void testValidAndInvalidFileName() {
		String input = "cat file file2 file3";
		valid = new MockValidator(input);
		String expectedOutput = "Error: file is not a valid path."
				+ System.getProperty("line.separator")
				+"file2: " + file2.getText()
				+"\n\n\nfile3: " + file3.getText() 
				+ System.getProperty("line.separator");
		Assert.assertEquals(expectedOutput, outContent.toString());

	}
	@Test
	public void testInvalidPath() {
		String input = "cat /badPath/file";
		valid = new MockValidator(input);
		String expectedOutput = "Error: /badPath/file is not a valid path."
				+ System.getProperty("line.separator");
		Assert.assertEquals(expectedOutput, outContent.toString());

	}
	@Test
	public void testSpacesBeforeFile() {
		String output = ((MockViewFile) MockCommand.execute("cat")).runCommand
				("cat          file1");
		String expectedOutput = "file1: " + file.getText();
		Assert.assertEquals(expectedOutput, output);

	}
	@Test
	public void testSpacesBetweenMultipleFiles() {
		String output = ((MockViewFile) MockCommand.execute("cat")).runCommand
				("cat        file1      file2        file3");
		String expectedOutput = "file1: " + file.getText()
		+"\n\n\nfile2: " + file2.getText()
		+"\n\n\nfile3: " + file3.getText();
		Assert.assertEquals(expectedOutput, output);

	}

}