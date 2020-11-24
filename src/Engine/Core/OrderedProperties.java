/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Core;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;

/**
 *
 * @author child
 */
public class OrderedProperties extends Properties{ //this class extends java.util.Properties to be a ble to save the properties in the same order they were originally in
    private final LinkedHashSet<Object> keyOrder = new LinkedHashSet<>();

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(keyOrder);
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        keyOrder.add(key);
        return super.put(key, value);
    }
}
