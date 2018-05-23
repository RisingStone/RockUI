package com.risingstone.risingstoneui.Xml;

import java.util.List;
import java.util.Map;

public class XMLNode<E> {
    String name;
    E val;
    Map<String, String> attributes;
    List<XMLNode> children;

    public XMLNode() {
    }

    public XMLNode(String name, E val, Map<String, String> attributes, List<XMLNode> children) {
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

    public E getVal() {
        return val;
    }

    public void setVal(E val) {
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
