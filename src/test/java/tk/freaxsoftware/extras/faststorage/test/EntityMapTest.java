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

import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectoryHandler;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermission;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermissionHandler;
import tk.freaxsoftware.extras.faststorage.mapped.DefaultEntityMap;
import tk.freaxsoftware.extras.faststorage.mapped.EntityMap;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;

/**
 * Entity map general test.
 * @author Stanislav Nepochatov
 */
public class EntityMapTest {
    
    private static final String MAP_RAW = "RootDir:DIR:0,Root,null,{Root directory},{Tom,true,true,false,24$Joe,true,true,false,24}\n" +
        "ChildrenDirs:DIR:1,Private,Root,{My private files},{Tom,true,true,false,24$Joe,true,true,false,24$Micky,true,true,false,24}\n" +
        "ChildrenDirs:DIR:2,Images,Private,{My photos and etc.},{Jackob,true,true,false,24}\n" +
        "ChildrenDirs:DIR:3,Video,Private,{My movies.},{Admin,true,true,false,24}\n" +
        "ChildrenDirs:DIR:4,Src,Private,{Source files of some evil program!},{Tom,true,true,false,24$Joe,true,true,false,24}\n";
    
    public EntityMapTest() {
    }
    
    @Test
    public void testEntityMap() {
        EntityMap map = new DefaultEntityMap();
        map.readFromEcsv(MAP_RAW);
        
        Assert.assertFalse(map.getKeys().isEmpty());
        
        Assert.assertTrue(map.containsKey("RootDir"));
        Assert.assertFalse(map.isList("RootDir"));
        ExampleDirectory rootDirectory = map.get("RootDir", ExampleDirectory.class);
        Assert.assertNotNull(rootDirectory);
        
        Assert.assertTrue(map.containsKey("ChildrenDirs"));
        Assert.assertTrue(map.isList("ChildrenDirs"));
        
        List<?> childDirectoriesWrong = map.getList("ChildrenDirs", ExamplePermission.class);
        Assert.assertTrue(childDirectoriesWrong.isEmpty());
        
        List<ExampleDirectory> childDirectories = map.getList("ChildrenDirs", ExampleDirectory.class);
        Assert.assertEquals(4, childDirectories.size());
        
        String rawMap = map.writeToEcsv();
        Assert.assertEquals(MAP_RAW, rawMap);
        
        ExamplePermission permission = new ExamplePermission("User1", true, false, false, 102);
        map.add("Permissions", permission);
        
        ExamplePermission mapPermission = map.get("Permissions", ExamplePermission.class);
        Assert.assertEquals(mapPermission, permission);
        
        map.remove("Permissions");
        
        EntityMap map2 = new DefaultEntityMap();
        map2.readFromEcsv(rawMap);
        
        Assert.assertEquals(map2, map);
    }
    
    @Before
    public void setUp() {
        Handlers.registerHandler(ExampleDirectory.TYPE, ExampleDirectory.class, new ExampleDirectoryHandler());
        Handlers.registerHandler(ExamplePermission.TYPE, ExamplePermission.class, new ExamplePermissionHandler());
    }
    
}
