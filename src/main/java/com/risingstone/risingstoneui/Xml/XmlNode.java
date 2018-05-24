package com.risingstone.risingstoneui.Xml;

import java.util.List;
import java.util.Map;

public class XmlNode {
    String name;
    String val;
    Map<String, String> attributes;
    List<XmlNode> children;

    public XmlNode() {
    }

    public XmlNode(String name, String val, Map<String, String> attributes, List<XmlNode> children) {
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

    public List<XmlNode> getChildren() {
        return children;
    }

    public void setChildren(List<XmlNode> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "XmlNode{" +
                "name='" + name + '\'' +
                ", val='" + val + '\'' +
                ", attributes=" + attributes +
                ", children=" + children +
                '}';
    }
}
