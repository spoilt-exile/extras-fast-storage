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
package tk.freaxsoftware.extras.faststorage.storage;

import java.util.List;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.EntityListReference;
import tk.freaxsoftware.extras.faststorage.generic.EntityReference;

/**
 * Methods definition for entity handler implementaion. Handler do referencing works 
 * and inter-entity communication.
 * @author Stanislav Nepochatov
 * @param <E> entity type generic;
 * @param <K> entity key type generic;
 */
public interface EntityHandler<E extends ECSVAble<K>, K> extends ECSVStorage<E, K> {
    
    /**
     * Get type of entity which supports handler.
     * @return type unique name;
     */
    String getType();
    
    /**
     * Finds out if handler is ready for communication and loaded all items from storage.
     * @return true if references could be executed / false if storage still loading.
     */
    Boolean isReady();
    
    /**
     * Get reference for entity inside storage by it's key.
     * @param key entity key;
     * @return reference for entity lazy loading;
     */
    EntityReference<E, K> getReference(K key);
    
    /**
     * Get list reference for entity inside storage by list of keys;
     * @param keys list of keys;
     * @return list reference for lazy loading;
     */
    EntityListReference<E, K> getListReference(List<K> keys);
    
    /**
     * Reads entity from raw string.
     * @param rawString entity raw string;
     * @return entity instance;
     */
    E readFromString(String rawString);
    
    /**
     * Writes entity to string.
     * @param entity entity to write;
     * @return string form of entity;
     */
    String writeToString(E entity);
}
