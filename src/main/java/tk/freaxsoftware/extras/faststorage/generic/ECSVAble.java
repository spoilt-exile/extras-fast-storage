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

import tk.freaxsoftware.extras.faststorage.parsing.ParseResult;

/**
 * Interface with some util methods for object serialization/deserialization with ECSV format.
 * @author Stanislav Nepochatov
 */
public interface ECSVAble {
    
    /**
     * Get string representation of entity type. 
     * This string will be used during saving entity in ECSV string as key.<br/>
     * <br/>
     * Example: <code>TYPE:word1,word2,{some text},[arr1,arr2]</code><br/>
     * <b>Reccomended value: class short or full name.</b>
     * @return string with type of entity;
     */
    public String getEntityType();
    
    /**
     * Get count of basic elements in this ECSV entity. 
     * @return count of basic elements (simple words);
     */
    public int getBasicCount();
    
    /**
     * Get count of array elements in this ECSV entity.
     * @return count of arrays;
     */
    public int getArrayCount();
    
    /**
     * Get current type of entity.
     * @return type of entity (used for validation);
     */
    public ECSVType getCurrentType();
    
    /**
     * Pack object to ECSV string and append it to buffer.
     * @param buffer output buffer;
     * @param appendType append type id before data;
     */
    public void pack(StringBuffer buffer, Boolean appendType);
    
    /**
     * Pack obejct to ECSV string (basic).
     * @param appendType append type info to output;
     * @return this object string representation in ECSV format;
     */
    public String packToString(Boolean appendType);
    
    /**
     * Unpack object with given parse result.
     * @param result result of single line parsing;
     */
    public void unPack(ParseResult result);
    
}
