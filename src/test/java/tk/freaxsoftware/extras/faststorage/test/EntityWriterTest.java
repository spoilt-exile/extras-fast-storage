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
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;
import tk.freaxsoftware.extras.faststorage.storage.Handlers;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriterImpl;
import tk.freaxsoftware.extras.faststorage.writing.EntityStreamWriter;
import tk.freaxsoftware.extras.faststorage.writing.EntityStreamWriterImpl;

/**
 * Entity writing test.
 * @author Stanislav Nepochatov
 */
public class EntityWriterTest {
    
    @Test
    public void packTest() throws IOException {
        EntityStreamWriter<ExampleDirectory> packer = new EntityStreamWriterImpl();
        
        List<ExampleDirectory> dirList = new ArrayList<>();
        dirList.add(makeExampleDirectory(0, "Root", null, "Root directory", new String[] {"Tom", "Joe"}));
        dirList.add(makeExampleDirectory(1, "Private", "Root", "My private files", new String[] {"Tom", "Joe", "Micky"}));
        dirList.add(makeExampleDirectory(2, "Images", "Private", "My photos and etc.", new String[] {"Jackob"}));
        dirList.add(makeExampleDirectory(3, "Video", "Private", "My movies.", new String[] {"Admin"}));
        dirList.add(makeExampleDirectory(4, "Src", "Private", "Source files of some evil program!", new String[] {"Tom", "Joe"}));
        
        FileWriter writer = new FileWriter("test-pack.ecsv");
        
        StringBuffer entityBuffer = new StringBuffer();
        for (ExampleDirectory dir: dirList) {
            EntityWriter entityWriter = new EntityWriterImpl(dir.getDefinition(), entityBuffer);
            dir.writeToECSV(entityWriter);
            entityBuffer.append(ECSVFormat.LINE_BREAK_CHAR);
        }
        writer.write(entityBuffer.toString());
        writer.close();
    }
    
    private ExampleDirectory makeExampleDirectory(Integer id, String name, String parent, String desc, String[] permNames) {
        ExampleDirectory dir1 = new ExampleDirectory();
        dir1.setId(id);
        dir1.setName(name);
        dir1.setParentName(parent);
        dir1.setDescription(desc);

        List<ExamplePermission> permissions = new ArrayList<>(permNames.length);
        for (String permName: permNames) {
            permissions.add(new ExamplePermission(permName, Boolean.TRUE, Boolean.TRUE, Boolean.FALSE, 24));
        }
        dir1.setPermissions(permissions);
        
        return dir1;
    }
    
    @Before
    public void initHandler() {
        Handlers.registerHandler(TYPE, ExamplePermission.class, new ExamplePermissionHandler());
    }
}
