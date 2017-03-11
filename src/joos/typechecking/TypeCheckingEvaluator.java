package joos.typechecking;


import joos.commons.*;
import joos.environment.Environment;
import joos.environment.EnvironmentUtils;
import joos.exceptions.InvalidSyntaxException;
import joos.exceptions.TypeLinkingException;
import joos.typelinking.TypeLinking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static joos.environment.EnvironmentUtils.findEvironment;
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeNode;

/**
 * Created by yifei on 09/03/17.
 */
public class TypeCheckingEvaluator {
	private String returntype;
	private ParseTreeNode root;

	public Type check(ParseTreeNode currentnode,Map<String, Environment> PackageMap,Environment current) throws TypeLinkingException, InvalidSyntaxException {
		switch (currentnode.token.getType()) {
			case STRING_LITERAL:
				return new Type("String");
			case INT:
			case INTEGER_LITERAL:
				return new Type("Int");
			case SHORT:
				return new Type("Short");
			case BYTE:
				return new Type("Byte");
			case CHAR:
				return new Type("Char");
			case BOOLEAN:
				return new Type("Boolean");
			case THIS:
				return new Type(current.mName);
			case INCLUSIVE_OR_EXPRESSION:
			case AND_EXPRESSION:
			case CONDITIONAL_AND_EXPRESSION:
			case CONDITIONAL_OR_EXPRESSION:
				if(currentnode.children.size()>1){
					if(check(currentnode.children.get(0),PackageMap,current).equals("Boolean") && check(currentnode.children.get(2),PackageMap,current).equals("Boolean") ){
						return check(currentnode.children.get(0),PackageMap,current);
					}
					else{
						throw new TypeLinkingException("and expression has non boolean input");
					}
				}
				else {
					return check(currentnode.children.get(0),PackageMap,current);
				}
			case ADDITIVE_EXPRESSION:
			case MULTIPLICATIVE_EXPRESSION:
				if(currentnode.children.size()>1){
					if(check(currentnode.children.get(0),PackageMap,current).equals("String")){
						if(!check(currentnode.children.get(2),PackageMap,current).equals("NULL")){
							return check(currentnode.children.get(0),PackageMap,current);
						}
						else{
							throw new TypeLinkingException("fail string concation");
						}
					}
					if(check(currentnode.children.get(2),PackageMap,current).equals("String")){
						if(!check(currentnode.children.get(0),PackageMap,current).equals("NULL")){
							return check(currentnode.children.get(2),PackageMap,current);
						}
						else{
							throw new TypeLinkingException("fail string concation");
						}
					}
					if(isnumicType(check(currentnode.children.get(0),PackageMap,current)) && isnumicType(check(currentnode.children.get(2),PackageMap,current))){
						return new Type("Int");
					}
					else{
						throw new TypeLinkingException("and expression has non boolean input");
					}
				}
				else {
					return check(currentnode.children.get(0),PackageMap,current);
				}
			case IF_THEN_STATEMENT:
			case WHILE_STATEMENT:
			case WHILE_STATEMENT_NO_SHORT_IF:
				if(!check(currentnode.children.get(2),PackageMap,current).equals("Boolean")){
					throw new TypeLinkingException("if condition does not evaluate to boolean");
				}
				return check(currentnode.children.get(4),PackageMap,current);

			case IF_THEN_ELSE_STATEMENT:
				if(!check(currentnode.children.get(2),PackageMap,current).equals("Boolean")){
					throw new TypeLinkingException("if condition does not evaluate to boolean");
				}
				check(currentnode.children.get(4),PackageMap,current);
				return check(currentnode.children.get(4),PackageMap,current);

			case FOR_STATEMENT:
			case FOR_STATEMENT_NO_SHORT_IF:
				if(!check(currentnode.children.get(4),PackageMap,current).equals("Boolean")){
					throw new TypeLinkingException("for condition does not evaluate to boolean");
				}
				return check(currentnode.children.get(8),PackageMap,current);

			case ASSIGNMENT:
				Type left=check(currentnode.children.get(0),PackageMap,current);
				Type right=check(currentnode.children.get(2),PackageMap,current);
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
				left=check(currentnode.children.get(0),PackageMap,current);
				right=check(currentnode.children.get(2),PackageMap,current);
				if(left.equals(right)){
					return left;
				}
				if(isnumicType(left)&&isnumicType(right)){
					return new Type("Int");
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
				return left;
			case EQUALITY_EXPRESSION:
				left=check(currentnode.children.get(0),PackageMap,current);
				right=check(currentnode.children.get(2),PackageMap,current);
				if(left.equals(right)){
					return new Type("boolean");
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
				return new Type("boolean");

			case RELATIONAL_EXPRESSION:
				if(currentnode.children.size()>1){
					if(currentnode.children.get(1).token.getType()==TokenType.INSTANCEOF){
						return new Type("boolean");
					}
					if(isnumicType(check(currentnode.children.get(0),PackageMap,current))&& isnumicType(check(currentnode.children.get(2),PackageMap,current)) ){
						return check(currentnode.children.get(0),PackageMap,current);
					}
					else{
						//throw new TypeLinkingException("comparison expression has non numeric input "+current.mName);
						return null;
					}
				}
				else {
					return check(currentnode.children.get(0),PackageMap,current);
				}

			case RETURN_STATEMENT:
				if(!check(currentnode.children.get(1),PackageMap,current).equals(returntype)){
					throw new TypeLinkingException("return type does match method declation");
				}
			return null;

			case NAME:
				/*
				String fullname=fullnameFromnamenode(currentnode);
				Environment local=findEvironment(current,root,currentnode);
				if(local.mVariableToType.get(fullname)!=null) {
					return local.mVariableToType.get(fullname);
				}
				*/
				if(currentnode.type==null){
					System.out.println("NAME type null");
				}
				System.out.print(currentnode.type);
				return currentnode.type;
			case METHOD_INVOCATION:
				List<String> parameterTyps=new ArrayList<>();
				if(currentnode.children.size()==4) {
					if(currentnode.children.get(2).children!=null) {
						for (ParseTreeNode parameter : currentnode.children.get(2).children.get(0).children) {
							if (parameter.token.getType() != TokenType.COMMA) {
								parameterTyps.add(check(parameter, PackageMap, current).name);
							}
						}
					}
					String Fullname=fullnameFromnamenode(currentnode.children.get(0));
					String classname=Fullname.substring(0,Fullname.lastIndexOf(".")-1);
					String methodname=Fullname.substring(Fullname.lastIndexOf(".")-1, Fullname.length());
					MethodSignature m=PackageMap.get(classname).getMethodSignatures(PackageMap).get(methodname).get(parameterTyps);
					return new Type(m.type);
				}
				if(currentnode.children.get(4).children!=null)
					for (ParseTreeNode parameter:currentnode.children.get(4).children.get(0).children){
						if(parameter.token.getType()!=TokenType.COMMA) {
							parameterTyps.add(check(parameter, PackageMap,current).name);
						}
					}
				String methodname=((TerminalToken)currentnode.children.get(2).token).getRawValue();
				MethodSignature m=PackageMap.get(check(currentnode.children.get(0), PackageMap,current).name).getMethodSignatures(PackageMap).get(methodname).get(parameterTyps);
				return new Type(m.type);

			case ARRAY_ACCESS:
				if(!isnumicType(check(currentnode.children.get(2),PackageMap,current))){
					throw new TypeLinkingException("index must be numeric");
				}
				return check(currentnode.children.get(0),PackageMap,current);

			case FIELD_ACCESS:
				Environment Typecalled=PackageMap.get(check(currentnode.children.get(0),PackageMap,current).name);
				return Typecalled.mVariableToType.get(((TerminalToken)currentnode.children.get(1).token).getRawValue());
			case PRIMARY_NO_NEW_ARRAY:
				if (currentnode.children.size()>1){
					return  check(currentnode.children.get(1),PackageMap,current);
				}
				return  check(currentnode.children.get(0),PackageMap,current);

			case EXPRESSION_OPT:
				if(currentnode.children==null){
					return new Type("Void");
				}
				else{
					return  check(currentnode.children.get(0),PackageMap,current);
				}
			case CLASS_BODY_DECLARATIONS:
			case BLOCK_STATEMENTS:
				for(ParseTreeNode child: currentnode.children){
					check(child,PackageMap,current);
				}
				return null;

			case BLOCK_STATEMENTS_OPT:
			case INTERFACE_MEMBER_DECLARATIONS_OPT:
				if(currentnode.children!=null&&currentnode.children.size()>0){
					return check(currentnode.children.get(0),PackageMap,current);
				}
				return null;
			case METHOD_HEADER:
				if(currentnode.children.get(1).token.getType()== TokenType.TYPE){
					returntype =check(currentnode.children.get(1).children.get(0),PackageMap,current).name;
				}
				else{
					returntype = "Void";
				}
			return null;

			case LOCAL_VARIABLE_DECLARATION:
				Type Typedef=check(currentnode.children.get(0),PackageMap,current);
				Type initilization=check(currentnode.children.get(1),PackageMap,current);
				if(!Typedef.equals(initilization)){
					throw new TypeLinkingException("inilize with wrong type");
				}
				return null;
			case FIELD_DECLARATION:
				Typedef=check(currentnode.children.get(1),PackageMap,current);
				initilization=check(currentnode.children.get(2),PackageMap,current);
				if(!Typedef.equals(initilization)){
					throw new TypeLinkingException("inilize with wrong type");
				}
				return null;
			case CLASS_INSTANCE_CREATION_EXPRESSION:
				Type typedef=check(currentnode.children.get(1),PackageMap,current);

				Environment typeenviroment;
				try {
					typeenviroment=getEnvironmentFromTypeNode(current,currentnode.children.get(1).children.get(0),PackageMap);
				} catch (InvalidSyntaxException e) {
					throw new TypeLinkingException("cannot find constractor");
				}
				parameterTyps =new ArrayList<>();
				if(currentnode.children.get(3).children!=null) {
					for (ParseTreeNode parameter : currentnode.children.get(3).children.get(0).children) {
						if (parameter.token.getType() != TokenType.COMMA) {
							parameterTyps.add(check(parameter, PackageMap, current).name);
						}
					}
				}
				System.out.println(typedef.name+"  "+typeenviroment.mName);
				if(typeenviroment.getMethodSignatures(PackageMap).get(typedef.name).get(parameterTyps)==null){
					throw new TypeLinkingException("cant find constructor");
				};
				return typedef;
			case ARRAY_CREATION_EXPRESSION:
				return new Type(check(currentnode.children.get(0), PackageMap, current).name+"[]");
			case METHOD_DECLARATION:
				check(currentnode.children.get(0),PackageMap,current);
				return check(currentnode.children.get(1),PackageMap,current);
			case SEMICOLON:
			case MODIFIERS_OPT:
				if(currentnode.children.get(100).equals(null))
				return null;
			case METHOD_BODY:
					if (currentnode.children.get(0).token.getType()==TokenType.BLOCK){
						return check(currentnode.children.get(0),PackageMap,current);
					}
				return null;
			case CLASS_OR_INTERFACE_TYPE:
				return currentnode.type;
			case CLASS_TYPE:
			case REFERENCE_TYPE:
			case TYPE:
			case VARIABLE_INITIALIZER:
			case VARIABLE_DECLARATORS:
			case LOCAL_VARIABLE_DECLARATION_STATEMENT:
			case STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT:
			case INTEGRAL_TYPE:
			case NUMERIC_TYPE:
			case PRIMITIVE_TYPE:
			case STATEMENT:
			case BLOCK_STATEMENT:
			case CLASS_MEMBER_DECLARATION:
			case GOAL:
			case SHIFT_EXPRESSION:
			case EXCLUSIVE_OR_EXPRESSION:
			case CONDITIONAL_EXPRESSION:
			case LEFT_HAND_SIDE:
			case ASSIGNMENT_EXPRESSION:
			case PRIMARY:
			case LITERAL:
			case EXPRESSION:
			case CLASS_BODY_DECLARATION:
			case TYPE_DECLARATIONS_OPT:
			case TYPE_DECLARATIONS:
			case CLASS_BODY_DECLARATIONS_OPT:
			case TYPE_DECLARATION:    //weird semi Colum case
				return check(currentnode.children.get(0),PackageMap,current);
			case CONSTRUCTOR_DECLARATION:
			case VARIABLE_DECLARATOR:
				return check(currentnode.children.get(2),PackageMap,current);
			case CLASS_DECLARATION:
				return check(currentnode.children.get(5),PackageMap,current);
			case INTERFACE_DECLARATION:
				return check(currentnode.children.get(4),PackageMap,current);
			case BLOCK:
			case CLASS_BODY:
			case CONSTRUCTOR_BODY:
			case INTERFACE_BODY:
				return check(currentnode.children.get(1),PackageMap,current);
			case COMPILATION_UNIT:
				root=currentnode;
				return check(currentnode.children.get(2),PackageMap,current);
			default:
				System.out.println(currentnode.token.getType());
				return null;
		}
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
