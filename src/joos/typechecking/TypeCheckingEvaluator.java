package joos.typechecking;


import jdk.nashorn.internal.codegen.types.*;
import jdk.nashorn.internal.ir.Terminal;
import jdk.nashorn.internal.ir.TernaryNode;
import jdk.nashorn.internal.parser.*;
import joos.commons.*;
import joos.commons.TokenType;
import joos.commons.Type;
import joos.environment.Environment;
import joos.environment.EnvironmentUtils;
import joos.exceptions.InvalidSyntaxException;
import joos.exceptions.TypeLinkingException;
import joos.typelinking.TypeLinking;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static joos.environment.EnvironmentUtils.*;

/**
 * Created by yifei on 09/03/17.
 */
public class TypeCheckingEvaluator {
	private String returntype="";
	private ParseTreeNode root;

	public Type check(ParseTreeNode currentnode,Map<String, Environment> PackageMap,Environment rootenv) throws TypeLinkingException, InvalidSyntaxException {
		switch (currentnode.token.getType()) {
			case STRING_LITERAL:
			case STRING_LITERAL_WITH_QUOTES:
				return new Type("java.lang.String");
			case INTEGER_LITERAL:
			case INT:
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
				if(rootenv.PackageName.equals(""))
				{
					return new Type(rootenv.mName);
				}
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
				if(currentnode.children.size()>1) {
					boolean numeric=true;
					boolean findstring=false;
					for (int i = 0; i < currentnode.children.size(); i++) {
						if (i % 2 == 0) {
							Type temp = check(currentnode.children.get(i), PackageMap, rootenv);
							if (isnumicType(temp)) {
								continue;
							}
							numeric=false;
							if(temp.equals("java.lang.String")){
								findstring=true;
							}
						}
					}
					if(numeric) {
						return new Type("int");
					}
					else{
						if(findstring){
							return new Type("java.lang.String");
						}
						else {
							throw new TypeLinkingException("string concatenate without string");
						}
					}
				}
				else {
					return check(currentnode.children.get(0),PackageMap,rootenv);
				}
			case MULTIPLICATIVE_EXPRESSION:
				if(currentnode.children.size()>1) {
					for (int i = 0; i < currentnode.children.size(); i++) {
						if (i % 2 == 0) {
							Type temp = check(currentnode.children.get(i), PackageMap, rootenv);
							if (!isnumicType(temp)) {
								throw new TypeLinkingException("multi expression has bad input");
							}
						}
					}
					return new Type("int");
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
				return check(currentnode.children.get(6),PackageMap,rootenv);

			case FOR_STATEMENT:
			case FOR_STATEMENT_NO_SHORT_IF:
				check(currentnode.children.get(2),PackageMap,rootenv);
				check(currentnode.children.get(6),PackageMap,rootenv);
				Type conditionclause=check(currentnode.children.get(4),PackageMap,rootenv);
				if(!conditionclause.equals("boolean")&&!conditionclause.equals("void")){
					System.out.println(check(currentnode.children.get(4),PackageMap,rootenv).name);
					throw new TypeLinkingException("for condition does not evaluate to boolean");
				}
				return check(currentnode.children.get(8),PackageMap,rootenv);

			case ASSIGNMENT:
				Type left=check(currentnode.children.get(0),PackageMap,rootenv);
				Type right=check(currentnode.children.get(2),PackageMap,rootenv);
				if(left.equals(right)){
					return left;
				}
				if(left.equals("short")&&right.equals("byte")){
					return left;
				}
				if(left.equals("int")&&right.equals("char")){
					return left;
				}
				if(!assignable(left.name,right.name,PackageMap)){
					throw new TypeLinkingException("cannot cast "+left.name+" to "+right.name);
				}
				return left;
			case CAST_EXPRESSION:
				if(currentnode.children.size()==4) {
					left = check(currentnode.children.get(1), PackageMap, rootenv);
					right = check(currentnode.children.get(3), PackageMap, rootenv);
					if (left.equals(right)) {
						return left;
					}
					if (isnumicType(left) && isnumicType(right)) {
						return left;
					}
					if (!assignable(left.name,right.name,PackageMap) && !assignable(right.name,left.name,PackageMap)) {
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
						return left;
					}
					if (!assignable(left.name,right.name,PackageMap) && !assignable(right.name,left.name,PackageMap))  {
						throw new TypeLinkingException("cannot cast 2" + left.name + " to " + right.name);
					}
					return left;
				}
				left = check(currentnode.children.get(1), PackageMap, rootenv);
				if(currentnode.children.get(2).children!=null&&currentnode.children.get(2).children.size()>0) {
					left.name = left.name + "[]";
				}
				right = check(currentnode.children.get(4), PackageMap, rootenv);
				if (left.equals(right)) {
					return left;
				}
				if (isnumicType(left) && isnumicType(right)) {
					return left;
				}
					if (!assignable(left.name,right.name,PackageMap) && !assignable(right.name,left.name,PackageMap))  {
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
				if(left.equals("null")||right.equals("null")){
					return new Type("boolean");
				}
				if (!assignable(left.name,right.name,PackageMap) && !assignable(right.name,left.name,PackageMap)) {
					throw new TypeLinkingException(rootenv.mName+"cannot cast equality " + left.name + " to " + right.name);
				}
				return new Type("boolean");

			case RELATIONAL_EXPRESSION:
				if(currentnode.children.size()>1){
					if(currentnode.children.get(1).token.getType()==TokenType.INSTANCEOF){
						left=check(currentnode.children.get(0),PackageMap,rootenv);
						if(isnumicType(left)||left.equals("boolean")){
							throw new TypeLinkingException("Cannot check instanceof on simple types");
						}
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
				if(ret.equals(returntype)||(ret.equals("null")&&!returntype.equals("void"))){
					return null;
				}
				if(assignable(returntype,ret.name,PackageMap)){
					System.out.println("return assignable "+returntype+ret.name);
					return null;
				}
				throw new TypeLinkingException("return type does match method declation "+ret.name+ " return type "+ returntype);

			case NAME:
				if(currentnode.type==null){
					System.out.println(rootenv.mName+" "+fullnameFromnamenode(currentnode)+" NAME type null");
					currentnode.children.get(100).equals(null);
					return new Type("char");
				}
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
						Type typename=check(currentnode.children.get(0),PackageMap,rootenv);
						m=EnvironmentUtils.getEnvironmentFromTypeName(rootenv,typename.name,PackageMap).findMethodSignature(PackageMap,new MethodSignature(methodname, null, parameterTyps, null, null));
					}
					else {
						m = rootenv.findMethodSignature(PackageMap, new MethodSignature(fullname, null, parameterTyps, null, null));
					}
					if(m==null){
						throw new TypeLinkingException("unable to find method "+fullname+rootenv.mName);
					}
					return new Type(m.type);
				}
				if(currentnode.children.get(4).children!=null&&currentnode.children.get(4).children.size()>0)
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
				if(primary.name.contains("[]")&&((TerminalToken)currentnode.children.get(2).token).getRawValue().equals("length")){
					return new Type("int");
				}
				Environment Typecalled=EnvironmentUtils.getEnvironmentFromTypeName(rootenv,primary.name,PackageMap);
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
				if(currentnode.children==null||currentnode.children.size()==0){
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
				}
				else{
					returntype = "void";
				}
			return null;

			case LOCAL_VARIABLE_DECLARATION:
				ParseTreeNode arraynode=EnvironmentUtils.findNodeWithTokenType(currentnode.children.get(0),TokenType.ARRAY_TYPE);
				Type Typedef;
				if(arraynode!=null) {
					arraynode=EnvironmentUtils.findNodeWithTokenType(currentnode.children.get(0),TokenType.NAME);
				}
				if(arraynode!=null){
					Typedef=new Type(getFullQualifiedNameFromTypeName(rootenv,fullnameFromnamenode(arraynode),PackageMap)+"[]");
				}else{
					Typedef=check(currentnode.children.get(0),PackageMap,rootenv);
				}
				Type initilization=check(currentnode.children.get(1),PackageMap,rootenv);
				if(Typedef.equals(initilization)){
					return null;
				}
				if(assignable(Typedef.name,initilization.name,PackageMap)){
					return null;
				}
				System.out.println(Typedef.name+initilization.name);
				throw new TypeLinkingException("local variableinilize with wrong type");
			case FIELD_DECLARATION:
				Typedef=check(currentnode.children.get(1),PackageMap,rootenv);
				initilization=check(currentnode.children.get(2),PackageMap,rootenv);
				if(initilization==null){
					return null;
				}
				if(Typedef.equals(initilization)){
					return null;
				}
				if(assignable(Typedef.name,initilization.name,PackageMap)) {
					return null;
				}
				throw new TypeLinkingException(rootenv.mName+" field inilize with wrong type "+Typedef.name+initilization.name);
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

				if(!EnvironmentUtils.verifyConstructorSignature(typeenviroment,parameterTyps,PackageMap)) {
					System.out.println(typeenviroment.mName+ "  "+parameterTyps.get(0));
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
				check(currentnode.children.get(2), PackageMap, rootenv);
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
				if(!currentnode.type.name.contains(".")) {
					return new Type(EnvironmentUtils.getFullQualifiedNameFromTypeName(rootenv, currentnode.type.name, PackageMap));
				}
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
				return new Type(element.name+"[]");

			case VARIABLE_INITIALIZER:
				return check(currentnode.children.get(0),PackageMap,rootenv);

			case STATEMENT_EXPRESSION_LIST:
				for(int i=0;i<currentnode.children.size();i++){
					if(i%2==0){
						check(currentnode.children.get(i), PackageMap, rootenv);
					}
				}
				return null;
			case DIM_EXPR:
				Type index=check(currentnode.children.get(1), PackageMap, rootenv);
				if(!index.equals("int")){
					throw new TypeLinkingException("dimension index not int "+index.name);
				}
				return index;
			case FOR_INIT:
			case FOR_UPDATE_OPT:
			case FOR_UPDATE:
				if(currentnode.children.size()==0){
					return null;
				}
				return check(currentnode.children.get(0),PackageMap,rootenv);
			case CLASS_TYPE:
			case REFERENCE_TYPE:
			case TYPE:
			case FOR_INIT_OPT:
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
			case DIM_EXPRS:
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
				return null;
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
		if(parent.equals(child)){
			return true;
		}

		if(parent.contains("[]")&&child.contains("[]")){
			return assignableArray(parent.substring(0,parent.length()-2),child.substring(0,child.length()-2),PackageMap);
		}

		if(parent.equals("java.lang.Object")&&!isnumicType(new Type(child))&&!child.equals("boolean")){
			return true;
		}

		if(!parent.equals("null")&&!isnumicType(new Type(parent))&&child.equals("null")){
			return true;
		}

		if(parent.equals("short")&&child.equals("byte")){
			return true;
		}
		if(parent.equals("int")&&child.equals("char")){
			return true;
		}
		if(parent.equals("int")&&child.equals("short")){
			return true;
		}
		if(parent.equals("int")&&child.equals("byte")){
			return true;
		}
		if(isnumicType(new Type(parent))&&isnumicType(new Type(child))){
			System.out.println("assign fail "+parent+"  "+child);
			return false;
		}

		if((parent.equals("java.lang.Cloneable")||parent.equals("java.io.Serializable"))&&child.contains("[]")){
			return true;
		}

		if(parent.contains("[]")||child.contains("[]")){
			return false;
		}

		if(parent.equals("boolean")||child.equals("boolean")){
			return false;
		}

		if(parent.equals("null")||child.equals("null")){
			return false;
		}


		Environment childenv=PackageMap.get(child);
		Environment parentenv=PackageMap.get(parent);
		boolean returnboo=false;
		try {
			returnboo=childenv.extendsEnvironment(parentenv,PackageMap);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		if(returnboo){
			return returnboo;
		}
		try {
			returnboo=childenv.implementsEnvironment(parentenv,PackageMap);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		return returnboo;
	}

	boolean assignableArray(String parent,String child,Map<String, Environment> PackageMap){
		if(parent.equals(child)){
			return true;
		}

		if(parent.contains("[]")&&child.contains("[]")){
			return assignableArray(parent.substring(0,parent.length()-2),child.substring(0,child.length()-2),PackageMap);
		}
		/*
		if(isnumicType(new Type(parent))&&isnumicType(new Type(child))){
			return true;
		}
		*/
		if(parent.equals("java.lang.Object")&&!isnumicType(new Type(child))&&!child.equals("boolean")){
			return true;
		}

		if(parent.equals("short")&&child.equals("byte")){
			return true;
		}
		if(parent.equals("int")&&child.equals("char")){
			return true;
		}

		if(!parent.equals("null")&&isnumicType(new Type(parent))&&child.equals("null")){
			return true;
		}

		if(isnumicType(new Type(parent))&&isnumicType(new Type(child))){
			System.out.println("assign fail "+parent+"  "+child);
			return false;
		}
		if((parent.equals("java.lang.Cloneable")||parent.equals("java.io.Serializable"))&&child.contains("[]")){
			return true;
		}

		if(parent.contains("[]")||child.contains("[]")){
			return false;
		}

		if(parent.equals("boolean")||child.equals("boolean")){
			return false;
		}
		if(parent.equals("null")||child.equals("null")){
			return false;
		}

		Environment childenv=PackageMap.get(child);
		Environment parentenv=PackageMap.get(parent);
		boolean returnboo=false;
		try {
			returnboo=childenv.extendsEnvironment(parentenv,PackageMap);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		if(returnboo){
			return returnboo;
		}
		try {
			returnboo=childenv.implementsEnvironment(parentenv,PackageMap);
		} catch (InvalidSyntaxException e) {
			e.printStackTrace();
		}
		return returnboo;
	}
}
