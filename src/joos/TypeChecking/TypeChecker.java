package joos.TypeChecking;

import joos.commons.ParseTreeNode;
import joos.commons.Type;

/**
 * Created by yifei on 09/03/17.
 */
public class TypeChecker {

	public Type check(ParseTreeNode root){
		switch (root.token.getType()) {
			case STRING_LITERAL:
				return new Type("String");
			case INTEGER_LITERAL:
				return new Type("Int");
			case BOOLEAN:
				return new Type("boolean");
			case CLASS_BODY_DECLARATIONS:
			case BLOCK_STATEMENTS:
				for(ParseTreeNode child: root.children){
					check(child);
				}
				return null;
			case BLOCK_STATEMENTS_OPT:
				if(root.children!=null){
					return check(root.children.get(0));
				}
				return null;

			case CLASS_BODY_DECLARATION:
			case TYPE_DECLARATIONS_OPT:
			case TYPE_DECLARATIONS:
			case CLASS_BODY_DECLARATIONS_OPT:
			case METHOD_BODY:
			case TYPE_DECLARATION:    //weird semi Colum case
				return check(root.children.get(0));
			case COMPILATION_UNIT:
				return check(root.children.get(2));
			case CLASS_DECLARATION:
				return check(root.children.get(5));
			case BLOCK:
			case CLASS_BODY:
			case METHOD_DECLARATION:
				return check(root.children.get(1));
		}
		return null;
	}
}
