package joos.codegeneration;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import joos.environment.Environment;
import joos.commons.MethodSignature;
import joos.environment.Environment.EnvironmentType;
import joos.exceptions.InvalidSyntaxException;

import static joos.environment.Environment.getMethodSignature;
import static joos.environment.EnvironmentUtils.getEnvironmentType;
import static joos.environment.EnvironmentUtils.getExtendedEnvironments;


class Pair<T1, T2> {
  public T1 first;
  public T2 second;
  public Pair(T1 t1, T2 t2) {
    first = t1;
    second = t2;
  }
}

public class CodeGeneration {
  private static String getMethodLabel(Environment environment, MethodSignature methodSignature) {
    String ret = "METHOD$" + getClassLabel(environment) + "$" + methodSignature.name + "@";
    for (String type : methodSignature.parameterTypes) {
      ret += type + "#";
    }
    return ret;
  }

  private static String getClassLabel(Environment environment) {
    String ret = "";
    if (environment.PackageName.length() > 0) {
      ret += environment.PackageName + ".";
    }
    return ret + environment.mName;
  }

  private static List<Pair<Environment, MethodSignature>> getMethodList(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    List<Pair<Environment, MethodSignature>> list = new ArrayList();
    List<Environment> extendedEnvironments = getExtendedEnvironments(environment, packageMap);
    if (extendedEnvironments != null && extendedEnvironments.size() > 0) {
      list.addAll(getMethodList(extendedEnvironments.get(0), packageMap));
    }

    for (Environment child : environment.mChildrenEnvironments) {
      if (getEnvironmentType(child) == EnvironmentType.METHOD) {
        MethodSignature methodSignature = getMethodSignature(child, packageMap, "");
        boolean contains = false;
        for (Pair<Environment, MethodSignature> pair : list) {
          if (pair.second.equals(methodSignature)) {
            pair.first = environment;
            contains = true;
            break;
          }
        }
        if (!contains) {
          list.add(new Pair(environment, methodSignature));
        }
      }
    }
    return list;
  }

  public static void generateForClass(Environment environment, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    String filename = getClassLabel(environment);
    File file = new File(filename + ".s");
    file.createNewFile();
    FileWriter writer = new FileWriter(file);

    generateVTable(writer, environment, packageMap);
    for (Environment child : environment.mChildrenEnvironments) {
      if (getEnvironmentType(child) == EnvironmentType.METHOD) {
        generateForMethod(writer, environment, child, packageMap);
      }
    }

    writer.flush();
    writer.close();
  }

  public static void generateVTable(FileWriter writer, Environment environment, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    String className = getClassLabel(environment);
    List<Pair<Environment, MethodSignature>> methodList = getMethodList(environment, packageMap);
    writer.write("global VTABLE$" + className + "\nVTABLE$" + className + ":\n");
    for (Pair<Environment, MethodSignature> pair : methodList) {
      writer.write("  dd " + getMethodLabel(pair.first, pair.second) + "\n");
    }
    writer.write("\n");
  }

  public static void generateForMethod(FileWriter writer, Environment classEnv, Environment methodEnv, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    MethodSignature methodSignature = getMethodSignature(methodEnv, packageMap, "");
    String label = getMethodLabel(classEnv, methodSignature);
    writer.write("global " + label + "\n" + label + ":\n");
    //do more
    writer.write("\n");
  }
}
