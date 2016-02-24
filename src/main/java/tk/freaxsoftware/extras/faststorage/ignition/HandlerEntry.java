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
package tk.freaxsoftware.extras.faststorage.ignition;

import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import static tk.freaxsoftware.extras.faststorage.generic.ECSVFields.PR_STRING;
import tk.freaxsoftware.extras.faststorage.reading.EntityReader;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;

/**
 * Handler entry containce major info about handler to further ignition.
 * @author Stanislav Nepochatov
 */
public class HandlerEntry implements ECSVAble<String> {
    
    public static final String TYPE = "HANDLER";
    
    public static final ECSVDefinition DEFINITION = ECSVDefinition.createNew()
                .addKey(String.class)
                .addPrimitive(PR_STRING)
                .addPrimitive(PR_STRING)
                .addPrimitive(PR_STRING);
    
    /**
     * Type of entity.
     */
    private String type;
    
    /**
     * Entity full class name like:<br/>
     * tk.freaxsoftware.extras.faststorage.example.ExampleDirectory
     */
    private String entityClassname;
    
    /**
     * Full classname of handler implementation for this class.
     */
    private String handlerClassname;
    
    /**
     * Path to storage file.
     */
    private String storageFilepath;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEntityClassname() {
        return entityClassname;
    }

    public void setEntityClassname(String entityClassname) {
        this.entityClassname = entityClassname;
    }

    public String getHandlerClassname() {
        return handlerClassname;
    }

    public void setHandlerClassname(String handlerClassname) {
        this.handlerClassname = handlerClassname;
    }

    public String getStorageFilepath() {
        return storageFilepath;
    }

    public void setStorageFilepath(String storageFilepath) {
        this.storageFilepath = storageFilepath;
    }

    @Override
    public String getKey() {
        return type;
    }

    @Override
    public void setKey(String key) {
        //Do nothing.
    }

    @Override
    public ECSVDefinition getDefinition() {
        return DEFINITION;
    }

    @Override
    public void readFromECSV(EntityReader<String> reader) {
        this.type = reader.readKey();
        this.entityClassname = reader.readString();
        this.handlerClassname = reader.readString();
        this.storageFilepath = reader.readString();
    }

    @Override
    public void writeToECSV(EntityWriter<String> writer) {
        throw new UnsupportedOperationException("Saving not supported for this entity.");
    }

    @Override
    public void update(ECSVAble<String> updatedEntity) {
        throw new UnsupportedOperationException("Update not supported for this entity.");
    }

    @Override
    public String getEntityType() {
        return TYPE;
    }
    
}
