/*
 * This file is part of FastStorage library.
 * 
 * Copyright (C) 2015 Freax Software
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package tk.freaxsoftware.extras.faststorage.reading;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import tk.freaxsoftware.extras.faststorage.exception.EntityStateException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition.ECSVFieldDate;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition.ECSVFieldKey;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition.ECSVFieldReference;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFields;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;
import tk.freaxsoftware.extras.faststorage.generic.EntityListReference;
import tk.freaxsoftware.extras.faststorage.generic.EntityReference;
import tk.freaxsoftware.extras.faststorage.storage.EntityHandler;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;

/**
 * Default implementation of entity reader.
 * @author Stanislav Nepochatov
 * @param <K>
 */
public class EntityReaderImpl<K> implements EntityReader<K> {
    
    /**
     * ECSV entity fields defintion.
     */
    private final ECSVDefinition definition;
    
    /**
     * List of field defintions.
     */
    private ListIterator<ECSVDefinition.ECSVFieldPrimitive> fieldIterator;
    
    /**
     * Current field in stack.
     */
    private ECSVDefinition.ECSVFieldPrimitive currentField;
    
    /**
     * List of parsed strings.
     */
    private List<String> parsedStrings;
    
    /**
     * Iterator through parsed strings.
     */
    private ListIterator<String> parsedIter;
    
    /**
     * Current string for reading.
     */
    private String currentParsed;
    
    private Boolean readyToRead = false;
    
    public EntityReaderImpl(ECSVDefinition defintion, String rawEntityString) {
        this.definition = defintion;
        fieldIterator = this.definition.getFields().listIterator();
        currentField = fieldIterator.next();
        parseInit(rawEntityString);
        readyToRead = true;
    }
    
    /**
     * Parse internal entity structure.
     * @param rawString raw entity string form;
     */
    private void parseInit(String rawString) {
        ECSVParser parser = new ECSVParser(definition);
        parsedStrings = parser.parseEntity(rawString);
        parsedIter = parsedStrings.listIterator();
        currentParsed = parsedIter.next();
    }
    
    /**
     * Checks state of reader and field reading order. Throws unchecked 
     * {@code EntityStateException} if order of writing messed up or reader not initiated;
     * @param field 
     */
    private void checkFieldAndState(ECSVFields field) {
        if (!readyToRead) {
            throw new EntityStateException("Current reader isn't ready to read information.");
        } else if (field != currentField.getField()) {
            throw new EntityStateException("Expected " + currentField.getField().name() + " but received " + field.name());
        }
    }
    
    private void moveForward() {
        currentField = this.fieldIterator.next();
        currentParsed = this.parsedIter.next();
    }

    @Override
    public String readType() {
        checkFieldAndState(ECSVFields.TYPE);
        String type = currentParsed;
        moveForward();
        return type;
    }

    @Override
    public K readKey() {
        checkFieldAndState(ECSVFields.KEY);
        String keyStr = currentParsed;
        ECSVFieldKey keyField = (ECSVFieldKey) currentField;
        moveForward();
        if (keyField.getKeyClass() == String.class) {
            return (K) keyStr;
        } else if (keyField.getKeyClass() == Integer.class) {
            return (K) new Integer(Integer.parseInt(keyStr));
        } else if (keyField.getKeyClass() == Float.class) {
            return (K) new Float(Float.parseFloat(keyStr));
        } else {
            try {
                Constructor<K> keyConstructor = keyField.getKeyClass().getConstructor(String.class);
                K keyInstance = keyConstructor.newInstance(keyStr);
                return keyInstance;
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                //Do nothing;
            }
            return null;
        }
    }

    @Override
    public String readWord() {
        checkFieldAndState(ECSVFields.PR_WORD);
        String word = currentParsed;
        moveForward();
        return word;
    }

    @Override
    public String readString() {
        checkFieldAndState(ECSVFields.PR_STRING);
        String string = currentParsed;
        moveForward();
        return string;
    }

    @Override
    public Boolean readBoolean() {
        checkFieldAndState(ECSVFields.PR_BOOLEAN);
        String booleanValue = currentParsed;
        moveForward();
        return Boolean.parseBoolean(booleanValue);
    }

    @Override
    public Integer readInteger() {
        checkFieldAndState(ECSVFields.PR_INT);
        String intValue = currentParsed;
        moveForward();
        return Integer.parseInt(intValue);
    }

    @Override
    public Float readFloat() {
        checkFieldAndState(ECSVFields.PR_FLOAT);
        String floatValue = currentParsed;
        moveForward();
        return Float.parseFloat(floatValue);
    }

