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

import java.util.ArrayList;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.example.ExamplePermission;

/**
 * Test factory for ExampleDirectory
 * @author Stanislav Nepochatov
 */
public class ExampleDirectoryTestFactory {
    
    public static ExampleDirectory makeExampleDirectory(Integer id, String name, String parent, String desc, String[] permNames) {
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
}
