package joos.codegeneration;

import joos.environment.Environment;
import joos.exceptions.InvalidSyntaxException;
import joos.typechecking.TypeCheckingEvaluator;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class SubTypingTesting {
	private List<String> classlist = new ArrayList<>();
	private int id;
	private Map<String, Environment> packageMap;

	public SubTypingTesting(Map<String, Environment> packageMap) {
		this.packageMap = packageMap;
		this.initialize();
	}

	//int[][] lookuptable;
	public void initialize(){
		StringWriter writer = new StringWriter();
		classlist.add("shot");
		classlist.add("byte");
		classlist.add("char");
		classlist.add("int");
		classlist.add("boolean");
		for(String s:packageMap.keySet()){
			classlist.add(s);
			classlist.add(s+"[]");
		}
		writer.write("global subtypecheckingtable\n");
		writer.write("subtypecheckingtable:");
		//lookuptable=new int[classlist.size()][classlist.size()];
		for(int i=0;i<classlist.size();i++){
			for(int j=0;j<classlist.size();j++) {
				if (i == j) {
					//lookuptable[i][j]=1;
					writer.write("dd 0x1\n");
					break;
				}
				boolean assible = TypeCheckingEvaluator.assignable(classlist.get(j), classlist.get(i), packageMap);
				Environment childenv = packageMap.get(classlist.get(i));
				Environment parentenv = packageMap.get(classlist.get(j));
				;
				if (assible) {
					//lookuptable[i][j]=1;
					writer.write("dd 0x1\n");
					break;
				} else {
					writer.write("dd 0x0\n");
				}
			}
		}

		File file = new File("subteypchecking.s");
		try {
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write("\n");
			fileWriter.write(writer.toString());
			fileWriter.flush();
			fileWriter.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	public int getrow(String classname){
		int index=classlist.indexOf(classname);
		return index*4*classlist.size();
	}

	public int getoffset(String classname){
		int index=classlist.indexOf(classname);
		return index*4;
	}

	public int getuniqueid(){
		id++;
		return id;
	}

}
