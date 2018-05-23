package com.risingstone.risingstoneui.Xml;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class XmlUtils {

    public static void printXmlNode(XMLNode node){
        LinkedList<XMLNode> queue = new LinkedList<>();
        queue.push(node);
        while(!queue.isEmpty()){
            queue.addAll(queue.peek().getChildren());
            System.out.println(queue.poll());
        }
    }

    public static List convertNodeToList(XMLNode node){
        List list = new ArrayList();
        LinkedList<XMLNode> queue = new LinkedList<>();
        queue.push(node);
        while(!queue.isEmpty()){
            queue.addAll(queue.peek().getChildren());
            list.add(queue.poll());
        }
        return list;
    }
}
