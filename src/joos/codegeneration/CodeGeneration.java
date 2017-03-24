package joos.codegeneration;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.io.File;
import java.io.StringWriter;
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
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeNode;


class Pair<T1, T2> {
  public T1 first;
  public T2 second;
  public Pair(T1 t1, T2 t2) {
    first = t1;
    second = t2;
  }
}

public class CodeGeneration {
  public static String getMethodLabel(Environment environment, MethodSignature methodSignature) {
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

  public static String getClassLabel(Environment environment) {
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
          if (pair.second.name.equals(methodSignature.name) && pair.second.parameterTypes.equals(methodSignature.parameterTypes)) {
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
      if (pair.second.name.equals(methodSignature.name) && pair.second.parameterTypes.equals(methodSignature.parameterTypes)) return i * 4;
      i++;
    }
    return -1;
  }

  private static Pair<Integer, Type> getOffsetForField(Environment environment, String field, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    List<Pair<Type, String>> list = getFieldList(environment, packageMap);
    int i = 0;
    for (Pair<Type, String> pair : list) {
      if (pair.second.equals(field)) return new Pair((i + 1) * 4, pair.first);
      i++;
    }
    return null;
  }

  public static void generateForClass(Environment environment, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    if(getEnvironmentType(environment) != EnvironmentType.CLASS) {
      // We don't need to generate code for interfaces
      return;
    }
    StringWriter writer = new StringWriter();
    Set<String> externs = new HashSet();

    generateVTable(writer, environment, externs, packageMap);
    Interfaces.generateInterfaceTable(writer, environment, packageMap);
    for (String key : environment.mVariableToType.keySet()) {
      if (environment.mVariableToType.get(key).modifiers.contains(TokenType.STATIC)) {
        String label = "STATICFIELD$" + getClassLabel(environment) + "$" + key;
        writer.write("global " + label + "\n" + label + ":\n  dd 0\n\n");
      }
    }
    for (Environment child : environment.mChildrenEnvironments) {
      switch(getEnvironmentType(child)) {
        case METHOD:
          generateForMethod(writer, environment, child, externs, packageMap);
          break;
        case CONSTRUCTOR:
          generateForConstructor(writer, environment, child, externs, packageMap);
          break;
      }
    }

    writer.flush();

    String filename = getClassLabel(environment);
    File file = new File(filename + ".s");
    file.createNewFile();
    FileWriter fileWriter = new FileWriter(file);
    for (String extern : externs) {
      fileWriter.write("extern " + extern + "\n");
    }
    fileWriter.write("\n");
    fileWriter.write(writer.toString());
    fileWriter.flush();
    fileWriter.close();
  }

  public static void generateVTable(StringWriter writer, Environment environment, Set<String> externs, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    String className = getClassLabel(environment);
    List<Pair<Environment, MethodSignature>> methodList = getMethodList(environment, packageMap);
    writer.write("global VTABLE$" + className + "\nVTABLE$" + className + ":\n");
    for (Pair<Environment, MethodSignature> pair : methodList) {
      String label = getMethodLabel(pair.first, pair.second);
      if (pair.first != environment) {
        externs.add(label);
      }
      writer.write("  dd " + label + "\n");
    }
    writer.write("\n");
  }

  public static void generateForMethod(StringWriter writer, Environment classEnv, Environment methodEnv, Set<String> externs, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    MethodSignature methodSignature = getMethodSignature(methodEnv, packageMap, "");
    String label = getMethodLabel(classEnv, methodSignature);
    writer.write("global " + label + "\n" + label + ":\n");
    Map<String, Pair<Integer, Type>> offsets = new HashMap();
    offsets.put("this", new Pair(-8, Type.newObject(classEnv.mName, classEnv, null)));
    int i = -12;
    for (String param : methodEnv.mVariableDeclarations.keySet()) {
      offsets.put(param, new Pair(i, methodEnv.mVariableToType.get(param)));
      i -= 4;
    }
    writer.write("  push ebp\n");
    writer.write("  mov ebp, esp\n");
    generateForNode(writer, methodEnv, methodEnv.mScope.children.get(1), offsets, 0, externs, packageMap);
    writer.write("  pop ebp\n");
    writer.write("  ret\n");
    writer.write("\n");
  }

  public static void generateForConstructor(StringWriter writer, Environment classEnv, Environment constructorEnv, Set<String> externs, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    List<String> argTypes = constructorEnv.getConstructorSignature(packageMap);
    String label = "CONSTRUCTOR$" + getClassLabel(classEnv) + "@";
    for (String argType : argTypes) {
      label += argType + "#";
    }
    writer.write("global " + label + "\n" + label + ":\n");
    Map<String, Pair<Integer, Type>> offsets = new HashMap();
    offsets.put("this", new Pair(-8, Type.newObject(classEnv.mName, classEnv, null)));
    int i = -12;
    for (String param : constructorEnv.mVariableDeclarations.keySet()) {
      offsets.put(param, new Pair(i, constructorEnv.mVariableToType.get(param)));
      i -= 4;
    }
    int size = (getFieldList(classEnv, packageMap).size() + 1) * 4;
    writer.write("  push ebp\n");
    writer.write("  mov ebp, esp\n");
    writer.write("  mov eax, " + size + "\n");
    externs.add("__malloc");
    writer.write("  call __malloc\n");
    writer.write("  mov dword [ebp + 8], eax\n"); // set this
    writer.write("  mov dword [eax], VTABLE$" + getClassLabel(classEnv) + "\n");
    for (int j = 4; j < size; j += 4) {
      writer.write("  mov dword [eax + " + j + "], 0\n");
    }
    generateForNode(writer, constructorEnv, constructorEnv.mScope.children.get(2), offsets, 0, externs, packageMap);
    writer.write("  mov eax, [ebp + 8]\n"); //return this
    writer.write("  pop ebp\n");
    writer.write("  ret\n");
    writer.write("\n");
  }

  private static void generateForMethodNode(StringWriter writer, Environment environment, ParseTreeNode node, Map<String, Pair<Integer, Type>> offsets, int currentOffset, Set<String> externs, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
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
        writer.write("  mov eax, [ebp + 8]\n"); //this
        writer.write("  push eax\n"); // push this
        writer.write("  mov eax, [eax]\n"); //VTABLE
        writer.write("  add eax, " + offset + "\n");
        writer.write("  mov eax, [eax]\n");
        return;
      }
      String prefix = name.substring(0, dotIndex);
      if (generateForName(writer, environment, prefix, offsets, externs, packageMap).first) {
        Environment classEnv = getEnvironmentFromTypeName(environment, prefix, packageMap);
        MethodSignature methodSignature = classEnv.getMethodSignatures(packageMap).get(name.substring(dotIndex + 1)).get(argTypes);
        writer.write("  push 0\n"); //fake this for static call
        String label = getMethodLabel(classEnv, methodSignature);
        if (classEnv != environment.getParentClassEnvironment()) {
          externs.add(label);
        }
        writer.write("  mov eax, " + label + "\n");
      } else {
        MethodSignature methodSignature = node.children.get(0).type.environment.getMethodSignatures(packageMap).get(name.substring(dotIndex + 1)).get(argTypes);
        int offset = getVTableOffsetForMethod(node.children.get(0).type.environment, methodSignature, packageMap);
        writer.write("  push eax\n"); //push new this
        writer.write("  mov eax, [eax]\n"); //VTABLE
        writer.write("  add eax, " + offset + "\n");
        writer.write("  mov eax, [eax]\n");
      }
    } else {
      generateForNode(writer, environment, node.children.get(0), offsets, currentOffset, externs, packageMap);
      String name = ((TerminalToken)findNodeWithTokenType(node.children.get(2), TokenType.IDENTIFIER).token).getRawValue();
      MethodSignature methodSignature = node.children.get(0).type.environment.getMethodSignatures(packageMap).get(name).get(argTypes);
      int offset = getVTableOffsetForMethod(node.children.get(0).type.environment, methodSignature, packageMap);
      writer.write("  push eax\n"); //push new this
      writer.write("  mov eax, [eax]\n"); //VTABLE
      writer.write("  add eax, " + offset + "\n");
      writer.write("  mov eax, [eax]\n");
    }


  }

  private static Pair<Boolean, Environment> generateForName(StringWriter writer, Environment environment, String name, Map<String, Pair<Integer, Type>> offsets, Set<String> externs, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    int dotIndex = name.indexOf('.');
    String prefix = dotIndex == -1 ? name : name.substring(0, dotIndex);
    boolean stat = false;
    Environment fieldEnv;
    if (offsets.containsKey(prefix)) {
      fieldEnv = packageMap.get(offsets.get(prefix).second.name);
      int offset = offsets.get(prefix).first;
      writer.write("  mov eax, [ebp - " + offset + "]\n");
    } else {
      fieldEnv = environment.getParentClassEnvironment();
      Pair<Integer, Type> pair = getOffsetForField(fieldEnv, prefix, packageMap);
      if (pair != null) {
        int offset = pair.first;
        fieldEnv = packageMap.get(pair.second.name);
        writer.write("  mov eax, [ebp + 8]\n"); //this
        writer.write("  mov eax, [eax + " + offset + "]\n");
      } else {
        fieldEnv = getEnvironmentFromTypeName(environment, prefix, packageMap);
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
      if (prefix.equals("length")) return new Pair(stat, null);
      if (stat) {
        stat = false;
        String label = "STATICFIELD$" + getClassLabel(fieldEnv) + "$" + prefix;
        if (fieldEnv != environment.getParentClassEnvironment()) {
          externs.add(label);
        }
        writer.write("  mov eax, [" + label +"]\n");
        fieldEnv = packageMap.get(fieldEnv.mVariableToType.get(prefix).name);
      } else {
        Pair<Integer, Type> pair = getOffsetForField(fieldEnv, prefix, packageMap);
        int offset = pair.first;
        fieldEnv = packageMap.get(pair.second.name);
        writer.write("  mov eax, [eax + " + offset + "]\n");
      }
      dotIndex = newDotIndex;
    }
    return new Pair(stat, fieldEnv);
  }

  public static void generateForNode(StringWriter writer, Environment environment, ParseTreeNode node, Map<String, Pair<Integer, Type>> offsets, int currentOffset, Set<String> externs, Map<String, Environment> packageMap) throws IOException, InvalidSyntaxException {
    Environment currentEnvironment = environment;
    Map<String, Pair<Integer, Type>> currentOffsets = new HashMap(offsets);
    for (Environment childEnv : environment.mChildrenEnvironments) {
      if (childEnv.mScope == node) {
        currentEnvironment = childEnv;
        if (currentEnvironment.mVariableDeclarations.size() > 0) writer.write("  sub esp, " + currentEnvironment.mVariableDeclarations.size() * 4 + "\n");
        for (String var : currentEnvironment.mVariableDeclarations.keySet()) {
          currentOffset += 4;
          currentOffsets.put(var, new Pair(currentOffset, currentEnvironment.mVariableToType.get(var)));
        }
        break;
      }
    }
    switch (node.token.getType()) {
      case METHOD_INVOCATION: {
        int numArgs = 0;
        if (node.children.get(node.children.size() - 2).children.size() > 0) {
          for (ParseTreeNode param : node.children.get(node.children.size() - 2).children.get(0).children) {
            if (param.token.getType() == TokenType.COMMA) continue;
            generateForNode(writer, currentEnvironment, param, currentOffsets, currentOffset, externs, packageMap);
            numArgs++;
            writer.write("  push eax\n");
          }
        }
        generateForMethodNode(writer, currentEnvironment, node, currentOffsets, currentOffset, externs, packageMap);
        writer.write("  call eax\n");
        writer.write("  add esp, " + (numArgs + 1) * 4 + "\n");
        return;
      }
      case IF_THEN_STATEMENT:
        generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+currentEnvironment.mName+"end\n");
        generateForNode(writer, currentEnvironment, node.children.get(4), currentOffsets, currentOffset, externs, packageMap);
        writer.write(currentEnvironment.mName+"end:\n");
        return;
      case IF_THEN_ELSE_STATEMENT:
      case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
        generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+currentEnvironment.mName+"else\n");
        generateForNode(writer, currentEnvironment, node.children.get(4), currentOffsets, currentOffset, externs, packageMap);
        writer.write("  je "+currentEnvironment.mName+"end\n");
        writer.write(currentEnvironment.mName+"else:\n");
        generateForNode(writer, currentEnvironment, node.children.get(6), currentOffsets, currentOffset, externs, packageMap);
        writer.write(currentEnvironment.mName+"end:\n");
        return;
      case WHILE_STATEMENT:
      case WHILE_STATEMENT_NO_SHORT_IF:
        writer.write(currentEnvironment.mName+"start:\n");
        generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+currentEnvironment.mName+"end\n");
        generateForNode(writer, currentEnvironment, node.children.get(4), currentOffsets, currentOffset, externs, packageMap);
        writer.write("  je "+currentEnvironment.mName+"start\n");
        writer.write(currentEnvironment.mName+"end:\n");
        return;
      case FOR_STATEMENT:
      case FOR_STATEMENT_NO_SHORT_IF:
        for (Environment childEnv : currentEnvironment.mChildrenEnvironments) {
          if (childEnv.mScope == node.children.get(8)) {
            currentEnvironment = childEnv;
            for (String var : childEnv.mVariableDeclarations.keySet()) {
              currentOffset += 4;
              currentOffsets.put(var, new Pair(currentOffset, childEnv.mVariableToType.get(var)));
            }
          }
        }
        generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs, packageMap);
        writer.write(currentEnvironment.mName+"start:\n");
        generateForNode(writer, currentEnvironment, node.children.get(4), currentOffsets, currentOffset, externs, packageMap);
        writer.write("  cmp eax 0\n");
        writer.write("  je "+currentEnvironment.mName+"end\n");
        generateForNode(writer, currentEnvironment, node.children.get(8), currentOffsets, currentOffset, externs, packageMap);
        generateForNode(writer, currentEnvironment, node.children.get(6), currentOffsets, currentOffset, externs, packageMap);
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
              generateForNode(writer, currentEnvironment, node.children.get(i), currentOffsets, currentOffset, externs, packageMap);
              writer.write("  cmp eax 0\n");
              writer.write("  je "+currentEnvironment.mName+"end\n");
            }
          }
          writer.write(currentEnvironment.mName+"end:\n");
        }
        else {
          generateForNode(writer, currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs, packageMap);
        }
        return;
      case INCLUSIVE_OR_EXPRESSION:
        if(node.children.size()>1){
          for(int i=0;i<node.children.size();i++) {
            if(1%2==0) {
              generateForNode(writer, currentEnvironment, node.children.get(i), currentOffsets, currentOffset, externs, packageMap);
              writer.write("  cmp eax 1\n");
              writer.write("  je "+currentEnvironment.mName+"end\n");
            }
          }
          writer.write(currentEnvironment.mName+"end:\n");
        }
        else {
          generateForNode(writer, currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs, packageMap);
        }
        return;
      case NAME:
        generateForName(writer, currentEnvironment, getNameFromTypeNode(node), currentOffsets, externs, packageMap);
        return;
      case BOOLEAN_LITERAL_TRUE:
        writer.write("  mov eax, 1\n");
        return;
      case NULL_LITERAL:
      case BOOLEAN_LITERAL_FALSE:
        writer.write("  mov eax, 0\n");
        return;
      case INTEGER_LITERAL:
        writer.write("  mov eax, " + ((TerminalToken)node.token).getRawValue() + "\n");
        return;
      case CHAR_LITERAL:
        writer.write("  mov eax, " + Character.getNumericValue(((TerminalToken)node.token).getRawValue().charAt(0)) + "\n");
        return;
      case STRING_LITERAL:
        writer.write("  mov eax, 0\n"); //TODO
        return;
      case ESCAPE: {
        String value = ((TerminalToken)node.token).getRawValue();
        switch (value.charAt(0)) {
          case 't':
            writer.write("  mov eax, 9\n");
            return;
          case 'b':
            writer.write("  mov eax, 8\n");
            return;
          case 'n':
            writer.write("  mov eax, 10\n");
            return;
          case 'r':
            writer.write("  mov eax, 13\n");
            return;
          default:
            writer.write("  mov eax, " + Character.getNumericValue(value.charAt(0)) + "\n");
            return;
        }
      }
      case VARIABLE_DECLARATOR: {
        if (node.children.size() == 3) {
          generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs, packageMap);
          String var = ((TerminalToken)node.children.get(0).token).getRawValue();
          writer.write("  mov dword [ebp - " + currentOffsets.get(var).first + "], eax\n");
        }
        return;
      }
      case ASSIGNMENT: {
        generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs, packageMap);
        switch (node.children.get(0).children.get(0).token.getType()) {
          case NAME: {
            String name = getNameFromTypeNode(node.children.get(0).children.get(0));
            int dotIndex = name.lastIndexOf('.');
            if (dotIndex == -1) {
              if (currentOffsets.containsKey(name)) {
                writer.write("  mov dword [ebp - " + currentOffsets.get(name).first + "], eax\n");
              } else {
                writer.write("  mov ebx, eax\n");
                writer.write("  mov eax, [ebp + 8]\n");
                // writer.write("  mov dword [eax + " + getOffsetForField(currentEnvironment.getParentClassEnvironment(), name, packageMap) + "], ebx\n");
                writer.write("  mov eax, ebx\n");
              }
            } else {
              writer.write("  mov ebx, eax\n");
              Environment fieldEnv = generateForName(
                writer,
                currentEnvironment,
                name.substring(0, dotIndex),
                currentOffsets,
                externs,
                packageMap
              ).second;
              name = name.substring(dotIndex + 1);
              int offset = getOffsetForField(fieldEnv, name, packageMap).first;
              writer.write("  mov dword [eax + " + offset + "], ebx\n");
              writer.write("  mov eax, ebx\n");
            }
            break;
          }
          case FIELD_ACCESS: {
            String fieldName = ((TerminalToken)findNodeWithTokenType(node.children.get(0).children.get(0).children.get(2), TokenType.IDENTIFIER).token).getRawValue();
            writer.write("  push eax\n");
            generateForNode(writer, currentEnvironment, node.children.get(0).children.get(0).children.get(0), currentOffsets, currentOffset, externs, packageMap);
            writer.write("  pop ebx\n");
            int offset = getOffsetForField(
              packageMap.get(node.children.get(0).children.get(0).children.get(0).type.name),
              fieldName,
              packageMap
            ).first;
            writer.write("  mov dword [eax + " + offset + "], ebx\n");
            writer.write("  mov eax, ebx\n");
            break;
          }
          case ARRAY_ACCESS: {
            writer.write("  push eax\n"); // push the value to assign
            generateForNode(
              writer,
              currentEnvironment,
              node.children.get(0).children.get(0).children.get(2),
              currentOffsets,
              currentOffset,
              externs,
              packageMap
            );
            writer.write("  mov ebx, 4\n");
            writer.write("  mul ebx\n");
            writer.write("  add eax, 8\n");
            switch (node.children.get(0).children.get(0).children.get(0).token.getType()) {
              case NAME: {
                writer.write("  mov ebx, eax\n"); //ebx is the index
                generateForName(
                  writer,
                  currentEnvironment,
                  getNameFromTypeNode(node.children.get(0).children.get(0).children.get(0)),
                  currentOffsets,
                  externs,
                  packageMap
                ); // eax is the array
                writer.write("  add eax, ebx\n");
                writer.write("  pop ebx\n"); // the value
                writer.write("  mov dword [eax], ebx\n");
                writer.write("  mov eax, ebx\n");
                break;
              }
              case PRIMARY_NO_NEW_ARRAY: {
                writer.write("  push eax\n");
                generateForNode(
                  writer,
                  currentEnvironment,
                  node.children.get(0).children.get(0).children.get(0),
                  currentOffsets,
                  currentOffset,
                  externs,
                  packageMap
                );
                writer.write("  pop ebx\n"); // the index
                writer.write("  add eax, ebx\n");
                writer.write("  pop ebx\n"); // the value
                writer.write("  mov dword [eax], ebx\n");
                writer.write("  mov eax, ebx\n");
                break;
              }
            }
            break;
          }
        }
        return;
      }
      case ARRAY_CREATION_EXPRESSION: {
        generateForNode(writer, currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs, packageMap);
        writer.write("  push eax\n"); // size
        writer.write("  mov ebx, 4\n");
        writer.write("  mul ebx\n");
        writer.write("  add eax, 8\n");
        externs.add("__malloc");
        writer.write("  call __malloc\n"); // eax is pointer to array
        writer.write("  pop ebx\n"); // size
        writer.write("  mov dword [eax], VTABLE$java.lang.Object\n");
        writer.write("  mov dword [eax + 4], ebx\n");
        writer.write("  push eax\n");
        writer.write("  push eax\n");
        writer.write("  mov eax, ebx\n");
        writer.write("  mov ebx, 4\n");
        writer.write("  mul ebx\n"); // eax size of array memory
        writer.write("  pop ebx\n"); // ebx is beginning
        writer.write("  add ebx, 8\n"); // offset by header data
        writer.write("  add eax, ebx\n"); // eax is end of memory
        String label = "ARRAYINIT$" + node.getFirstTerminalNode().token.getIndex();
        writer.write(label + ":\n");
        writer.write("  cmp eax, ebx\n");
        writer.write("  je " + label + ".end\n");
        writer.write("  add ebx, 4\n");
        writer.write("  mov dword [ebx], 0\n");
        writer.write("  jmp " + label + "\n");
        writer.write(".end:\n");
        writer.write("  pop eax\n");
        return;
      }
      case CLASS_INSTANCE_CREATION_EXPRESSION: {
        int numArgs = 0;
        List<String> argTypes = new ArrayList();
        if (node.children.get(3).children.size() > 0) {
          for (ParseTreeNode param : node.children.get(3).children.get(0).children) {
            if (param.token.getType() == TokenType.COMMA) continue;
            argTypes.add(param.type.name);
            generateForNode(writer, currentEnvironment, param, currentOffsets, currentOffset, externs, packageMap);
            numArgs++;
            writer.write("  push eax\n");
          }
        }
        writer.write("  push 0\n");
        Environment classEnv = getEnvironmentFromTypeNode(currentEnvironment, node.children.get(1), packageMap);
        int size = (getFieldList(currentEnvironment, packageMap).size() + 1) * 4;
        String constructorLabel = "CONSTRUCTOR$" + getClassLabel(classEnv) + "@";
        for (String argType : argTypes) {
          constructorLabel += argType + "#";
        }
        if (classEnv != currentEnvironment.getParentClassEnvironment()) {
          externs.add(constructorLabel);
        }
        writer.write("  call " + constructorLabel + "\n");
        writer.write("  add esp, " + (numArgs + 1) * 4 + "\n");
        return;
      }
      case THIS: {
        writer.write("  mov eax, [ebp + 8]\n");
        return;
      }
    }
    if (node.children != null) {
      for (ParseTreeNode child : node.children) {
        generateForNode(writer, currentEnvironment, child, currentOffsets, currentOffset, externs, packageMap);
      }
    }
    if (currentEnvironment.mScope == node && currentEnvironment.mVariableDeclarations.size() > 0) {
      writer.write("  add esp, " + currentEnvironment.mVariableDeclarations.size() * 4 + "\n");
    }
  }
}
