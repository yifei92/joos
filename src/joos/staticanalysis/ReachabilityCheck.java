package joos.staticanalysis;

import joos.commons.ParseTreeNode;
import joos.commons.Type;
import joos.environment.Environment;
import joos.exceptions.InvalidSyntaxException;
import joos.exceptions.TypeLinkingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yifei on 14/03/17.
 */
public class ReachabilityCheck {
	private List<ParseTreeNode> in=new ArrayList<>();
	private List<ParseTreeNode> out=new ArrayList<>();

	public boolean check(ParseTreeNode currentnode, Map<String, Environment> PackageMap, Environment rootenv) throws TypeLinkingException, InvalidSyntaxException {
		switch (currentnode.token.getType()) {

			case IF_THEN_STATEMENT:

			default:
				System.out.println("error forgot "+currentnode.token.getType());
				System.out.print(currentnode.children.get(1000));
				return true;
		}
	}
}
