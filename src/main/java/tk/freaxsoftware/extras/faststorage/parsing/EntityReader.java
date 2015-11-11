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
package tk.freaxsoftware.extras.faststorage.parsing;

import java.util.Date;
import java.util.List;
import java.util.Map;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.EntityReference;

/**
 * ECSV entity reader helper interface.
 * @author Stanislav Nepochatov
 */
public interface EntityReader {
    
    /**
     * Reads entity type attribute.
     * @return string type value;
     */
    String readType();
    
    /**
     * Reads integer entity key value.
     * @return integer key;
     */
    Integer readKeyInt();
    
    /**
     * Reads string key of entity.
     * @return string key;
     */
    String readKeyStr();
    
    /**
     * Reads single word in stream.
     * @return word string value;
     */
    String readWord();
    
    /**
     * Reads escaped string in stream.
     * @return string value;
     */
    String readString();
    
    /**
     * Reads boolean value in stream.
     * @return boolean value;
     */
    Boolean readBoolean();
    
    /**
     * Reads integer value in stream.
     * @return integer value;
     */
    Integer readInteger();
    
    /**
     * Reads float value in stream.
     * @return float value;
     */
    Float readFloat();
    
    /**
     * Reads date in default date format.
     * @return date value;
     */
    Date readDate();
    
    /**
     * Reads entity reference.
     * @param <R> reference type generic;
     * @param entityRefClass referented entity class;
     * @return reference helper object for lazy loading;
     */
    <R extends ECSVAble> EntityReference<R> readReference(Class<R> entityRefClass);
    
    /**
     * Reads array in stream.
     * @return list of parsed array values;
     */
    List readArray();
    
    /**
     * Reads map in stream as key-value array.
     * @return map of parsed values;
     */
    Map<String, Object> readMap();
    
    /**
     * Reads internal ECSV entity in string white zone.
     * @param <E> entity type generic;
     * @param entityInternalClass entity class;
     * @return parsed entity;
     */
    <E extends ECSVAble> E readInternal(Class<E> entityInternalClass);
}
