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