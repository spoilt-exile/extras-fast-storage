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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Field;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.reading.EntityStreamReader;
import tk.freaxsoftware.extras.faststorage.reading.EntityStreamReaderImpl;
import tk.freaxsoftware.extras.faststorage.storage.EntityHandler;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;

/**
 * Main library ignition class for easy handler registration.
 * @author Stanislav Nepochatov
 */
public class FastStorageIgnition {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(FastStorageIgnition.class);
    
    /**
     * Reads handler's info from stream and init them with registration.
     * @param descriptorStream stream of resource with ECSV list of handler enties;
     * @throws EntityProcessingException 
     */
    public static void ignite(InputStream descriptorStream) throws EntityProcessingException {
        LOGGER.info("starts ignition...");
        Reader reader = new InputStreamReader(descriptorStream);
        EntityStreamReader<HandlerEntry> entityReader = new EntityStreamReaderImpl<>(HandlerEntry.class, HandlerEntry.DEFINITION);
        List<HandlerEntry> handlerEntries = entityReader.readEntities(reader);
        for (HandlerEntry entry: handlerEntries) {
            try {
                igniteEntry(entry);
            } catch (Exception ex) {
                LOGGER.error("unable to ignite entry: " + entry, ex);
            }
        }
    }
    
    /**
     * Ignite single entry.
     * @param entry handler major info holder;
     */
    private static void igniteEntry(HandlerEntry entry) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        Class entityClass = Class.forName(entry.getEntityClassname());
        Class handlerClass = Class.forName(entry.getHandlerClassname());
        LOGGER.info("process ignition on entry for " + entityClass.getCanonicalName() + " with handler: " + handlerClass.getCanonicalName());
        EntityHandler handlerInstance = null;
        ECSVDefinition definition = extractDefinition(entityClass);
        try {
            handlerInstance = (EntityHandler) handlerClass.getDeclaredConstructor(Class.class, ECSVDefinition.class, String.class)
                    .newInstance(entityClass, definition, entry.getStorageFilepath());
        } catch (Exception ex) {
        }
        if (handlerInstance == null) {
            try {
                handlerInstance = (EntityHandler) handlerClass.getDeclaredConstructor(String.class)
                        .newInstance(entry.getStorageFilepath());
            } catch (Exception ex) {
            }
        }
        if (handlerInstance == null) {
            handlerInstance = (EntityHandler) handlerClass.newInstance();
        }
        Handlers.registerHandler(entry.getType(), entityClass, handlerInstance);
    }
    
    /**
     * Get entity definition from class, by using static field or else.
     * @param entityClass class of entity;
     * @return entity definition objest;
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InstantiationException 
     */
    private static ECSVDefinition extractDefinition(Class entityClass) throws IllegalArgumentException, IllegalAccessException, InstantiationException {
        Field definitionField = null;
        try {
            definitionField = entityClass.getDeclaredField("DEFINITION");
        } catch (Exception ex) {
            LOGGER.error("unable to extract entity definition by static DEFINITION field", ex);
        }
        if (definitionField == null) {
            ECSVAble entity = (ECSVAble) entityClass.newInstance();
            return entity.getDefinition();
        } else {
            return (ECSVDefinition) definitionField.get(null);
        }
    }
}
