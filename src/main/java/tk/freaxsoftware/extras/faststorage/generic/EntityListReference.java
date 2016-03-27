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
package tk.freaxsoftware.extras.faststorage.generic;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.extras.faststorage.storage.EntityHandler;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;
import tk.freaxsoftware.extras.faststorage.utils.ThreadPoolUtil;

/**
 * Entity list reference class. Used for lazy reference aquire.
 * @author Stanislav Nepochatov
 * @param <R> referenced entity generic type;
 * @param <K> referenced entity key generic type;
 */
public class EntityListReference<R extends ECSVAble<K>, K> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityListReference.class);
    
    /**
     * Referenced entity key.
     */
    private final List<K> keys;
    
    /**
     * Entity class.
     */
    private final Class<R> entityClass;
    
    /**
     * Enable internal cache.
     */
    private final Boolean enableCache;
    
    /**
     * Hanlder responsible for referenced entity.
     */
    private EntityHandler<R, K> handler;
    
    /**
     * Referenced entities cache.
     */
    private List<R> entities;
    
    /**
     * Default constructor. Should be used when status of handler during entity construction unknown.
     * @param givenKeys list of keys;
     * @param givenClass class of referenced entity;
     * @param cacheEnabled enable internal reference handler caching;
     */
    public EntityListReference(List<K> givenKeys, Class<R> givenClass, Boolean cacheEnabled) {
        this.keys = givenKeys;
        this.entityClass = givenClass;
        this.enableCache = cacheEnabled;
    }
    
    /**
     * Gets keys of reference.
     * @return list of keys instance;
     */
    public List<K> getKeys() {
        return keys;
    }
    
    /**
     * Clean cached state of reference handler.
     */
    public void cleanCache() {
        entities = null;
    }
    
    /**
     * Gets list of enteties from reference. Method may block execution for a while.
     * @return list of enteties from reference;
     */
    public List<R> getEntities() {
        if (enableCache && entities != null) {
            return entities;
        }
        Callable<List<R>> callable = new Callable<List<R>>() {

            @Override
            public List<R> call() throws Exception {
                return getHandler().get(keys);
            }
        };
        try {
            List<R> result = ThreadPoolUtil.getExecutor().submit(callable).get();
            if (enableCache) {
                entities = result;
            }
            return result;
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error("can't get entities list thorough reference", ex);
        }
        return null;
    }
    
    /**
     * Gets handler in safe way.
     * @return entity handler;
     */
    private EntityHandler<R, K> getHandler() {
        if (handler == null) {
            handler = Handlers.getHandlerByClass(entityClass);
        }
        return handler;
    }
}
