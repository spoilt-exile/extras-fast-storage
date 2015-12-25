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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.exception.EntityStoreException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.reading.EntityStreamReader;
import tk.freaxsoftware.extras.faststorage.reading.EntityStreamReaderImpl;

/**
 * Abstract entity storage implementation. Basic implementation for storing 
 * entities in simple file.
 * @author Stanislav Nepochatov
 * @param <E> entity type generic;
 * @param <K> entities key type generic;
 */
public abstract class AbstractEntityStorage<E extends ECSVAble<K>, K> implements EntityStorage<E, K> {
    
    /**
     * Path to resource.
     */
    protected final String path;
    
    protected final Class<E> entityClass;
    
    protected final ECSVDefinition entityDefinition;
    
    /**
     * Entities in-memory storage.
     */
    protected List<E> entitiesStore;
    
    /**
     * Sync lock for storage.
     */
    protected final Object entitiesLock = new Object();
    
    /**
     * Default constructor.
     * @param entityClass
     * @param entityDefinition
     * @param filePath path to stored entities file.
     */
    public AbstractEntityStorage(Class<E> entityClass, ECSVDefinition entityDefinition, String filePath) {
        this.entityClass = entityClass;
        this.entityDefinition = entityDefinition;
        this.path = filePath;
        synchronized (entitiesLock) {
            entitiesStore = readEntitiesFromStore();
        }
    }
    
    /**
     * Internal reading method.
     * @return list of entities;
     */
    protected List<E> readEntitiesFromStore() {
        List<E> readedEntities = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(path));
            EntityStreamReader<E> entityReader = new EntityStreamReaderImpl<>(entityClass, entityDefinition);
            readedEntities = entityReader.readEntities(reader);
        } catch (FileNotFoundException fex) {
            throw new EntityStoreException("File not found: " + path, fex);
        } catch (EntityProcessingException pex) {
            throw new EntityStoreException("Error during processing of entities", pex);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ioex) {}
            }
        }
        return readedEntities == null ? new ArrayList<E>() : readedEntities;
    }
    
    /**
     * Append entity record to end of storage resource. Should be called inside sync block.
     */
    protected void appendEntityToStore(E entity) {
        
    }
    
    /**
     * Save entire storage to resource. Should be called inside sync block.
     */
    protected void saveEntitiesToStore() {
        
    }

    @Override
    public void create(E entity) {
        synchronized(entitiesLock) {
            entitiesStore.add(entity);
            appendEntityToStore(entity);
        }
    }

    @Override
    public void update(E entity) {
        E findedEntity = get(entity.getKey());
        if (findedEntity != null) {
            findedEntity.update(entity);
            saveEntitiesToStore();
        }
    }

    @Override
    public E get(K key) {
        synchronized(entitiesLock) {
            for (E entity: entitiesStore) {
                if (entity.getKey().equals(key)) {
                    return entity;
                }
            }
        }
        return null;
    }

    @Override
    public List<E> get(List<K> keys) {
        synchronized(entitiesLock) {
            List<E> entities = new ArrayList<>(keys.size());
            for (E entity: entitiesStore) {
                for (K key: keys) {
                    if (entity.getKey().equals(key)) {
                        entities.add(entity);
                        break;
                    }
                }
            }
            return entities;
        }
    }

    @Override
    public List<E> getAll() {
        return entitiesStore;
    }

    @Override
    public void delete(E entity) {
        synchronized(entitiesLock) {
            entitiesStore.remove(entity);
            saveEntitiesToStore();
        }
    }
    
}
