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
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.EntityListReference;
import tk.freaxsoftware.extras.faststorage.generic.EntityReference;
import tk.freaxsoftware.extras.faststorage.reading.EntityReader;
import tk.freaxsoftware.extras.faststorage.reading.EntityReaderImpl;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriterImpl;

/**
 * Abstract entity handler implementation.
 * @author Stanislav Nepochtov
 * @param <E> entity generic type;
 * @param <K> entity key generic type;
 */
public abstract class AbstractEntityHandler<E extends ECSVAble<K>, K> extends AbstractEntityStorage<E, K> implements EntityHandler<E, K> {

    public AbstractEntityHandler(Class<E> entityClass, ECSVDefinition entityDefinition, String filePath) {
        super(entityClass, entityDefinition, filePath);
    }

    @Override
    public String writeToString(E entity) {
        StringBuffer buffer = new StringBuffer();
        EntityWriter<K> writer = new EntityWriterImpl<>(entityDefinition, buffer);
        entity.writeToECSV(writer);
        return buffer.toString();
    }

    @Override
    public E readFromString(String rawString) {
        E entity = getNewEntity();
        EntityReader<K> reader = new EntityReaderImpl<>(entityDefinition, rawString);
        entity.readFromECSV(reader);
        return entity;
    }

    @Override
    public EntityListReference<E, K> getListReference(List<K> keys) {
        return new EntityListReference<>(keys, this);
    }

    @Override
    public EntityReference<E, K> getReference(K key) {
        return new EntityReference<>(key, this);
    }

    @Override
    public Boolean isReady() {
        return true; //TODO: implement ready check
    }
    
}
