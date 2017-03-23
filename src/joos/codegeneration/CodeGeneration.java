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

  private static List<Pair<Type, String>> getFieldList(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    List<Pair<Type, String>> list = new ArrayList();
    Environment classEnv = environment.getParentClassEnvironment();
    List<Environment> extendedEnvironments = getExtendedEnvironments(classEnv, packageMap);
    if (extendedEnvironments != null && extendedEnvironments.size() > 0) {
      list.addAll(getFieldList(extendedEnvironments.get(0), packageMap));
    }

    for (String field : classEnv.mVariableDeclarations.keySet()) {
      if (classEnv.mVariableToType.get(field).modifiers.contains(TokenType.STATIC)) continue;
      list.add(new Pair(classEnv.mVariableToType.get(field), field));
    }
    return list;
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
    // System.out.println(methodSignature.name);
    List<Pair<Environment, MethodSignature>> list = getMethodList(environment, packageMap);
    int i = 0;
    for (Pair<Environment, MethodSignature> pair : list) {
      // System.out.println(pair.second.name);
      if (pair.second.equals(methodSignature)) return i * 4;
      i++;
    }
    return -1;
  }

  private static int getOffsetForField(Environment environment, String field, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    System.out.println(field);
    List<Pair<Type, String>> list = getFieldList(environment, packageMap);
    int i = 0;
    for (Pair<Type, String> pair : list) {
      if (pair.second.equals(field)) return i * 4;
      i++;
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
    writer.write("  sub esp, " + (i + 4) + "\n");
    writer.write("  ret\n");
    writer.write("\n");
  }

  private static void generateForMethodNode(FileWriter writer, Environment environment, ParseTreeNode node, Map<String, Pair<Integer, Type>> offsets, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    List<String> argTypes = new ArrayList();
    if (node.children.get(node.children.size() -2).children.size() > 0) {
      for (ParseTreeNode arg : node.children.get(node.children.size() - 2).children.get(0).children) {
        if (arg.token.getType() == TokenType.COMMA) {
          continue;
        }
        argTypes.add(arg.type.name);
      }
    }
    if (node.children.size() == 4) {
      String name = getNameFromTypeNode(node.children.get(0));
      int dotIndex = name.lastIndexOf('.');
      if (dotIndex == -1) {
        MethodSignature methodSignature = environment.getParentClassEnvironment().getMethodSignatures(packageMap).get(name).get(argTypes);
        int offset = getVTableOffsetForMethod(environment.getParentClassEnvironment(), methodSignature, packageMap);
        writer.write("  mov eax, [ebp]\n"); //this
        writer.write("  push eax\n"); // push this
        writer.write("  mov eax, [eax]\n"); //VTABLE
        writer.write("  mov eax, [eax + " + offset + "]\n");
        return;
      }
      String prefix = name.substring(0, dotIndex);
      if (generateForName(writer, environment, prefix, offsets, packageMap)) {
        Environment classEnv = getEnvironmentFromTypeName(environment, prefix, packageMap);
        MethodSignature methodSignature = classEnv.getMethodSignatures(packageMap).get(name.substring(dotIndex + 1)).get(argTypes);
        writer.write("  push 0\n"); //fake this for static call
        writer.write("  mov eax, " + getMethodLabel(classEnv, methodSignature) + "\n");
      } else {
        MethodSignature methodSignature = node.children.get(0).type.environment.getMethodSignatures(packageMap).get(name.substring(dotIndex + 1)).get(argTypes);
        int offset = getVTableOffsetForMethod(node.children.get(0).type.environment, methodSignature, packageMap);
        writer.write("  mov eax, [eax]\n");
        writer.write("  push eax\n"); //push new this
        writer.write("  mov eax, [eax]\n"); //VTABLE
        writer.write("  mov eax, eax + " + offset + "\n");
      }
    } else {
      generateForNode(writer, environment, node.children.get(0), offsets, packageMap);
      String name = ((TerminalToken)findNodeWithTokenType(node.children.get(2), TokenType.IDENTIFIER).token).getRawValue();
      MethodSignature methodSignature = node.children.get(0).type.environment.getMethodSignatures(packageMap).get(name).get(argTypes);
      int offset = getVTableOffsetForMethod(node.children.get(0).type.environment, methodSignature, packageMap);
      writer.write("  mov eax, [eax]\n");
      writer.write("  push eax\n"); //push new this
      writer.write("  mov eax, [eax]\n"); //VTABLE
      writer.write("  mov eax, eax + " + offset + "\n");
    }


  }

  private static boolean generateForName(FileWriter writer, Environment environment, String name, Map<String, Pair<Integer, Type>> offsets, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    System.out.println(name);
    int dotIndex = name.indexOf('.');
    String prefix = dotIndex == -1 ? name : name.substring(0, dotIndex);
    boolean stat = false;
    Environment classEnv;
    if (offsets.containsKey(prefix)) {
      classEnv = offsets.get(prefix).second.environment;
      writer.write("  mov eax, [ebp + " + offsets.get(prefix).first + "]\n");
    } else {
      classEnv = environment.getParentClassEnvironment();
      int offset = getOffsetForField(classEnv, prefix, packageMap);
      if (offset != -1) {
        writer.write("  mov eax, [ebp]\n"); //this
        writer.write("  mov eax, [eax + " + offset + "]\n");
      } else {
        classEnv = getEnvironmentFromTypeName(environment, prefix, packageMap);
        stat = true;
      }
    }
    while (dotIndex != -1) {
      int newDotIndex = name.indexOf('.', dotIndex + 1);
      if (newDotIndex == -1) {
        prefix = name.substring(dotIndex + 1);
      } else {
        prefix = name.substring(dotIndex + 1, newDotIndex);
      }
      if (prefix.equals("length")) return stat;
      if (stat) {
        stat = false;
        writer.write("  mov eax, [STATICFIELD$" + getClassLabel(classEnv) + "$" + prefix +"]\n");
      } else {
        writer.write("  mov eax, [eax + " + getOffsetForField(classEnv, prefix, packageMap) + "]\n");
      }
      dotIndex = newDotIndex;
    }
    return stat;
  }

  public static void generateForNode(FileWriter writer, Environment environment, ParseTreeNode node, Map<String, Pair<Integer, Type>> offsets, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    System.out.println(node.token.getType());
    Environment currentEnvironment = environment;
    Map<String, Pair<Integer, Type>> currentOffsets = new HashMap(offsets);
    for (Environment childEnv : environment.mChildrenEnvironments) {
      if (childEnv.mScope == node) {
        currentEnvironment = childEnv;
        if (currentEnvironment.mVariableDeclarations.size() > 0) writer.write("  sub esp, " + currentEnvironment.mVariableDeclarations.size() * 4 + "\n");
        for (String var : currentEnvironment.mVariableDeclarations.keySet()) {
          currentOffsets.put(var, new Pair(currentOffsets.size() * 4, currentEnvironment.mVariableToType.get(var)));
        }
        break;
      }
    }
    switch (node.token.getType()) {
      case METHOD_INVOCATION:
        int numArgs = 0;
        for (ParseTreeNode param : node.children.get(node.children.size() - 2).children) {
          generateForNode(writer, currentEnvironment, param, currentOffsets, packageMap);
          numArgs++;
          writer.write("  push eax\n");
        }
        generateForMethodNode(writer, currentEnvironment, node, currentOffsets, packageMap);
        writer.write("  push ebp\n");
        writer.write("  mov ebp, esp - " + (numArgs + 1) * 4 + "\n");
        writer.write("  call eax\n");
        writer.write("  pop ebp\n");
        return;
      case IF_THEN_STATEMENT:
        generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+currentEnvironment.mName+"end\n");
        generateForNode(writer, currentEnvironment, node.children.get(4), currentOffsets, packageMap);
        writer.write(currentEnvironment.mName+"end:\n");
        return;
      case IF_THEN_ELSE_STATEMENT:
      case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
        generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+currentEnvironment.mName+"else\n");
        generateForNode(writer, currentEnvironment, node.children.get(4), currentOffsets, packageMap);
        writer.write("  je "+currentEnvironment.mName+"end\n");
        writer.write(currentEnvironment.mName+"else:\n");
        generateForNode(writer, currentEnvironment, node.children.get(6), currentOffsets, packageMap);
        writer.write(currentEnvironment.mName+"end:\n");
        return;
      case WHILE_STATEMENT:
      case WHILE_STATEMENT_NO_SHORT_IF:
        writer.write(currentEnvironment.mName+"start:\n");
        generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+currentEnvironment.mName+"end\n");
        generateForNode(writer, currentEnvironment, node.children.get(4), currentOffsets, packageMap);
        writer.write("  je "+currentEnvironment.mName+"start\n");
        writer.write(currentEnvironment.mName+"end:\n");
        return;
      case FOR_STATEMENT:
      case FOR_STATEMENT_NO_SHORT_IF:
        for (Environment childEnv : currentEnvironment.mChildrenEnvironments) {
          if (childEnv.mScope == node.children.get(8)) {
            currentEnvironment = childEnv;
            for (String var : childEnv.mVariableDeclarations.keySet()) {
              currentOffsets.put(var, new Pair(currentOffsets.size() * 4, childEnv.mVariableToType.get(var)));
            }
          }
        }
        generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, packageMap);
        writer.write(currentEnvironment.mName+"start:\n");
        generateForNode(writer, currentEnvironment, node.children.get(4), currentOffsets, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+currentEnvironment.mName+"end\n");
        generateForNode(writer, currentEnvironment, node.children.get(8), currentOffsets, packageMap);
        generateForNode(writer, currentEnvironment, node.children.get(6), currentOffsets, packageMap);
        writer.write("  je "+currentEnvironment.mName+"start\n");
        writer.write(currentEnvironment.mName+"end:\n");
        if (currentEnvironment.mVariableDeclarations.size() > 0) {
          writer.write("  add esp, " + currentEnvironment.mVariableDeclarations.size() * 4 + "\n");
        }
        return;
      case AND_EXPRESSION:
        if(node.children.size()>1){
          for(int i=0;i<node.children.size();i++) {
            if(1%2==0) {
              generateForNode(writer, currentEnvironment, node.children.get(i), currentOffsets, packageMap);
              writer.write("  cmp eax 0\n");
              writer.write("  je "+currentEnvironment.mName+"end\n");
            }
          }
          writer.write(currentEnvironment.mName+"end:\n");
        }
        else {
          generateForNode(writer, currentEnvironment, node.children.get(0), currentOffsets, packageMap);
        }
        return;
      case INCLUSIVE_OR_EXPRESSION:
        if(node.children.size()>1){
          for(int i=0;i<node.children.size();i++) {
            if(1%2==0) {
              generateForNode(writer, currentEnvironment, node.children.get(i), currentOffsets, packageMap);
              writer.write("  cmp eax 1\n");
              writer.write("  je "+currentEnvironment.mName+"end\n");
            }
          }
          writer.write(currentEnvironment.mName+"end:\n");
        }
        else {
          generateForNode(writer, currentEnvironment, node.children.get(0), currentOffsets, packageMap);
        }
        return;
      case NAME:
        generateForName(writer, currentEnvironment, getNameFromTypeNode(node), currentOffsets, packageMap);
        return;
    }
    if (node.children != null) {
      for (ParseTreeNode child : node.children) {
        generateForNode(writer, currentEnvironment, child, currentOffsets, packageMap);
      }
    }
    if (currentEnvironment.mScope == node && currentEnvironment.mVariableDeclarations.size() > 0) {
      writer.write("  add esp, " + currentEnvironment.mVariableDeclarations.size() * 4 + "\n");
    }
  }
}
