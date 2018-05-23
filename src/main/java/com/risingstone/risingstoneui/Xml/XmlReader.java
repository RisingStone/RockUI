package com.risingstone.risingstoneui.Xml;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;

public class XmlReader {
    Stack<XMLNode> elementStack;
    XMLNode<String> currentNode;
    boolean isEndElement = false;

    public interface Callback<E> {
        void onParseComplete(XMLNode<E> result);//maybe use generics?
    }

    class SaxElementHandler extends DefaultHandler {
        Callback parseCallback;
        public SaxElementHandler(Callback parseCallback) {
            this.parseCallback = parseCallback;
        }

        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

            XMLNode<String> newNode = new XMLNode<>(qName, "", new HashMap<>(), new ArrayList<>());

            if(attributes.getLength() > 0){
                Map<String, String> attributeMap = new HashMap<>();
                for(int i = 0; i < attributes.getLength(); i++){
                    attributeMap.put(attributes.getQName(i), attributes.getValue(i));
                }
                newNode.setAttributes(attributeMap);
            }
            elementStack.push(newNode);
            isEndElement = false;
        }

        public void endElement(String uri, String localName, String qName) throws SAXException {
            currentNode = elementStack.pop();
            if(!elementStack.isEmpty()) {
                XMLNode<String> lastNode = elementStack.peek();
                lastNode.getChildren().add(currentNode);
            }
            isEndElement = true;
        }

        public void characters(char ch[], int start, int length) throws SAXException {
            String val = "" + new String(ch, start, length);
            if(!isEndElement){
                elementStack.peek().setVal(val);
            }
        }

        public void startDocument() throws SAXException {
            System.out.println("document started");
        }

        public void endDocument() throws SAXException {
            System.out.println("document ended");
            parseCallback.onParseComplete(currentNode);
        }
    }

    public void readXMLFile(File file, Callback<String> parseCallback){
        try {
            SaxElementHandler handler = new SaxElementHandler(parseCallback);
            elementStack = new Stack<XMLNode>();

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            InputStream inputStream= new FileInputStream(file);
            Reader reader = new InputStreamReader(inputStream,"UTF-8");

            InputSource is = new InputSource(reader);
            is.setEncoding("UTF-8");

            saxParser.parse(is, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

