package com.risingstone.risingstoneui.xml;

import java.util.List;
import java.util.Map;

public class XmlNode {
    String name;
    Object val;
    Map<String, Object> attributes;
    List<XmlNode> children;

    public XmlNode() {
    }

    public XmlNode(String name, Object val, Map<String, Object> attributes, List<XmlNode> children) {
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

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
        this.val = val;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
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
