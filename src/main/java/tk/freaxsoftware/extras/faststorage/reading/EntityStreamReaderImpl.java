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

package tk.freaxsoftware.extras.faststorage.reading;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.BufferedReader;
import java.io.IOException;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.exception.EntityStateException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;

/**
 * Entity stream reader implementation.
 * @author Stanislav Nepochatov
 * @param <E> all ECSVAble successors;
 */
public class EntityStreamReaderImpl<E extends ECSVAble, K> implements EntityStreamReader<E> {
    
    /**
     * Entity class.
     */
    private Class<E> entityClass;
    
    /**
     * Entity definition to perfrom reading.
     */
    private ECSVDefinition entityDefinition;
    
    /**
     * Default constructor.
     * @param definition supplyed definition;
     */
    public EntityStreamReaderImpl(Class<E> eClass, ECSVDefinition definition) {
        this.entityClass = eClass;
        this.entityDefinition = definition;
    }

    @Override
    public List<E> readEntities(String rawEntities) throws EntityProcessingException {
        String[] entityLines = rawEntities.split(ECSVFormat.LINE_BREAK);
        return readEntities(Arrays.asList(entityLines));
    }

    @Override
    public List<E> readEntities(List<String> rawEntities) throws EntityProcessingException {
        try {
            return readEntetiesInternal(rawEntities);
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new EntityProcessingException("Error during creation of entity instance, please check public availability of constructor.", ex);
        } catch (EntityStateException esex) {
            throw new EntityProcessingException("Unable to read entity", esex);
        }
    }

    @Override
    public List<E> readEntities(Reader entityReader) throws EntityProcessingException {
        BufferedReader reader = null;
        if (!(entityReader instanceof BufferedReader)) {
            reader = new BufferedReader(entityReader);
        } else {
            reader = (BufferedReader) entityReader;
        }
        List<String> strings = new ArrayList<>();
        
        try {
            while (reader.ready()) {
                strings.add(reader.readLine());
            }
        } catch (IOException ex) {
            throw new EntityProcessingException("IOException raised during reading of stream", ex);
        }

        return readEntities(strings);
    }
    
    /**
     * Internal implementation of entity reading.
     * @param rawEntities list of raw entity strings;
     * @return list with entities;
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    private List<E> readEntetiesInternal(List<String> rawEntities) throws InstantiationException, IllegalAccessException {
        List<E> enteties = new ArrayList<>(rawEntities.size());
        EntityReaderImpl<K> reader = new EntityReaderImpl<>(entityDefinition);
        for (String rawEntity: rawEntities) {
            reader.parseInit(rawEntity);
            E newEntity = getNewEntity();
            newEntity.readFromECSV(reader);
            enteties.add(newEntity);
        }
        return enteties;
    }

    /**
     * Get new instance of entity.
     * @return new entity;
     * @throws InstantiationException
     * @throws IllegalAccessException 
     */
    protected E getNewEntity() throws InstantiationException, IllegalAccessException {
        return entityClass.newInstance();
    }
}
