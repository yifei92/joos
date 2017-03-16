package joos.staticanalysis;

import joos.commons.ParseTreeNode;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import joos.commons.Type;
import joos.environment.Environment;
import joos.exceptions.InvalidSyntaxException;
import joos.exceptions.StaticAnalysisException;
import joos.exceptions.TypeLinkingException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yifei on 14/03/17.
 */
public class ReachabilityCheck {

	enum ExprType{booleanliteral,Stingliteral,intliteral}
	protected  class ExprResult{
		boolean constant=false;
		ExprType type;
		boolean value;
		int num;
		String s;
	}

	public void check(ParseTreeNode currentnode, Map<String, Environment> PackageMap, Environment rootenv) throws TypeLinkingException, InvalidSyntaxException, StaticAnalysisException {
		switch (currentnode.token.getType()) {
			case IF_THEN_STATEMENT:
				currentnode.children.get(4).in=currentnode.in;
				check(currentnode.children.get(4), PackageMap, rootenv);
				currentnode.out=currentnode.in;
				return ;

			case	IF_THEN_ELSE_STATEMENT:
			case IF_THEN_ELSE_STATEMENT_NO_SHORT_IF:
				currentnode.children.get(4).in=currentnode.in;
				currentnode.children.get(6).in=currentnode.in;
				check(currentnode.children.get(4), PackageMap, rootenv);
				check(currentnode.children.get(6), PackageMap, rootenv);
				currentnode.out=currentnode.children.get(4).out&&currentnode.children.get(6).out;
				return;
			case WHILE_STATEMENT:
			case WHILE_STATEMENT_NO_SHORT_IF:
				ExprResult cond=checkexpr(currentnode.children.get(2),PackageMap,rootenv);
				if(cond.constant){
					if(cond.value){
						currentnode.children.get(4).in=currentnode.in;
						check(currentnode.children.get(4), PackageMap, rootenv);
						currentnode.out=false;
					}
					else {
						currentnode.children.get(4).in=false;
						check(currentnode.children.get(4), PackageMap, rootenv);
						currentnode.out=currentnode.in;
					}
					return;
				}
				currentnode.children.get(4).in=currentnode.in;
				check(currentnode.children.get(4), PackageMap, rootenv);
				currentnode.out=currentnode.in;
				return;

			case RETURN_STATEMENT:
				currentnode.out=false;
				return;
			case STATEMENT:
			case LOCAL_VARIABLE_DECLARATION_STATEMENT:
				if(currentnode.in==false){
						throw new StaticAnalysisException("unreachable statment");
				}
				currentnode.out=currentnode.in;
				return;
			case BLOCK_STATEMENTS:
				currentnode.children.get(0).in=currentnode.in;
				for(int i=0;i<currentnode.children.size();i++){
					if(i!=0){
						currentnode.children.get(i).in=currentnode.children.get(i-1).out;
					}
					check(currentnode.children.get(i),PackageMap,rootenv);
				}
				currentnode.out=currentnode.children.get(currentnode.children.size()-1).out;
				return;
			case BLOCK_STATEMENT:
				currentnode.children.get(0).in = currentnode.in;
				check(currentnode.children.get(0), PackageMap, rootenv);
				currentnode.out = currentnode.children.get(0).out;
				return;
			case BLOCK_STATEMENTS_OPT:
				if(currentnode.children.size()>0&&currentnode.children.get(0).token.getType()==TokenType.BLOCK_STATEMENTS) {
					currentnode.children.get(0).in = currentnode.in;
					check(currentnode.children.get(0), PackageMap, rootenv);
					currentnode.out = currentnode.children.get(0).out;
				}
				return;
			case BLOCK:
				currentnode.children.get(1).in=currentnode.in;
				check(currentnode.children.get(1), PackageMap, rootenv);
				currentnode.out = currentnode.children.get(0).out;
				return;
			case CONSTRUCTOR_BODY:
				currentnode.children.get(1).in=true;
				check(currentnode.children.get(1), PackageMap, rootenv);
				currentnode.out=currentnode.in;
				return;
			case COMPILATION_UNIT:
				check(currentnode.children.get(2), PackageMap, rootenv);
				return;
			case TYPE_DECLARATION:
				if(currentnode.children.get(0).token.getType()== TokenType.CLASS_DECLARATION) {
					check(currentnode.children.get(0), PackageMap, rootenv);
				}
				return;
			case METHOD_BODY:
				if(currentnode.children.get(0).token.getType()==TokenType.BLOCK){
					currentnode.children.get(0).in=true;
					check(currentnode.children.get(0), PackageMap, rootenv);
					currentnode.out=currentnode.children.get(0).out;
				}
				return;
			case METHOD_DECLARATION:
				currentnode.children.get(1).in=true;
				check(currentnode.children.get(1), PackageMap, rootenv);
				currentnode.out=currentnode.children.get(1).out;
				return;
			case CLASS_BODY_DECLARATIONS:
				for(ParseTreeNode child: currentnode.children){
					check(child,PackageMap,rootenv);
				}
				return ;
			case CLASS_DECLARATION:
				check(currentnode.children.get(5), PackageMap, rootenv);
				return;
			case CLASS_BODY:
				check(currentnode.children.get(1), PackageMap, rootenv);
				return;
			case CONSTRUCTOR_DECLARATION:
				check(currentnode.children.get(2), PackageMap, rootenv);
				return;
			case CLASS_MEMBER_DECLARATION:
				if(currentnode.children.get(0).token.getType()==TokenType.METHOD_DECLARATION){
					currentnode.children.get(0).in=true;
					check(currentnode.children.get(0), PackageMap, rootenv);
					currentnode.out=currentnode.children.get(0).out;
				}
				return;
			case CLASS_BODY_DECLARATION:
			case CLASS_BODY_DECLARATIONS_OPT:
			case GOAL:
			case TYPE_DECLARATIONS_OPT:
			case TYPE_DECLARATIONS:
				check(currentnode.children.get(0), PackageMap, rootenv);
				return;
			default:
				System.out.println("error forgot "+currentnode.token.getType());
				System.out.print(currentnode.children.get(1000));
				return ;
		}
	}

