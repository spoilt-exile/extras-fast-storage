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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.exception.EntityStoreException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;
import tk.freaxsoftware.extras.faststorage.reading.EntityStreamReader;
import tk.freaxsoftware.extras.faststorage.reading.EntityStreamReaderImpl;
import tk.freaxsoftware.extras.faststorage.writing.EntityStreamWriter;
import tk.freaxsoftware.extras.faststorage.writing.EntityStreamWriterImpl;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriterImpl;

/**
 * Abstract entity storage implementation. Basic implementation for storing 
 * entities in simple file.
 * @author Stanislav Nepochatov
 * @param <E> entity type generic;
 * @param <K> entities key type generic;
 */
public abstract class AbstractEntityStorage<E extends ECSVAble<K>, K> implements EntityStorage<E, K> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityStorage.class);
    
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
        if (filePath != null) {
            this.path = filePath;
            synchronized (entitiesLock) {
                entitiesStore = readEntitiesFromStore();
            }
            if (entitiesStore.isEmpty()) {
                onStorageCreation();
            }
        } else {
            this.path = null;
        }
    }
    
    /**
     * Internal reading method.
     * @return list of entities;
     */
    protected List<E> readEntitiesFromStore() {
        LOGGER.info("reading entity storage with file:" + path);
        List<E> readedEntities = null;
        File storageFile = new File(path);
        Boolean fileExsists = false;
        try {
            if (!storageFile.exists()) {
                LOGGER.warn("trying to create new file");
                storageFile.createNewFile();
                fileExsists = true;
            } else {
                fileExsists = true;
            }
        } catch (IOException ioex) {
            LOGGER.error("Can't create new entity file: " + path, ioex);
            throw new EntityStoreException("Can't create new entity file: " + path, ioex);
        }
        BufferedReader reader = null;
        if (fileExsists) {
            try {
                reader = new BufferedReader(new FileReader(storageFile));
                EntityStreamReader<E> entityReader = new EntityStreamReaderImpl<>(entityClass, entityDefinition);
                readedEntities = entityReader.readEntities(reader);
            } catch (FileNotFoundException fex) {
                LOGGER.error("File not found: " + path, fex);
                throw new EntityStoreException("File not found: " + path, fex);
            } catch (EntityProcessingException pex) {
                LOGGER.error("Error during processing of entities", pex);
                throw new EntityStoreException("Error during processing of entities", pex);
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException ioex) {
                        LOGGER.error("unable to close entity reader", ioex);
                    }
                }
            }
        }
        return readedEntities == null ? new ArrayList<E>() : readedEntities;
    }
    
    /**
     * Append entity record to end of storage resource. Should be called inside sync block.
     * @param entity new created entity;
     */
    protected void appendEntityToStore(E entity) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path, true);
            StringBuffer buffer = new StringBuffer();
            EntityWriterImpl entityWriter = new EntityWriterImpl(entityDefinition, buffer);
            entityWriter.reset();
            entity.writeToECSV(entityWriter);
            writer.write(buffer.toString());
            writer.append(ECSVFormat.LINE_BREAK_CHAR);
            writer.flush();
            writer.close();
        } catch (IOException ioex) {
            LOGGER.error("Storage IOException at appending: " + path, ioex);
            throw new EntityStoreException("Storage IOException at appending: " + path, ioex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ioex) {
                    LOGGER.error("unable to close entity reader", ioex);
                }
            }
        }
    }
    
    /**
     * Save entire storage to resource. Should be called inside sync block.
     */
    protected void saveEntitiesToStore() {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            EntityStreamWriter<E> entityStreamWriter = new EntityStreamWriterImpl<>(entityDefinition);
            entityStreamWriter.writeEntities(entitiesStore, writer);
            writer.flush();
            writer.close();
        } catch (IOException ioex) {
            LOGGER.error("Storage IOException at saving: " + path, ioex);
            throw new EntityStoreException("Storage IOException at saving: " + path, ioex);
        } catch (EntityProcessingException pex) {
            LOGGER.debug("Error during processing of entities", pex);
            throw new EntityStoreException("Error during processing of entities", pex);
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ioex) {
                    LOGGER.error("unable to close entity reader", ioex);
                }
            }
        }
    }

    @Override
    public void create(E entity) {
        if (entity.getKey() == null) {
            entity.setKey(getNewKey());
            if (entity.getKey() == null) {
                throw new EntityStoreException("Can't store entity without key: " + entity.toString() , null);
            }
        }
        synchronized(entitiesLock) {
            entitiesStore.add(entity);
            appendEntityToStore(entity);
        }
    }

    @Override
    public void save(E entity) {
        E findedEntity = get(entity.getKey());
        if (findedEntity != null) {
            //Don't call update() if changes occurred in the same entity instance.
            if (findedEntity != entity) {
                findedEntity.update(entity);
            }
            saveEntitiesToStore();
        } else {
            create(entity);
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
