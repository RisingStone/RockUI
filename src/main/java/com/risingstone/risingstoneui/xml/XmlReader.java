package com.risingstone.risingstoneui.xml;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class XmlReader {
    private FutureTask<XmlNode> future;

    private class XMLReaderCallable implements Callable<XmlNode>{

        /**
         * Using the countdown latch to hold the thread from returning the result
         * until the handler tells us the document is done.
         */
        private CountDownLatch latch;
        private File file;

        private Stack<XmlNode> elementStack;
        private XmlNode currentNode;
        private boolean isEndElement = false;

        public XMLReaderCallable(File file) {
            this.file = file;
            this.latch = new CountDownLatch(0);
        }

        @Override
        public XmlNode call() throws Exception {
            try{
                SaxElementHandler handler = new SaxElementHandler();
                elementStack = new Stack<>();

                SAXParserFactory factory = SAXParserFactory.newInstance();
                SAXParser saxParser = factory.newSAXParser();

                InputStream inputStream = new FileInputStream(file);
                Reader reader = new InputStreamReader(inputStream,"UTF-8");

                InputSource is = new InputSource(reader);
                is.setEncoding("UTF-8");

                saxParser.parse(is, handler);
            } catch (Exception e) {
                e.printStackTrace();
            }
            latch.await();
            return currentNode;
        }

        class SaxElementHandler extends DefaultHandler {

            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                XmlNode newNode = new XmlNode(qName, "", new HashMap<>(), new ArrayList<>());

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
                    XmlNode lastNode = elementStack.peek();
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
                latch.countDown();
            }
        }
    }


    public Future<XmlNode> readXMLFile(File file){
        future = new FutureTask(new XMLReaderCallable(file));
        future.run();
        return future;
    }
}

