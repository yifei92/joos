package joos.typelinking;

import joos.commons.ParseTreeNode;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import joos.environment.Environment;
import joos.exceptions.TypeLinkingException;


import java.util.Map;

import static joos.environment.EnvironmentBuilder.getClassOrInterfaceName;
import static joos.environment.EnvironmentUtils.findEvironment;

/**
 * Created by yifei on 24/02/17.
 */
public class TypeLinking {
    public void check(ParseTreeNode parseTree, Environment environment, Map<String, Environment> PackageMap,String currentpackage)throws TypeLinkingException{
        check(parseTree,parseTree,environment,PackageMap,currentpackage);
    }
    public void check(ParseTreeNode root,ParseTreeNode current, Environment environment, Map<String, Environment> PackageMap,String currentpackage) throws TypeLinkingException {
        if (current == null) return;
        switch (current.token.getType()) {
            case INTERFACE_DECLARATION:
            case CLASS_DECLARATION:
            //case TYPE_DECLARATION:
                String name=getClassOrInterfaceName(current);
                if(!currentpackage.equals(name)&&currentpackage.startsWith(name)){
                    //throw  new TypeLinkingException("type name same prefix as package");
                }

                for(String key:environment.mSingleImports){
                    if(key.equals(currentpackage)){
                        break;
                    }
                    if(key.substring(key.lastIndexOf(".")+1,key.length()).equals(name)){
                        throw new TypeLinkingException("type same name as an import");
                    }
                    if(key.startsWith(name)){
                        throw new TypeLinkingException("type same name as package name prifix");
                    }
                    if(!PackageMap.containsKey(key)){
                        throw  new TypeLinkingException("unable to find file for reference "+key);
                    }
                }

                for(String importondemand:environment.mOnDemandeImports){
                    boolean find=false;
                    for(String packagename:PackageMap.keySet()){
                        if(packagename.startsWith(importondemand)&&packagename.length()>importondemand.length()&&packagename.charAt(importondemand.length())=='.'){
                            find=true;
                        }
                    }
                    if(!find){
                        throw  new TypeLinkingException("unable to find file for reference on demand "+importondemand);
                    }

                    if(importondemand.startsWith(name)){
                        throw new TypeLinkingException("type same name as package name prifix");
                    }
                }
                break;
            case CLASS_OR_INTERFACE_TYPE:
            //case CLASS_TYPE:
                if(current.children.get(0).children.size()==1){
                    name=((TerminalToken)current.children.get(0).children.get(0).token).getRawValue();
                    if(name.equals(environment.mName)){
                        break;
                    }
                    Boolean find=false;
                    for(String key:environment.mSingleImports){
                        if(key.substring(key.lastIndexOf(".")+1,key.length()).equals(name)){
                            if(find){
                                throw new TypeLinkingException("type already import by another packege");
                            }
                            else{
                                find=true;
                            }
                        }
                    }
                    if(find){
                        break;
                    }
                    String packgequalifedName=null;
                    if(!environment.PackageName.equals("")){
                        packgequalifedName=environment.PackageName+"."+name;
                    }
                    else{
                        packgequalifedName=name;
                    }
                    if(PackageMap.containsKey(packgequalifedName)){
                        break;
                    }
                    for(String key:environment.mOnDemandeImports){
                        if(PackageMap.containsKey(key+"."+name)){
                            if(find){
                                throw new TypeLinkingException("type already import by another packege");
                            }
                            else{
                                find=true;
                            }
                        }
                    }
                    if(!find){
                        throw new TypeLinkingException("unable to find reference to type "+name);
                    }
                    break;
                }

                else{
                    name="";
                    for(ParseTreeNode child:current.children.get(0).children){
                        name+=((TerminalToken)child.token).getRawValue();
                    }
                    if(!PackageMap.containsKey(name)){
                        throw new TypeLinkingException("can't find decration for"+name);
                    }
                }
                break;
            /*
            case INTERFACE_TYPE:
                if(current.children.get(0).children.get(0).children.size()==1){
                    name=((TerminalToken)current.children.get(0).children.get(0).children.get(0).token).getRawValue();
                    if(name.equals(environment.mName)){
                        break;
                    }
                    Boolean find=false;
                    for(String key:environment.mSingleImports){
                        if(key.substring(key.lastIndexOf("."),key.length()).equals(name)){
                            if(find){
                                throw new TypeLinkingException("type already import by another packege");
                            }
                            else{
                                find=true;
                            }
                        }
                    }
                    if(find){
                        break;
                    }
                    String packgequalifedName=null;
                    if(!environment.PackageName.equals("")){
                        packgequalifedName=environment.PackageName+"."+name;
                    }
                    else{
                        packgequalifedName=name;
                    }
                    if(PackageMap.containsKey(packgequalifedName)){
                        break;
                    }
                    for(String key:environment.mOnDemandeImports){
                        if(PackageMap.containsKey(key+"."+name)){
                            if(find){
                                throw new TypeLinkingException("type already import by another packege");
                            }
                            else{
                                find=true;
                            }
                        }
                    }
                    if(!find){
                        throw new TypeLinkingException("unable to find reference to type "+name);
                    }
                    break;
                }
                else{
                    name="";
                    for(ParseTreeNode child:current.children.get(0).children.get(0).children){
                        name+=((TerminalToken)child.token).getRawValue();
                    }
                    if(!PackageMap.containsKey(name)){
                        throw new TypeLinkingException("can't find decration for"+name);
                    }
                }
                break;
                */
            default:
        }
        if(current.children!=null) {
            for (ParseTreeNode child : current.children) {
                check(root, child, environment, PackageMap,currentpackage);
            }
        }
        return;
    }
}
