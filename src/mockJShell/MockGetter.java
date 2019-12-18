package mockJShell;
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


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import driver.ExceptionHandler;
import driver.File;

//Hard coded for testing purposes
public class MockGetter extends MockCommand {

	private String fileContents;

	
	@Override
	public String runCommand(String input) {
		if (input == ""){
			MockExceptionHandler.invalidCommand(input);	
		}
		fileContents = readLink(input);
		String fileName = input.substring(input.lastIndexOf("/")+1, 
				input.length());
		File file = new File(fileName, fileContents);
		fs.getCurDir().addFile(file);
		return "";
	}
	// TODO Auto-generated method stub

	private String readLink(String input){
		
		return "Abbas is a good prof";
	}

	@Override
	public String printDocumentation() {
		return null;
		// TODO Auto-generated method stub

	}

}
