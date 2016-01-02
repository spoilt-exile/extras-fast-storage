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

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermission;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermissionHandler;
import static tk.freaxsoftware.extras.faststorage.example.ExamplePermissionHandler.TYPE;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;
import tk.freaxsoftware.extras.faststorage.writing.EntityStreamWriter;
import tk.freaxsoftware.extras.faststorage.writing.EntityStreamWriterImpl;

/**
 * Entity writing test.
 * @author Stanislav Nepochatov
 */
public class EntityWriterTest {
    
    @Test
    public void packTest() throws IOException, EntityProcessingException {
        EntityStreamWriter<ExampleDirectory> entityWriter = new EntityStreamWriterImpl(new ExampleDirectory().getDefinition());
        
        List<ExampleDirectory> dirList = new ArrayList<>();
        dirList.add(ExampleDirectoryTestFactory.makeExampleDirectory(0, "Root", null, "Root directory", new String[] {"Tom", "Joe"}));
        dirList.add(ExampleDirectoryTestFactory.makeExampleDirectory(1, "Private", "Root", "My private files", new String[] {"Tom", "Joe", "Micky"}));
        dirList.add(ExampleDirectoryTestFactory.makeExampleDirectory(2, "Images", "Private", "My photos and etc.", new String[] {"Jackob"}));
        dirList.add(ExampleDirectoryTestFactory.makeExampleDirectory(3, "Video", "Private", "My movies.", new String[] {"Admin"}));
        dirList.add(ExampleDirectoryTestFactory.makeExampleDirectory(4, "Src", "Private", "Source files of some evil program!", new String[] {"Tom", "Joe"}));
        
        FileWriter writer = new FileWriter("test-pack.ecsv");
        entityWriter.writeEntities(dirList, writer);
        writer.flush();
        writer.close();
    }
    

    
    @Before
    public void initHandler() {
        Handlers.registerHandler(TYPE, ExamplePermission.class, new ExamplePermissionHandler());
    }
}
