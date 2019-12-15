# J-Shell
Mocked command line interface on Java's console with multiple commands such as cat, echo, curl and more listed below on an artificial FileSystem. The code was done with a group of 4 friends (including me) through a 4 month agile scrum method.

Commands available:
  1-find: Command that finds if a specific file or directory with name in quotations exists in the given path's directories.
  
  2-history: Prints out all the input the user has been giving, including typos and lines with wrong syntax.
  
  3-cp: This is the corresponding class for the command cp, which copies the contents from a given OLDPATH into a given NEWPATH.
  
  4-cd: This class corresponds to the cd method, which changes the current working directory to the directory DIR, when an input in the            format, "cd DIR", is entered.
  
  5-ls: Command in Jshell that prints a Dir's contents in the Filesystem.
  
  6-mkdir: Command in Jshell that makes new Directories in the FileSystem.
  
  7-pwd: Command in Jshell that prints current working dir in the FileSystem.
  
  8-exit: A command in JShell that exits the entire program and stops requesting input from the user once it is called.
  
  9-pushd: This class corresponds to the pushd method, which pushes the current working directory onto the DirectoryStack, and changes the             current working directory to DIR when an input in the format, "popd DIR", is entered.
  
  10-popd: This class corresponds to the popd method, which pops the next directory off of the DirectoryStack when the input "popd" is entered.
  
  11-echo: This class represents the echo command, which overwrites the given text of a file with the given text or if no file is given, just prints out the given string
  
  12-tree: Class responsible for the command 'tree' which takes in no input other than the command name itself and prints a tree of the given fs.
  
  13-man: A Command in JShell that prints the documentation, examples, and possible exceptions for the given command.
  
  14-mv: This is the corresponding class for the command cp, which copies the contents from a given OLDPATH into a given NEWPATH
  
  15-cat: This class represents the cat command, which prints the contents of a file, for the user too view.
  
  16-save: A Command in JShell that saves the entire state of the current FileSystem  as a file on the user's actual computer at the desired path.
  
  17-load: A Command in JShell that loads up a save file that represents a FileSystem, so that the user can continue from where they last left off. 
  
  18-curl: A Command in JShell that reads the text on a given URL, and puts all of that text into a file in the current working directory.
  

