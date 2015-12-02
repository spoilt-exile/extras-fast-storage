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

package tk.freaxsoftware.extras.faststorage.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermission;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermissionHandler;
import static tk.freaxsoftware.extras.faststorage.example.ExamplePermissionHandler.TYPE;
import tk.freaxsoftware.extras.faststorage.reading.EntityReader;
import tk.freaxsoftware.extras.faststorage.reading.EntityReaderImpl;
import tk.freaxsoftware.extras.faststorage.reading.ParseException;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;

/**
 * Parsing test run.
 * @author Stanislav Nepochatov
 */
public class EntityReaderTest {
    
    @Test
    public void parseTest() throws FileNotFoundException, InstantiationException, IllegalAccessException, ParseException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader("test-parse.ecsv"));
        while (reader.ready()) {
            ExampleDirectory example = new ExampleDirectory();
            EntityReader eReader = new EntityReaderImpl(example.getDefinition(), reader.readLine());
            example.readFromECSV(eReader);
            
            assertNotNull(example.getId());
            assertNotNull(example.getName());
            assertNotNull(example.getParentName());
            assertNotNull(example.getDescription());
            assertNotNull(example.getPermissions());
        }
    }
    
    @Before
    public void initHandler() {
        Handlers.registerHandler(TYPE, ExamplePermission.class, new ExamplePermissionHandler());
    }
}
