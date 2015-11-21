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

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import tk.freaxsoftware.extras.faststorage.example.ExampleDirectory;
import tk.freaxsoftware.extras.faststorage.reading.ECSVParser;

/**
 * ECSV parsing main test.
 * @author Stanislav Nepochatov
 */
public class ECSVParserTest {
    
    ECSVParser parser;
    
    public ECSVParserTest() {
        ExampleDirectory example = new ExampleDirectory();
        parser = new ECSVParser(example.getDefinition());
    }
    
    @Test
    public void majorTest() {
        parser.parseEntity("DIR:4,Src,Private,{Source files of some evil program!},[hide,black],[Tom:false,Joe:false]");
        parser.parseEntity("DIR:0,Root,null,{Root directory},[],[Tom:false,Joe:false]");
        parser.parseEntity("DIR:3,Video,Private,{},[shared,red],[]");
    }
    
    @Before
    public void setUp() {
    }
    
}
