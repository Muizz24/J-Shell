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
import driver.*;
import mockJShell.MockCommand;
import mockJShell.MockCommandHistory;
import mockJShell.MockFileSystem;
import mockJShell.MockJShell;
import mockJShell.MockValidator;
import mockJShell.MockViewFile;
import java.lang.reflect.Field;

public class CommandHistoryTest {
  private static MockJShell mockJShell = new MockJShell();
  private static String output = "";
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final String[] history = {"testcase", "Jibberish", "mkdir", 
      "pwd failure", "zzz"};
  private MockFileSystem<Directory> fs;
  
  @Before
  public void setUp() throws Exception {
    // Make a new scanner before every test case for catching errors
    System.setOut(new PrintStream(outContent));
    // Make an empty output and empty command history
    MockCommandHistory.clearHistory();
    output = "";
    // Add some history to commandHistory
    for (String oldInput : history) {
      MockCommandHistory.addHistory(oldInput);
    }
    fs = MockFileSystem.getInstance();
    fs.getRoot().addFile(new File("OUTFILE","oldtext"));
  }

  @After
  public void tearDown() throws Exception {
    // Destroy the old scanner after every test case meant for error catching
    System.setOut(originalOut);
    Field field = (fs.getClass()).getDeclaredField("fs");
    field.setAccessible(true);
    field.set(null, null); // setting the ref parameter to null
  }

  @Test
  public void testForViewingAlotOfHistory() {
    MockCommandHistory.addHistory("history");
    output = ((MockCommand) MockCommand.execute("history")).runCommand("");
    Assert.assertEquals("1.testcase\n2.Jibberish\n3.mkdir\n4.pwd failure\n5.z"
    + "zz\n6.history\n",
        CommandHistoryTest.output);
  }

  @Test
  public void testForTruncatedHistory() {
    MockCommandHistory.addHistory("history 3");
    output = ((MockCommand) MockCommand.execute("history")).runCommand(" 3");
    Assert.assertEquals("4.pwd failure\n5.zzz\n6.history 3\n", output);
  }

  @Test
  public void testForTruncateHigherThanCommandHistorySize() {
    MockCommandHistory.addHistory("history 100");
    output = ((MockCommand) MockCommand.execute("history")).runCommand(" 100");
    Assert.assertEquals(
        "1.testcase\n2.Jibberish\n3.mkdir\n4.pwd failure\n5." +
    "zzz\n6.history 100\n", output);
  }

  @Test
  public void testFoInvalidSyntaxOfTruncateHistory() {
    MockCommandHistory.addHistory("history again");
    output = ((MockCommand) MockCommand.execute("history")).runCommand("again");
    Assert.assertEquals("Error: Invalid syntax for history. Check man history"
        + System.getProperty("line.separator"), outContent.toString());
  }

  @Test
  public void testForSpacesInbetweenCmdAndCmdinput() {
    MockCommandHistory.addHistory("history      1");
    output = ((MockCommand) MockCommand.execute("history")).runCommand("  "
        + "    1");
    Assert.assertEquals("6.history      1\n", output);
  }

  @Test
  public void testForTrailingWhiteSpacesAfterCmdinput() {
    MockCommandHistory.addHistory("history 1     ");
    output = ((MockCommand) MockCommand.execute("history")).runCommand(" 1   "
        + "  ");
    Assert.assertEquals("6.history 1     \n", output);
  }

  @Test
  public void testForWrongSyntaxAfterProperSyntax() {
    MockCommandHistory.addHistory("history 1 again");
    output = ((MockCommand) MockCommand.execute("history")).runCommand(" 1"
        + " again");
    Assert.assertEquals("Error: Invalid syntax for history. Check man history"
        + System.getProperty("line.separator"), outContent.toString());
  }

  @Test
  public void testForAppendOutputToOUTFILE() {
    fs.getRoot().addFile(new File("OUTFILE", "oldtext"));
    new MockValidator("history 1 >> OUTFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand(" "
        + "OUTFILE");
    Assert.assertEquals("OUTFILE: oldtext\n6.history 1 >> OUTFILE\n", output);
  }

  @Test
  public void testForOverWriteOutputToOUTFILE() {
    fs.getRoot().addFile(new File("OUTFILE", "oldtext"));
    new MockValidator("history 1 > OUTFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand(" "
        + "OUTFILE");
    Assert.assertEquals("OUTFILE: 6.history 1 > OUTFILE\n", output);
  }

  @Test
  public void testForNoErrorRedirection() {
    new MockValidator("history again > OUTFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand(""
        + " OUTFILE");
    Assert.assertEquals("OUTFILE: oldtext", output);
  }

  @Test
  public void testforInvalidRedirection() {
    new MockValidator("history 1 >>> OUTFILE");
    output = ((MockViewFile) MockCommand.execute("cat")).runCommand(""
        + " OUTFILE");
    Assert.assertEquals("OUTFILE: oldtext", output);
  }

}
