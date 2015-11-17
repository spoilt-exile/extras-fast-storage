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
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;

/**
 * Parser main interface.
 * @author Stanislav Nepochatov
 * @param <E> all ECSVAble successors;
 * @deprecated replaced by ECSVStorage
 * @see tk.freaxsoftware.extras.faststorage.storage.ECSVStorage
 */
public interface Parser<E extends ECSVAble> {
    
    /**
     * Read entity from specified single line.
     * @param rawEntity string representation of entity;
     * @return inited entity;
     * @throws org.devnote.libecsvparser.parsing.ParseException if parse failed;
     */
    public E readEntity(String rawEntity, Boolean extractType) throws InstantiationException, IllegalAccessException, ParseException;
    
    /**
     * Read entities from multiline string.
     * @param rawEntities string with entities separated by UNIX line break;
     * @return parsed entities;
     * @throws org.devnote.libecsvparser.parsing.ParseException if parse failed;
     */
    public List<E> readEntities(String rawEntities, Boolean extractType) throws InstantiationException, IllegalAccessException, ParseException;
    
    /**
     * Read entities from multiline string.
     * @param rawEntities list with entities strings;
     * @return parsed entities;
     * @throws org.devnote.libecsvparser.parsing.ParseException if parse failed;
     */
    public List<E> readEntities(List<String> rawEntities, Boolean extractType) throws InstantiationException, IllegalAccessException, ParseException;
    
    /**
     * Read entities from multiline string.
     * @param entityReader reader to read entities;
     * @return parsed entities;
     * @throws org.devnote.libecsvparser.parsing.ParseException if parse failed;
     */
    public List<E> readEntities(Reader entityReader, Boolean extractType) throws InstantiationException, IllegalAccessException, ParseException;
}
