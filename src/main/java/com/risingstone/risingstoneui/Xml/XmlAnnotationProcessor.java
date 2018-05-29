package com.risingstone.risingstoneui.Xml;

import java.lang.reflect.Field;

public class XmlAnnotationProcessor {

    /**
     * Returns the field name of the annotated field.
     * Uses {@link XmlAnnotation} to get the supplied field name, or defaults to the field name
     * @param field
     * @return
     */
    public static String mapAnnotationToXmlKey(Field field){
        System.out.println("mapAnnotationToXmlKey: " + field.getName() +  " gets mapped to: " + field.getAnnotation(XmlAnnotation.class).key());
        String name = field.getAnnotation(XmlAnnotation.class).key();
        if (name != null && !name.equals("")) {
            return name;
        }
        return field.getName();
    }

    /**
     * Checks to see if the field type and the xml type match up, this is by attempting to cast and checking for cast errors.
     * This should be used to see if we need to delegate this value to the Component's {@link XmlMapperComponent}
     * @param value
     * @param field
     * @return
     */
    public static boolean checkFieldTypeMatchesValueType(Object value, Field field){
        Class declaredClass = field.getDeclaringClass();

        return declaredClass.isAssignableFrom(value.getClass());
    }


}
