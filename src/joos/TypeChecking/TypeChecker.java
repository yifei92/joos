package joos.TypeChecking;

import com.sun.xml.internal.bind.v2.schemagen.episode.Package;
import joos.commons.*;
import joos.environment.Environment;
import joos.environment.EnvironmentUtils;
import joos.exceptions.InvalidSyntaxException;
import joos.exceptions.TypeLinkingException;
import joos.typelinking.TypeLinking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yifei on 09/03/17.
 */
public class TypeChecker {
	private String returntype;

	public Type check(ParseTreeNode root,Map<String, Environment> PackageMap) throws TypeLinkingException {
		switch (root.token.getType()) {
			case STRING_LITERAL:
				return new Type("String");
			case INT:
				return new Type("Int");
			case SHORT:
				return new Type("Short");
			case BYTE:
				return new Type("Byte");
			case CHAR:
				return new Type("Char");
			case BOOLEAN:
				return new Type("Boolean");
			case AND_EXPRESSION:
			case CONDITIONAL_OR_EXPRESSION:
				if(root.children.size()>1){
					if(check(root.children.get(0),PackageMap).equals("Boolean") && check(root.children.get(2),PackageMap).equals("Boolean") ){
						return check(root.children.get(0),PackageMap);
					}
					else{
						throw new TypeLinkingException("and expression has non boolean input");
					}
				}
				else {
					return check(root.children.get(0),PackageMap);
				}
			case ADDITIVE_EXPRESSION:
			case MULTIPLICATIVE_EXPRESSION:
				if(root.children.size()>1){
					if(check(root.children.get(0),PackageMap).equals("String")){
						if(!check(root.children.get(2),PackageMap).equals("NULL")){
							return check(root.children.get(0),PackageMap);
						}
						else{
							throw new TypeLinkingException("fail string concation");
						}
					}
					if(check(root.children.get(2),PackageMap).equals("String")){
						if(!check(root.children.get(0),PackageMap).equals("NULL")){
							return check(root.children.get(2),PackageMap);
						}
						else{
							throw new TypeLinkingException("fail string concation");
						}
					}
					if(isnumicType(check(root.children.get(0),PackageMap)) && isnumicType(check(root.children.get(2),PackageMap))){
						return new Type("Int");
					}
					else{
						throw new TypeLinkingException("and expression has non boolean input");
					}
				}
				else {
					return check(root.children.get(0),PackageMap);
				}
			case IF_THEN_STATEMENT:
			case WHILE_STATEMENT:
			case WHILE_STATEMENT_NO_SHORT_IF:
				if(!check(root.children.get(2),PackageMap).equals("Boolean")){
					throw new TypeLinkingException("if condition does not evaluate to boolean");
				}
				return check(root.children.get(4),PackageMap);

			case IF_THEN_ELSE_STATEMENT:
				if(!check(root.children.get(2),PackageMap).equals("Boolean")){
					throw new TypeLinkingException("if condition does not evaluate to boolean");
				}
				check(root.children.get(4),PackageMap);
				return check(root.children.get(4),PackageMap);

			case FOR_STATEMENT:
			case FOR_STATEMENT_NO_SHORT_IF:
				if(!check(root.children.get(4),PackageMap).equals("Boolean")){
					throw new TypeLinkingException("for condition does not evaluate to boolean");
				}
				return check(root.children.get(8),PackageMap);

			case ASSIGNMENT:
				Type left=check(root.children.get(0),PackageMap);
				Type right=check(root.children.get(2),PackageMap);
				if(left.equals(right)){
					return null;
				}
				if(left.equals("short")&&right.equals("Byte")){
					return null;
				}
				if(left.equals("int")&&right.equals("char")){
					return null;
				}
				Environment leftenv=PackageMap.get(left);
				try {
					if(!EnvironmentUtils.getImplementedEnvironments(leftenv,PackageMap).contains(right)){
						throw new TypeLinkingException("cannot cast "+left+" to "+right);
					}
				} catch (InvalidSyntaxException e) {
					throw new TypeLinkingException("cannot cast "+left+" to "+right);
				}
				return null;
			case CAST_EXPRESSION:
				left=check(root.children.get(0),PackageMap);
				right=check(root.children.get(2),PackageMap);
				if(left.equals(right)){
					return null;
				}
				if(isnumicType(left)&&isnumicType(right)){
					return null;
				}
				 leftenv=PackageMap.get(left);
				Environment rightenv=PackageMap.get(right);
				try {
					if(!(EnvironmentUtils.getImplementedEnvironments(leftenv,PackageMap).contains(right)||EnvironmentUtils.getImplementedEnvironments(rightenv,PackageMap).contains(left))){
						throw new TypeLinkingException("cannot cast "+left+" to "+right);
					}
				} catch (InvalidSyntaxException e) {
					throw new TypeLinkingException("cannot cast "+left+" to "+right);
				}
				return null;
			case EQUALITY_EXPRESSION:
				left=check(root.children.get(0),PackageMap);
				right=check(root.children.get(2),PackageMap);
				if(left.equals(right)){
					return null;
				}
				leftenv=PackageMap.get(left);
				rightenv=PackageMap.get(right);
				try {
					if(!(EnvironmentUtils.getImplementedEnvironments(leftenv,PackageMap).contains(right)||EnvironmentUtils.getImplementedEnvironments(rightenv,PackageMap).contains(left))){
						throw new TypeLinkingException("cannot cast "+left+" to "+right);
					}
				} catch (InvalidSyntaxException e) {
					throw new TypeLinkingException("cannot cast "+left+" to "+right);
				}
				return null;

			case RETURN_STATEMENT:
				if(!check(root.children.get(1),PackageMap).equals(returntype)){
				throw new TypeLinkingException("return type does match method declation");
			}
			return null;

			case METHOD_INVOCATION:
				List<String> parameterTyps=new ArrayList<>();
				if(root.children.size()==4) {
					if(root.children.get(2).children!=null)
					for (ParseTreeNode parameter:root.children.get(2).children.get(0).children){
						if(parameter.token.getType()!=TokenType.COMMA) {
							parameterTyps.add(check(parameter, PackageMap).name);
						}
					}
					String Fullname=fullnameFromnamenode(root.children.get(0));
					String classname=Fullname.substring(0,Fullname.lastIndexOf(".")-1);
					String methodname=Fullname.substring(Fullname.lastIndexOf(".")-1, Fullname.length());
					MethodSignature m=PackageMap.get(classname).mMethodSignatures.get(methodname).get(parameterTyps);
					return new Type(m.type);
				}
				if(root.children.get(4).children!=null)
					for (ParseTreeNode parameter:root.children.get(4).children.get(0).children){
						if(parameter.token.getType()!=TokenType.COMMA) {
							parameterTyps.add(check(parameter, PackageMap).name);
						}
					}
				String methodname=((TerminalToken)root.children.get(2).token).getRawValue();
				MethodSignature m=PackageMap.get(check(root.children.get(0), PackageMap).name).mMethodSignatures.get(methodname).get(parameterTyps);
				return new Type(m.type);

			case ARRAY_ACCESS:
				if(!isnumicType(check(root.children.get(2),PackageMap))){
					throw new TypeLinkingException("index must be numeric");
				}
				return check(root.children.get(0),PackageMap);
			case EXPRESSION_OPT:
				if(root.children==null){
					return new Type("Void");
				}
				else{
					return  check(root.children.get(0),PackageMap);
				}
			case CLASS_BODY_DECLARATIONS:
			case BLOCK_STATEMENTS:
				for(ParseTreeNode child: root.children){
					check(child,PackageMap);
				}
				return null;

			case BLOCK_STATEMENTS_OPT:
				if(root.children!=null){
					return check(root.children.get(0),PackageMap);
				}
				return null;
			case METHOD_HEADER:
				if(root.children.get(1).token.getType()== TokenType.TYPE){
					returntype =((TerminalToken)root.children.get(1).children.get(1).token).getRawValue();
				}
				else{
					returntype = "Void";
				}
			return null;

			case METHOD_DECLARATION:
				check(root.children.get(0),PackageMap);
				return check(root.children.get(1),PackageMap);
			case PRIMARY:
			case EXPRESSION:
			case CLASS_BODY_DECLARATION:
			case TYPE_DECLARATIONS_OPT:
			case TYPE_DECLARATIONS:
			case CLASS_BODY_DECLARATIONS_OPT:
			case METHOD_BODY:
			case TYPE_DECLARATION:    //weird semi Colum case
				return check(root.children.get(0),PackageMap);
			case COMPILATION_UNIT:
				return check(root.children.get(2),PackageMap);
			case CLASS_DECLARATION:
				return check(root.children.get(5),PackageMap);
			case BLOCK:
			case CLASS_BODY:
				return check(root.children.get(1),PackageMap);
		}
		return null;
	}

	Boolean isnumicType(Type t){
		if(t.equals("Int")||t.equals("Short")||t.equals("Byte")||t.equals("Char")){
			return true;
		}
		else{
			return false;
		}
	}
	String fullnameFromnamenode(ParseTreeNode namenode){
		String name="";
		for(ParseTreeNode child:namenode.children){
			name+=((TerminalToken)child.token).getRawValue();
		}
		return name;
	}
}
