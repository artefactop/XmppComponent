package org.xmpp.utils;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: pepe
 * Date: 10/07/12
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
public class WhiteList {

    private final Map<String, ArrayList<String>> whiteList;

    public WhiteList(){
        this(new ConcurrentHashMap<String, ArrayList<String>>());
    }

    public WhiteList(Map<String, ArrayList<String>> whiteList){
        this.whiteList = whiteList;
    }

    public void addElement(String namespace, String jid){
        ArrayList<String> al = whiteList.get(namespace);
        if(al != null){
            al.add(jid);
        }else{
            al = new ArrayList<String>();
            al.add(jid);
            whiteList.put(namespace, al);
        }
    }

    public void addList(String namespace, ArrayList<String> newAl){
        ArrayList<String> al = whiteList.get(namespace);
        if(al != null){
            al.addAll(newAl);
        }else{
            whiteList.put(namespace, newAl);
        }
    }

    public boolean check(String ns, String fromDomain) {
        ArrayList<String> al = whiteList.get(ns);
        return al != null && al.contains(fromDomain);
    }
}
