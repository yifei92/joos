package joos.codegeneration;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import joos.commons.MethodSignature;
import joos.environment.Environment;
import joos.environment.Environment.EnvironmentType;

import static joos.environment.Environment.getMethodSignature;
import static joos.environment.EnvironmentUtils.getEnvironmentType;

public class Interfaces {

  /**
   * List of all of the interfaces in our program
   */
  private static List<Environment> mListOfInterfaces = null;

  private static void generateInterfacesList(Map<String, Environment> packageMap) {
    if (mListOfInterfaces != null) {
      return;
    }
    mListOfInterfaces = new ArrayList<>();
    for(String key: packageMap.keySet()) {
      Environment interface = packageMap.get(key);
      if (getEnvironmentType(interface) == EnvironmentType.INTERFACE) {
        // this environment is an interface. We need to put it in our interface list
        mListOfInterfaces.add(interface);
      }
    }
  }

  public static void generateInterfaceTable(FileWriter writer, Environment environment, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    generateInterfacesList();
    String className = CodeGeneration.getClassLabel(environment);
    // list of all methods in this class
    List<Environment> methodList = environment.getAllMethodEnvironments();
    // list of all interfaces this class should have implemented
    List<Environment> implementedInterfaces = EnvironmentUtils.getImplementedEnvironments(classEnvironment, packageMap);
    writer.write("global InterfaceTABLE$" + className + "\InterfaceTABLE$" + className + ":\n");
    // Iterate over the global interface list
    for (Environment interface : mListOfInterfaces) {
      boolean foundImplementation = false;
      for (Environment implementedInterface : implementedInterfaces) {
        // Check this interface against the interfaces this class implements
        if (interface == implementedInterface) {
          foundImplementation = true;
          // this class implements this interface so we should add entries for each of the 
          // implemented methods
          for(Environment abstractMethod: interface.mChildrenEnvironments) {
            // find the implementation of this method in this class
            for(Environment method : methodList) {
              if(method.implementsAbstractMethod(abstractMethod)) {
                MethodSignature signature = Environment.getMethodSignature(method, packageMap, null);
                writer.write("  dd " + CodeGeneration.getMethodLabel(method, signature) + "\n");
              }
            }
          }
          break;
        }
      }
      if(!foundImplementation) {
        // if we didn't find an implementation of this interface then we should put a blank entry for
        // for each of the methods in this interface
        if(interface.mChildrenEnvironments != null) {
          for(Environment abstractMethod : interface.mChildrenEnvironments) {
            writer.write("  dd 00000000\n");    
          }
        }
      }
    }
    writer.write("\n");
  } 
}