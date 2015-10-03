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
 * Enumeration of ECSV entities types.
 * @author Stanislav Nepochatov
 */
public enum ECSVType {
    
    /**
     * <p>Basic ECSV line with only words.</p>
     * 
     * <p>For example: <code>word1,word2,{area where comma ignored}</code></p>
     */
    BASIC_ECSV,
    
    /**
     * <p>Complex ECSV line with arrays.</p>
     * 
     * <p>For example: <code>word1,word2,[arr1,arr2,arr3...]</code></p>
     */
    COMPLEX_ECSV,
    
    /**
     * <p>Key-value element.</p>
     * 
     * <p>For example: <code>key1:value1</code></p>
     */
    KEY_VALUE
    
}
