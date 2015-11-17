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
package tk.freaxsoftware.extras.faststorage.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Global thread pool util class for whole parser.
 * @author Stanislav Nepochatov
 */
public class ThreadPoolUtil {
    
    /**
     * Executor service instance.
     */
    private static final ExecutorService executor = Executors.newFixedThreadPool(4);
    
    /**
     * Gets thread pool.
     * @return thread pool executor;
     */
    public static ExecutorService getExecutor() {
        return executor;
    }
    
}
