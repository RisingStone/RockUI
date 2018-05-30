package com.risingstone.risingstoneui.xml;

import java.lang.reflect.Field;

public abstract class XmlMapperComponent<E> {
    public XmlMapperComponent(){

    }

    /**
     * XmlMapperComponents may need the value of the xml Tag
     * Examples include a text component
     */
    E value;

    /**
     * Convert String values to the fields required value.
     * @return
     */
    public abstract Object StringToValue(Field field, String attributeValue);

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
}
