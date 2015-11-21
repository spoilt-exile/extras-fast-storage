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
     * Integer to string converter.
     */
    public static FieldConverter<Integer> CONVERTER_INTEGER = new FieldConverter<Integer>() {

        @Override
        public Integer convertFrom(String rawValue) {
            return Integer.parseInt(rawValue);
        }

        @Override
        public String convertTo(Integer fieldValue) {
            return fieldValue.toString();
        }
    };
    
    /**
     * Float to string converter.
     */
    public static FieldConverter<Float> CONVERTER_FLOAT = new FieldConverter<Float>() {

        @Override
        public Float convertFrom(String rawValue) {
            return Float.parseFloat(rawValue);
        }

        @Override
        public String convertTo(Float fieldValue) {
            return fieldValue.toString();
        }
    };
    
    /**
     * Boolean to string converter.
     */
    public static FieldConverter<Boolean> CONVERTER_BOOLEAN = new FieldConverter<Boolean>() {

        @Override
        public Boolean convertFrom(String rawValue) {
            return Boolean.parseBoolean(rawValue);
        }

        @Override
        public String convertTo(Boolean fieldValue) {
            return fieldValue.toString();
        }
    };
    
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
     * @param converter field converter for list content (use null if type of list is string);
     * @return instance;
     */
    public ECSVDefinition addArray(FieldConverter converter) {
        fields.add(new ECSVFieldArray(ECSVFields.CX_ARRAY, converter));
        return this;
    }
    
    /**
     * Add map of primitives field to current definition and return current instance.
     * @param keyConverter key field converter of map (use null if type of map key is string);
     * @param valueConverter value field converter of map (use null if type of map value is string);
     * @return instance;
     */
    public ECSVDefinition addMap(FieldConverter keyConverter, FieldConverter valueConverter) {
        fields.add(new ECSVFIeldMap(ECSVFields.CX_MAP, keyConverter, valueConverter));
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
        
        private final FieldConverter typeConverter;

        public ECSVFieldArray(ECSVFields givenField, FieldConverter givenConverter) {
            super(givenField);
            typeConverter = givenConverter;
        }

        public FieldConverter getTypeConverter() {
            return typeConverter;
        }
    }
    
    /**
     * Map field holder with internal key and value info.
     */
    public static class ECSVFIeldMap extends ECSVFieldPrimitive {
        
        private final FieldConverter keyConverter;
       
        private final FieldConverter valueConverter;
        
        public ECSVFIeldMap(ECSVFields givenField, FieldConverter givenKeyConverter, FieldConverter givenValueConverter) {
            super(givenField);
            keyConverter = givenKeyConverter;
            valueConverter = givenValueConverter;
        }

        public FieldConverter getKeyConverter() {
            return keyConverter;
        }

        public FieldConverter getValueConverter() {
            return valueConverter;
        }
    }
    
    /**
     * Internal field holder.
     * @param <E> entity generic;
     */
    public static class ECSVFieldInternal<E extends ECSVAble> extends ECSVFieldPrimitive {
        
        private final Class<E> entityClass;
        
        private final String separator;

        /**
         * Simple internal constructor.
         * @param givenEntityClass entity class;
         */
        public ECSVFieldInternal(Class<E> givenEntityClass) {
            super(ECSVFields.CX_INTERNAL);
            entityClass = givenEntityClass;
            separator = null;
        }
        
        /**
         * Array internal constructor.
         * @param givenEntityClass entity class;
         * @param givenSeparator separator char for packing array into string;
         */
        public ECSVFieldInternal(Class<E> givenEntityClass, char givenSeparator) {
            super(ECSVFields.CX_INTERNAL_ARRAY);
            entityClass = givenEntityClass;
            separator = String.valueOf(givenSeparator);
        }

        public Class<E> getEntityClass() {
            return entityClass;
        }

        public String getSeparator() {
            return separator;
        }
    }
    
    /**
     * Field converter interface for conversation between field forms.
     * @param <T> type generic;
     */
    public static interface FieldConverter<T> {
        T convertFrom(String rawValue);
        String convertTo(T fieldValue);
    }
}
