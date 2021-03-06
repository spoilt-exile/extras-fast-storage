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

import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;
import tk.freaxsoftware.extras.faststorage.reading.EntityReader;

/**
 * Interface with some util methods for object serialization/deserialization with ECSV format.
 * @param <K> entity key;
 * @author Stanislav Nepochatov
 */
public interface ECSVAble<K> {
    
    /**
     * Get string representation of entity type. Entity type 
     * may be used during serialization.
     * @return string with type of entity;
     */
    String getEntityType();
    
    /**
     * Gets entity key.
     * @return entity unique key;
     */
    K getKey();
    
    /**
     * Set entity key.
     * @param key auto-generated key by storage;
     */
    void setKey(K key);
    
    /**
     * Get entity definition by fields.
     * @return fully inited definition of entity;
     */
    ECSVDefinition getDefinition();
    
    /**
     * Reads all data from reader and apply them to current entity instance.
     * @param reader helper object for entity readeing;
     */
    void readFromECSV(EntityReader<K> reader);
    
    /**
     * Writes all data from entity to ECSV stream.
     * @param writer helper object to entity writing;
     */
    void writeToECSV(EntityWriter<K> writer);
    
    /**
     * Update current entity with updated copy.
     * @param updatedEntity copy of entity with updated data except key;
     */
    void update(ECSVAble<K> updatedEntity);
}
