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
package tk.freaxsoftware.extras.faststorage.exception;

/**
 * Entity processing exception class. Thows when reading/writing, entity creation 
 * or general parsing failed.
 * @author Stanislav Nepochatov
 */
public class EntityProcessingException extends Exception {
    
    /**
     * Default constructor.
     * @param message message about error;
     * @param cause cause of error;
     */
    public EntityProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
