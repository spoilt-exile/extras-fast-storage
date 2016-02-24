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
package tk.freaxsoftware.extras.faststorage.example;

import java.util.List;
import tk.freaxsoftware.extras.faststorage.storage.AbstractEntityHandler;

/**
 * Storage implementation example.
 * @author Stanislav Nepochatov
 */
public class ExampleDirectoryHandler extends AbstractEntityHandler<ExampleDirectory, Integer> {
    
    public ExampleDirectoryHandler() {
        this(null);
    }

    public ExampleDirectoryHandler(String filePath) {
        super(ExampleDirectory.class, ExampleDirectory.DEFINITION, filePath);
    }

    @Override
    public Integer getNewKey() {
        return entitiesStore.size() + 1;
    }

    @Override
    public List<ExampleDirectory> find(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExampleDirectory getNewEntity() {
        return new ExampleDirectory();
    }

    @Override
    public String getType() {
        return ExampleDirectory.TYPE;
    }
    
}
