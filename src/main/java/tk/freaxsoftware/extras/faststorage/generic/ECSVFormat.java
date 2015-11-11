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
 * Format definition class.
 * @author Stanislav Nepochatov
 */
public final class ECSVFormat {
    
    /**
     * Entity separator constant. One entity - one line.
     */
    public static final String LINE_BREAK = "\n";
    
    /**
     * Entity separator char constant. One entity - one line.
     */
    public static final char LINE_BREAK_CHAR = '\n';
    
    /**
     * Generic separator constant (comma).
     */
    public static final String GENERIC_SEPARATOR = ",";
    
    /**
     * Generic separator char constant (comma).
     */
    public static final char GENERIC_SEPARATOR_CHAR = ',';
    
    /**
     * White zone begin constant. Parser ignores commas during white zone parsing.
     */
    public static final String WHITE_ZONE_BEGIN = "{";
    
    /**
     * White zone begin char constant. Parser ignores commas during white zone parsing.
     */
    public static final char WHITE_ZONE_BEGIN_CHAR = '{';
    
    /**
     * White zone end constant.
     */
    public static final String WHITE_ZONE_END = "}";
    
    /**
     * White zone end char constant.
     */
    public static final char WHITE_ZONE_END_CHAR = '}';
    
    /**
     * Array begin constant. Array may contain simple string value separated by comma, or key-value pairs.
     */
    public static final String ARRAY_BEGIN = "[";
    
    /**
     * Array begin char constant. Array may contain simple string value separated by comma, or key-value pairs.
     */
    public static final char ARRAY_BEGIN_CHAR = '[';
    
    /**
     * Array end constant.
     */
    public static final String ARRAY_END = "]";
    
    /**
     * Array end char constant.
     */
    public static final char ARRAY_END_CHAR = ']';
    
    /**
     * Key-value pair separation constant.
     */
    public static final String KEY_VALUE_SEPARATOR = ":";
    
    /**
     * Key-value pair separation char constant.
     */
    public static final char KEY_VALUE_SEPARATOR_CHAR = ':';
    
    /**
     * Default separation char for entity separation within white zone of stream.
     */
    public static final char INTERNAL_DEFAULT_SEPARATOR = '$';
    
}
