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

import java.io.Writer;
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
import tk.freaxsoftware.extras.faststorage.utils.WriterWrapper;

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
    
    private final WriterWrapper writer;
    
    /**
     * List of field defintions.
     */
    private ListIterator<ECSVDefinition.ECSVFieldPrimitive> fieldIterator;
    
    /**
     * Current field in stack.
     */
    private ECSVDefinition.ECSVFieldPrimitive currentField;
    
    /**
     * Default constructor.
     * @param definition fields definition from entity;
     * @param writer string buffer for writing;
     */
    public EntityWriterImpl(ECSVDefinition definition, Writer writer) {
        this.definition = definition;
        this.writer = new WriterWrapper(writer);
    }
    
    public void reset() {
        fieldIterator = this.definition.getFields().listIterator();
        currentField = fieldIterator.next();
    }
    
    /**
     * Checks strict order of entity writing according to fields definition. Throws unchecked 
     * {@code EntityStateException} if order of writing messed up;
     * @param field called field for writing to check;
     */
    private void checkField(ECSVFields field) {
        if (field != currentField.getField()) {
            throw new EntityStateException("Expected " + currentField.getField().name() + " but received " + field.name());
        }
    }
    
    /**
     * Move field stack forward and place separator between fields in stream.
     */
    public void moveForward() {
        if (fieldIterator.hasNext()) {
            writer.append(ECSVFormat.GENERIC_SEPARATOR_CHAR);
            currentField = fieldIterator.next();
        } else {
            currentField = null;
        }
    }

    @Override
    public void writeKey(K key) {
        checkField(ECSVFields.KEY);
        if (key == null) {
            throw new EntityStateException("Entity key can't be null!");
        }
        writer.append(key.toString());
        moveForward();
    }

    @Override
    public void writeWord(String word) {
        checkField(ECSVFields.PR_WORD);
        if (word == null) {
            writer.append(ECSVFormat.NULL_VALUE);
        } else {
            writer.append(word);
        }
        moveForward();
    }

    @Override
    public void writeString(String string) {
        checkField(ECSVFields.PR_STRING);
        if (string == null) {
            writer.append(ECSVFormat.NULL_VALUE);
        } else {
            writer.append(ECSVFormat.WHITE_ZONE_BEGIN_CHAR);
            writer.append(string);
            writer.append(ECSVFormat.WHITE_ZONE_END_CHAR);
        }
        moveForward();
    }

    @Override
    public void writeBoolean(Boolean bool) {
        checkField(ECSVFields.PR_BOOLEAN);
        if (bool == null) {
            writer.append(ECSVFormat.NULL_VALUE);
        } else {
            writer.append(bool.toString());
        }
        moveForward();
    }

    @Override
    public void writeInteger(Integer integer) {
        checkField(ECSVFields.PR_INT);
        if (integer == null) {
            writer.append(ECSVFormat.NULL_VALUE);
        } else {
            writer.append(integer.toString());
        }
        moveForward();
    }

    @Override
    public void writeFloat(Float flt) {
        checkField(ECSVFields.PR_FLOAT);
        if (flt == null) {
            writer.append(ECSVFormat.NULL_VALUE);
        } else {
            writer.append(flt.toString());
        }
        moveForward();
    }

    @Override
    public void writeDate(Date date) {
        checkField(ECSVFields.SC_DATE);
        if (date == null) {
            writer.append(ECSVFormat.NULL_VALUE);
        } else {
            ECSVDefinition.ECSVFieldDate dateField = (ECSVDefinition.ECSVFieldDate) currentField;
            writer.append(ECSVFormat.WHITE_ZONE_BEGIN_CHAR);
            writer.append(dateField.format(date));
            writer.append(ECSVFormat.WHITE_ZONE_END_CHAR);
        }
        moveForward();
    }

    @Override
    public <R extends ECSVAble<K>, K> void writeReference(EntityReference<R, K> reference) {
        checkField(ECSVFields.SC_REF);
        if (reference == null) {
            writer.append(ECSVFormat.NULL_VALUE);
        } else {
            writer.append(reference.getKey().toString());
        }
        moveForward();
    }

    @Override
    public <R extends ECSVAble<K>, K> void writeReferenceArray(EntityListReference<R, K> referenceArray) {
        checkField(ECSVFields.SC_REF_ARRAY);
        writer.append(ECSVFormat.ARRAY_BEGIN_CHAR);
        if (referenceArray != null) {
            ListIterator<K> keysIter = referenceArray.getKeys().listIterator();
            while (keysIter.hasNext()) {
                K key = keysIter.next();
                writer.append(key.toString());
                if (keysIter.hasNext()) {
                    writer.append(ECSVFormat.GENERIC_SEPARATOR_CHAR);
                }
            }
        }
        writer.append(ECSVFormat.ARRAY_END_CHAR);
        moveForward();
    }

    @Override
    public void writeArray(List array) {
        checkField(ECSVFields.CX_ARRAY);
        writer.append(ECSVFormat.ARRAY_BEGIN_CHAR);
        if (array != null) {
            ECSVDefinition.ECSVFieldArray arrayField = (ECSVDefinition.ECSVFieldArray) currentField;
            ECSVDefinition.FieldConverter converter = arrayField.getTypeConverter();
            ListIterator arrayIter = array.listIterator();
            while (arrayIter.hasNext()) {
                Object element = arrayIter.next();
                writer.append(converter != null ? converter.convertTo(element) : element.toString());
                if (arrayIter.hasNext()) {
                    writer.append(ECSVFormat.GENERIC_SEPARATOR_CHAR);
                }
            }
        }
        writer.append(ECSVFormat.ARRAY_END_CHAR);
        moveForward();
    }

    @Override
    public void writeMap(Map map) {
        checkField(ECSVFields.CX_MAP);
        writer.append(ECSVFormat.ARRAY_BEGIN_CHAR);
        if (map != null) {
            ECSVDefinition.ECSVFIeldMap mapField = (ECSVDefinition.ECSVFIeldMap) currentField;
            ECSVDefinition.FieldConverter keyConverter = mapField.getKeyConverter();
            ECSVDefinition.FieldConverter valueConverter = mapField.getValueConverter();
            Iterator<Map.Entry> mapIterator = map.entrySet().iterator();
            while (mapIterator.hasNext()) {
                Map.Entry entry = mapIterator.next();
                writer.append(keyConverter != null ? keyConverter.convertTo(entry.getKey()) : entry.getKey().toString());
                writer.append(ECSVFormat.KEY_VALUE_SEPARATOR_CHAR);
                writer.append(valueConverter != null ? valueConverter.convertTo(entry.getValue()) : entry.getValue().toString());
                if (mapIterator.hasNext()) {
                    writer.append(ECSVFormat.GENERIC_SEPARATOR_CHAR);
                }
            }
        }
        writer.append(ECSVFormat.ARRAY_END_CHAR);
        moveForward();
    }

    @Override
    public <E extends ECSVAble> void writeInternal(E entity) {
        checkField(ECSVFields.CX_INTERNAL);
        if (entity == null) {
            writer.append(ECSVFormat.NULL_VALUE);
        } else {
            writer.append(ECSVFormat.WHITE_ZONE_BEGIN_CHAR);
            EntityHandler handler = Handlers.getHandlerByClass(entity.getClass());
            writer.append(handler.writeToString(entity));
            writer.append(ECSVFormat.WHITE_ZONE_END_CHAR);
        }
        moveForward();
    }

    @Override
    public <E extends ECSVAble> void writeInternalArray(List<E> entityArray) {
        checkField(ECSVFields.CX_INTERNAL_ARRAY);
        writer.append(ECSVFormat.WHITE_ZONE_BEGIN_CHAR);
        if (entityArray != null) {
            ECSVDefinition.ECSVFieldInternal internalField = (ECSVDefinition.ECSVFieldInternal) currentField;
            if (!entityArray.isEmpty()) {
                EntityHandler handler = Handlers.getHandlerByClass(internalField.getEntityClass());
                ListIterator<E> entityIter = entityArray.listIterator();
                while (entityIter.hasNext()) {
                    E entity = entityIter.next();
                    writer.append(handler.writeToString(entity));
                    if (entityIter.hasNext()) {
                        writer.append(internalField.getSeparator());
                    }
                }
            }
        }
        writer.append(ECSVFormat.WHITE_ZONE_END_CHAR);
        moveForward();
    }
    
}
