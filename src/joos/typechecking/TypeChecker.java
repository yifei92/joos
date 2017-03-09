package joos.typechecking;

import java.util.Map;
import joos.commons.ParseTreeNode;
import joos.environment.Environment;
import joos.environment.EnvironmentUtils.EnvironmentType;
import joos.exceptions.InvalidSyntaxException;
import static joos.environment.EnvironmentUtils.getEnvironmentType;

public class TypeChecker {

  static public void check(Environment environment, Map<String, Environment> packageMap) throws InvalidSyntaxException {
    EnvironmentType type = getEnvironmentType(environment);
    switch (type) {
      case CLASS: {
        break;
      }
      case INTERFACE: {
        break;
      }
    }
    for (Environment childEnvironment : environment.mChildrenEnvironments) {
      check(childEnvironment, packageMap);
    }
  }
}
