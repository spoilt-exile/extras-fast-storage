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

package tk.freaxsoftware.extras.faststorage.reading;

import java.io.Reader;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;

/**
 * Entity stream reader interface. For reading some list of enteties from raw multiline 
 * string, list of strings or reader.
 * @author Stanislav Nepochatov
 * @param <E> all ECSVAble successors;
 * @see tk.freaxsoftware.extras.faststorage.storage.ECSVStorage
 */
public interface EntityStreamReader<E extends ECSVAble> {
    
    /**
     * Read entities from multiline string.
     * @param rawEntities string with entities separated by UNIX line break;
     * @return parsed entities;
     * @throws tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException
     */
    public List<E> readEntities(String rawEntities) throws EntityProcessingException;
    
    /**
     * Read entities from list of strings.
     * @param rawEntities list with entities strings;
     * @return parsed entities;
     * @throws tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException
     */
    public List<E> readEntities(List<String> rawEntities) throws EntityProcessingException;
    
    /**
     * Read entities from reader.
     * @param entityReader reader to read entities;
     * @return parsed entities;
     * @throws tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException
     */
    public List<E> readEntities(Reader entityReader) throws EntityProcessingException;
}
