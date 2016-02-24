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
package tk.freaxsoftware.extras.faststorage.mapped;

import java.io.Writer;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;

/**
 * Mapped entry contains raw entity form, parsed entity and key.
 * @author Stanislav Nepochatov
 * @since 3.0
 */
public interface EntityMapEntry {
    
    /**
     * Gets key of entry.
     * @return string of key;
     */
    String getKey();
    
    /**
     * Get raw string type of entity.
     * @return raw type;
     */
    String getType();
    
    /**
     * Get entity class instance.
     * @return class of entity in entry or null if parsing faild;
     */
    Class getEntityType();
    
    /**
     * Get raw unparsed entity string.
     * @return raw string;
     */
    String getRawEntity();
    
    /**
     * Get entity.
     * @return entity or null if parsing failed;
     */
    ECSVAble getEntity();
    
    /**
     * Finds out if parsing process complete normally or failed.
     * @return true if parsing succeseded and entity could be retrieved or false if no entity inside entry;
     */
    Boolean isParsed();
    
    /**
     * Get casted entity.
     * @param <V> type generic;
     * @param valueClass desired class to cast;
     * @return casted entity or null if parsing failed or cast failed;
     */
    <V extends ECSVAble> V getEntity(Class<V> valueClass);
    
    /**
     * Updates entity inside entry. Used if there is entity with same key in map.
     * @param updatedEntity updated entity to store;
     */
    void update(ECSVAble updatedEntity);
    
    /**
     * Reads entity from raw string and save it in entry.
     * @param entryString raw entity string;
     */
    void readFromEcsv(String entryString);
    
    /**
     * Writes entity raw string into writer stream.
     * @param entryWriter writer instance;
     */
    void writeToEcsv(Writer entryWriter);
}
