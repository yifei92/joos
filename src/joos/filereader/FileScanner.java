package joos.filereader;

import java.lang.StringBuilder;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

public class FileScanner {

	public String readFile(String fileName) {
        StringBuilder program = new StringBuilder();
        try {
            FileReader fr = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fr);
            String currentLine;
            while ((currentLine = br.readLine()) != null) {
                program.append(currentLine);
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