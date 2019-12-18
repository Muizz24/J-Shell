// **********************************************************
// Assignment2:
// Student1: Muizz Uddin Ahmed
// UTORID user_name: ahmed323
// UT Student #: 1004160043
// Author:
//
// Student2: Nicholas Michael Gibson Elliott
// UTORID user_name: ellio232
// UT Student #: 1004416713
// Author:
//
// Student3: Anthony Alaimo
// UTORID user_name: alaimoa1
// UT Student #: 1004421814
// Author:
//
// Student4: Millan Singh Khurana
// UTORID user_name: khuran53
// UT Student #: 1004169259
// Author:
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************
package mockJShell;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import driver.Command;
import driver.CommandHistory;
import driver.ExceptionHandler;
import driver.Validator;

public class MockLoader extends Command{


  @Override
  public String runCommand(String input){
    if(CommandHistory.getCommandHistory().size() == 0) {
      loadCommands(input);
    }else{
      ExceptionHandler.invalidLoadCall();
    }
    return "";
  }

  private void loadCommands(String input){
    List<String> commandList = new ArrayList<String>();
    File file = new File(input);
    try {
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String command;
      PrintStream originalStream = System.out;
      PrintStream temporaryStream = new PrintStream(new OutputStream(){
        public void write(int b) {
        }
      });
      System.setOut(temporaryStream);
      while ((command = reader.readLine()) != null) {
        if (command.split(" ")[0].equals("save")) {
          CommandHistory.addHistory(command);
        }else {
          Validator validator = new Validator(command);
        }
      }
      System.setOut(originalStream);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      ExceptionHandler.invalidComputerFileName(input);

    } catch (IOException e) {
      // TODO Auto-generated catch block
      ExceptionHandler.unreadableFile();
    }
  }

  @Override
  public String printDocumentation() {
    return "";
    // TODO Auto-generated method stub

  }

}
