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
package tk.freaxsoftware.extras.faststorage.writing;

import java.io.Writer;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;

/**
 * Entity stream writer interface. For writing list of entities in string or writer.
 * @author Stanislav Nepochatov
 * @param <E> all ECSVAble successors;
 */
public interface EntityStreamWriter<E extends ECSVAble> {
    
    /**
     * Write entities into multiline string.
     * @param entities entities to write;
     * @return multiline string with entities;
     * @throws tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException
     */
    public String writeEntities(List<E> entities) throws EntityProcessingException;
    
    /**
     * Write entities to writer.
     * @param entities entities to write;
     * @param entityWriter writer for result writing;
     * @throws tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException
     */
    public void writeEntities(List<E> entities, Writer entityWriter) throws EntityProcessingException;
    
}
