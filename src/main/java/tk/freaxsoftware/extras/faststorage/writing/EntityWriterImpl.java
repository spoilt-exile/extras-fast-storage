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
package tk.freaxsoftware.extras.faststorage.writing;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import tk.freaxsoftware.extras.faststorage.exception.EntityStateException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFields;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;
import tk.freaxsoftware.extras.faststorage.generic.EntityListReference;
import tk.freaxsoftware.extras.faststorage.generic.EntityReference;
import tk.freaxsoftware.extras.faststorage.storage.EntityHandler;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;

/**
 * Default implementation of entity writer.
 * @author Stanislav Nepochatov
 * @param <K> entity key type generic;
 */
public class EntityWriterImpl<K> implements EntityWriter<K> {
    
    /**
     * ECSV entity fields defintion.
     */
    private final ECSVDefinition definition;
    
    /**
     * Internal string buffer.
     */
    private final StringBuffer buffer;
    
    /**
     * List of field defintions.
     */
    private final ListIterator<ECSVDefinition.ECSVFieldPrimitive> fieldIterator;
    
    /**
     * Current field in stack.
     */
    private ECSVDefinition.ECSVFieldPrimitive currentField;
    
    /**
     * Default constructor.
     * @param definition fields definition from entity;
     * @param buffer string buffer for writing;
     */
    public EntityWriterImpl(ECSVDefinition definition, StringBuffer buffer) {
        this.definition = definition;
        this.buffer = buffer;
        fieldIterator = this.definition.getFields().listIterator();
        currentField = fieldIterator.next();
    }
    
    /**
     * Checks strict order of entity writing according to fields definition. Throws unchecked 
     * {@code EntityStateException} if order of writing messed up;
     * @param field called field for writing to check;
     */
    public void checkField(ECSVFields field) {
        if (field != currentField.getField()) {
            throw new EntityStateException("Expected " + currentField.getField().name() + " but received " + field.name());
        }
    }
    
    /**
     * Move field stack forward and place separator between fields in stream.
     */
    public void moveForward() {
        if (fieldIterator.hasNext()) {
            buffer.append(ECSVFormat.GENERIC_SEPARATOR_CHAR);
            currentField = fieldIterator.next();
        } else {
            currentField = null;
        }
    }

    @Override
    public void writeKey(K key) {
        checkField(ECSVFields.KEY);
        buffer.append(key.toString());
        moveForward();
    }

    @Override
    public void writeWord(String word) {
        checkField(ECSVFields.PR_WORD);
        buffer.append(word);
        moveForward();
    }

    @Override
    public void writeString(String string) {
        checkField(ECSVFields.PR_STRING);
        buffer.append(ECSVFormat.WHITE_ZONE_BEGIN_CHAR);
        buffer.append(string);
        buffer.append(ECSVFormat.WHITE_ZONE_END_CHAR);
        moveForward();
    }

    @Override
    public void writeBoolean(Boolean bool) {
        checkField(ECSVFields.PR_BOOLEAN);
        buffer.append(bool.toString());
        moveForward();
    }

    @Override
    public void writeInteger(Integer integer) {
        checkField(ECSVFields.PR_INT);
        buffer.append(integer.toString());
        moveForward();
    }

    @Override
    public void writeFloat(Float flt) {
        checkField(ECSVFields.PR_FLOAT);
        buffer.append(flt.toString());
        moveForward();
    }

    @Override
    public void writeDate(Date date) {
        checkField(ECSVFields.SC_DATE);
        ECSVDefinition.ECSVFieldDate dateField = (ECSVDefinition.ECSVFieldDate) currentField;
        buffer.append(ECSVFormat.WHITE_ZONE_BEGIN_CHAR);
        buffer.append(new SimpleDateFormat(dateField.getDateFormat()).format(date));
        buffer.append(ECSVFormat.WHITE_ZONE_END_CHAR);
        moveForward();
    }

    @Override
    public <R extends ECSVAble<K>, K> void writeReference(EntityReference<R, K> reference) {
        checkField(ECSVFields.SC_REF);
        buffer.append(reference.getKey().toString());
        moveForward();
    }

