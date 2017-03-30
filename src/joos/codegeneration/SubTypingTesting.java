package joos.codegeneration;

import joos.environment.Environment;
import joos.exceptions.InvalidSyntaxException;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by yifei on 26/03/17.
 */
public class SubTypingTesting {
	List<String> classlist;
	int id;
	//int[][] lookuptable;
	public void initilize(StringWriter writer, Map<String, Environment> PackageMap){
		for(String s:PackageMap.keySet()){
			classlist.add(s);
		}
		writer.write("global $$subtypecheckingtable\n");
		writer.write("$$subtypecheckingtable:");
		//lookuptable=new int[classlist.size()][classlist.size()];
		for(int i=0;i<classlist.size();i++){
			for(int j=0;j<classlist.size();i++) {
				if(i==j){
					//lookuptable[i][j]=1;
					writer.write("dd 0x1\n");
					break;
				}
				Environment childenv=PackageMap.get(classlist.get(i));
				Environment parentenv=PackageMap.get(classlist.get(j));
				boolean assible=false;
				try {
					assible=childenv.extendsEnvironment(parentenv,PackageMap);
					if(assible){
						//lookuptable[i][j]=1;
						writer.write("dd 0x1\n");
						break;
					}
					assible=childenv.implementsEnvironment(parentenv,PackageMap);
					if(assible){
						//lookuptable[i][j]=1;
						writer.write("dd 0x1\n");
						break;
					}
				} catch (InvalidSyntaxException e) {
					e.printStackTrace();
				}

				//lookuptable[i][j]=0;
				writer.write("dd 0x0\n");
			}
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
