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

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.extras.faststorage.storage.EntityHandler;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;
import tk.freaxsoftware.extras.faststorage.utils.ThreadPoolUtil;

/**
 * Entity reference class. Used for lazy reference aquire.
 * @author Stanislav Nepochatov
 * @param <R> referenced entity generic type;
 * @param <K> referenced entity key generic type;
 */
public class EntityReference<R extends ECSVAble<K>, K> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityReference.class);
    
    /**
     * Referenced entity key.
     */
    private final K key;
    
    /**
     * Referenced entity class.
     */
    private final Class<R> entityClass;
    
    /**
     * Hanlder responsible for referenced entity.
     */
    private EntityHandler<R, K> handler;
    
    /**
     * Constructor.
     * @param givenKey key of entity;
     * @param givenHandler handler for entity;
     */
    public EntityReference(K givenKey, Class<R> givenClass) {
        key = givenKey;
        entityClass = givenClass;
    }
    
    /**
     * Gets key of reference.
     * @return key instance;
     */
    public K getKey() {
        return key;
    }
    
    /**
     * Gets entity from reference. Method may block execution for a while.
     * @return entity from reference;
     */
    public R getEntity() {
        Callable<R> callable = new Callable<R>() {

            @Override
            public R call() throws Exception {
                return getHandler().get(key);
            }
        };
        try {
            R entity = ThreadPoolUtil.getExecutor().submit(callable).get();
            return entity;
        } catch (InterruptedException | ExecutionException ex) {
            LOGGER.error("can't get entity thorough reference", ex);
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
