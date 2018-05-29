package com.risingstone.risingstoneui.Xml;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class XmlToPojo {

    public static Object convertXmlToPOJO(File xmlFile, Class<? extends XmlMapperComponent> clazz) throws ExecutionException, InterruptedException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        //Get the tree of the XML
        XmlNode node = new XmlReader().readXMLFile(xmlFile).get();

        //Map fields to keys in xml
        Map<String, Field> fieldToKeyMap = new HashMap<>();

        //Make an instance of the class
        Object obj = clazz.newInstance();

        //Grab all the fields in the object
        Field[] fields = clazz.getFields();

        //Check if field is mapped or we use the field name and put in map.
        for(Field field: fields){
            //First check if transient
            if(!Modifier.isTransient(field.getModifiers())) {
                System.out.println("Field: " + field.getName());
                //Use the mapp util to return the mapped field.  Defaults to getName if not mapped.
                fieldToKeyMap.put(XmlAnnotationProcessor.mapAnnotationToXmlKey(field), field);
                System.out.println("Mapped Field: " + XmlAnnotationProcessor.mapAnnotationToXmlKey(field));
            }
        }

        //Iterate through the Node attributes to fill out the fields in the objects that correspond to attributes.
        //We check grab the mapped attribute name and then set the field in the object with the value from the attribute.
        for(String attribute: node.getAttributes().keySet()){
            System.out.println("attribute: " + attribute);
            if(fieldToKeyMap.containsKey(attribute)){
                Field field = fieldToKeyMap.get(attribute);
                field.set(obj, node.getAttributes().get(attribute));
            }
        }

        return obj;
    }


    /**
     * Instantiates and populates a node into a POJO.
     * Every XML tag should be an Object.
     * Fields of the object should be defined in the XML attributes for the tag.
     *
     * @param node
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object buildObjectFromNode(XmlNode node) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //Map fields to keys in xml
        Map<String, Field> fieldToKeyMap = new HashMap<>();

        //Create instance of class of object name.
        Class<?> clazz = Class.forName(node.getName());
        Object obj = clazz.newInstance();

        //Grab all the fields in the object
        Field[] fields = clazz.getFields();

        //Check if field is mapped or we use the field name and put in map.
        for(Field field: fields){
            //First check if transient
            if(!Modifier.isTransient(field.getModifiers())) {
                //Check if we have a mapped name or just the field name.
                if (XmlAnnotationProcessor.mapAnnotationToXmlKey(field).equals(field.getName())) {
                    fieldToKeyMap.put(XmlAnnotationProcessor.mapAnnotationToXmlKey(field), field);
                } else {
                    fieldToKeyMap.put(field.getName(), field);
                }
            }
        }

        //Iterate through the Node attributes to fill out the fields in the objects that correspond to attributes.
        for(String attribute: node.getAttributes().keySet()){
            if(fieldToKeyMap.containsKey(attribute)){
                Field field = fieldToKeyMap.get(attribute);
                field.set(obj, node.getAttributes().get(attribute));
            }
        }

        return obj;
    }

    private Object buildObjectFromNodeWithConstructor(XmlNode node) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //Map fields to keys in xml
        Map<String, Field> fieldToKeyMap = new HashMap<>();

        //Create instance of class of object name.
        Class<?> clazz = Class.forName(node.getName());

        //Grab all the fields in the object
        Field[] fields = clazz.getFields();

        //List of fields for constructor
        Class[] constructorClasses = new Class[fields.length];

        //Check if field is mapped or we use the field name and put in map.
        int i = 0;
        for(Field field: fields){
            //First check if transient
            if(!Modifier.isTransient(field.getModifiers())) {
                //Add to constructor list
                constructorClasses[i] = field.getDeclaringClass();
                i++;

                //Check if we have a mapped name or just the field name.
                if (XmlAnnotationProcessor.mapAnnotationToXmlKey(field).equals(field.getName())) {
                    fieldToKeyMap.put(XmlAnnotationProcessor.mapAnnotationToXmlKey(field), field);
                } else {
                    fieldToKeyMap.put(field.getName(), field);
                }
            }
        }

        Constructor<?> constructor = clazz.getConstructor(constructorClasses);

        Object instance = constructor.newInstance("stringparam", 42);
        return instance;
    }
}
