package joos.environment;

import joos.commons.ParseTreeNode;
import java.util.List;

public class EnvironmentUtils {

	/**
	 * Given the root node from the environment tree and a scope returns the environment node for that scope
	 */
	public static Environment findEvironment(Environment environment, ParseTreeNode mScope) {
		if (environment.mScope == mScope) {
			return environment;
		} else if (environment.mChildrenEnvironments != null && environment.mChildrenEnvironments.size() != 0) {
			for (Environment env : environment.mChildrenEnvironments) {
				return findEvironment(env, mScope);
			}
		}
		return null;
	}
}