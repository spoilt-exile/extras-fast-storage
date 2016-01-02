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

import java.io.File;
import java.util.Collections;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectoryHandler;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermission;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermissionHandler;
import static tk.freaxsoftware.extras.faststorage.example.ExamplePermissionHandler.TYPE;
import tk.freaxsoftware.extras.faststorage.generic.EntityReference;
import tk.freaxsoftware.extras.faststorage.storage.EntityHandler;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;

/**
 * Test storage mechenism.
 * @author Stanislav Nepochatov
 */
public class EntityHandlerTest {
    
    private static final String STORAGE_FILE = "directories.ecsv";
    
    private EntityHandler<ExampleDirectory, Integer> handler;
    
    public EntityHandlerTest() {
    }
    
    @Before
    public void setUp() {
        Handlers.registerHandler(TYPE, ExamplePermission.class, new ExamplePermissionHandler());
        handler = new ExampleDirectoryHandler(STORAGE_FILE);
        Handlers.registerHandler(ExampleDirectory.TYPE, ExampleDirectory.class, handler);
        handler.create(ExampleDirectoryTestFactory.makeExampleDirectory(0, "Root", null, "Root directory", new String[] {"Tom", "Joe"}));
        handler.create(ExampleDirectoryTestFactory.makeExampleDirectory(1, "Private", "Root", "My private files", new String[] {"Tom", "Joe", "Micky"}));
        handler.create(ExampleDirectoryTestFactory.makeExampleDirectory(2, "Images", "Private", "My photos and etc.", new String[] {"Jackob"}));
        handler.create(ExampleDirectoryTestFactory.makeExampleDirectory(3, "Video", "Private", "My movies.", new String[] {"Admin"}));
        handler.create(ExampleDirectoryTestFactory.makeExampleDirectory(4, "Src", "Private", "Source files of some evil program!", new String[] {"Tom", "Joe"}));
    }
    
    @Test
    public void storageTest() {
        handler.delete(handler.get(0));
        ExampleDirectory directory1 = handler.get(1);
        directory1.setName("Public");
        directory1.setDescription("Public folder");
        directory1.setPermissions(Collections.EMPTY_LIST);
        handler.update(directory1);
    }
    
    @Test
    public void referenceTest() {
        EntityReference<ExampleDirectory, Integer> testReference = 
                Handlers.getHandlerByClass(ExampleDirectory.class).getReference(1);
        ExampleDirectory directory0 = testReference.getEntity();
        Assert.assertNotNull(directory0);
        Assert.assertEquals(Integer.valueOf(1), directory0.getId());
        System.out.println(directory0.toString());
    }
    
    @After
    public void tearDown() {
        File storageFile = new File(STORAGE_FILE);
        storageFile.delete();
    }
    
}
