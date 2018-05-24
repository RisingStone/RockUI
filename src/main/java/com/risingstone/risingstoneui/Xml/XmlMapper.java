package com.risingstone.risingstoneui.Xml;

import java.util.Map;

public abstract class XmlMapper {
    Map<String, Object> keywordMap;

    /**
     * Convert String Keywords such as 'Center' 'Bold' 'Green' into int values
     * @return
     */
    public abstract int StringToValue();
}
