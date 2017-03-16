package joos.staticanalysis;

import joos.commons.ParseTreeNode;
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

	protected  class ExprResult{
		boolean constant=false;
		boolean value;
		int num;
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
				currentnode.children.get(4).in=currentnode.in;
				check(currentnode.children.get(4), PackageMap, rootenv);
				currentnode.out=currentnode.in;
				return;

			case RETURN_STATEMENT:
				currentnode.out=false;
				return;
			case STATEMENT:
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
				if(currentnode.children.get(0).token.getType()==TokenType.STATEMENT){
					currentnode.children.get(0).in = currentnode.in;
					check(currentnode.children.get(0), PackageMap, rootenv);
					currentnode.out = currentnode.children.get(0).out;
				}
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

	ExprResult checkexpr(ParseTreeNode currentnode, Map<String, Environment> PackageMap, Environment rootenv){
			return null;
	}

}
