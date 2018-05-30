package com.risingstone.risingstoneui.xml;

import java.lang.reflect.Field;

public abstract class XmlMapperComponent {
    public XmlMapperComponent(){

    }

    /**
     * XmlMapperComponents may need the value of the xml Tag
     * Examples include a text component
     */
    Object value;

    /**
     * Convert String values to the fields required value.
     * @return
     */
    public abstract Object StringToValue(Field field, String attributeValue);
}
