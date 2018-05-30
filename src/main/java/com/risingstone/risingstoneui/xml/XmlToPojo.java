package com.risingstone.risingstoneui.xml;

import com.sun.istack.internal.Nullable;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class XmlToPojo {

    public static Object convertXmlToPOJO(File xmlFile, Class clazz) throws ExecutionException, InterruptedException, IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
        //Get the tree of the XML
        XmlNode node = new XmlReader().readXMLFile(xmlFile).get();

        Object obj = buildObjectFromNode(node, clazz);

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
    private static Object buildObjectFromNode(XmlNode node, Class clazz) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        //Map fields to keys in xml for attributes
        Map<String, Field> fieldToKeyAttributeMap = new HashMap<>();

        //Maps the children nodes/tags to the fields in the xml
        Map<Field, String> tagFieldToTypeMap =  new HashMap<>();

        //Maps path name to XML Tag
        Map<String, String> nameToPathMap = new HashMap<>();



        //Create instance of class of object tag.
        Object obj = clazz.newInstance();
        //Grab all the fields in the object, not using declared because I don't wanna deal with private and security exceptions.
        Field[] fields = clazz.getFields();

        //SETUP FOR BUILDING OBJECTS BY LOOKING AT FIELDS IN OBJECT AND MAPPING TO NODES
        //Check if field is mapped or we use the field tag and put in map.
        for(Field field: fields){
            //First check if transient and attriubute
            if(!Modifier.isTransient(field.getModifiers()) && field.getAnnotation(XmlAnnotation.class).type().equals(XmlAnnotation.Type.Attribute)) {
                System.out.println("Attriubte Field: " + field.getName());
                //Use the mapp util to return the mapped field.  Defaults to getTag if not mapped.
                fieldToKeyAttributeMap.put(XmlAnnotationProcessor.mapAnnotationToXmlKey(field), field);
                System.out.println("Mapped Field: " + XmlAnnotationProcessor.mapAnnotationToXmlKey(field));
            }

            if(!Modifier.isTransient(field.getModifiers()) && field.getAnnotation(XmlAnnotation.class).type().equals(XmlAnnotation.Type.Tag)) {
                System.out.println("Tagged filed: " + field.getName());
                if(field.getType().isAssignableFrom(List.class) || field.getType().isArray()){
                    ParameterizedType listType = (ParameterizedType) field.getGenericType();
                    Class<?> stringListClass = (Class<?>) listType.getActualTypeArguments()[0];
                    tagFieldToTypeMap.put(field, stringListClass.getSimpleName());
                    nameToPathMap.put(stringListClass.getSimpleName(), stringListClass.getName());
                    System.out.println("List Tagged type map: " + stringListClass.getSimpleName() + " : " + stringListClass.getName());
                }else{
                    tagFieldToTypeMap.put(field, field.getType().getSimpleName());
                    nameToPathMap.put(field.getType().getSimpleName(), field.getType().getName());
                    System.out.println("Tagged type: " + field.getType().getSimpleName());
                }
            }

            if(!Modifier.isTransient(field.getModifiers()) && field.getAnnotation(XmlAnnotation.class).type().equals(XmlAnnotation.Type.Value)) {

            }
        }

        //ATTRIBUTE LOGIC
        //Iterate through the Node attributes to fill out the fields in the objects that correspond to attributes.
        //We check grab the mapped attribute tag and then set the field in the object with the value from the attribute.
        for(String attribute: node.getAttributes().keySet()){
            System.out.println("attribute: " + attribute + " value: " + node.getAttributes().get(attribute));
            if(fieldToKeyAttributeMap.containsKey(attribute)){
                setAttributeToField(fieldToKeyAttributeMap, attribute, node, obj);
            }
        }

        System.out.println("Object after Attributes: " + obj.toString());

        //TAG LOGIC
        //Check each child and see what the field is and what the child class is.
        //We need a special case for if there is a list, where we check if the obj has a list,
        //if no list, then we create and add, if list, then just add.
        for(Field field : tagFieldToTypeMap.keySet()){

            System.out.println("Looking for Tags by name of: " + tagFieldToTypeMap.get(field));

            List<XmlNode> nodes = XmlUtils.getListOfChildrenByNameShallow(tagFieldToTypeMap.get(field), node);

            if(nodes.size() > 1){
                //We have several Nodes to map the this fieldTag
                for(XmlNode tagNode : nodes){
                    //Check List
                    if(field.getType().isAssignableFrom(List.class)){
                        System.out.println("Field is a list: " + field.getName());
                        addObjectToList(field, tagNode, obj);
                        continue;
                    }

                    //Check Array
                    if(field.getClass().isArray()){
                        System.out.println("Field is an array : " + field.getName());
                        continue;
                    }

                    //If not List or Array then it's an object
                    System.out.println("Field is a field : " + field.getName());
                    field.set(obj, buildObjectFromNode(tagNode, field.getType()));
                }
            }else if(!nodes.isEmpty()){
                //We either have 0 or 1 nodes for this tag.  Lets try to build it and set it as a field,
                //first we must check what the field is, array, list or not.
                //Check List
                if(field.getType().isAssignableFrom(List.class)){
                    System.out.println("Field is a list: " + field.getName());
                    addObjectToList(field, nodes.get(0), obj);
                    continue;
                }

                //Check Array
                if(field.getClass().isArray()){
                    continue;
                }

                //If not List or Array then it's an object
                field.set(obj, buildObjectFromNode(nodes.get(0), field.getType()));
            }
        }

        //TODO: Check all fields that use value annotation. We need to insert the value into the value field of the XmlMapperComponent implementation

        return obj;
    }

    private static void addObjectToList(Field field, XmlNode node, Object obj) throws IllegalAccessException, InstantiationException, ClassNotFoundException {

        ParameterizedType listType = (ParameterizedType) field.getGenericType();
        Class<?> stringListClass = (Class<?>) listType.getActualTypeArguments()[0];
        System.out.println("Class: " + stringListClass.getName());

        if(field.get(obj) == null){
            List newList = new ArrayList();
            field.set(obj, newList);
        }

        List<Object> list = (List<Object>) field.get(obj);
        list.add(buildObjectFromNode(node, stringListClass));
        field.set(obj, list);
    }

    private static void setAttributeToField(Map<String, Field> fieldToKeyMap, String attribute, XmlNode node, Object obj) throws IllegalAccessException {
        Field field = fieldToKeyMap.get(attribute);
        if(field.getType().isAssignableFrom(node.getAttributes().get(attribute).getClass())){
            field.set(obj, node.getAttributes().get(attribute));
        }else{
            Object value = ((XmlMapperComponent)obj).StringToValue(field, node.getAttributes().get(attribute));
            field.set(obj, value);
        }
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