    @Override
    public Date readDate() {
        checkFieldAndState(ECSVFields.SC_DATE);
        ECSVFieldDate dateField = (ECSVFieldDate) currentField;
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateField.getDateFormat());
        String dateValue = currentParsed;
        moveForward();
        try {
            return dateFormat.parse(dateValue);
        } catch (ParseException pex) {
            return null;
        }
    }

    @Override
    public <R extends ECSVAble<K>, K> EntityReference<R, K> readReference(Class<R> entityRefClass) {
        checkFieldAndState(ECSVFields.SC_REF);
        ECSVFieldReference referenceField = (ECSVFieldReference) currentField;
        String referenceValue = currentParsed;
        moveForward();
        EntityHandler<R, K> handler = Handlers.getHandlerByClass(entityRefClass);
        K key = null;
        if (referenceField.getReferenceKeyClass() == String.class) {
            key = (K) referenceValue;
        } else if (referenceField.getReferenceKeyClass() == Integer.class) {
            key = (K) new Integer(Integer.parseInt(referenceValue));
        } else if (referenceField.getReferenceKeyClass() == Float.class) {
            key = (K) new Float(Float.parseFloat(referenceValue));
        } else {
            try {
                Constructor<K> keyConstructor = referenceField.getReferenceKeyClass().getConstructor(String.class);
                key = keyConstructor.newInstance(referenceValue);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                //Do nothing;
            }
        }
        if (key != null) {
            return handler.getReference(key);
        } else {
            return null;
        }
    }

    @Override
    public <R extends ECSVAble<K>, K> EntityListReference<R, K> readReferenceArray(Class<R> entityRefClass) {
        checkFieldAndState(ECSVFields.SC_REF_ARRAY);
        ECSVFieldReference referenceField = (ECSVFieldReference) currentField;
        String referenceValue = currentParsed;
        moveForward();
        EntityHandler<R, K> handler = Handlers.getHandlerByClass(entityRefClass);
        List<K> keys = new ArrayList<>();
        String[] keysStr = referenceValue.split(ECSVFormat.GENERIC_SEPARATOR);
        for (String keyStr: keysStr) {
            K key = null;
            if (referenceField.getReferenceKeyClass() == String.class) {
                key = (K) keyStr;
            } else if (referenceField.getReferenceKeyClass() == Integer.class) {
                key = (K) new Integer(Integer.parseInt(keyStr));
            } else if (referenceField.getReferenceKeyClass() == Float.class) {
                key = (K) new Float(Float.parseFloat(keyStr));
            } else {
                try {
                    Constructor<K> keyConstructor = referenceField.getReferenceKeyClass().getConstructor(String.class);
                    key = keyConstructor.newInstance(keyStr);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                    //Do nothing;
                }
            }
            if (key != null) {
                keys.add(key);
            }
        }
        return handler.getListReference(keys);
    }

    @Override
    public List readArray() {
        checkFieldAndState(ECSVFields.CX_ARRAY);
        ECSVDefinition.ECSVFieldArray arrayField = (ECSVDefinition.ECSVFieldArray) currentField;
        ECSVDefinition.FieldConverter converter = arrayField.getTypeConverter();
        String arrayValue = currentParsed;
        moveForward();
        String[] elements = arrayValue.split(ECSVFormat.GENERIC_SEPARATOR);
        List array = new ArrayList(elements.length);
        for (String element: elements) {
            array.add(converter != null ? converter.convertFrom(element) : element);
        }
        return array;
    }

    @Override
    public Map readMap() {
        checkFieldAndState(ECSVFields.CX_MAP);
        ECSVDefinition.ECSVFIeldMap mapField = (ECSVDefinition.ECSVFIeldMap) currentField;
        ECSVDefinition.FieldConverter keyConverter = mapField.getKeyConverter();
        ECSVDefinition.FieldConverter valueConverter = mapField.getValueConverter();
        String mapValue = currentParsed;
        moveForward();
        String[] entries = mapValue.split(ECSVFormat.GENERIC_SEPARATOR);
        Map map = new HashMap();
        for (String entry: entries) {
            String[] pair = entry.split(ECSVFormat.KEY_VALUE_SEPARATOR);
            if (pair.length == 2) {
                map.put(keyConverter != null ? keyConverter.convertFrom(pair[0]) : pair[0], 
                        valueConverter != null ? valueConverter.convertFrom(pair[1]) : pair[1]);
            }
        }
        return map;
    }

    @Override
    public <E extends ECSVAble> E readInternal(Class<E> entityInternalClass) {
        checkFieldAndState(ECSVFields.CX_INTERNAL);
        ECSVDefinition.ECSVFieldInternal internalField = (ECSVDefinition.ECSVFieldInternal) currentField;
        Class internalEntityClass = internalField.getEntityClass();
        String internalEntityValue = currentParsed;
        moveForward();
        EntityHandler handler = Handlers.getHandlerByClass(internalEntityClass);
        if (handler != null) {
            return (E) handler.readFromString(internalEntityValue);
        }
        return null;
    }

    @Override
    public <E extends ECSVAble> List<E> readInternalArray(Class<E> entityInternalClass) {
        checkFieldAndState(ECSVFields.CX_INTERNAL);
        ECSVDefinition.ECSVFieldInternal internalField = (ECSVDefinition.ECSVFieldInternal) currentField;
        Class internalEntityClass = internalField.getEntityClass();
        String separator = internalField.getSeparator();
        String internalEntitiesValue = currentParsed;
        moveForward();
        EntityHandler handler = Handlers.getHandlerByClass(internalEntityClass);
        String[] internalArray = internalEntitiesValue.split(separator);
        if (handler != null && internalArray.length > 0) {
            List<E> internalList = new ArrayList(internalArray.length);
            for (String internalEntry: internalArray) {
                internalList.add((E) handler.readFromString(internalEntry));
            }
            return internalList;
        }
        return null;
    }
    
}
