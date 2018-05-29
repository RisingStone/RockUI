package com.risingstone.risingstoneui.xml;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class XmlToPojo {

    public static Object convertXmlToPOJO(File xmlFile) throws ExecutionException, InterruptedException, IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        //Get the tree of the XML
        XmlNode node = new XmlReader().readXMLFile(xmlFile).get();

        Object obj = buildObjectFromNode(node, (String) node.attributes.get("NameSpace"));

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
    private static Object buildObjectFromNode(XmlNode node, @Nullable String nameSpace) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        //Map fields to keys in xml
        Map<String, Field> fieldToKeyMap = new HashMap<>();

        //Create instance of class of object name.
        Class<?> clazz = Class.forName(getFullClassName(node.name, nameSpace));
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
     * Check className, namespace and java path to find the class. Throw exception if not found.
     * @param className
     * @param namespace
     * @return
     */
    private static String getFullClassName(String className, @Nullable String namespace){
        System.out.println("Find class: " + className);
        if(isClass(className)){
            return className;
        }

        System.out.println("Find class: " + namespace + "." + className);
        if(isClass(namespace + "." + className)){
            return namespace + "." + className;
        }

        for(XmlClassPaths searchPackages : XmlClassPaths.values()){
            System.out.println("Find class: " + searchPackages.getClassPath() + "." + className);
            if(isClass(searchPackages.getClassPath() + "." + className)){
                return searchPackages.getClassPath() + "." + className;
            }
        }

        return null;
    }

    private static boolean isClass(String className) {
        try  {
            Class.forName(className);
            return true;
        }  catch (ClassNotFoundException e) {
            return false;
        }
    }
}