	ExprResult checkexpr(ParseTreeNode currentnode, Map<String, Environment> PackageMap, Environment rootenv) {
		switch (currentnode.token.getType()) {
			case INTEGER_LITERAL:
				ExprResult ret = new ExprResult();
				ret.constant = true;
				ret.type = ExprType.intliteral;
				ret.num = Integer.parseInt(((TerminalToken) currentnode.token).getRawValue());
				return ret;
			case BOOLEAN_LITERAL_TRUE:
				ret = new ExprResult();
				ret.constant = true;
				ret.type = ExprType.booleanliteral;
				ret.value = true;
				return ret;
			case BOOLEAN_LITERAL_FALSE:
				ret = new ExprResult();
				ret.constant = true;
				ret.type = ExprType.booleanliteral;
				ret.value = false;
				return ret;
			case CHAR_LITERAL_WITH_QUOTES:
			case STRING_LITERAL_WITH_QUOTES:
				ret = new ExprResult();
				ret.constant = true;
				ret.type = ExprType.Stingliteral;
				return ret;
			case NULL_LITERAL:
				return new ExprResult();
			case CONDITIONAL_OR_EXPRESSION:
			case CONDITIONAL_AND_EXPRESSION:
				if (currentnode.children.size() == 0) {
					return checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				ExprResult left = checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				ExprResult right = checkexpr(currentnode.children.get(2), PackageMap, rootenv);
				if (!left.constant || !right.constant) {
					return new ExprResult();
				}
				if (left.type != ExprType.booleanliteral || right.type != ExprType.booleanliteral) {
					return new ExprResult();
				}
				if (currentnode.children.get(1).token.getType() == TokenType.BOOL_OP_AND) {
					 ret = new ExprResult();
					ret.constant = true;
					ret.type = ExprType.booleanliteral;
					ret.value = left.value && right.value;
					return ret;
				} else {
					 ret = new ExprResult();
					ret.constant = true;
					ret.type = ExprType.booleanliteral;
					ret.value = left.value || right.value;
					return ret;
				}
			case INCLUSIVE_OR_EXPRESSION:
			case EXCLUSIVE_OR_EXPRESSION:
				if (currentnode.children.size() == 0) {
					return checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				left = checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				right = checkexpr(currentnode.children.get(2), PackageMap, rootenv);
				if (!left.constant || !right.constant) {
					return new ExprResult();
				}
				if (left.type != ExprType.booleanliteral || right.type != ExprType.booleanliteral) {
					return new ExprResult();
				}
				ret = new ExprResult();
				ret.constant = true;
				ret.type = ExprType.booleanliteral;
				ret.value = left.value || right.value;
				return ret;
			case AND_EXPRESSION:
				if (currentnode.children.size() == 0) {
					return checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				left = checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				right = checkexpr(currentnode.children.get(2), PackageMap, rootenv);
				if (!left.constant || !right.constant) {
					return new ExprResult();
				}
				if (left.type != ExprType.booleanliteral || right.type != ExprType.booleanliteral) {
					return new ExprResult();
				}
				ret = new ExprResult();
				ret.constant = true;
				ret.type = ExprType.booleanliteral;
				ret.value = left.value && right.value;
				return ret;
			case ADDITIVE_EXPRESSION:
				if (currentnode.children.size() == 0) {
					return checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				left = checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				right = checkexpr(currentnode.children.get(2), PackageMap, rootenv);
				if (!left.constant || !right.constant) {
					return new ExprResult();
				}
				if (left.type == ExprType.intliteral && right.type == ExprType.intliteral) {
					ret = new ExprResult();
					ret.constant = true;
					ret.type = ExprType.intliteral;
					ret.num = left.num + right.num;
					return ret;
				}
				if (left.type == ExprType.Stingliteral && right.type == ExprType.Stingliteral) {
					ret = new ExprResult();
					ret.constant = true;
					ret.type = ExprType.Stingliteral;
					//ret.num = left.num + right.num;
					return ret;
				}
				return new ExprResult();
			case MULTIPLICATIVE_EXPRESSION:
				if (currentnode.children.size() == 0) {
					return checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				left = checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				right = checkexpr(currentnode.children.get(2), PackageMap, rootenv);
				if (!left.constant || !right.constant) {
					return new ExprResult();
				}
				if (left.type != ExprType.intliteral || right.type != ExprType.intliteral) {
					return new ExprResult();
				}
				ret = new ExprResult();
				ret.constant = true;
				ret.type = ExprType.intliteral;
				ret.num = left.num * right.num;
				return ret;

			case EQUALITY_EXPRESSION:
				if (currentnode.children.size() == 0) {
					return checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				left = checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				right = checkexpr(currentnode.children.get(2), PackageMap, rootenv);
				if (!left.constant || !right.constant) {
					return new ExprResult();
				}
				if (left.type == ExprType.intliteral && right.type == ExprType.intliteral) {
					ret = new ExprResult();
					ret.constant = true;
					ret.type = ExprType.booleanliteral;
					ret.value = left.num == right.num;
					return ret;
				}
				if (left.type == ExprType.booleanliteral && right.type == ExprType.booleanliteral) {
					ret = new ExprResult();
					ret.constant = true;
					ret.type = ExprType.booleanliteral;
					ret.value = left.value == right.value;
					return ret;
				}
			case ASSIGNMENT_EXPRESSION:
				return new ExprResult();
			case RELATIONAL_EXPRESSION:
				if (currentnode.children.size() == 0) {
					return checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				left = checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				right = checkexpr(currentnode.children.get(2), PackageMap, rootenv);
				if (!left.constant || !right.constant) {
					return new ExprResult();
				}
				if (currentnode.children.get(1).token.getType() == TokenType.INSTANCEOF) {
					return new ExprResult();
				}
				if (left.type == ExprType.intliteral && right.type == ExprType.intliteral) {
					ret = new ExprResult();
					ret.constant = true;
					ret.type = ExprType.booleanliteral;
					if (currentnode.children.get(1).token.getType() == TokenType.COMP_LESS_THAN) {
						ret.value = left.num < right.num;
					}
					if (currentnode.children.get(1).token.getType() == TokenType.COMP_GREATER_THAN) {
						ret.value = left.num > right.num;
					}
					if (currentnode.children.get(1).token.getType() == TokenType.COMP_LESS_THAN_EQ) {
						ret.value = left.num <= right.num;
					}
					if (currentnode.children.get(1).token.getType() == TokenType.COMP_GREATER_THAN_EQ) {
						ret.value = left.num >= right.num;
					}
					return ret;
				}
				return new ExprResult();
			case UNARY_EXPRESSION:
				if (currentnode.children.size() == 0) {
					return checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				ret=checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				if(ret.constant&&ret.type==ExprType.intliteral){
					ret.num=-ret.num;
				}
				return ret;
			case UNARY_EXPRESSION_NOT_PLUS_MINUS:
				if (currentnode.children.size() == 0) {
					return checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				ret=checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				if(ret.constant&&ret.type==ExprType.booleanliteral){
					ret.value=!ret.value;
				}
				return ret;
			case NAME:
				if(currentnode.type.modifiers!=null&&currentnode.type.modifiers.contains(TokenType.FINAL)&&currentnode.type.modifiers.contains(TokenType.STATIC)){
					System.out.println("not implemented yet");
					//currentnode.type.decl
				}
				return new ExprResult();
			case PRIMARY_NO_NEW_ARRAY:
				if(currentnode.children.size()>1){
					return  checkexpr(currentnode.children.get(1), PackageMap, rootenv);
				}
				if(currentnode.children.get(0).token.getType()==TokenType.LITERAL){
					return  checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				return new ExprResult();
			case ARRAY_CREATION_EXPRESSION:
				return new ExprResult();
			case BOOLEAN_LITERAL:
			case LITERAL:
			case PRIMARY:
			case EXPRESSION:
			case SHIFT_EXPRESSION:
			case CONDITIONAL_EXPRESSION:
			case POSTFIX_EXPRESSION:
				if (currentnode.children.size() == 0) {
					return checkexpr(currentnode.children.get(0), PackageMap, rootenv);
				}
				break;
			default:
				System.out.println("error forgot " + currentnode.token.getType());
				System.out.print(currentnode.children.get(1000));
				return null;
		}
		return null;
	}

}
