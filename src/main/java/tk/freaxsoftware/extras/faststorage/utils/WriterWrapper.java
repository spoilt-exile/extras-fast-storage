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
package tk.freaxsoftware.extras.faststorage.utils;

import java.io.IOException;
import java.io.Writer;
import tk.freaxsoftware.extras.faststorage.exception.EntityStoreException;

/**
 * Wrapper around standard writer to handle exceptions.
 * @author Stanislav Nepochatov
 */
public class WriterWrapper {
    
    private Writer target;

    public WriterWrapper(Writer target) {
        this.target = target;
    }
    
    public void append(String value) {
        try {
            target.append(value);
        } catch (IOException ioex) {
            throw new EntityStoreException("unable to write value", ioex);
        }
    }
    
    public void append(char value) {
        try {
            target.append(value);
        } catch (IOException ioex) {
            throw new EntityStoreException("unable to write value", ioex);
        }
    }
    
    public void append(long value) {
        try {
            target.append(String.valueOf(value));
        } catch (IOException ioex) {
            throw new EntityStoreException("unable to write value", ioex);
        }
    }
}
