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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.parsing.ParseException;
import tk.freaxsoftware.extras.faststorage.parsing.Parser;
import tk.freaxsoftware.extras.faststorage.parsing.StandardParser;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Parsing test run.
 * @author spoilt
 */
public class StandardParserTest {
    
//    @Test
//    public void parseTest() throws FileNotFoundException, InstantiationException, IllegalAccessException, ParseException {
//        FileReader reader = new FileReader("test-parse.ecsv");
//        
//        Parser<ExampleDirectory> parser = new StandardParser(ExampleDirectory.class);
//        
//        List<ExampleDirectory> dirs = parser.readEntities(reader, true);
//        
//        assertTrue(dirs.size() == 5);
//                
//        //First entity check
//        assertNotNull(dirs.get(0).getName());
//        assertNotNull(dirs.get(0).getParentName());
//        assertNotNull(dirs.get(0).getDescription());
//        assertNotNull(dirs.get(0).getMarks());
//        assertNotNull(dirs.get(0).getPermissions());
//        assertFalse(dirs.get(0).getPermissions().isEmpty());
//        
//        //Second entity check
//        assertNotNull(dirs.get(1).getName());
//        assertNotNull(dirs.get(1).getParentName());
//        assertNotNull(dirs.get(1).getDescription());
//        assertNotNull(dirs.get(1).getMarks());
//        assertNotNull(dirs.get(1).getPermissions());
//        assertFalse(dirs.get(1).getPermissions().isEmpty());
//        
//        //Third entity check
//        assertNotNull(dirs.get(2).getName());
//        assertNotNull(dirs.get(2).getParentName());
//        assertNotNull(dirs.get(2).getDescription());
//        assertNotNull(dirs.get(2).getMarks());
//        assertNotNull(dirs.get(2).getPermissions());
//        assertFalse(dirs.get(2).getPermissions().isEmpty());
//        
//        //Fourth entity check
//        assertNotNull(dirs.get(3).getName());
//        assertNotNull(dirs.get(3).getParentName());
//        assertNotNull(dirs.get(3).getDescription());
//        assertNotNull(dirs.get(3).getMarks());
//        assertNotNull(dirs.get(3).getPermissions());
//        assertFalse(dirs.get(3).getPermissions().isEmpty());
//
//        //Fifth entity check
//        assertNotNull(dirs.get(4).getName());
//        assertNotNull(dirs.get(4).getParentName());
//        assertNotNull(dirs.get(4).getDescription());
//        assertNotNull(dirs.get(4).getMarks());
//        assertNotNull(dirs.get(4).getPermissions());
//        assertFalse(dirs.get(4).getPermissions().isEmpty());
//    }
    
}
