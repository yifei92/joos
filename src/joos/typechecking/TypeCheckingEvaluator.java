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
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeName;
import static joos.environment.EnvironmentUtils.getEnvironmentFromTypeNode;

/**
 * Created by yifei on 09/03/17.
 */
public class TypeCheckingEvaluator {
	private String returntype;
	private ParseTreeNode root;

	public Type check(ParseTreeNode currentnode,Map<String, Environment> PackageMap,Environment rootenv) throws TypeLinkingException, InvalidSyntaxException {
		switch (currentnode.token.getType()) {
			case STRING_LITERAL:
			case STRING_LITERAL_WITH_QUOTES:
				return new Type("java.lang.String");
			case INT:
			case INTEGER_LITERAL:
				return new Type("int");
			case SHORT:
				return new Type("short");
			case BYTE:
				return new Type("byte");
			case CHAR:
			case CHAR_LITERAL_WITH_QUOTES:
				return new Type("char");
			case BOOLEAN:
			case BOOLEAN_LITERAL:
				return new Type("boolean");
			case NULL_LITERAL:
				return new Type("null");
			case THIS:
				System.out.println(rootenv.PackageName);
				return new Type(rootenv.PackageName+"."+rootenv.mName);
			case INCLUSIVE_OR_EXPRESSION:
			case AND_EXPRESSION:
			case CONDITIONAL_AND_EXPRESSION:
			case CONDITIONAL_OR_EXPRESSION:
				if(currentnode.children.size()>1){
					if(check(currentnode.children.get(0),PackageMap,rootenv).equals("boolean") && check(currentnode.children.get(2),PackageMap,rootenv).equals("boolean") ){
						return check(currentnode.children.get(0),PackageMap,rootenv);
					}
					else{
						throw new TypeLinkingException("and expression has non boolean input");
					}
				}
				else {
					return check(currentnode.children.get(0),PackageMap,rootenv);
				}
			case ADDITIVE_EXPRESSION:
			case MULTIPLICATIVE_EXPRESSION:
				if(currentnode.children.size()>1){
					if(check(currentnode.children.get(0),PackageMap,rootenv).equals("java.lang.String")){
						if(!check(currentnode.children.get(2),PackageMap,rootenv).equals("null")){
							return check(currentnode.children.get(0),PackageMap,rootenv);
						}
						else{
							throw new TypeLinkingException("fail string concation");
						}
					}
					if(check(currentnode.children.get(2),PackageMap,rootenv).equals("java.lang.String")){
						if(!check(currentnode.children.get(0),PackageMap,rootenv).equals("null")){
							return check(currentnode.children.get(2),PackageMap,rootenv);
						}
						else{
							throw new TypeLinkingException("fail string concation");
						}
					}
					if(isnumicType(check(currentnode.children.get(0),PackageMap,rootenv)) && isnumicType(check(currentnode.children.get(2),PackageMap,rootenv))){
						return new Type("int");
					}
					else{
						throw new TypeLinkingException("and expression has non boolean input");
					}
				}
				else {
					return check(currentnode.children.get(0),PackageMap,rootenv);
				}
			case IF_THEN_STATEMENT:
			case WHILE_STATEMENT:
			case WHILE_STATEMENT_NO_SHORT_IF:
				if(!check(currentnode.children.get(2),PackageMap,rootenv).equals("boolean")){
					throw new TypeLinkingException("if condition does not evaluate to boolean");
				}
				return check(currentnode.children.get(4),PackageMap,rootenv);

			case IF_THEN_ELSE_STATEMENT:
				if(!check(currentnode.children.get(2),PackageMap,rootenv).equals("boolean")){
					throw new TypeLinkingException("if condition does not evaluate to boolean");
				}
				check(currentnode.children.get(4),PackageMap,rootenv);
				return check(currentnode.children.get(4),PackageMap,rootenv);

			case FOR_STATEMENT:
			case FOR_STATEMENT_NO_SHORT_IF:
				if(!check(currentnode.children.get(4),PackageMap,rootenv).equals("boolean")){
					System.out.println(check(currentnode.children.get(4),PackageMap,rootenv).name);
					throw new TypeLinkingException("for condition does not evaluate to boolean");
				}
				return check(currentnode.children.get(8),PackageMap,rootenv);

			case ASSIGNMENT:
				Type left=check(currentnode.children.get(0),PackageMap,rootenv);
				Type right=check(currentnode.children.get(2),PackageMap,rootenv);
				if(left.equals(right)){
					return null;
				}
				if(left.equals("short")&&right.equals("byte")){
					return null;
				}
				if(left.equals("int")&&right.equals("char")){
					return null;
				}
				System.out.print(rootenv.mName+"  "+left.name+"  "+right.name);
				if(!assignable(left.name,right.name,PackageMap)){
					throw new TypeLinkingException("cannot cast "+left.name+" to "+right.name);
				}
				return null;
			case CAST_EXPRESSION:
				if(currentnode.children.size()==4) {
					left = check(currentnode.children.get(1), PackageMap, rootenv);
					right = check(currentnode.children.get(3), PackageMap, rootenv);
					if (left.equals(right)) {
						return left;
					}
					if (isnumicType(left) && isnumicType(right)) {
						return new Type("int");
					}
					if (!assignable(left.name,right.name,PackageMap) || !assignable(right.name,left.name,PackageMap)) {
						throw new TypeLinkingException("cannot cast 1" + left.name + " to " + right.name);
					}
					return left;
				}
				if(currentnode.children.get(2).token.getType()==TokenType.DIMS){
					left = check(currentnode.children.get(1), PackageMap, rootenv);
					left.name=left.name+"[]";
					right = check(currentnode.children.get(4), PackageMap, rootenv);
					if (left.equals(right)) {
						return left;
					}
					if (isnumicType(left) && isnumicType(right)) {
						return new Type("int");
					}
					if (!assignable(left.name,right.name,PackageMap) || !assignable(right.name,left.name,PackageMap))  {
						throw new TypeLinkingException("cannot cast 2" + left.name + " to " + right.name);
					}
					return left;
				}
				left = check(currentnode.children.get(1), PackageMap, rootenv);
				if(currentnode.children.get(3).children!=null&&currentnode.children.get(3).children.size()>0) {
					left.name = left.name + "[]";
				}
				right = check(currentnode.children.get(4), PackageMap, rootenv);
				if (left.equals(right)) {
					return left;
				}
				if (isnumicType(left) && isnumicType(right)) {
					return new Type("int");
				}
					if (!assignable(left.name,right.name,PackageMap) || !assignable(right.name,left.name,PackageMap))  {
						throw new TypeLinkingException("cannot cast 3" + left.name + " to " + right.name);
					}
				return left;
			case EQUALITY_EXPRESSION:
				if(currentnode.children.size()==1){
					return check(currentnode.children.get(0), PackageMap, rootenv);
				}
				left=check(currentnode.children.get(0),PackageMap,rootenv);
				right=check(currentnode.children.get(2),PackageMap,rootenv);
				if(left.equals(right)){
					return new Type("boolean");
				};
				if (!assignable(left.name,right.name,PackageMap) || !assignable(right.name,left.name,PackageMap)) {
					throw new TypeLinkingException(rootenv.mName+"cannot cast equality " + left.name + " to " + right.name);
					//System.out.println(currentnode.children.get(10000).name);
				}
				return new Type("boolean");

			case RELATIONAL_EXPRESSION:
				if(currentnode.children.size()>1){
					if(currentnode.children.get(1).token.getType()==TokenType.INSTANCEOF){
						return new Type("boolean");
					}
					if(isnumicType(check(currentnode.children.get(0),PackageMap,rootenv))&& isnumicType(check(currentnode.children.get(2),PackageMap,rootenv)) ){
						return new Type("boolean");
					}
					else{
						throw new TypeLinkingException("comparison expression has non numeric input");
					}
				}
				else {
					return check(currentnode.children.get(0),PackageMap,rootenv);
				}

			case RETURN_STATEMENT:
				Type ret=check(currentnode.children.get(1),PackageMap,rootenv);
				if(ret.equals(returntype)||(ret.equals("null")&&!returntype.equals("Void"))){
					return null;
				}
				throw new TypeLinkingException("return type does match method declation "+ret.name+ " return type "+ returntype);

			case NAME:
				if(currentnode.type==null){
					System.out.println(rootenv.mName+" "+fullnameFromnamenode(currentnode)+" NAME type null");
					return new Type("char");
				}
				System.out.println("name type resolve to "+fullnameFromnamenode(currentnode)+"  "+currentnode.type.name);
				return currentnode.type;
			case METHOD_INVOCATION:
				List<String> parameterTyps=new ArrayList<>();
				if(currentnode.children.size()==4) {
					if(currentnode.children.get(2).children!=null&&currentnode.children.get(2).children.size()>0) {
						for (ParseTreeNode parameter : currentnode.children.get(2).children.get(0).children) {
							if (parameter.token.getType() != TokenType.COMMA) {
								parameterTyps.add(check(parameter, PackageMap, rootenv).name);
							}
						}
					}

					String fullname=fullnameFromnamenode(currentnode.children.get(0));
					MethodSignature m=null;
					if(fullname.contains(".")){
						String variable=fullname.substring(0,fullname.lastIndexOf("."));
						String methodname=fullname.substring(fullname.lastIndexOf(".")+1,fullname.length());
						Environment local=EnvironmentUtils.findEvironment(rootenv,root,currentnode);
						Type typename=EnvironmentUtils.getVaribaleType(local,variable);
						if(typename==null){ //static invoke
							m=EnvironmentUtils.getEnvironmentFromTypeName(rootenv,variable,PackageMap).findMethodSignature(PackageMap,new MethodSignature(methodname, null, parameterTyps, null, null));
						}
						else{
							m=EnvironmentUtils.getEnvironmentFromTypeName(rootenv,typename.name,PackageMap).findMethodSignature(PackageMap,new MethodSignature(methodname, null, parameterTyps, null, null));
						}
					}
					else {
						m = rootenv.findMethodSignature(PackageMap, new MethodSignature(fullname, null, parameterTyps, null, null));
					}
					if(m==null){
						throw new TypeLinkingException("unable to find method "+fullname+rootenv.mName);
					}
					return new Type(m.type);
				}
				System.out.println("method invocation wiht primary");
				if(currentnode.children.get(4).children!=null)
					for (ParseTreeNode parameter:currentnode.children.get(4).children.get(0).children){
						if(parameter.token.getType()!=TokenType.COMMA) {
							parameterTyps.add(check(parameter, PackageMap,rootenv).name);
						}
					}
				String methodname=((TerminalToken)currentnode.children.get(2).token).getRawValue();
				Type primary=check(currentnode.children.get(0),PackageMap,rootenv);
				MethodSignature m=EnvironmentUtils.getEnvironmentFromTypeName(rootenv,primary.name,PackageMap).findMethodSignature(PackageMap,new MethodSignature(methodname, null, parameterTyps, null, null));
				if(m==null){
					throw new TypeLinkingException("unable to find method "+methodname);
				}
				return new Type(m.type);

			case ARRAY_ACCESS:
				if(!isnumicType(check(currentnode.children.get(2),PackageMap,rootenv))){
					throw new TypeLinkingException("index must be numeric");
				}
				Type arraytype=check(currentnode.children.get(0),PackageMap,rootenv);
				return new Type(arraytype.name.substring(0,arraytype.name.length()-2));

			case FIELD_ACCESS:
				primary=check(currentnode.children.get(0),PackageMap,rootenv);
				Environment Typecalled=EnvironmentUtils.getEnvironmentFromTypeName(rootenv,primary.name,PackageMap);
				System.out.println(Typecalled.mName+" "+((TerminalToken)currentnode.children.get(2).token).getRawValue());
				Type field=Typecalled.mVariableToType.get(((TerminalToken)currentnode.children.get(2).token).getRawValue());
				if(field==null){
					throw new TypeLinkingException("unable to find field");
				}
				return field;
			case PRIMARY_NO_NEW_ARRAY:
				if (currentnode.children.size()>1){
					return  check(currentnode.children.get(1),PackageMap,rootenv);
				}
				return  check(currentnode.children.get(0),PackageMap,rootenv);

			case EXPRESSION_OPT:
				if(currentnode.children==null){
					return new Type("void");
				}
				else{
					return  check(currentnode.children.get(0),PackageMap,rootenv);
				}
			case CLASS_BODY_DECLARATIONS:
			case BLOCK_STATEMENTS:
				for(ParseTreeNode child: currentnode.children){
					check(child,PackageMap,rootenv);
				}
				return null;

			case BLOCK_STATEMENTS_OPT:
			case INTERFACE_MEMBER_DECLARATIONS_OPT:
				if(currentnode.children!=null&&currentnode.children.size()>0){
					return check(currentnode.children.get(0),PackageMap,rootenv);
				}
				return null;
			case METHOD_HEADER:
				if(currentnode.children.get(1).token.getType()== TokenType.TYPE){
					returntype =check(currentnode.children.get(1).children.get(0),PackageMap,rootenv).name;
					System.out.println("populate return "+returntype);
					System.out.println(((TerminalToken)currentnode.children.get(2).children.get(0).token).getRawValue());
				}
				else{
					returntype = "void";
				}
			return null;

			case LOCAL_VARIABLE_DECLARATION:
				Type Typedef=check(currentnode.children.get(0),PackageMap,rootenv);
				Type initilization=check(currentnode.children.get(1),PackageMap,rootenv);
				if(!Typedef.equals(initilization)){
					throw new TypeLinkingException("local variableinilize with wrong type");
				}
				return null;
			case FIELD_DECLARATION:
				Typedef=check(currentnode.children.get(1),PackageMap,rootenv);
				initilization=check(currentnode.children.get(2),PackageMap,rootenv);
				if(initilization==null){
					return null;
				}
				if(Typedef.equals(initilization)){
					return null;
				}
				if(isnumicType(Typedef)&&isnumicType(initilization)) {
					return null;
				}
				throw new TypeLinkingException("field inilize with wrong type "+Typedef.name+initilization.name);
			case CLASS_INSTANCE_CREATION_EXPRESSION:
				Type typedef=check(currentnode.children.get(1),PackageMap,rootenv);

				Environment typeenviroment;
				try {
					typeenviroment=getEnvironmentFromTypeNode(rootenv,currentnode.children.get(1).children.get(0),PackageMap);
				} catch (InvalidSyntaxException e) {
					throw new TypeLinkingException("cannot find constractor 1");
				}
				parameterTyps =new ArrayList<>();
				if(currentnode.children.get(3).children!=null&&currentnode.children.get(3).children.size()>0) {
					for (ParseTreeNode parameter : currentnode.children.get(3).children.get(0).children) {
						if (parameter.token.getType() != TokenType.COMMA) {
							parameterTyps.add(check(parameter, PackageMap, rootenv).name);
						}
					}
				}
				MethodSignature methodSignature=typeenviroment.getConstructorSignature(PackageMap,new MethodSignature(typeenviroment.mName,null,parameterTyps,null,null));
				if(typeenviroment.mName.equals("String")) {
					System.out.println("find constructor " + typeenviroment.mMethodSignatures.keySet());
				}
				if(methodSignature==null) {
					throw new TypeLinkingException("cant find constructor 2");
				}
				return typedef;
			case UNARY_EXPRESSION_NOT_PLUS_MINUS:
				if(currentnode.children.size()==1){
					return check(currentnode.children.get(0),PackageMap,rootenv);
				}
				Type booType=check(currentnode.children.get(1),PackageMap,rootenv);
				if(!booType.equals("boolean")&&!booType.equals("Boolean")){
					throw new TypeLinkingException("bool not operator on no boolean type "+booType.name);
				}
				return booType;
			case ARRAY_CREATION_EXPRESSION:
				return new Type(check(currentnode.children.get(1), PackageMap, rootenv).name+"[]");
			case METHOD_DECLARATION:
				check(currentnode.children.get(0),PackageMap,rootenv);
				return check(currentnode.children.get(1),PackageMap,rootenv);
			case SEMICOLON:
			case MODIFIERS_OPT:
				if(currentnode.children.get(100).equals(null))
				return null;
			case METHOD_BODY:
					if (currentnode.children.get(0).token.getType()==TokenType.BLOCK){
						return check(currentnode.children.get(0),PackageMap,rootenv);
					}
				return null;
			case CLASS_OR_INTERFACE_TYPE:
				return currentnode.type;
			case	UNARY_EXPRESSION:
				if(currentnode.children.size()==1){
					return check(currentnode.children.get(0), PackageMap, rootenv);
				}
				else {
					return check(currentnode.children.get(1), PackageMap, rootenv);
				}
			case	VARIABLE_DECLARATOR:
				if(currentnode.children.size()==1){
					return null;
				}
				return check(currentnode.children.get(2), PackageMap, rootenv);

			case ARRAY_TYPE:
				Type element=check(currentnode.children.get(0), PackageMap, rootenv);
				System.out.println("array type "+element.name);
				return new Type(element.name+"[]");
			case CLASS_TYPE:
			case REFERENCE_TYPE:
			case TYPE:
			case VARIABLE_INITIALIZER:
			case VARIABLE_DECLARATORS:
			case LOCAL_VARIABLE_DECLARATION_STATEMENT:
			case STATEMENT_WITHOUT_TRAILING_SUBSTATEMENT:
			case STATEMENT_NO_SHORT_IF:
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
			case EXPRESSION_STATEMENT:
			case STATEMENT_EXPRESSION:
			case TYPE_DECLARATION:    //weird semi Colum case
				return check(currentnode.children.get(0),PackageMap,rootenv);
			case CONSTRUCTOR_DECLARATION:
				return check(currentnode.children.get(2),PackageMap,rootenv);
			case CLASS_DECLARATION:
				return check(currentnode.children.get(5),PackageMap,rootenv);
			case INTERFACE_DECLARATION:
				return check(currentnode.children.get(4),PackageMap,rootenv);
			case BLOCK:
			case CLASS_BODY:
			case CONSTRUCTOR_BODY:
			case INTERFACE_BODY:
				return check(currentnode.children.get(1),PackageMap,rootenv);
			case COMPILATION_UNIT:
				root=currentnode;
				return check(currentnode.children.get(2),PackageMap,rootenv);
			default:
				System.out.println("error forgot "+currentnode.token.getType());
				System.out.print(currentnode.children.get(1000));
				return null;
		}
	}

	boolean isnumicType(Type t){
		if(t.equals("int")||t.equals("short")||t.equals("byte")||t.equals("char")){
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

	boolean assignable(String parent,String child,Map<String, Environment> PackageMap){
		if(parent.contains("[]")&&child.contains("[]")){
			return assignable(parent.substring(0,parent.length()-2),child.substring(0,child.length()-2),PackageMap);
		}
		Environment childenv=PackageMap.get(child);
		Environment parentenv=PackageMap.get(parent);
		boolean returnboo=false;
		try {
			returnboo=childenv.extendsEnvironment(parentenv,PackageMap);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		return returnboo;
	}
}
