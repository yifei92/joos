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
import joos.commons.TerminalToken;
import joos.commons.Type.TypeType;
import joos.commons.Type;
import joos.environment.Environment;
import joos.environment.Environment.EnvironmentType;
import joos.exceptions.InvalidSyntaxException;

import static joos.environment.Environment.getMethodSignature;
import static joos.environment.EnvironmentUtils.getNameFromTypeNode;
import static joos.environment.EnvironmentUtils.findNodeWithTokenType;
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeName;
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

  private static int getVTableOffsetForMethod(Environment environment, MethodSignature methodSignature, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    List<Pair<Environment, MethodSignature>> list = getMethodList(environment, packageMap);
    int i = 0;
    for (Pair<Environment, MethodSignature> pair : list) {
      if (pair.second.equals(methodSignature)) return i * 4;
      i++;
    }
    return -1;
  }

  private static int getOffsetForField(Environment environment, String field) {
    int i = 1;
    for (String name : environment.mVariableDeclarations.keySet()) {
      if (name.equals(field)) {
        return i * 4;
      }
      if (!environment.mVariableToType.get(name).modifiers.contains(TokenType.STATIC)) {
        i++;
      }
    }
    return -1;
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
    Map<String, Pair<Integer, Type>> offsets = new HashMap();
    int i = 0;
    for (String param : methodEnv.mVariableDeclarations.keySet()) {
      offsets.put(param, new Pair(i, methodEnv.mVariableToType.get(param)));
      i += 4;
    }
    offsets.put("this", new Pair(i, Type.newObject(classEnv.mName, classEnv, null)));
    generateForNode(writer, methodEnv, methodEnv.mScope.children.get(1), offsets, packageMap);
    writer.write("  sub esp, " + i + "\n");
    writer.write("  ret\n");
    writer.write("\n");
  }

  private static void generateForMethodNode(FileWriter writer, Environment environment, ParseTreeNode node, Map<String, Pair<Integer, Type>> offsets, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    List<String> argTypes = new ArrayList();
    for (ParseTreeNode arg : node.children.get(node.children.size() - 2).children) {
      if (arg.token.getType() == TokenType.COMMA) {
        continue;
      }
      argTypes.add(arg.type.name);
    }
    if (node.children.size() == 4) {
      String name = getNameFromTypeNode(node.children.get(0));
      int dotIndex = name.lastIndexOf('.');
      if (dotIndex == -1) {
        MethodSignature methodSignature = environment.getMethodSignatures(packageMap).get(name).get(argTypes);
        int offset = getVTableOffsetForMethod(environment.getParentClassEnvironment(), methodSignature, packageMap);
        writer.write("  mov eax, [ebp]\n"); //this
        writer.write("  push eax"); // push this
        writer.write("  mov eax, [eax]\n"); //VTABLE
        writer.write("  mov eax, [eax + " + offset + "]\n");
        return;
      }
      String prefix = name.substring(0, dotIndex);
      if (generateForName(writer, environment, prefix, offsets, packageMap)) {
        Environment classEnv = getEnvironmentFromTypeName(environment, prefix, packageMap);
        MethodSignature methodSignature = classEnv.getMethodSignatures(packageMap).get(name.substring(dotIndex + 1)).get(argTypes);
        writer.write("  push 0"); //fake this for static call
        writer.write("  mov eax, " + getMethodLabel(classEnv, methodSignature) + "\n");
      } else {
        MethodSignature methodSignature = node.type.environment.getMethodSignatures(packageMap).get(name.substring(dotIndex + 1)).get(argTypes);
        int offset = getVTableOffsetForMethod(node.type.environment, methodSignature, packageMap);
        writer.write("  mov eax, [eax]\n");
        writer.write("  push eax"); //push new this
        writer.write("  mov eax, [eax]\n"); //VTABLE
        writer.write("  mov eax, eax + " + offset + "\n");
      }
    } else {
      generateForNode(writer, environment, node.children.get(0), offsets, packageMap);
      String name = ((TerminalToken)findNodeWithTokenType(node.children.get(2), TokenType.IDENTIFIER).token).getRawValue();
      MethodSignature methodSignature = node.children.get(0).type.environment.getMethodSignatures(packageMap).get(name).get(argTypes);
      int offset = getVTableOffsetForMethod(node.children.get(0).type.environment, methodSignature, packageMap);
      writer.write("  mov eax, [eax]\n");
      writer.write("  push eax"); //push new this
      writer.write("  mov eax, [eax]\n"); //VTABLE
      writer.write("  mov eax, eax + " + offset + "\n");
    }


  }

  private static boolean generateForName(FileWriter writer, Environment environment, String name, Map<String, Pair<Integer, Type>> offsets, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    int dotIndex = name.indexOf('.');
    String prefix = dotIndex == -1 ? name : name.substring(0, dotIndex);
    boolean stat = false;
    Environment classEnv;
    if (offsets.containsKey(prefix)) {
      classEnv = offsets.get(prefix).second.environment;
      writer.write("  mov eax, [ebp + " + offsets.get(prefix).first + "]\n");
    } else {
      classEnv = environment.getParentClassEnvironment();
      if (classEnv.mVariableDeclarations.containsKey(prefix)) {
        int offset = getOffsetForField(classEnv, prefix);
        writer.write("  mov eax, [ebp]\n"); //this
        writer.write("  mov eax, [eax + " + offset + "]\n");
      } else {
        classEnv = getEnvironmentFromTypeName(environment, prefix, packageMap);
        stat = true;
      }
    }
    while (dotIndex != -1) {
      int newDotIndex = name.indexOf('.', dotIndex + 1);
      prefix = name.substring(dotIndex + 1, newDotIndex);
      if (stat) {
        stat = false;
        writer.write("  mov eax, [STATICFIELD$" + getClassLabel(classEnv) + "$" + prefix +"]\n");
      } else {
        writer.write("  mov eax, [eax + " + getOffsetForField(classEnv, prefix) + "]\n");
      }
      dotIndex = newDotIndex;
    }
    return stat;
  }

  public static void generateForNode(FileWriter writer, Environment environment, ParseTreeNode node, Map<String, Pair<Integer, Type>> offsets, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    switch (node.token.getType()) {
      case METHOD_INVOCATION:
        int numArgs = 0;
        for (ParseTreeNode param : node.children.get(node.children.size() - 2).children) {
          generateForNode(writer, environment, param, offsets, packageMap);
          numArgs++;
          writer.write("  push eax");
        }
        generateForMethodNode(writer, environment, node, offsets, packageMap);
        writer.write("  push ebp\n");
        writer.write("  mov ebp, esp - " + (numArgs + 1) + "\n");
        writer.write("  call eax");
        writer.write("  pop ebp\n");
        break;
      case IF_THEN_STATEMENT:
        generateForNode(writer, environment, node.children.get(2), offsets, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+environment.mName+"end\n");
        generateForNode(writer, environment, node.children.get(4), offsets, packageMap);
        writer.write(environment.mName+"end:\n");
      case IF_THEN_ELSE_STATEMENT:
      case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
        generateForNode(writer, environment, node.children.get(2), offsets, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+environment.mName+"else\n");
        generateForNode(writer, environment, node.children.get(4), offsets, packageMap);
        writer.write("  je "+environment.mName+"end\n");
        writer.write(environment.mName+"else:\n");
        generateForNode(writer, environment, node.children.get(6), offsets, packageMap);
        writer.write(environment.mName+"end:\n");
      case WHILE_STATEMENT:
      case WHILE_STATEMENT_NO_SHORT_IF:
        writer.write(environment.mName+"start:\n");
        generateForNode(writer, environment, node.children.get(2), offsets, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+environment.mName+"end\n");
        generateForNode(writer, environment, node.children.get(4), offsets, packageMap);
        writer.write("  je "+environment.mName+"start\n");
        writer.write(environment.mName+"end:\n");
      case FOR_STATEMENT:
      case FOR_STATEMENT_NO_SHORT_IF:
        generateForNode(writer, environment, node.children.get(2), offsets, packageMap);
        writer.write(environment.mName+"start:\n");
        generateForNode(writer, environment, node.children.get(4), offsets, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+environment.mName+"end\n");
        generateForNode(writer, environment, node.children.get(8), offsets, packageMap);
        generateForNode(writer, environment, node.children.get(6), offsets, packageMap);
        writer.write("  je "+environment.mName+"start\n");
        writer.write(environment.mName+"end:\n");
      case AND_EXPRESSION:
        if(node.children.size()>1){
          for(int i=0;i<node.children.size();i++) {
            if(1%2==0) {
              generateForNode(writer, environment, node.children.get(i), offsets, packageMap);
              writer.write("  cmp eax 0\n");
              writer.write("  je "+environment.mName+"end\n");
            }
          }
          writer.write(environment.mName+"end:\n");
        }
        else {
          generateForNode(writer, environment, node.children.get(0), offsets, packageMap);
        }
      case INCLUSIVE_OR_EXPRESSION:
        if(node.children.size()>1){
          for(int i=0;i<node.children.size();i++) {
            if(1%2==0) {
              generateForNode(writer, environment, node.children.get(i), offsets, packageMap);
              writer.write("  cmp eax 1\n");
              writer.write("  je "+environment.mName+"end\n");
            }
          }
          writer.write(environment.mName+"end:\n");
        }
        else {
          generateForNode(writer, environment, node.children.get(0), offsets, packageMap);
        }
      case NAME:
        generateForName(writer, environment, getNameFromTypeNode(node), offsets, packageMap);
        break;
    }
  }
}
