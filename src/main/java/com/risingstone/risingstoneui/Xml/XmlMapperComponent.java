package com.risingstone.risingstoneui.Xml;

import java.lang.reflect.Field;

public abstract class XmlMapperComponent {
    /**
     * XmlMapperComponents may need the value of the Xml Tag
     * Examples include a text component
     */
    Object value;

    /**
     * Convert String values to the fields required value.
     * @return
     */
    public abstract Object StringToValue(Field field, XmlNode node);
}
