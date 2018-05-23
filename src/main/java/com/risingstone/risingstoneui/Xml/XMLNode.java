package com.risingstone.risingstoneui.Xml;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class XMLNode {
    String name;
    String val;
    Map<String, String> attributes;
    List<XMLNode> children;

    public XMLNode() {
    }

    public XMLNode(String name, String val, Map<String, String> attributes, List<XMLNode> children) {
        this.name = name;
        this.val = val;
        this.attributes = attributes;
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    public List<XMLNode> getChildren() {
        return children;
    }

    public void setChildren(List<XMLNode> children) {
        this.children = children;
    }

    public static XMLNode getChildByNameShallow(String name, XMLNode node){
        for(XMLNode child : node.getChildren()){
            if(name.equalsIgnoreCase(child.name)){
                return child;
            }
        }
        return null;
    }

    public static XMLNode getChildByNameDeep(String name, XMLNode node){
        LinkedList<XMLNode> queue = new LinkedList<>();
        queue.push(node);
        while(!queue.isEmpty()){
            queue.addAll(node.getChildren());
            if(node.getName().equalsIgnoreCase(name)){
                return node;
            }
            node = queue.pollLast();
        }
        return null;
    }

    public static XMLNode getChildByAttributeShallow(String key, String val, XMLNode node){
        for(XMLNode child : node.getChildren()){
            if(child.getAttributes().get(key).equalsIgnoreCase(val)){
                return child;
            }
        }
        return null;
    }

    public static XMLNode getChildByAttributeDeep(String key, String val, XMLNode node){
        LinkedList<XMLNode> queue = new LinkedList<>();
        queue.push(node);
        while(!queue.isEmpty()){
            queue.addAll(node.getChildren());
            if(node.getAttributes().get(key).equalsIgnoreCase(val)){
                return node;
            }
            node = queue.pollLast();
        }
        return null;
    }

    @Override
    public String toString() {
        return "XMLNode{" +
                "name='" + name + '\'' +
                ", val='" + val + '\'' +
                ", attributes=" + attributes +
                ", children=" + children +
                '}';
    }
}
