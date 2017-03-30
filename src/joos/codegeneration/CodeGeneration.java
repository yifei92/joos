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

  private Map<String, Environment> packageMap;
  private StringWriter writer;
  private SubTypingTesting subTypingTesting;

  /**
   * List of all of the interfaces in our program
   */
  private static List<Environment> mListOfInterfaces = null;
  private static int mInterfaceTableSize = -1;

  public CodeGeneration(Map<String, Environment> packageMap, SubTypingTesting subTypingTesting) {
    this.packageMap = packageMap;
    this.subTypingTesting = subTypingTesting;
  }

  public String getMethodLabel(Environment environment, MethodSignature methodSignature) {
    String ret = "";
    if (methodSignature.modifiers.contains(TokenType.STATIC)) {
      ret += "STATIC";
    }
    ret += "METHOD$" + getClassLabel(environment) + "$" + methodSignature.name + "@";
    for (String type : methodSignature.parameterTypes) {
      ret += type.replace("[]", "~") + "#";
    }
    return ret;
  }

  public String getClassLabel(Environment environment) {
    String ret = "";
    if (environment.PackageName.length() > 0) {
      ret += environment.PackageName + ".";
    }
    return ret + environment.mName;
  }

  private List<Pair<Type, String>> getFieldList(Environment environment) throws InvalidSyntaxException {
    List<Pair<Type, String>> list = new ArrayList();
    Environment classEnv = environment.getParentClassEnvironment();
    List<Environment> extendedEnvironments = getExtendedEnvironments(classEnv, packageMap);
    if (extendedEnvironments != null && extendedEnvironments.size() > 0) {
      list.addAll(getFieldList(extendedEnvironments.get(0)));
    }

    for (String field : classEnv.mVariableDeclarations.keySet()) {
      if (classEnv.mVariableToType.get(field).modifiers.contains(TokenType.STATIC)) continue;
      list.add(new Pair(classEnv.mVariableToType.get(field), field));
    }
    return list;
  }

  private List<Pair<Environment, MethodSignature>> getMethodList(Environment environment) throws InvalidSyntaxException {
    List<Pair<Environment, MethodSignature>> list = new ArrayList();
    List<Environment> extendedEnvironments = getExtendedEnvironments(environment, packageMap);
    if (extendedEnvironments != null && extendedEnvironments.size() > 0) {
      list.addAll(getMethodList(extendedEnvironments.get(0)));
    }

    for (Environment child : environment.mChildrenEnvironments) {
      if (getEnvironmentType(child) == EnvironmentType.METHOD) {
        MethodSignature methodSignature = child.getMethodSignature(packageMap, "");
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

  private int getVTableOffsetForMethod(Environment environment, MethodSignature methodSignature) throws InvalidSyntaxException {
    List<Pair<Environment, MethodSignature>> list = getMethodList(environment);
    int i = getInterfaceTableSize();
    for (Pair<Environment, MethodSignature> pair : list) {
      if (pair.second.name.equals(methodSignature.name) && pair.second.parameterTypes.equals(methodSignature.parameterTypes)) return i * 4;
      i++;
    }
    return -1;
  }

  private Pair<Integer, Type> getOffsetForField(Environment environment, String field) throws InvalidSyntaxException {
    List<Pair<Type, String>> list = getFieldList(environment);
    int i = 0;
    for (Pair<Type, String> pair : list) {
      if (pair.second.equals(field)) return new Pair((i + 2) * 4, pair.first);
      i++;
    }
    return null;
  }
  public int getInterfaceOffset(Environment methodEnvironment) {
    generateInterfacesList();
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
  public int getInterfaceTableSize() {
    generateInterfacesList();
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

  private void generateInterfacesList() {
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
    generateInterfacesList();
    String className = getClassLabel(classEnvironment);
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
                writer.write("  dd " + getMethodLabel(classEnvironment, signature) + "\n");
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

  public void generateForClass(Environment environment) throws IOException, InvalidSyntaxException {
    if(getEnvironmentType(environment) != EnvironmentType.CLASS) {
      // We don't need to generate code for interfaces
      return;
    }
    writer = new StringWriter();
    Set<String> externs = new HashSet();
    externs.add("subtypecheckingtable");
    externs.add("__exception");
    generateInterfaceTable(environment);
    generateVTable(environment, externs);
    for (String key : environment.mVariableToType.keySet()) {
      if (environment.mVariableToType.get(key).modifiers.contains(TokenType.STATIC)) {
        String label = "STATICFIELD$" + getClassLabel(environment) + "$" + key;
        writer.write("global " + label + "\n" + label + ":\n  dd 0\n\n");
      }
    }
    for (Environment child : environment.mChildrenEnvironments) {
      switch(getEnvironmentType(child)) {
        case METHOD:
          generateForMethod(environment, child, externs);
          break;
        case CONSTRUCTOR:
          generateForConstructor(environment, child, externs);
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

  public void generateVTable(Environment environment, Set<String> externs) throws IOException, InvalidSyntaxException {
    String className = getClassLabel(environment);
    List<Pair<Environment, MethodSignature>> methodList = getMethodList(environment);
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

  public void generateForMethod(Environment classEnv, Environment methodEnv, Set<String> externs) throws IOException, InvalidSyntaxException {
    MethodSignature methodSignature = methodEnv.getMethodSignature(packageMap, "");
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
    generateForNode(methodEnv, methodEnv.mScope.children.get(1), offsets, 0, externs);
    writer.write("  pop ebp\n");
    writer.write("  ret\n");
    writer.write("\n");
  }

  public void getDataForStringLiteral(List<Integer> data, ParseTreeNode node) {
    switch (node.token.getType()) {
      case ESCAPE: {
        String value = ((TerminalToken)node.token).getRawValue();
        int i;
        switch (value.charAt(0)) {
          case 't':
            i = 9;
            break;
          case 'b':
            i = 8;
            break;
          case 'n':
            i = 10;
            break;
          case 'r':
            i = 13;
            break;
          default:
            i = (int)value.charAt(0);
            break;
        }
        data.add(i);
        break;
      }
      case STRING_LITERAL: {
        String value = ((TerminalToken)node.token).getRawValue();
        for (int i = 0; i < value.length(); i++) {
          data.add((int)value.charAt(i));
        }
        break;
      }
    }
    if (node.children != null) {
      for (ParseTreeNode child : node.children) {
        getDataForStringLiteral(data, child);
      }
    }
  }

  public void generateForConstructor(Environment classEnv, Environment constructorEnv, Set<String> externs) throws IOException, InvalidSyntaxException {
    List<String> argTypes = constructorEnv.getConstructorSignature(packageMap);
    String label = "CONSTRUCTOR$" + getClassLabel(classEnv) + "@";
    for (String argType : argTypes) {
      label += argType.replace("[]", "~") + "#";
    }
    writer.write("global " + label + "\n" + label + ":\n");
    Map<String, Pair<Integer, Type>> offsets = new HashMap();
    offsets.put("this", new Pair(-8, Type.newObject(classEnv.mName, classEnv, null)));
    int i = -12;
    for (String param : constructorEnv.mVariableDeclarations.keySet()) {
      offsets.put(param, new Pair(i, constructorEnv.mVariableToType.get(param)));
      i -= 4;
    }
    int size = (getFieldList(classEnv).size() + 2) * 4;
    writer.write("  push ebp\n");
    writer.write("  mov ebp, esp\n");
    writer.write("  mov eax, " + size + "\n");
    externs.add("__malloc");
    writer.write("  call __malloc\n");
    writer.write("  mov dword [ebp + 8], eax\n"); // set this
    writer.write("  mov dword [eax], InterfaceTABLE$" + getClassLabel(classEnv) + "\n");
    writer.write("  mov dword [eax + 4], "+subTypingTesting.getrow(getClassLabel(classEnv))+"\n");
    for (int j = 4; j < size; j += 4) {
      writer.write("  mov dword [eax + " + j + "], 0\n");
    }
    generateForNode(constructorEnv, constructorEnv.mScope.children.get(2), offsets, 0, externs);
    writer.write("  mov eax, [ebp + 8]\n"); //return this
    writer.write("  pop ebp\n");
    writer.write("  ret\n");
    writer.write("\n");
  }

  private void generateForMethodOffset(Environment typeEnvironment, MethodSignature methodSignature)  throws IOException, InvalidSyntaxException {
    if (getEnvironmentType(typeEnvironment) == EnvironmentType.INTERFACE) {
      // get all of the interfaces that this interface may have extended (including itself)
      List<Environment> implementedInterfaces = typeEnvironment.getAllImplementedEnvironments(packageMap);
      implementedInterfaces.add(typeEnvironment);
      for(Environment interfc : implementedInterfaces) {
        if (interfc.mChildrenEnvironments != null) {
          for (Environment abstractMethod : interfc.mChildrenEnvironments) {
            if (methodSignature.equals(abstractMethod)) {
              // We have found the environment of the abstract method that is being invoked
              // We can search for this environment in the global interfaces table
              int offset = getInterfaceOffset(abstractMethod);
              // This is the offset of this method in the interface table
              writer.write("  add eax, " + offset + "\n");
              return;
            }
          }
        }
      }
    } else {
      int offset = getVTableOffsetForMethod(typeEnvironment, methodSignature);
      writer.write("  add eax, " + offset + "\n");
      return;
    }
  }

  private void generateForMethodNode(Environment environment, ParseTreeNode node, Map<String, Pair<Integer, Type>> offsets, int currentOffset, Set<String> externs) throws IOException, InvalidSyntaxException {
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
      // means name()
      String name = getNameFromTypeNode(node.children.get(0));
      int dotIndex = name.lastIndexOf('.');
      if (dotIndex == -1) {
        Environment typeEnvironment = environment.getParentClassEnvironment();
        MethodSignature methodSignature = typeEnvironment.getMethodSignatures(packageMap).get(name).get(argTypes);
        writer.write("  mov eax, [ebp + 8]\n"); //this
        writer.write("  push eax\n"); // push this
        writer.write("  mov eax, [eax]\n"); //top of INTERFACETABLE
        generateForMethodOffset(typeEnvironment, methodSignature);
        writer.write("  mov eax, [eax]\n");
        return;
      }
      String prefix = name.substring(0, dotIndex);
      if (generateForName(environment, prefix, offsets, externs).first) {
        // a.b.c() static
        Environment classEnv = getEnvironmentFromTypeName(environment, prefix, packageMap);
        MethodSignature methodSignature = classEnv.getMethodSignatures(packageMap).get(name.substring(dotIndex + 1)).get(argTypes);
        writer.write("  push 0\n"); //fake this for call
        String label = getMethodLabel(classEnv, methodSignature);
        if (classEnv != environment.getParentClassEnvironment()) {
          externs.add(label);
        }
        writer.write("  mov eax, " + label + "\n");
      } else {
        // a.b.c()
        Environment typeEnvironment = node.children.get(0).type.environment;
        MethodSignature methodSignature = typeEnvironment.getMethodSignatures(packageMap).get(name.substring(dotIndex + 1)).get(argTypes);
        writer.write("  push eax\n"); //push new this
        writer.write("  mov eax, [eax]\n"); //INTERFACETABLE
        generateForMethodOffset(typeEnvironment, methodSignature);
        writer.write("  mov eax, [eax]\n");
      }
    } else {
      // primary.c()
      generateForNode(environment, node.children.get(0), offsets, currentOffset, externs);
      String name = ((TerminalToken)findNodeWithTokenType(node.children.get(2), TokenType.IDENTIFIER).token).getRawValue();
      Environment typeEnvironment = node.children.get(0).type.environment;
      MethodSignature methodSignature = typeEnvironment.getMethodSignatures(packageMap).get(name).get(argTypes);
      writer.write("  push eax\n"); //push new this
      writer.write("  mov eax, [eax]\n"); //INTERFACETABLE
      generateForMethodOffset(typeEnvironment, methodSignature);
      writer.write("  mov eax, [eax]\n");
    }
  }

  private Pair<Boolean, Environment> generateForName(Environment environment, String name, Map<String, Pair<Integer, Type>> offsets, Set<String> externs) throws IOException, InvalidSyntaxException {
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
      Pair<Integer, Type> pair = getOffsetForField(fieldEnv, prefix);
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
        Pair<Integer, Type> pair = getOffsetForField(fieldEnv, prefix);
        int offset = pair.first;
        fieldEnv = packageMap.get(pair.second.name);
        writer.write("  mov eax, [eax + " + offset + "]\n");
      }
      dotIndex = newDotIndex;
    }
    return new Pair(stat, fieldEnv);
  }

  public void generateForNode(Environment environment, ParseTreeNode node, Map<String, Pair<Integer, Type>> offsets, int currentOffset, Set<String> externs) throws IOException, InvalidSyntaxException {
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
            generateForNode(currentEnvironment, param, currentOffsets, currentOffset, externs);
            numArgs++;
            writer.write("  push eax\n");
          }
        }
        generateForMethodNode(currentEnvironment, node, currentOffsets, currentOffset, externs);
        writer.write("  call eax\n");
        writer.write("  add esp, " + (numArgs + 1) * 4 + "\n");
        return;
      }
      case IF_THEN_STATEMENT:
        generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
        writer.write("  cmp eax, 0\n");
        int uniqueid=subTypingTesting.getuniqueid();
        writer.write("  je label"+uniqueid+"end\n");
        generateForNode(currentEnvironment, node.children.get(4), currentOffsets, currentOffset, externs);
        writer.write("label"+uniqueid+"end:\n");
        return;
      case IF_THEN_ELSE_STATEMENT:
      case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
        generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
        writer.write("  cmp eax, 0\n");
        uniqueid=subTypingTesting.getuniqueid();
        writer.write("  je label"+uniqueid+"else\n");
        generateForNode(currentEnvironment, node.children.get(4), currentOffsets, currentOffset, externs);
        writer.write("  je label"+uniqueid+"end\n");
        writer.write("label"+uniqueid+"else:\n");
        generateForNode(currentEnvironment, node.children.get(6), currentOffsets, currentOffset, externs);
        writer.write("label"+uniqueid+"end:\n");
        return;
      case WHILE_STATEMENT:
      case WHILE_STATEMENT_NO_SHORT_IF:
        uniqueid=subTypingTesting.getuniqueid();
        writer.write("label"+uniqueid+"start:\n");
        generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
        writer.write("  cmp eax, 0\n");
        writer.write("  je label"+uniqueid+"end\n");
        generateForNode(currentEnvironment, node.children.get(4), currentOffsets, currentOffset, externs);
        writer.write("  je label"+uniqueid+"start\n");
        writer.write("label"+uniqueid+"end:\n");
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
        uniqueid=subTypingTesting.getuniqueid();
        generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
        writer.write("label"+uniqueid+"start:\n");
        generateForNode(currentEnvironment, node.children.get(4), currentOffsets, currentOffset, externs);
        writer.write("  cmp eax, 0\n");
        writer.write("  je label"+uniqueid+"end\n");
        generateForNode(currentEnvironment, node.children.get(8), currentOffsets, currentOffset, externs);
        generateForNode(currentEnvironment, node.children.get(6), currentOffsets, currentOffset, externs);
        writer.write("  je label"+uniqueid+"start\n");
        writer.write("label"+uniqueid+"end:\n");
        if (currentEnvironment.mVariableDeclarations.size() > 0) {
          writer.write("  add esp, " + currentEnvironment.mVariableDeclarations.size() * 4 + "\n");
        }
        return;
      case ADDITIVE_EXPRESSION: {
        if(node.children.size() == 1) {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        } else {
          // Generate code for lhs
          // assume result is in eax
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);         
          // save the lhs
          writer.write("  push eax\n");
          // Generate for rhs
          generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
          // fetch the saved lhs from the stack
          writer.write("  pop ebx\n");
          if(node.children.get(1).token.getType() == TokenType.OP_PLUS) {
            writer.write("  add eax, ebx\n");
          } else if (node.children.get(1).token.getType() == TokenType.OP_MINUS) {
            writer.write("  sub eax, ebx\n");
          }
        }
        return;
      }
      case RELATIONAL_EXPRESSION: {
        if(node.children.size() == 1) {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        } else if(node.children.get(1).token.getType() == TokenType.INSTANCEOF) {
          //TODO: dis gon b some wierd shit
          generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
          //int offset=subTypingTesting.getoffset(node.children.get(1).type.name);
          writer.write("  mov ebx, [eax + 8]\n");
        } else {
          // Generate code for lhs
          // assume result is in eax
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
          // move result into the stack for safekeeping
          writer.write("  push eax\n");
          // Generate for rhs
          generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
          // fetch the saved lhs from the stack
          writer.write("  pop ebx\n");
          // do a comparison
          writer.write("  cmp ebx, eax\n");
          // eax is false by default
          writer.write("  mov eax, 0\n");          
          // conditional move true to eax
          switch(node.children.get(1).token.getType()) {
            case COMP_LESS_THAN:
              writer.write("cmovl eax, 1\n");
            break;
            case COMP_GREATER_THAN:
              writer.write("cmovg eax, 1\n");
            break;
            case COMP_LESS_THAN_EQ:
              writer.write("cmovle eax, 1\n");
            break;
            case COMP_GREATER_THAN_EQ:
              writer.write("cmovge eax, 1\n");
            break;
          }
        }
        return;
      }
      case EQUALITY_EXPRESSION: {
        if(node.children.size() == 1) {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        } else {
          // Generate code for lhs
          // assume result is in eax
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
          // move result into the stack for safekeeping
          writer.write("  push eax\n");
          // Generate for rhs
          generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
          // fetch the saved lhs from the stack
          writer.write("  pop ebx\n");
          // do a comparison
          writer.write("  cmp ebx, eax\n");
          // eax is false by default
          writer.write("  mov eax, 0\n");          
          // conditional move true to eax
          switch(node.children.get(1).token.getType()) {
            case COMP_EQ:
              writer.write("cmove eax, 1\n");
            break;
            case COMP_NOT_EQ:
              writer.write("cmovne eax, 1\n");
            break;
          }
        }
        return;

      }
      case SHIFT_EXPRESSION: {
        // just contains an additive expression
        generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
      }
      case AND_EXPRESSION:
        if(node.children.size()>1){
          uniqueid=subTypingTesting.getuniqueid();
          for(int i=0;i<node.children.size();i++) {
            if(1%2==0) {
              generateForNode(currentEnvironment, node.children.get(i), currentOffsets, currentOffset, externs);
              writer.write("  cmp eax, 0\n");
              writer.write("  je label"+uniqueid+"end\n");
            }
          }
          writer.write("label"+uniqueid+"end:\n");
        }
        else {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        }
        return;
      case EXCLUSIVE_OR_EXPRESSION: {
        generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
      }
      case INCLUSIVE_OR_EXPRESSION:
        if(node.children.size()>1){
          uniqueid=subTypingTesting.getuniqueid();
          for(int i=0;i<node.children.size();i++) {
            if(1%2==0) {
              generateForNode(currentEnvironment, node.children.get(i), currentOffsets, currentOffset, externs);
              writer.write("  cmp eax, 1\n");
              writer.write("  je label"+uniqueid+"end\n");
            }
          }
          writer.write("label"+uniqueid+"end:\n");
        }
        else {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        }
        return;
      case NAME:
        generateForName(currentEnvironment, getNameFromTypeNode(node), currentOffsets, externs);
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
        writer.write("  mov eax, " + (int)((TerminalToken)node.token).getRawValue().charAt(0) + "\n");
        return;
      case STRING_LITERAL_WITH_QUOTES: {
        List<Integer> list = new ArrayList();
        getDataForStringLiteral(list, node.children.get(1));
        int size = list.size();
        writer.write("  mov eax, " + (size + 2) * 4 + "\n");
        externs.add("__malloc");
        writer.write("  call __malloc\n"); // allocate array
        if (currentEnvironment.getParentClassEnvironment() != packageMap.get("java.lang.Object")) {
          externs.add("VTABLE$java.lang.Object");
        }
        writer.write("  mov dword [eax], VTABLE$java.lang.Object\n");
        writer.write("  mov dword [eax + 4], " + size + "\n");
        int i = 8;
        for (int val : list) { // populate array
          writer.write("  mov dword [eax + " + i + "], " + val + "\n");
          i += 4;
        }
        writer.write("  push eax\n");

        writer.write("  mov eax, 8\n");
        writer.write("  call __malloc\n"); // allocate string
        if (currentEnvironment.getParentClassEnvironment() != packageMap.get("java.lang.String")) {
          externs.add("VTABLE$java.lang.String");
        }
        writer.write("  mov dword [eax], VTABLE$java.lang.String\n");
        writer.write("  pop ebx\n");
        writer.write("  mov dword [eax + 4], ebx\n");
        return;
      }
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
            writer.write("  mov eax, " + (int)value.charAt(0) + "\n");
            return;
        }
      }
      case VARIABLE_DECLARATOR: {
        if (node.children.size() == 3) {
          generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
          String var = ((TerminalToken)node.children.get(0).token).getRawValue();
          writer.write("  mov dword [ebp - " + currentOffsets.get(var).first + "], eax\n");
        }
        return;
      }
      case ASSIGNMENT: {
        generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
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
                // writer.write("  mov dword [eax + " + getOffsetForField(currentEnvironment.getParentClassEnvironment(), name) + "], ebx\n");
                writer.write("  mov eax, ebx\n");
              }
            } else {
              writer.write("  mov ebx, eax\n");
              Environment fieldEnv = generateForName(
                currentEnvironment,
                name.substring(0, dotIndex),
                currentOffsets,
                externs
              ).second;
              name = name.substring(dotIndex + 1);
              int offset = getOffsetForField(fieldEnv, name).first;
              writer.write("  mov dword [eax + " + offset + "], ebx\n");
              writer.write("  mov eax, ebx\n");
            }
            break;
          }
          case FIELD_ACCESS: {
            String fieldName = ((TerminalToken)findNodeWithTokenType(node.children.get(0).children.get(0).children.get(2), TokenType.IDENTIFIER).token).getRawValue();
            writer.write("  push eax\n");
            generateForNode(currentEnvironment, node.children.get(0).children.get(0).children.get(0), currentOffsets, currentOffset, externs);
            writer.write("  pop ebx\n");
            int offset = getOffsetForField(
              packageMap.get(node.children.get(0).children.get(0).children.get(0).type.name),
              fieldName
            ).first;
            writer.write("  mov dword [eax + " + offset + "], ebx\n");
            writer.write("  mov eax, ebx\n");
            break;
          }
          case ARRAY_ACCESS: {
            writer.write("  push eax\n"); // push the value to assign
            generateForNode(
              currentEnvironment,
              node.children.get(0).children.get(0).children.get(2),
              currentOffsets,
              currentOffset,
              externs
            );
            writer.write("  mov ebx, 4\n");
            writer.write("  mul ebx\n");
            writer.write("  add eax, 8\n");
            switch (node.children.get(0).children.get(0).children.get(0).token.getType()) {
              case NAME: {
                writer.write("  mov ebx, eax\n"); //ebx is the index
                generateForName(
                  currentEnvironment,
                  getNameFromTypeNode(node.children.get(0).children.get(0).children.get(0)),
                  currentOffsets,
                  externs
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
                  currentEnvironment,
                  node.children.get(0).children.get(0).children.get(0),
                  currentOffsets,
                  currentOffset,
                  externs
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
      case FIELD_ACCESS: {
        generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        int offset = getOffsetForField(
          packageMap.get(node.children.get(0).type.name),
          ((TerminalToken)node.children.get(2).token).getRawValue()
        ).first;
        writer.write("  mov eax, [eax + " + offset + "]\n");
        return;
      }
      case ARRAY_ACCESS: {
        generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        writer.write("  push eax\n");
        generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
        writer.write("  add eax, 2\n");
        writer.write("  mov ebx, 4\n");
        writer.write("  mul ebx\n");
        writer.write("  mov ebx, eax\n");
        writer.write("  pop eax\n");
        writer.write("  add eax, eab\n");
        writer.write("  mov eax, [eax]\n");
        return;
      }
      case ARRAY_CREATION_EXPRESSION: {
        generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
        writer.write("  push eax\n"); // size
        writer.write("  mov ebx, 4\n");
        writer.write("  mul ebx\n");
        writer.write("  add eax, 8\n");
        externs.add("__malloc");
        writer.write("  call __malloc\n"); // eax is pointer to array
        writer.write("  pop ebx\n"); // size
        if (currentEnvironment.getParentClassEnvironment() != packageMap.get("java.lang.Object")) {
          externs.add("VTABLE$java.lang.Object");
        }
        writer.write("  mov dword [eax], InterfaceTABLE$java.lang.Object\n");
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
            generateForNode(currentEnvironment, param, currentOffsets, currentOffset, externs);
            numArgs++;
            writer.write("  push eax\n");
          }
        }
        writer.write("  push 0\n");
        Environment classEnv = getEnvironmentFromTypeNode(currentEnvironment, node.children.get(1), packageMap);
        int size = (getFieldList(currentEnvironment).size() + 1) * 4;
        String constructorLabel = "CONSTRUCTOR$" + getClassLabel(classEnv) + "@";
        for (String argType : argTypes) {
          constructorLabel += argType.replace("[]", "~") + "#";
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
      case CAST_EXPRESSION: {
        generateForNode(currentEnvironment, node.children.get(node.children.size() - 1), currentOffsets, currentOffset, externs);
        writer.write("  mov ebx, [eax + 8]\n"); // get class descriptor
        int offset=subTypingTesting.getoffset(node.children.get(1).type.name);
        writer.write("  mov ebx, [subtypecheckingtable+ebx+"+offset+"]\n");
        writer.write(" cmp ebx, 0\n");
        int unique=subTypingTesting.getuniqueid();
        writer.write(" je subtypingcheck"+unique+" \n");
        writer.write(" call __exception\n");
        writer.write("subtypingcheck"+unique+":\n");
        return;
      }
    }
    if (node.children != null) {
      for (ParseTreeNode child : node.children) {
        generateForNode(currentEnvironment, child, currentOffsets, currentOffset, externs);
      }
    }
    if (currentEnvironment.mScope == node && currentEnvironment.mVariableDeclarations.size() > 0) {
      writer.write("  add esp, " + currentEnvironment.mVariableDeclarations.size() * 4 + "\n");
    }
  }
}