    @Override
    public <R extends ECSVAble<K>, K> void writeReferenceArray(EntityListReference<R, K> referenceArray) {
        checkField(ECSVFields.SC_REF_ARRAY);
        buffer.append(ECSVFormat.ARRAY_BEGIN_CHAR);
        ListIterator<K> keysIter = referenceArray.getKeys().listIterator();
        while (keysIter.hasNext()) {
            K key = keysIter.next();
            buffer.append(key.toString());
            if (keysIter.hasNext()) {
                buffer.append(ECSVFormat.GENERIC_SEPARATOR_CHAR);
            }
        }
        buffer.append(ECSVFormat.ARRAY_END_CHAR);
        moveForward();
    }

    @Override
    public void writeArray(List array) {
        checkField(ECSVFields.CX_ARRAY);
        buffer.append(ECSVFormat.ARRAY_BEGIN_CHAR);
        ECSVDefinition.ECSVFieldArray arrayField = (ECSVDefinition.ECSVFieldArray) currentField;
        ECSVDefinition.FieldConverter converter = arrayField.getTypeConverter();
        ListIterator arrayIter = array.listIterator();
        while (arrayIter.hasNext()) {
            Object element = arrayIter.next();
            buffer.append(converter != null ? converter.convertTo(element) : element.toString());
            if (arrayIter.hasNext()) {
                buffer.append(ECSVFormat.GENERIC_SEPARATOR_CHAR);
            }
        }
        buffer.append(ECSVFormat.ARRAY_END_CHAR);
        moveForward();
    }

    @Override
    public void writeMap(Map map) {
        checkField(ECSVFields.CX_MAP);
        buffer.append(ECSVFormat.ARRAY_BEGIN_CHAR);
        ECSVDefinition.ECSVFIeldMap mapField = (ECSVDefinition.ECSVFIeldMap) currentField;
        ECSVDefinition.FieldConverter keyConverter = mapField.getKeyConverter();
        ECSVDefinition.FieldConverter valueConverter = mapField.getValueConverter();
        Iterator<Map.Entry> mapIterator = map.entrySet().iterator();
        while (mapIterator.hasNext()) {
            Map.Entry entry = mapIterator.next();
            buffer.append(keyConverter != null ? keyConverter.convertTo(entry.getKey()) : entry.getKey().toString());
            buffer.append(ECSVFormat.KEY_VALUE_SEPARATOR_CHAR);
            buffer.append(valueConverter != null ? valueConverter.convertTo(entry.getValue()) : entry.getValue().toString());
            if (mapIterator.hasNext()) {
                buffer.append(ECSVFormat.GENERIC_SEPARATOR_CHAR);
            }
        }
        buffer.append(ECSVFormat.ARRAY_END_CHAR);
        moveForward();
    }

    @Override
    public <E extends ECSVAble> void writeInternal(E entity) {
        checkField(ECSVFields.CX_INTERNAL);
        buffer.append(ECSVFormat.WHITE_ZONE_BEGIN_CHAR);
        EntityHandler handler = Handlers.getHandlerByClass(entity.getClass());
        buffer.append(handler.writeToString(entity));
        buffer.append(ECSVFormat.WHITE_ZONE_END_CHAR);
        moveForward();
    }

    @Override
    public <E extends ECSVAble> void writeInternalArray(List<E> entityArray) {
        checkField(ECSVFields.CX_INTERNAL_ARRAY);
        buffer.append(ECSVFormat.WHITE_ZONE_BEGIN_CHAR);
        ECSVDefinition.ECSVFieldInternal internalField = (ECSVDefinition.ECSVFieldInternal) currentField;
        if (!entityArray.isEmpty()) {
            EntityHandler handler = Handlers.getHandlerByClass(internalField.getEntityClass());
            ListIterator<E> entityIter = entityArray.listIterator();
            while (entityIter.hasNext()) {
                E entity = entityIter.next();
                buffer.append(handler.writeToString(entity));
                if (entityIter.hasNext()) {
                    buffer.append(internalField.getSeparator());
                }
            }
        }
        buffer.append(ECSVFormat.WHITE_ZONE_END_CHAR);
        moveForward();
    }
    
}
