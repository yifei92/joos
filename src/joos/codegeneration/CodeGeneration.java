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
import joos.typechecking.TypeCheckingEvaluator;

import static joos.environment.EnvironmentUtils.getNameFromTypeNode;
import static joos.environment.EnvironmentUtils.findNodeWithTokenType;
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeName;
import static joos.environment.EnvironmentUtils.getEnvironmentType;
import static joos.environment.EnvironmentUtils.getExtendedEnvironments;
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeNode;
import static joos.environment.EnvironmentUtils.moveUpToClassEnvironment;

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
    if (methodSignature.modifiers.contains(TokenType.NATIVE)) {
      return "NATIVEjava.io.OutputStream.nativeWrite";   // hardcode any natice method to use the only native function we have
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
            writer.write("  dd 0\n");
          }
        }
      }
    }
    writer.write("\n");
  }

  public void generateStartCode(Environment startMethodClassEnvironment, Set<String> externs) {
    writer.write("global _start\n");
    writer.write("_start:\n");
    // call static method initalizers
    for (Environment environment : packageMap.values()) {
      if(getEnvironmentType(environment) == EnvironmentType.CLASS) {
        String label = "STATICFIELDINITIALIZER$" + getClassLabel(environment);
        if (environment != startMethodClassEnvironment) {
          externs.add(label);
        }
        writer.write("  call " + label + "\n");
      }
    }
    // call the static int test() function
    String packagePrefix = "";
    if (startMethodClassEnvironment.PackageName != null && !startMethodClassEnvironment.PackageName.equals("")) {
      packagePrefix = startMethodClassEnvironment.PackageName + ".";
    }
    String staticMethodLabel = "STATICMETHOD$" + packagePrefix + startMethodClassEnvironment.mName + "$test@";
    writer.write("  push 0\n");
    writer.write("  mov eax, " + staticMethodLabel + "\n");
    writer.write("  call eax\n");
    writer.write("  add esp, 4\n");
    // move the return value into ebx
    writer.write("  mov ebx, eax\n");
    //Load the value 1 (indicating sys_exit) into register eax,
    writer.write("  mov eax, 1\n");
    //then execute the instruction int 0x80.
    writer.write("  int 0x80\n");
  }

  private boolean isStartMethod(Environment method) throws InvalidSyntaxException {
    MethodSignature sig = method.getMethodSignature(packageMap, null);
    return sig.modifiers != null && sig.modifiers.contains(TokenType.STATIC) &&
        sig.name != null && sig.name.equals("test") &&
        sig.parameterTypes != null && sig.parameterTypes.size() == 0 &&
        sig.type != null && sig.type.equals("int");
  }

  public void generateStaticFieldInitializer(Environment environment, Set<String> externs) throws IOException, InvalidSyntaxException {
    String label = "STATICFIELDINITIALIZER$" + getClassLabel(environment);
    writer.write("global " + label + "\n" + label + ":\n");

    for (Map.Entry<String, ParseTreeNode> declaration : environment.mVariableDeclarations.entrySet()) {
      String key = declaration.getKey();
      ParseTreeNode value = declaration.getValue();
      if (environment.mVariableToType.get(key).modifiers.contains(TokenType.STATIC)) {
        // we have a static variable
        // check if it has an enitialization expression
        ParseTreeNode variableInitializer = findNodeWithTokenType(value, TokenType.VARIABLE_INITIALIZER);
        if (variableInitializer != null) {
          // we have an initialization field generate code to initialize it
          generateForNode(environment, variableInitializer, externs);
          // set the resulting value in eax to the static field
          String staticFieldLabel = "STATICFIELD$" + getClassLabel(environment) + "$" + key;
          writer.write("  mov [" + staticFieldLabel + "], eax\n");
        }
      }
    }
    writer.write("  ret\n");
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
    writer.write("section .data\n");
    for (String key : environment.mVariableToType.keySet()) {
      if (environment.mVariableToType.get(key).modifiers.contains(TokenType.STATIC)) {
        String label = "STATICFIELD$" + getClassLabel(environment) + "$" + key;
        writer.write("global " + label + "\n" + label + ":\n  dd 0\n\n");
      }
    }
    writer.write("section .text\n");
    generateStaticFieldInitializer(environment, externs);

    Environment startMethodEnvironment = null;
    for (Environment child : environment.mChildrenEnvironments) {
      switch(getEnvironmentType(child)) {
        case METHOD:
          generateForMethod(environment, child, externs);
          if (isStartMethod(child)) {
            startMethodEnvironment = child;
          }
          break;
        case CONSTRUCTOR:
          generateForConstructor(environment, child, externs);
          break;
      }
    }

    if (startMethodEnvironment != null) {
      // The start method was found in this class.
      // Generate start code at the end of this file
      generateStartCode(moveUpToClassEnvironment(startMethodEnvironment), externs);
    }

    writer.flush();

    String filename = getClassLabel(environment);
    File file = new File("output/" + filename + ".s");
    file.getParentFile().mkdirs();
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
    if(methodSignature.modifiers.contains(TokenType.NATIVE)){
      return;   //hardcode native method definition do not do anything since we assume that is will be a gloabal label
    }
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

    // implicit super call
    List<Environment> extendedEnvironments = getExtendedEnvironments(classEnv, packageMap);
    if (extendedEnvironments != null && extendedEnvironments.size() > 0) {
      String constructorLabel = "CONSTRUCTOR$" + getClassLabel(extendedEnvironments.get(0)) + "@";
      externs.add(constructorLabel);
      writer.write("  push dword [ebp + 8]\n");
      writer.write("  call " + constructorLabel + "\n");
      writer.write("  add esp, 4\n");
    }

    // field initializers
    for (String fieldName : classEnv.mVariableDeclarations.keySet()) {
      if (classEnv.mVariableToType.get(fieldName).modifiers.contains(TokenType.STATIC)) continue;
      ParseTreeNode fieldNode = classEnv.mVariableDeclarations.get(fieldName).children.get(2).children.get(0);
      if (fieldNode.children.size() == 3) {
        generateForNode(classEnv, fieldNode.children.get(2), offsets, 0, externs);
      }
      writer.write("  mov ebx, [ebp + 8]\n");
      writer.write("  mov dword [ebx + " + getOffsetForField(classEnv, fieldName).first + "], eax\n");
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
      if (generateForName(environment, prefix, node, offsets, externs).first) {
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

        writer.write("  cmp eax, 0\n");
        writer.write("  jne EXCEPTION$" + node.children.get(1).getFirstTerminalNode().token.getIndex() + "m\n");

        writer.write("  call __exception\n");

        writer.write("EXCEPTION$" + node.children.get(1).getFirstTerminalNode().token.getIndex() + "m:\n");
        writer.write("  push eax\n"); //push new this
        writer.write("  mov eax, [eax]\n"); //INTERFACETABLE
        generateForMethodOffset(typeEnvironment, methodSignature);
        writer.write("  mov eax, [eax]\n");
      }
    } else {
      // primary.c()
      generateForNode(environment, node.children.get(0), offsets, currentOffset, externs);
      String name = ((TerminalToken)findNodeWithTokenType(node.children.get(2), TokenType.IDENTIFIER).token).getRawValue();
      Environment typeEnvironment = packageMap.get(node.children.get(0).type.name);
      MethodSignature methodSignature = typeEnvironment.getMethodSignatures(packageMap).get(name).get(argTypes);
      writer.write("  cmp eax, 0\n");
      writer.write("  jne EXCEPTION$" + node.children.get(2).getFirstTerminalNode().token.getIndex() + "\n");

      writer.write("  call __exception\n");

      writer.write("EXCEPTION$" + node.children.get(2).getFirstTerminalNode().token.getIndex() + ":\n");
      writer.write("  push eax\n"); //push new this
      writer.write("  mov eax, [eax]\n"); //INTERFACETABLE
      generateForMethodOffset(typeEnvironment, methodSignature);
      writer.write("  mov eax, [eax]\n");
    }
  }

  private Pair<Boolean, Environment> generateForName(Environment environment, String name, ParseTreeNode node, Map<String, Pair<Integer, Type>> offsets, Set<String> externs) throws IOException, InvalidSyntaxException {
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

    int i = 0;
    while (dotIndex != -1) {
      int newDotIndex = name.indexOf('.', dotIndex + 1);
      if (newDotIndex == -1) {
        prefix = name.substring(dotIndex + 1);
      } else {
        prefix = name.substring(dotIndex + 1, newDotIndex);
      }
      if (stat) {
        stat = false;
        String label = "STATICFIELD$" + getClassLabel(fieldEnv) + "$" + prefix;
        if (fieldEnv != environment.getParentClassEnvironment()) {
          externs.add(label);
        }
        writer.write("  mov eax, [" + label +"]\n");
        fieldEnv = packageMap.get(fieldEnv.mVariableToType.get(prefix).name);
      } else {
        writer.write("  cmp eax, 0\n");
        writer.write("  jne EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + "$" + i + "\n");

        writer.write("  call __exception\n");

        writer.write("EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + "$" + i + ":\n");
        if (prefix.equals("length") && fieldEnv == null) {
          writer.write("  mov eax, [eax + 8]\n");
          return new Pair(false, null);
        }
        Pair<Integer, Type> pair = getOffsetForField(fieldEnv, prefix);
        int offset = pair.first;
        writer.write("  mov eax, [eax + " + offset + "]\n");
        fieldEnv = packageMap.get(pair.second.name);
      }
      dotIndex = newDotIndex;
      i++;
    }
    return new Pair(stat, fieldEnv);
  }

  public void generateForMethodInvocation(Environment currentEnvironment, ParseTreeNode node, Map<String, Pair<Integer, Type>> currentOffsets, int currentOffset, Set<String> externs) throws IOException, InvalidSyntaxException {
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
  }

  public void generateForNode(Environment environment, ParseTreeNode node, Set<String> externs) throws IOException, InvalidSyntaxException {
    generateForNode(environment, node, new HashMap<>(), 0, externs);
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
        generateForMethodInvocation(currentEnvironment, node, currentOffsets, currentOffset, externs);
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
        writer.write("  jmp label"+uniqueid+"end\n");
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
        writer.write("  jmp label"+uniqueid+"start\n");
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
        writer.write("  jmp label"+uniqueid+"start\n");
        writer.write("label"+uniqueid+"end:\n");
        if (currentEnvironment.mVariableDeclarations.size() > 0) {
          writer.write("  add esp, " + currentEnvironment.mVariableDeclarations.size() * 4 + "\n");
        }
        return;
      case UNARY_EXPRESSION: {
        if (node.children.size() == 1) {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        } else {
          // get the value in eax of the unary expression
          generateForNode(currentEnvironment, node.children.get(1), currentOffsets, currentOffset, externs);
          // multiply by -1
          Type unaryType = node.children.get(1).getFirstType();
          if (unaryType.name.equals("int") || unaryType.name.equals("short")) {
            writer.write("  imul eax, -1\n");
          } else {
            writer.write("  mul eax, -1\n");
          }
        }
        return;
      }
      case UNARY_EXPRESSION_NOT_PLUS_MINUS: {
        if (node.children.size() == 1) {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        } else {
          generateForNode(currentEnvironment, node.children.get(1), currentOffsets, currentOffset, externs);
          writer.write("  not eax \n");
          writer.write("  and eax, 1\n");
        }
        return;
      }
      case CONDITIONAL_OR_EXPRESSION:
      case CONDITIONAL_AND_EXPRESSION: {
        if (node.children.size() == 1) {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        } else {
          // generate code for the first expression
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
          // save the running total onto the stack
          writer.write("  push eax\n");
          for (int i = 1 ; i < node.children.size() ; i++) {
            ParseTreeNode child = node.children.get(i);
            if (child.token.getType() != TokenType.BOOL_OP_AND&&child.token.getType() != TokenType.BOOL_OP_OR) {
              // generate the code for this expression
              generateForNode(currentEnvironment, child, currentOffsets, currentOffset, externs);
              // fetch the running total
              writer.write("  pop ebx ; pop last result\n");
              // apply the and operator to the running total
              if (node.token.getType() == TokenType.CONDITIONAL_AND_EXPRESSION) {
                writer.write("  and ebx, eax ; && op\n");
              } else {
                writer.write("  or ebx, eax  ; || op\n");
              }
              // save the running total
              writer.write("  push ebx\n");
            }
          }
          // all results must be in the eax
          writer.write("  pop eax\n");
        }
        return;
      }
      case MULTIPLICATIVE_EXPRESSION: {
        if(node.children.size() == 1) {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        } else {
          // Flag to keep track of whether or not we should add or subtract the current result
          // from the running total
          TokenType operator = null;
          // generate code for the first expression
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
          // save the running total onto the stack
          writer.write("  push eax\n");
          for (int i = 1 ; i < node.children.size() ; i++) {
            ParseTreeNode child = node.children.get(i);
            TokenType type = child.token.getType();
            if (type == TokenType.STAR || type == TokenType.OP_REMAINDER || type == TokenType.OP_DIV) {
              operator = type;
            } else {
              // generate the code for this expression
              generateForNode(currentEnvironment, node.children.get(i), currentOffsets, currentOffset, externs);
              // fetch the running total
              writer.write("  pop ebx\n");
              // apply the current operator to the running total
              switch(operator) {
                case STAR:
                  writer.write("  imul ebx, eax\n");
                break;
                case OP_REMAINDER:
                  // move result of last expression into ecx
                  // this is the divisor
                  writer.write("  mov ecx, eax\n");
                  // move the running total into eax
                  // this is the dividend
                  writer.write("  mov eax, ebx\n");
                  // perform the division
                  writer.write("  div ecx\n");
                  // move the remainder from edx into ebx
                  writer.write("  mov ebx, edx\n");
                break;
                case OP_DIV:
                  // move result of last expression into ecx
                  // this is the divisor
                  writer.write("  mov ecx, eax\n");
                  // move the running total into eax
                  // this is the dividend
                  writer.write("  mov eax, ebx\n");
                  // perform the division
                  writer.write("  div ecx\n");
                  // move the quotient from eax into ebx
                  writer.write("  mov ebx, eax\n");
                break;
              }
              // save the running total
              writer.write("  push ebx\n");
            }
          }
          // all results must be in the eax
          writer.write("  pop eax\n");
        }
        return;
      }
      case ADDITIVE_EXPRESSION: {
        // TODO need to be able to concat strings.
        if(node.children.size() == 1) {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        } else {
          // Flag to keep track of whether or not we should add or subtract the current result
          // from the running total
          boolean isPlus = false;
          // generate code for the first expression
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
          // save the running total onto the stack
          writer.write("  push eax\n");
          for (int i = 1 ; i < node.children.size() ; i++) {
            ParseTreeNode child = node.children.get(i);
            TokenType type = child.token.getType();
            if (type == TokenType.OP_PLUS) {
              isPlus = true;
            } else if (type == TokenType.OP_MINUS) {
              isPlus = false;
            } else {
              // generate the code for this expression
              generateForNode(currentEnvironment, node.children.get(i), currentOffsets, currentOffset, externs);
              // fetch the running total
              writer.write("  pop ebx\n");
              // apply the current operator to the running total
              if(isPlus) {
                writer.write("  add ebx, eax\n");
              } else {
                writer.write("  sub ebx, eax\n");
              }
              // save the running total
              writer.write("  push ebx\n");
            }
          }
          // all results must be in the eax
          writer.write("  pop eax\n");
        }
        return;
      }
      case RELATIONAL_EXPRESSION: {
        if(node.children.size() == 1) {
          generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        } else if(node.children.get(1).token.getType() == TokenType.INSTANCEOF) {
          //TODO: dis gon b some wierd shit
          generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
          int offset=subTypingTesting.getoffset(node.children.get(0).type.name);
          writer.write("  mov ebx, [eax + 8]\n");
          writer.write(" mov eax , "+subTypingTesting.getrowsize()+"\n");
          writer.write(" mul ebx \n");
          writer.write("  mov eax, [subtypecheckingtable+eax+"+offset+"]  ;check instance of\n");
          return;
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
          writer.write("mov ecx, 1\n");
          switch(node.children.get(1).token.getType()) {
            case COMP_LESS_THAN:
              writer.write("cmovl eax, ecx\n");
            break;
            case COMP_GREATER_THAN:
              writer.write("cmovg eax, ecx\n");
            break;
            case COMP_LESS_THAN_EQ:
              writer.write("cmovle eax, ecx\n");
            break;
            case COMP_GREATER_THAN_EQ:
              writer.write("cmovge eax, ecx\n");
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
          writer.write("mov ecx, 1\n");
          switch(node.children.get(1).token.getType()) {
            case COMP_EQ:
              writer.write("cmove eax, ecx\n");
            break;
            case COMP_NOT_EQ:
              writer.write("cmovne eax, ecx\n");
            break;
          }
        }
        return;
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
        generateForName(currentEnvironment, getNameFromTypeNode(node), node, currentOffsets, externs);
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
        writer.write("  mov eax, " + (size + 3) * 4 + "\n");
        externs.add("__malloc");
        writer.write("  call __malloc\n"); // allocate array
        if (currentEnvironment.getParentClassEnvironment() != packageMap.get("java.lang.Object")) {
          externs.add("InterfaceTABLE$java.lang.Object");
        }
        writer.write("  mov dword [eax], InterfaceTABLE$java.lang.Object\n");
        writer.write("  mov dword [eax + 4],"+subTypingTesting.getoffset("char[]")+"\n");
        writer.write("  mov dword [eax + 8], " + size + "\n");
        int i = 12;
        for (int val : list) { // populate array
          writer.write("  mov dword [eax + " + i + "], " + val + "\n");
          i += 4;
        }
        writer.write("  push eax\n");

        writer.write("  mov eax, 12\n");
        writer.write("  call __malloc\n"); // allocate string
        if (currentEnvironment.getParentClassEnvironment() != packageMap.get("java.lang.String")) {
          externs.add("InterfaceTABLE$java.lang.String");
        }
        writer.write("  mov dword [eax], InterfaceTABLE$java.lang.String\n");
        writer.write("  mov dword [eax + 4], " + subTypingTesting.getoffset("java.lang.String") + "\n");
        writer.write("  pop ebx\n");
        writer.write("  mov dword [eax + 8], ebx\n");
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
                writer.write("  mov dword [eax + " + getOffsetForField(currentEnvironment.getParentClassEnvironment(), name).first + "], ebx\n");
                writer.write("  mov eax, ebx\n");
              }
            } else {
              writer.write("  mov ebx, eax\n");
              Environment fieldEnv = generateForName(
                currentEnvironment,
                name.substring(0, dotIndex),
                node,
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

            writer.write("  cmp eax, 0\n");
            writer.write("  jne EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + "\n");

            writer.write("  call __exception\n");

            writer.write("EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + ":\n");
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
            switch (node.children.get(0).children.get(0).children.get(0).token.getType()) {
              case NAME: {
                writer.write("  mov ebx, eax\n"); //ebx is the index
                generateForName(
                  currentEnvironment,
                  getNameFromTypeNode(node.children.get(0).children.get(0).children.get(0)),
                  node,
                  currentOffsets,
                  externs
                ); // eax is the array
                writer.write("  cmp ebx, [eax + 8]\n");
                writer.write("  jnge EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + "a\n");

                writer.write("  call __exception\n");

                writer.write("EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + "a:\n");
                writer.write("  push eax\n");
                writer.write("  mov eax, ebx\n");
                writer.write("  mov ebx, 4\n");
                writer.write("  mul ebx\n");
                writer.write("  add eax, 12\n");

                writer.write("  pop ebx\n"); // the array
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
                writer.write("  cmp ebx, [eax + 8]\n");
                writer.write("  jnge EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + "a\n");

                writer.write("  call __exception\n");

                writer.write("EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + "a:\n");
                writer.write("  push eax\n");
                writer.write("  mov eax, ebx\n");
                writer.write("  mov ebx, 4\n");
                writer.write("  mul ebx\n");
                writer.write("  add eax, 12\n");

                writer.write("  pop ebx\n"); // the array
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

        writer.write("  cmp eax, 0\n");
        writer.write("  jne EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + "\n");

        writer.write("  call __exception\n");

        writer.write("EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + ":\n");

        int offset;
        String identifier = ((TerminalToken)node.children.get(2).token).getRawValue();
        if (node.children.get(0).type.type == TypeType.ARRAY && identifier.equals("length")) {
          offset = 8;
        } else {
          offset = getOffsetForField(
            packageMap.get(node.children.get(0).type.name),
            ((TerminalToken)node.children.get(2).token).getRawValue()
          ).first;
        }
        writer.write("  mov eax, [eax + " + offset + "]\n");
        return;
      }
      case ARRAY_ACCESS: {
        generateForNode(currentEnvironment, node.children.get(0), currentOffsets, currentOffset, externs);
        writer.write("  push eax\n");
        generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
        writer.write("  mov ebx, eax\n");
        writer.write("  pop eax\n"); // the array
        writer.write("  cmp ebx, [eax + 8]\n");
        writer.write("  jnge EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + "a\n");

        writer.write("  call __exception\n");

        writer.write("EXCEPTION$" + node.getFirstTerminalNode().token.getIndex() + "a:\n");
        writer.write("  push eax\n");
        writer.write("  mov eax, ebx\n");

        writer.write("  add eax, 3\n");
        writer.write("  mov ebx, 4\n");
        writer.write("  mul ebx\n");
        writer.write("  mov ebx, eax\n");
        writer.write("  pop eax\n");
        writer.write("  add eax, ebx\n");
        writer.write("  mov eax, [eax]\n");
        return;
      }
      case ARRAY_CREATION_EXPRESSION: {

        generateForNode(currentEnvironment, node.children.get(2), currentOffsets, currentOffset, externs);
        writer.write("  push eax\n"); // size
        writer.write("  mov ebx, 4\n");
        writer.write("  mul ebx\n");
        writer.write("  add eax, 12\n");
        externs.add("__malloc");
        writer.write("  call __malloc\n"); // eax is pointer to array
        writer.write("  pop ebx\n"); // size
        if (currentEnvironment.getParentClassEnvironment() != packageMap.get("java.lang.Object")) {
          externs.add("InterfaceTABLE$java.lang.Object");
        }
        writer.write("  mov dword [eax], InterfaceTABLE$java.lang.Object\n");
        writer.write("  mov dword [eax + 4],"+subTypingTesting.getoffset(node.type.name)+"\n");
        writer.write("  mov dword [eax + 8], ebx\n");
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

        Environment classEnv = getEnvironmentFromTypeNode(currentEnvironment, node.children.get(1), packageMap);
        int size = (getFieldList(currentEnvironment).size() + 2) * 4;

        writer.write("  mov eax, " + size + "\n");
        externs.add("__malloc");
        writer.write("  call __malloc\n");
        if (classEnv != currentEnvironment.getParentClassEnvironment()) {
          externs.add("InterfaceTABLE$" + getClassLabel(classEnv));
        }
        writer.write("  mov dword [eax], InterfaceTABLE$" + getClassLabel(classEnv) + "\n");
        writer.write("  mov dword [eax + 4], " + subTypingTesting.getoffset(getClassLabel(classEnv)) + "\n");
        for (int j = 8; j < size; j += 4) {
          writer.write("  mov dword [eax + " + j + "], 0\n");
        }

        writer.write("  push eax\n"); // set this

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
      case RETURN_STATEMENT: {
        generateForNode(currentEnvironment, node.children.get(1), currentOffsets, currentOffset, externs);
        writer.write("  add esp, " + currentOffset + "\n");
        writer.write("  pop ebp\n");
        writer.write("  ret\n");
        return;
      }
      case CAST_EXPRESSION: {
        generateForNode(currentEnvironment, node.children.get(node.children.size() - 1), currentOffsets, currentOffset, externs);
        if(TypeCheckingEvaluator.isprimitiveType(node.children.get(node.children.size() - 1).type)){
          return;
        }
        writer.write("  mov ebx, [eax + 8]\n"); // get class descriptor
        int offset=subTypingTesting.getoffset(node.children.get(1).type.name);
        writer.write("push eax\n");
        writer.write(" mov eax , "+subTypingTesting.getrowsize()+"\n");
        writer.write(" mul ebx \n");
        writer.write("  mov ebx, [subtypecheckingtable+eax+"+offset+"] ; check cast expression\n");
        writer.write(" cmp ebx, 0\n");
        int unique=subTypingTesting.getuniqueid();
        writer.write(" je subtypingcheck"+unique+" \n");
        writer.write(" call __exception\n");
        writer.write("subtypingcheck"+unique+":\n");
        writer.write("pop eax\n");
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
