package com.risingstone.risingstoneui.xml;

import java.util.List;
import java.util.Map;

public class XmlNode {
    String tag;
    Object val;
    Map<String, String> attributes;
    List<XmlNode> children;

    public XmlNode() {
    }

    public XmlNode(String tag, Object val, Map<String, String> attributes, List<XmlNode> children) {
        this.tag = tag;
        this.val = val;
        this.attributes = attributes;
        this.children = children;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Object getVal() {
        return val;
    }

    public void setVal(Object val) {
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
                "tag='" + tag + '\'' +
                ", val='" + val + '\'' +
                ", attributes=" + attributes +
                ", children=" + children +
                '}';
    }
}
