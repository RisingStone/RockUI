package com.risingstone.risingstoneui.Xml;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface XmlAnnotation {
    enum Type {
        Tag,
        Attribute,
        Value,
    }

    String key() default "";
    Type type() default Type.Attribute;
    boolean required() default true;
}
