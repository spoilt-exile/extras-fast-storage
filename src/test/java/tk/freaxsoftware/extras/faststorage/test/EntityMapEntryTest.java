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

import java.io.StringWriter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectoryHandler;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermission;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermissionHandler;
import tk.freaxsoftware.extras.faststorage.mapped.DefaultEntityMapEntry;
import tk.freaxsoftware.extras.faststorage.mapped.EntityMapEntry;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;

/**
 * Entity map entry test class.
 * @author Stanislav Nepochatov
 */
public class EntityMapEntryTest {
    
    private static final String ENTITY_RAW = "TEST:DIR:0,Root,null,{Root directory},{Tom,true,true,false,24$Joe,true,true,false,24}\n";
    private static final String ENTITY_RAW2 = "TEST:DIR2:0,Root,null,{Root directory},{Tom,true,true,false,24$Joe,true,true,false,24}\n";
    
    public EntityMapEntryTest() {
    }
    
    @Test
    public void testEntry() {
        EntityMapEntry entry = new DefaultEntityMapEntry(ENTITY_RAW);
        Assert.assertTrue(entry.isParsed());
        
        Assert.assertNull(entry.getEntity(ExamplePermission.class));
        Assert.assertNotNull(entry.getEntity(ExampleDirectory.class));
        Assert.assertEquals(ExampleDirectory.class, entry.getEntityType());
        
        StringWriter writer = new StringWriter();
        entry.writeToEcsv(writer);
        Assert.assertEquals(writer.toString(), ENTITY_RAW);
        
        EntityMapEntry entry2 = new DefaultEntityMapEntry(ENTITY_RAW2);
        Assert.assertFalse(entry2.isParsed());
    }
    
    @Before
    public void setUp() {
        Handlers.registerHandler(ExampleDirectory.TYPE, ExampleDirectory.class, new ExampleDirectoryHandler());
        Handlers.registerHandler(ExamplePermission.TYPE, ExamplePermission.class, new ExamplePermissionHandler());
    }
    
    @After
    public void teardown() {
        Handlers.clearHandlers();
    }
}
