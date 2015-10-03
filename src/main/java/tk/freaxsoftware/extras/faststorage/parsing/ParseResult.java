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

import java.util.ArrayList;
import java.util.List;

/**
 * Parse result structure which holds parsed data.
 * @author Stanislav Nepochatov
 */
public class ParseResult {
    
    /**
     * Type provided by parser.
     */
    private String suggestedType;
    
    /**
     * Type of entity.
     */
    private String type;
    
    /**
     * List with basic words.
     */
    private String[] basics;
    
    /**
     * List with arrays.
     */
    private List<String[]> arrays;
    
    /**
     * Raw string of data.
     */
    private String rawString;
    
    /**
     * Result validation status.
     */
    private Boolean valid;
    
    /**
     * Defualt construtor.
     * @param typeName type name of entity (optional);
     * @param basicsSize size of basic words array;
     * @param arraysCount size of list with arrays;
     */
    public ParseResult(String typeName, int basicsSize, int arraysCount) {
        suggestedType = typeName;
        valid = true;
        basics = new String[basicsSize];
        arrays = new ArrayList(arraysCount);
    }

    /**
     * Get type name suggested by parser.
     * @return string wit type name;
     */
    public String getSuggestedType() {
        return suggestedType;
    }

    /**
     * Set type name suggested type.
     * @param suggestedType type to set;
     */
    public void setSuggestedType(String suggestedType) {
        this.suggestedType = suggestedType;
    }

    /**
     * Get type extracted from entity.
     * @return the type of entity;
     */
    public String getType() {
        return type;
    }

    /**
     * Set type entity. Usually you shouln't do that.
     * @param type the type to set;
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Get basic words array.
     * @return the array with basic words;
     */
    public String[] getBasics() {
        return basics;
    }

    /**
     * Set basic words array.
     * @param basics the array to set;
     */
    public void setBasics(String[] basics) {
        this.basics = basics;
    }

    /**
     * Get list of parsed arrays.
     * @return the arrays;
     */
    public List<String[]> getArrays() {
        return arrays;
    }

    /**
     * Set list of parsed arrays.
     * @param arrays the arrays to set;
     */
    public void setArrays(List<String[]> arrays) {
        this.arrays = arrays;
    }

    /**
     * Get unparsed data.
     * @return the raw string;
     */
    public String getRawString() {
        return rawString;
    }

    /**
     * Set unparsed data.
     * @param rawString the raw to set;
     */
    public void setRawString(String rawString) {
        this.rawString = rawString;
    }

    /**
     * Get validation status of this result.
     * @return true if parsed complete / false if there is error;
     * @see #getParseError() 
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * Set validation status of this result.
     * @param valid the validation status to set;
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
