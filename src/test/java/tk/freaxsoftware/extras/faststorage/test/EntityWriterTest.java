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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriterImpl;
import tk.freaxsoftware.extras.faststorage.writing.Packer;
import tk.freaxsoftware.extras.faststorage.writing.StandardPacker;

/**
 * Entity writing test.
 * @author Stanislav Nepochatov
 */
public class EntityWriterTest {
    
    @Test
    public void packTest() throws IOException {
        Packer<ExampleDirectory> packer = new StandardPacker();
        
        List<ExampleDirectory> dirList = new ArrayList<>();
        dirList.add(makeExampleDirectory(0, "Root", null, "Root directory", new String[] {"hide", "red"}, new String[] {"Tom", "Joe"}));
        dirList.add(makeExampleDirectory(1, "Private", "Root", "My private files", new String[] {"private", "blue"}, new String[] {"Tom", "Joe", "Micky"}));
        dirList.add(makeExampleDirectory(2, "Images", "Private", "My photos and etc.", new String[] {"private", "green"}, new String[] {"Jackob"}));
        dirList.add(makeExampleDirectory(3, "Video", "Private", "My movies.", new String[] {"shared", "red"}, new String[] {"Admin"}));
        dirList.add(makeExampleDirectory(4, "Src", "Private", "Source files of some evil program!", new String[] {"hide", "black"}, new String[] {"Tom", "Joe"}));
        
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
    
    private ExampleDirectory makeExampleDirectory(Integer id, String name, String parent, String desc, String[] marks, String[] permNames) {
        ExampleDirectory dir1 = new ExampleDirectory();
        dir1.setId(id);
        dir1.setName(name);
        dir1.setParentName(parent);
        dir1.setDescription(desc);
        List<String> marksList = new ArrayList<>(marks.length);
        for (String mark: marks) {
            marksList.add(mark);
        }
        dir1.setMarks(marksList);
        Map<String, Boolean> perms1 = new HashMap<>();
        for (String permName: permNames) {
            perms1.put(permName, Boolean.FALSE);
        }
        dir1.setPermissions(perms1);
        
        return dir1;
    }
    
}
