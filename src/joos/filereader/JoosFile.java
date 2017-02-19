package joos.filereader;

public class JoosFile {

	// Filename of the joos file that was scanned in
	public final String mFilePath;
	// String that contains the contents of the file
	public final String mProgram;

	public JoosFile(String filePath, String program) {
		mFilePath = filePath;
		mProgram = program;
	}

	public String getFileName() {
		String filename=mFilePath;
		if(mFilePath.lastIndexOf("/")!=-1){
			filename=mFilePath.substring(mFilePath.lastIndexOf("/")+1);
		}
		return filename;
	}
}