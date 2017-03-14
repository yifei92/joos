package joos.typelinking;

import joos.commons.ParseTreeNode;
import joos.commons.TerminalToken;
import joos.commons.TokenType;
import joos.commons.Type;
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
                if(currentpackage.contains(".")) {
                    for (String packagename : PackageMap.keySet()) {
                        if (packagename.startsWith(currentpackage) && !packagename.equals(currentpackage)&&packagename.contains(".")) {
                            System.out.println(packagename + "  " + currentpackage);
                            throw new TypeLinkingException("type same name as package name prefix");
                        }
                    }
                }


                for(String key:environment.mSingleImports){
                    if(key.equals(currentpackage)){
                        break;
                    }
                    if(key.substring(key.lastIndexOf(".")+1,key.length()).equals(name)){
                        throw new TypeLinkingException("type same name as an import");
                    }
                    if(key.startsWith(name)){
                        throw new TypeLinkingException("type same name as package name prefix");
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

                    if(importondemand.startsWith(name)&&!importondemand.equals(currentpackage.substring(0,currentpackage.lastIndexOf('.')))){
                        throw new TypeLinkingException("type same name as package name prefix");
                    }
                }

                break;
            case CLASS_OR_INTERFACE_TYPE:
            //case CLASS_TYPE:
                if(current.children.get(0).children.size()==1){
                    name=((TerminalToken)current.children.get(0).children.get(0).token).getRawValue();
                    String packgequalifedName=null;
                    if(!environment.PackageName.equals("")){
                        packgequalifedName=environment.PackageName+"."+name;
                    }
                    else{
                        packgequalifedName=name;
                    }
                    if(name.equals(environment.mName)){
                        current.type=new Type(packgequalifedName);
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
                        current.type=new Type(packgequalifedName);
                        break;
                    }

                    if(PackageMap.containsKey(packgequalifedName)){
                        current.type=new Type(packgequalifedName);
                        break;
                    }
                    for(String key:environment.mOnDemandeImports){
                        if(PackageMap.containsKey(key+"."+name)){
                            if(find){
                                throw new TypeLinkingException("type already import by another packege");
                            }
                            else{
                                find=true;
                                packgequalifedName=key+"."+name;
                            }
                        }
                    }
                    if(!find){
                        throw new TypeLinkingException("unable to find reference to type "+name);
                    }
                    current.type=new Type(packgequalifedName);
                    break;
                }

                else{
                    name="";
                    for(int i=0;i<current.children.get(0).children.size();i++){
                        String currentpackagename="";
                        if(currentpackage.contains(".")){
                            currentpackagename=currentpackage.substring(0,currentpackage.lastIndexOf(".")+1);
                        }
                        name+=((TerminalToken)current.children.get(0).children.get(i).token).getRawValue();
                        if(i%2==0&&i<current.children.get(0).children.size()-1) {
                            for (String packagename : PackageMap.keySet()) {
                                if (packagename.startsWith(currentpackagename+name) &&(packagename.contains(".")==currentpackagename.contains("."))) {
                                    System.out.println(name+"  "+packagename);
                                    throw new TypeLinkingException("full qualifed name prefix of another package");
                                }
                            }
                            for(String singleimprot : environment.mSingleImports){
                                if(singleimprot.equals(name)){
                                    System.out.println(name+" "+singleimprot);
                                    throw new TypeLinkingException("full qualifed name prefix of another package imported");
                                }
                            }
                        }
                    }
                    if(!PackageMap.containsKey(name)){
                        throw new TypeLinkingException("can't find decration for"+name);
                    }
                }
                current.type=new Type(name);
                break;

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
