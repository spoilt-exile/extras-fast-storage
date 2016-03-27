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

import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;

/**
 * Methods definition for entity handler implementaion. Handler do referencing works 
 * and inter-entity communication.
 * @author Stanislav Nepochatov
 * @param <E> entity type generic;
 * @param <K> entity key type generic;
 */
public interface EntityHandler<E extends ECSVAble<K>, K> extends EntityStorage<E, K> {
    
    /**
     * Entity factory method.
     * @return new empty entity;
     */
    E getNewEntity();
    
    /**
     * Get type of entity which supports handler.
     * @return type unique name;
     */
    String getType();
    
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
