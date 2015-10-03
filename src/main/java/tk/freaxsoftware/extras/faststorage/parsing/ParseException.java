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

package tk.freaxsoftware.extras.faststorage.parsing;

/**
 * Parsing exception class.
 * @author spoilt
 */
public class ParseException extends Exception {
    
    /**
     * Position of parser cursor where error occurred.
     */
    private int errorPosition;
    
    /**
     * String of data which can't be parsed.
     */
    private String errorString;
    
    /**
     * Default constructor.
     * @param message short message about exceptional situation;
     * @param position current position of parser;
     */
    public ParseException(String message, String error, int position) {
        super(message);
        errorPosition = position;
        errorString = error;
    }

    /**
     * Get position of parsing error.
     * @return position in single line;
     */
    public int getErrorPosition() {
        return errorPosition;
    }

    /**
     * Get unparsed data string.
     * @return the data string;
     */
    public String getErrorString() {
        return errorString;
    }
    
}
