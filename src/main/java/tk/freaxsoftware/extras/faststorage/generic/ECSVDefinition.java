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
package tk.freaxsoftware.extras.faststorage.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * ECSV entity type definition.
 * @author Stanislav Nepochatov
 */
public class ECSVDefinition {
    
    /**
     * List of fields.
     */
    private List<ECSVFieldPrimitive> fields;
    
    /**
     * Private constructor.
     */
    private ECSVDefinition() {
        fields = new ArrayList<>();
    }
    
    /**
     * Factory method: get new definition instance.
     * @return new instance.
     */
    public static ECSVDefinition createNew() {
        return new ECSVDefinition();
    }

    public List<ECSVFieldPrimitive> getFields() {
        return fields;
    }

    public void setFields(List<ECSVFieldPrimitive> fields) {
        this.fields = fields;
    }
    
    /**
     * Adds key field to current definition and return current instance.
     * @param keyClass class of key value;
     * @return instance;
     */
    public ECSVDefinition addKey(Class keyClass) {
        fields.add(new ECSVFieldKey(ECSVFields.KEY, keyClass));
        return this;
    }
    
    /**
     * Adds primitive field to current definition and return current instance.
     * @param field field definition to add;
     * @return instance;
     * @see ECSVFields
     */
    public ECSVDefinition addPrimitive(ECSVFields field) {
        fields.add(new ECSVFieldPrimitive(field));
        return this;
    }
    
    /**
     * Adds date field to current definition and return current instance.
     * @param format date format string;
     * @return instance;
     */
    public ECSVDefinition addDate(String format) {
        fields.add(new ECSVFieldDate(ECSVFields.SC_DATE, format));
        return this;
    }
    
    /**
     * Add reference field to current definition and return current instance.
     * @param <R> reference type generic;
     * @param <K> reference entity key class;
     * @param refClass reference type class;
     * @return instance;
     */
    public <R extends ECSVAble, K> ECSVDefinition addReference(Class<R> refClass, Class<K> refKeyClass) {
        fields.add(new ECSVFieldReference(ECSVFields.SC_REF, refClass, refKeyClass));
        return this;
    }
    
    /**
     * Add reference array field to current definition and return current instance.
     * @param <R> reference type generic;
     * @param <K> reference entity key class;
     * @param refClass reference type class;
     * @return instance;
     */
    public <R extends ECSVAble, K> ECSVDefinition addReferenceArray(Class<R> refClass, Class<K> refKeyClass) {
        fields.add(new ECSVFieldReference(ECSVFields.SC_REF_ARRAY, refClass, refKeyClass));
        return this;
    }
    
    /**
     * Add array of primitives field to current definition and return current instance.
     * @param innerType inner type in array;
     * @return instance;
     */
    public ECSVDefinition addArray(ECSVFields innerType) {
        fields.add(new ECSVFieldArray(ECSVFields.CX_ARRAY, innerType));
        return this;
    }
    
    /**
     * Add map of primitives field to current definition and return current instance.
     * @param keyType key type of map;
     * @param valueType value type of map;
     * @return instance;
     */
    public ECSVDefinition addMap(ECSVFields keyType, ECSVFields valueType) {
        fields.add(new ECSVFIeldMap(ECSVFields.CX_MAP, valueType, valueType));
        return this;
    }
    
    /**
     * Add internal field to current definition and return current instance.
     * @param <E> internal entity type generic;
     * @param internalClass internal entity type class;
     * @return instance;
     */
    public <E extends ECSVAble> ECSVDefinition addInternal(Class<E> internalClass) {
        fields.add(new ECSVFieldInternal(internalClass));
        return this;
    }
    
    /**
     * Add internal array field to current definition and return current instance.
     * @param <E> internal entity type generic;
     * @param internalClass internal entity type class;
     * @param entitySeparator separation char which used for entity separation within stream;
     * @return instance;
     */
    public <E extends ECSVAble> ECSVDefinition addInternalArray(Class<E> internalClass, char entitySeparator) {
        fields.add(new ECSVFieldInternal(internalClass, entitySeparator));
        return this;
    }
    
    /**
     * Field holder class.
     */
    public static class ECSVFieldPrimitive {
        
        /**
         * Incased field definition.
         */
        private ECSVFields field;
        
        public ECSVFieldPrimitive(ECSVFields givenField) {
            this.field = givenField;
        }

        public ECSVFields getField() {
            return field;
        }

