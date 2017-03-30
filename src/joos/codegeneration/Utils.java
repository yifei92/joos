package joos.codegeneration;

import joos.commons.MethodSignature;
import joos.environment.Environment;
import joos.environment.Environment.EnvironmentType;
import joos.exceptions.InvalidSyntaxException;
import joos.commons.TokenType;

public class Utils {

  public static String getMethodLabel(Environment environment, MethodSignature methodSignature) {
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

  public static String getClassLabel(Environment environment) {
    String ret = "";
    if (environment.PackageName.length() > 0) {
      ret += environment.PackageName + ".";
    }
    return ret + environment.mName;
  }
}