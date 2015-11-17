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
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.writing.Packer;
import tk.freaxsoftware.extras.faststorage.writing.StandardPacker;
import org.junit.Test;

/**
 * Packing test run.
 * @author Stanislav Nepochatov
 */
public class StandardPackerTest {
    
//    @Test
//    public void packTest() throws IOException {
//        Packer<ExampleDirectory> packer = new StandardPacker();
//        
//        List<ExampleDirectory> dirList = new ArrayList<>();
//        dirList.add(makeExampleDirectory("Root", null, "Root directory", new String[] {"hide", "red"}, new String[] {"Tom", "Joe"}));
//        dirList.add(makeExampleDirectory("Private", "Root", "My private files", new String[] {"private", "blue"}, new String[] {"Tom", "Joe", "Micky"}));
//        dirList.add(makeExampleDirectory("Images", "Private", "My photos and etc.", new String[] {"private", "green"}, new String[] {"Jackob"}));
//        dirList.add(makeExampleDirectory("Video", "Private", "My movies.", new String[] {"shared", "red"}, new String[] {"Admin"}));
//        dirList.add(makeExampleDirectory("Src", "Private", "Source files of some evil program!", new String[] {"hide", "black"}, new String[] {"Tom", "Joe"}));
//        
//        FileWriter writer = new FileWriter("test-pack.ecsv");
//        
//        System.out.println(packer.packEntities(dirList, true));
//        packer.packEntities(dirList, true, writer);
//        writer.close();
//    }
//    
//    private ExampleDirectory makeExampleDirectory(String name, String parent, String desc, String[] marks, String[] permNames) {
//        ExampleDirectory dir1 = new ExampleDirectory();
//        dir1.setName(name);
//        dir1.setParentName(parent);
//        dir1.setDescription(desc);
//        dir1.setMarks(marks);
//        List<ExampleDirectory.ExamplePermission> perms1 = new ArrayList<>();
//        for (String permName: permNames) {
//            ExampleDirectory.ExamplePermission perm1 = new ExampleDirectory.ExamplePermission();
//            perm1.setUserName(permName);
//            perm1.setMayAcess(false);
//            perms1.add(perm1);
//        }
//        dir1.setPermissions(perms1);
//        
//        return dir1;
//    }
    
}