        public void setField(ECSVFields field) {
            this.field = field;
        }
    }
    
    /**
     * Key field holder class.
     */
    public static class ECSVFieldKey extends ECSVFieldPrimitive {
        private Class keyClass;

        public ECSVFieldKey(ECSVFields givenField, Class givenKeyClass) {
            super(givenField);
            keyClass = givenKeyClass;
        }

        public Class getKeyClass() {
            return keyClass;
        }

        public void setKeyClass(Class keyClass) {
            this.keyClass = keyClass;
        }
    }
    
    /**
     * Date field holder with format.
     */
    public static class ECSVFieldDate extends ECSVFieldPrimitive {
        
        private String dateFormat;

        public ECSVFieldDate(ECSVFields givenField, String givenFormat) {
            super(givenField);
            dateFormat = givenFormat;
        }

        public String getDateFormat() {
            return dateFormat;
        }

        public void setDateFormat(String dateFormat) {
            this.dateFormat = dateFormat;
        }
    }
    
    /**
     * Reference/internal field holder with class.
     * @param <R> reference or internal entity type generic;
     */
    public static class ECSVFieldReference<R extends ECSVAble, K> extends ECSVFieldPrimitive {
        
        private Class<R> referenceClass;
        
        private Class<K> referenceKeyClass;

        public ECSVFieldReference(ECSVFields givenField, Class<R> givenRefClass, Class<K> givenKeyClass) {
            super(givenField);
            referenceClass = givenRefClass;
            referenceKeyClass = givenKeyClass;
        }

        public Class<R> getReferenceClass() {
            return referenceClass;
        }

        public void setReferenceClass(Class<R> referenceClass) {
            this.referenceClass = referenceClass;
        }

        public Class<K> getReferenceKeyClass() {
            return referenceKeyClass;
        }

        public void setReferenceKeyClass(Class<K> referenceKeyClass) {
            this.referenceKeyClass = referenceKeyClass;
        }
    }
    
    /**
     * Array field holder with internal type info.
     */
    public static class ECSVFieldArray extends ECSVFieldPrimitive {
        
        private ECSVFields innerType;

        public ECSVFieldArray(ECSVFields givenField, ECSVFields givenInnerType) {
            super(givenField);
            innerType = givenInnerType;
        }

        public ECSVFields getInnerType() {
            return innerType;
        }

        public void setInnerType(ECSVFields innerType) {
            this.innerType = innerType;
        }
    }
    
    /**
     * Map field holder with internal key and value info.
     */
    public static class ECSVFIeldMap extends ECSVFieldPrimitive {
        
        private ECSVFields keyType;

        private ECSVFields valueType;
        
        public ECSVFIeldMap(ECSVFields givenField, ECSVFields givenKeyType, ECSVFields givenValueType) {
            super(givenField);
            keyType = givenKeyType;
            valueType = givenValueType;
        }

        public ECSVFields getKeyType() {
            return keyType;
        }

        public void setKeyType(ECSVFields keyType) {
            this.keyType = keyType;
        }

        public ECSVFields getValueType() {
            return valueType;
        }

        public void setValueType(ECSVFields valueType) {
            this.valueType = valueType;
        }
    }
    
    /**
     * Internal field holder.
     * @param <E> entity generic;
     */
    public static class ECSVFieldInternal<E extends ECSVAble> extends ECSVFieldPrimitive {
        
        private Class<E> entityClass;
        
        private char separator = (char) -1;

        /**
         * Simple internal constructor.
         * @param givenEntityClass entity class;
         */
        public ECSVFieldInternal(Class<E> givenEntityClass) {
            super(ECSVFields.CX_INTERNAL);
            entityClass = givenEntityClass;
        }
        
        /**
         * Array internal constructor.
         * @param givenEntityClass entity class;
         * @param givenSeparator separator char for packing array into string;
         */
        public ECSVFieldInternal(Class<E> givenEntityClass, char givenSeparator) {
            super(ECSVFields.CX_INTERNAL_ARRAY);
            entityClass = givenEntityClass;
            separator = givenSeparator;
        }

        public Class<E> getEntityClass() {
            return entityClass;
        }

        public void setEntityClass(Class<E> entityClass) {
            this.entityClass = entityClass;
        }

        public char getSeparator() {
            return separator;
        }

        public void setSeparator(char separator) {
            this.separator = separator;
        }
    }
}
