package joos.codegeneration;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import joos.commons.MethodSignature;
import joos.commons.ParseTreeNode;
import joos.commons.TokenType;
import joos.commons.Type.TypeType;
import joos.commons.Type;
import joos.environment.Environment;
import joos.environment.Environment.EnvironmentType;
import joos.exceptions.InvalidSyntaxException;

import static joos.environment.Environment.getMethodSignature;
import static joos.environment.EnvironmentUtils.getNameFromTypeNode;
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
    String ret = "";
    if (methodSignature.modifiers.contains(TokenType.STATIC)) {
      ret += "STATIC";
    }
    ret += "METHOD$" + getClassLabel(environment) + "$" + methodSignature.name + "@";
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
        if (methodSignature.modifiers.contains(TokenType.STATIC)) continue;
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
    for (String key : environment.mVariableToType.keySet()) {
      System.out.println(key + ": " + environment.mVariableToType.get(key).modifiers);
      if (environment.mVariableToType.get(key).modifiers.contains(TokenType.STATIC)) {
        String label = "STATICFIELD$" + getClassLabel(environment) + "$" + key;
        writer.write("global " + label + "\n" + label + ":\n  dd 0\n\n");
      }
    }
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
    Map<String, Integer> offsets = new HashMap();
    int i = 0;
    for (String param : methodEnv.mVariableDeclarations.keySet()) {
      offsets.put(param, i);
      i += 4;
    }

    //do more
    writer.write("  sub esp, " + i + "\n");
    writer.write("  ret\n");
    writer.write("\n");
  }

  public static void generateForNode(FileWriter writer, Environment environment, ParseTreeNode node, Map<String, Integer> offsets, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    switch (node.token.getType()) {
      case METHOD_INVOCATION:
        writer.write("  push ebp\n  mov ebp, esp\n");
        if (node.children.size() == 4) {
          Type type = node.children.get(0).type;
          String name = getNameFromTypeNode(node.children.get(0));
          int dotIndex = name.indexOf('.');
          String methodName;
          if (dotIndex == -1) {
            methodName = name.substring(dotIndex + 1);
          } else {
            methodName = name;
          }
          if (type.type == TypeType.TYPE) {
            Environment classEnv = type.subType.environment;
            Environment methodEnv = null;
            for (Environment child : classEnv.mChildrenEnvironments) {
              if (child.mName.equals(methodName)) {
                methodEnv = child;
                break;
              }
            }
            for (ParseTreeNode arg : node.children.get(2).children) {
              generateForNode(writer, environment, arg, offsets, packageMap);
              writer.write("  push eax\n");
            }
            MethodSignature methodSignature = getMethodSignature(methodEnv, packageMap, "");
            writer.write("  call " + getMethodLabel(classEnv, methodSignature) + "\n");
          }
        }
        writer.write("  pop ebp\n");
        break;
    }
  }
}
