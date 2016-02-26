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

import java.io.IOException;
import java.io.Writer;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.extras.faststorage.exception.EntityStoreException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;
import tk.freaxsoftware.extras.faststorage.reading.ECSVParser;
import tk.freaxsoftware.extras.faststorage.storage.EntityHandler;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;

/**
 * Mapped entry default implementation.
 * @author Stanislav Nepochatov
 */
public class DefaultEntityMapEntry implements EntityMapEntry {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultEntityMapEntry.class);
    
    /**
     * Key of entry.
     */
    private String key;
    
    /**
     * Type of entity inside. Refer to global type of entity.
     */
    private String type;
    
    /**
     * Raw form of entity.
     */
    private String rawEntity;
    
    /**
     * Parsed entity, decasted to ECSVAble instance.
     */
    private ECSVAble entity;
    
    /**
     * Empty constructor.
     */
    public DefaultEntityMapEntry() {}
    
    /**
     * Default constructor.
     * @param csv ECSV string of entry;
     */
    public DefaultEntityMapEntry(String csv) {
        readFromEcsv(csv);
    }

    /**
     * Entity based constructor.
     * @param key entry key;
     * @param entity entity to save;
     */
    public DefaultEntityMapEntry(String key, ECSVAble entity) {
        this.key = key;
        this.type = entity.getEntityType();
        this.entity = entity;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public Class getEntityType() {
        return entity != null ? entity.getClass() : null;
    }

    @Override
    public String getRawEntity() {
        return rawEntity;
    }

    @Override
    public ECSVAble getEntity() {
        return entity;
    }

    @Override
    public Boolean isParsed() {
        return entity != null;
    }

    @Override
    public <V extends ECSVAble> V getEntity(Class<V> valueClass) {
        if (entity.getClass().isAssignableFrom(valueClass)) {
            return (V) entity;
        }
        return null;
    }
    
    @Override
    public void update(ECSVAble updatedEntity) {
        this.entity.update(updatedEntity);
    }

    @Override
    public void readFromEcsv(String entryString) {
        String[] firstParse = ECSVParser.parseKeyValue(entryString);
        key = firstParse[0];
        String[] restParse = ECSVParser.parseKeyValue(firstParse[1]);
        type = restParse[0];
        rawEntity = restParse[1];
        EntityHandler handler = Handlers.getHandlerByType(type);
        if (handler != null) {
            entity = handler.readFromString(restParse[1]);
        }
    }

    @Override
    public void writeToEcsv(Writer entryWriter) {
        try {
            entryWriter.write(key);
            entryWriter.write(ECSVFormat.KEY_VALUE_SEPARATOR_CHAR);
            entryWriter.write(type);
            entryWriter.write(ECSVFormat.KEY_VALUE_SEPARATOR_CHAR);
            if (isParsed()) {
                EntityHandler handler = Handlers.getHandlerByType(type);
                entryWriter.write(handler.writeToString(entity));
            } else {
                entryWriter.write(rawEntity);
            }
            entryWriter.write(ECSVFormat.LINE_BREAK_CHAR);
        } catch (IOException ioex) {
            LOGGER.error("unable to save mapped entry", ioex);
            throw new EntityStoreException("unable to save mapped entry", ioex);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.key);
        hash = 79 * hash + Objects.hashCode(this.type);
        hash = 79 * hash + Objects.hashCode(this.rawEntity);
        hash = 79 * hash + Objects.hashCode(this.entity);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultEntityMapEntry other = (DefaultEntityMapEntry) obj;
        if (!Objects.equals(this.key, other.key)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.rawEntity, other.rawEntity)) {
            return false;
        }
        if (!Objects.equals(this.entity, other.entity)) {
            return false;
        }
        return true;
    }
}
