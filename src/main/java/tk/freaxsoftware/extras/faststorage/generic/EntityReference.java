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
import tk.freaxsoftware.extras.faststorage.storage.EntityHandler;
import tk.freaxsoftware.extras.faststorage.utils.ThreadPoolUtil;

/**
 * Entity reference class. Used for lazy reference aquire.
 * @author Stanislav Nepochatov
 * @param <R> referenced entity generic type;
 * @param <K> referenced entity key generic type;
 */
public class EntityReference<R extends ECSVAble<K>, K> {
    
    /**
     * Referenced entity key.
     */
    private final K key;
    
    /**
     * Hanlder responsible for referenced entity.
     */
    private final EntityHandler<R, K> handler;
    
    /**
     * Constructor.
     * @param givenKey key of entity;
     * @param givenHandler handler for entity;
     */
    public EntityReference(K givenKey, EntityHandler<R, K> givenHandler) {
        key = givenKey;
        handler = givenHandler;
    }
    
    /**
     * Gets entity from reference. Method may block execution for a while.
     * @return entity from reference;
     */
    public R getEntity() {
        Callable<R> callable = new Callable<R>() {

            @Override
            public R call() throws Exception {
                return handler.get(key);
            }
        };
        try {
            R entity = ThreadPoolUtil.getExecutor().submit(callable).get();
            return entity;
        } catch (InterruptedException | ExecutionException ex) {
            //Do nothing;
        }
        return null;
    }
}
