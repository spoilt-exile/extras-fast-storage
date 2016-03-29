package tk.freaxsoftware.extras.faststorage.test;

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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermission;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.generic.EntityReference;
import tk.freaxsoftware.extras.faststorage.ignition.FastStorageIgnition;
import tk.freaxsoftware.extras.faststorage.storage.EntityHandler;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;

/**
 * Handlers ignition test.
 * @author Stanislav Nepochatov
 */
public class IgnitionTest {
    
    private static String perm1 = "Jackob,true,true,false,1112";
    
    public IgnitionTest() {
    }
    
    @Before
    public void setUp() throws FileNotFoundException, EntityProcessingException {
        FileInputStream stream = new FileInputStream("faststorage.ign");
        FastStorageIgnition.ignite(stream);
    }
    
    @Test
    public void igniteDirTest() {
        EntityHandler<ExampleDirectory, Integer> directoryHandler = Handlers.getHandlerByClass(ExampleDirectory.class);
        assertNotNull(directoryHandler);
        EntityReference<ExampleDirectory, Integer> directoryRef2 = new EntityReference<>(2, ExampleDirectory.class);
        ExampleDirectory directory2 = directoryRef2.getEntity();
        assertNotNull(directory2);
    }
    
    @Test
    public void ignitionPermTest() {
        EntityHandler<ExamplePermission, Object> permissionHandler = Handlers.getHandlerByClass(ExamplePermission.class);
        assertNotNull(permissionHandler);
        ExamplePermission permission1 = permissionHandler.readFromString(perm1);
        assertNotNull(permission1);
        assertEquals("Jackob", permission1.getUserName());
        assertEquals(true, permission1.isCanRead());
        assertEquals(true, permission1.isCanWrite());
        assertEquals(false, permission1.isCanDelete());
        assertEquals(Integer.valueOf(1112), permission1.getMode());
    }
    
    @After
    public void teardown() {
        Handlers.clearHandlers();
    }
    
}
