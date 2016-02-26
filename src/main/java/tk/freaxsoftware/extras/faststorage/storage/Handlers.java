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

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;

/**
 * All ECSV handler entry point,
 * @author Stanislav Nepochatov
 */
public class Handlers {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(Handlers.class);
    
    /**
     * Record list of handlers in runtime.
     */
    private static final List<Record> records = new ArrayList<>();
    
    /**
     * Register new handler.
     * @param type entity type string id;
     * @param entityClass entity class;
     * @param handler instance of handler;
     */
    public static void registerHandler(String type, Class<? extends ECSVAble> entityClass, EntityHandler handler) {
        LOGGER.info("registering handler for entity: " + entityClass.getCanonicalName() + " handler: " + handler.getClass().getCanonicalName());
        records.add(new Record(type, entityClass, handler));
    }
    
    /**
     * Get handler by class.
     * @param entityClass supplied entity class.
     * @return handler instance or null;
     */
    public static EntityHandler getHandlerByClass(Class entityClass) {
        for (Record record: records) {
            if (record.getEntityClass() == entityClass) {
                return record.getInstance();
            }
        }
        return null;
    }
    
    /**
     * Get handler by type.
     * @param type supplied entity type.
     * @return handler instance or null;
     */
    public static EntityHandler getHandlerByType(String type) {
        for (Record record: records) {
            if (record.getType().equals(type)) {
                return record.getInstance();
            }
        }
        return null;
    }
    
    /**
     * Internal record class.
     */
    private static class Record {
        
        /**
         * String id of entity type.
         */
        private final String type;
        
        /**
         * Entity class instance.
         */
        private final Class<? extends ECSVAble> entityClass;
        
        /**
         * Instance of entity handler class.
         */
        private final EntityHandler instance;

        /**
         * Default constructor.
         * @param type entity type string id;
         * @param entityClass entity class;
         * @param instance instance of handler;
         */
        public Record(String type, Class<? extends ECSVAble> entityClass, EntityHandler instance) {
            this.type = type;
            this.entityClass = entityClass;
            this.instance = instance;
        }

        public String getType() {
            return type;
        }

        public Class<? extends ECSVAble> getEntityClass() {
            return entityClass;
        }

        public EntityHandler getInstance() {
            return instance;
        }
    }
}
