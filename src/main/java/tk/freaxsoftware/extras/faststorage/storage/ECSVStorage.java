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

/**
 * Basic storage operation interface,
 * @author Stanislav Nepochatov
 * @param <E> entity type;
 * @param <I> entitie's id type;
 */
public interface ECSVStorage<E extends ECSVAble, I> {
    
    /**
     * Creates new entity in storage.
     * @param entity new entity to save;
     */
    void create(E entity);
    
    /**
     * Updates entity in storage.
     * @param entity updated entity;
     */
    void update(E entity);
    
    /**
     * Gets entity from storage by some unique key.
     * @param key entity key;
     * @return entity with specified key;
     */
    E get(I key);
    
    /**
     * Finds entity in storage by string query.
     * @param query search query;
     * @return list of enteties founded by query;
     */
    List<E> find(String query);
    
    /**
     * Deletes entity from storage.
     * @param entity object to delete;
     */
    void delete(E entity);
    
}
