package joos.codegeneration;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.StringWriter;
import java.io.IOException;

import joos.commons.MethodSignature;
import joos.environment.Environment;
import joos.environment.Environment.EnvironmentType;
import joos.exceptions.InvalidSyntaxException;

import static joos.environment.EnvironmentUtils.getEnvironmentType;

public class Interfaces {

  Map<String, Environment> packageMap;
  StringWriter writer;

  public Interfaces(Map<String, Environment> packageMap, StringWriter writer) {
    this.packageMap = packageMap;
    this.writer = writer;
  }

  /**
   * List of all of the interfaces in our program
   */
  private static List<Environment> mListOfInterfaces = null;
  private static int mInterfaceTableSize = -1;

  public int getInterfaceOffset(Environment methodEnvironment) {
    generateInterfacesList(packageMap);
    int offset = 0;
    for(Environment interfc : mListOfInterfaces) {
      if (interfc.mChildrenEnvironments != null) {
        for(Environment method : interfc.mChildrenEnvironments) {
          if (methodEnvironment == method) {
            break;
          }
          offset += 4;
        }
      }
    }
    return offset;
  }

  /**
   * Returns the size of the interface table in 32 byte chunks
   */
  public int getInterfaceTableSize(Map<String, Environment> packageMap) {
    generateInterfacesList(packageMap);
    if (mInterfaceTableSize == -1) {
      mInterfaceTableSize = 0;
      for(Environment interfc : mListOfInterfaces) {
        if(interfc.mChildrenEnvironments!= null) {
          mInterfaceTableSize+=interfc.mChildrenEnvironments.size();
        }
      }
    }
    return mInterfaceTableSize;
  }

  private void generateInterfacesList(Map<String, Environment> packageMap) {
    if (mListOfInterfaces != null) {
      return;
    }
    mListOfInterfaces = new ArrayList<>();
    for(String key: packageMap.keySet()) {
      Environment interfc = packageMap.get(key);
      if (getEnvironmentType(interfc) == EnvironmentType.INTERFACE) {
        // this environment is an interfc. We need to put it in our interfc list
        mListOfInterfaces.add(interfc);
      }
    }
  }

  public void generateInterfaceTable(Environment classEnvironment) throws IOException, InvalidSyntaxException {
    generateInterfacesList(packageMap);
    String className = Utils.getClassLabel(classEnvironment);
    // list of all methods in this class
    List<Environment> methodList = classEnvironment.getAllMethodEnvironments();
    // list of all interfaces this class should have implemented
    List<Environment> implementedInterfaces = classEnvironment.getAllImplementedEnvironments(packageMap);
    writer.write("global InterfaceTABLE$" + className + "\nInterfaceTABLE$" + className + ":\n");
    // Iterate over the global interface list
    for (Environment interfc : mListOfInterfaces) {
      boolean foundImplementation = false;
      for (Environment implementedInterface : implementedInterfaces) {
        // Check this interface against the interfaces this class implements
        if (interfc == implementedInterface) {
          foundImplementation = true;
          // this class implements this interface so we should add entries for each of the
          // implemented methods
          for(Environment abstractMethod: interfc.mChildrenEnvironments) {
            // find the implementation of this method in this class
            for(Environment method : methodList) {
              if(method.implementsAbstractMethod(abstractMethod, packageMap)) {
                MethodSignature signature = method.getMethodSignature(packageMap, null);
                writer.write("  dd " + Utils.getMethodLabel(classEnvironment, signature) + "\n");
              }
            }
          }
          break;
        }
      }
      if(!foundImplementation) {
        // if we didn't find an implementation of this interface then we should put a blank entry for
        // for each of the methods in this interface
        if(interfc.mChildrenEnvironments != null) {
          for(Environment abstractMethod : interfc.mChildrenEnvironments) {
            writer.write("  dd 00000000\n");
          }
        }
      }
    }
    writer.write("\n");
  }
}
