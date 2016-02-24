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

/**
 * ECSV format fields definition for parsing advande.
 * @author Stanislav Nepochatov
 */
public enum ECSVFields {
    
    /**
     * Entity key.
     */
    KEY,
    
    /**
     * One word without spaces or any special chars. 
     */
    PR_WORD,
    
    /**
     * String with spaces etc.
     */
    PR_STRING,
    
    /**
     * Boolean value as true or false form.
     */
    PR_BOOLEAN,
    
    /**
     * Integer value.
     */
    PR_INT,
    
    /**
     * Float digital value.
     */
    PR_FLOAT,
    
    /**
     * Date value, much like <code>STRING</code>.
     */
    SC_DATE,
    
    /**
     * Reference for another entity (maybe even other type).
     */
    SC_REF,
    
    /**
     * Array of references for sublist of another entity (maybe even other type).
     */
    SC_REF_ARRAY,
    
    /**
     * Simple array of words or digits.
     */
    CX_ARRAY,
    
    /**
     * Array as key-value storage.
     */
    CX_MAP,
    
    /**
     * Internal entity inside white zone of string.
     */
    CX_INTERNAL, 
    
    /**
     * Internal enteties array inside white zone of string.
     */
    CX_INTERNAL_ARRAY;
}
