package joos.filereader;

import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class FileScanner {

    /**
     * Returns a scanned list of JoosFiles. 
     * Null if something went wrong.
     */
    public static List<JoosFile> scanFiles(String[] filePaths) {
        if (filePaths.length < 0) {
            System.out.println("Invalid number of arguments!");
            System.out.println("Format: ./joosc <filepath1> <filepath2> <filepath3> ... ");
            return null;            
        }
        FileScanner fileScanner = new FileScanner();
        List<JoosFile> joosFiles = new ArrayList<>();
        for (String filePath : filePaths) { 
            String programString = fileScanner.readFile(filePath);
            if (programString == null || programString.length() == 0) {
                System.out.println("There was a problem scanning file:");
                System.out.println(filePath);
                return null;
            }
            joosFiles.add(new JoosFile(filePath, programString));
        }
        return joosFiles;
    }

	private String readFile(String fileName) {
        StringBuilder program = new StringBuilder();
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String currentLine;
            int value = 0;
            while((value = br.read()) != -1) {
                program.append((char)value);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }

        String programString = program.toString();
        if (programString == null || programString.length() == 0) {
            System.out.println("Program is empty!");
            return null;
        }
        return programString;
	}
}