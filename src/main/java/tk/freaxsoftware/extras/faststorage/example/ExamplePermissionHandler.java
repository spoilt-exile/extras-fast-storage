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

import java.io.StringWriter;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.reading.EntityReaderImpl;
import tk.freaxsoftware.extras.faststorage.storage.EntityHandler;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriterImpl;

/**
 * Handler for permissions.
 * @author Stanislav Nepochatov
 */
public class ExamplePermissionHandler implements EntityHandler<ExamplePermission, Object> {
    
    public static final String TYPE = "PERM";
    
    private final ExamplePermission reference = new ExamplePermission();

    @Override
    public String getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExamplePermission readFromString(String rawString) {
        EntityReaderImpl<Object> reader = new EntityReaderImpl<>(reference.getDefinition());
        reader.parseInit(rawString);
        ExamplePermission newEntity = new ExamplePermission();
        newEntity.readFromECSV(reader);
        return newEntity;
    }

    @Override
    public String writeToString(ExamplePermission entity) {
        StringWriter buffer = new StringWriter();
        EntityWriterImpl<Object> writer = new EntityWriterImpl<>(reference.getDefinition(), buffer);
        writer.reset();
        entity.writeToECSV(writer);
        return buffer.toString();
    }

    @Override
    public void create(ExamplePermission entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void save(ExamplePermission entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExamplePermission get(Object key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ExamplePermission> get(List<Object> keys) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ExamplePermission> getAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ExamplePermission> find(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(ExamplePermission entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ExamplePermission getNewEntity() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getNewKey() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void onStorageCreation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
