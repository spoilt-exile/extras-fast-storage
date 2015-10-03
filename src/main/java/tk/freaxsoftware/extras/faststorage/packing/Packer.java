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
package tk.freaxsoftware.extras.faststorage.packing;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;

/**
 * Entity packer interface.
 * @author Stanislav Nepochatov
 * @param <E> all ECSVAble successors;
 */
public interface Packer<E extends ECSVAble> {
    
    /**
     * Pack list of entities.
     * @param entities entities to pack;
     * @param appendTypes append type of entity to data;
     * @return multiline string with entities;
     */
    public String packEntities(List<E> entities, Boolean appendTypes);
    
    /**
     * Pack list of entities.
     * @param entities entities to pack;
     * @param appendTypes append type of entity to data;
     * @param entityWriter writer for result writing;
     */
    public void packEntities(List<E> entities, Boolean appendTypes, Writer entityWriter) throws IOException;
    
}
