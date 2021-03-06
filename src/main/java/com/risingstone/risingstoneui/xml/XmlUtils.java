package com.risingstone.risingstoneui.xml;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XmlUtils {

    public static void printXmlNode(XmlNode node){
        LinkedList<XmlNode> queue = new LinkedList<>();
        queue.push(node);
        while(!queue.isEmpty()){
            queue.addAll(queue.peek().getChildren());
            System.out.println(queue.poll());
        }
    }

    public static List convertNodeToList(XmlNode node){
        List list = new ArrayList();
        LinkedList<XmlNode> queue = new LinkedList<>();
        queue.push(node);
        while(!queue.isEmpty()){
            queue.addAll(queue.peek().getChildren());
            list.add(queue.poll());
        }
        return list;
    }

    public static XmlNode getChildByNameShallow(String name, XmlNode node){
        for(XmlNode child : node.getChildren()){
            if(name.equalsIgnoreCase(child.tag)){
                return child;
            }
        }
        return null;
    }

    public static XmlNode getChildByNameDeep(String name, XmlNode node){
        LinkedList<XmlNode> queue = new LinkedList<>();
        queue.push(node);
        while(!queue.isEmpty()){
            queue.addAll(node.getChildren());
            if(node.getTag().equalsIgnoreCase(name)){
                return node;
            }
            node = queue.pollLast();
        }
        return null;
    }

    public static XmlNode getChildByAttributeShallow(String attributeName, XmlNode node){
        for(XmlNode child : node.getChildren()){
            if(child.getAttributes().containsKey(attributeName)){
                return child;
            }
        }
        return null;
    }

    public static XmlNode getChildByAttributeDeep(String attributeName, XmlNode node){
        LinkedList<XmlNode> queue = new LinkedList<>();
        queue.push(node);
        while(!queue.isEmpty()){
            queue.addAll(node.getChildren());
            if(queue.peekFirst().getAttributes().containsKey(attributeName)){
                return queue.peekFirst();
            }
            node = queue.pollFirst();
        }
        return null;
    }

    public static List<XmlNode> getListOfChildrenByNameShallow(String name, XmlNode node){
        List<XmlNode> list = new ArrayList<>();
        for(XmlNode child : node.getChildren()){
            if(name.equalsIgnoreCase(child.tag)){
                list.add(child);
            }
        }
        return list;
    }

    public static List<XmlNode> getListOfChildrenByNameDeep(String name, XmlNode node){
        List<XmlNode> list = new ArrayList<>();
        LinkedList<XmlNode> queue = new LinkedList<>();
        queue.push(node);
        while(!queue.isEmpty()){
            queue.addAll(node.getChildren());
            if(node.getTag().equalsIgnoreCase(name)){
                list.add(node);
            }
            node = queue.pollLast();
        }
        return list;
    }
}
