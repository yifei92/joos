package joos.typelinking;

import joos.commons.ParseTreeNode;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import joos.environment.Environment;
import joos.exceptions.TypeLinkingException;

import static joos.environment.EnvironmentBuilder.getClassOrInterfaceName;
import static joos.environment.EnvironmentUtils.findEvironment;

/**
 * Created by yifei on 24/02/17.
 */
public class TypeLinking {
    public void check(ParseTreeNode parseTree, Environment environment)throws TypeLinkingException{
        check(parseTree,parseTree,environment);
    }
    public void check(ParseTreeNode root,ParseTreeNode current, Environment environment) throws TypeLinkingException {
        if (current == null) return;
        switch (current.token.getType()) {
            case INTERFACE_DECLARATION:
            case CLASS_DECLARATION:
                if(environment.mImports.containsKey(getClassOrInterfaceName(current))){
                    throw new TypeLinkingException("type same name as packege");
                }
            break;
            case CLASS_TYPE:
                if(current.children.get(0).children.get(0).token.getType()==TokenType.SIMPLE_NAME){
                    String name=((TerminalToken)current.children.get(0).children.get(0).children.get(0).token).getRawValue();
                    Environment local=findEvironment(environment,root,current);
                    if(local.mVariableDeclarations.get(name).token.getType()!=TokenType.CLASS_DECLARATION){
                        throw new TypeLinkingException("cant find refrenced class");
                    }
                }
                break;
            case INTERFACE_TYPE:
                if(current.children.get(0).children.get(0).token.getType()==TokenType.SIMPLE_NAME){
                    String name=((TerminalToken)current.children.get(0).children.get(0).children.get(0).token).getRawValue();
                    Environment local=findEvironment(environment,root,current);
                    if(local.mVariableDeclarations.get(name).token.getType()!=TokenType.CLASS_DECLARATION){
                        throw new TypeLinkingException("cant find refrenced interface");
                    }
                }
                break;
            case FIELD_ACCESS:
                String name=((TerminalToken)current.children.get(2).children.get(0).children.get(0).token).getRawValue();
                Environment local=findEvironment(environment,root,current);
                if(local.mVariableDeclarations.get(name).token.getType()!=TokenType.VARIABLE_DECLARATOR){
                    throw new TypeLinkingException("cant find refrenced interface");
                }
                break;
            case METHOD_INVOCATION:
                name=((TerminalToken)current.children.get(2).children.get(0).children.get(0).token).getRawValue();
                local=findEvironment(environment,root,current);
                if(local.mVariableDeclarations.get(name).token.getType()!=TokenType.METHOD_DECLARATION){
                    throw new TypeLinkingException("cant find refrenced interface");
                }
                break;
        }
        for (ParseTreeNode child : current.children) {
            check(root,child, environment);
        }
        return;
    }
}
