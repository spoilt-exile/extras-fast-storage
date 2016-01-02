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

package tk.freaxsoftware.extras.faststorage.exception;

import tk.freaxsoftware.extras.faststorage.generic.ECSVFields;

/**
 * Parsing exception class.
 * @author Stanislav Nepochatov
 */
public class ECSVParseException extends RuntimeException {
    
    /**
     * Position of parser cursor where error occurred.
     */
    private final int errorPosition;
    
    /**
     * String of data which can't be parsed.
     */
    private final String errorString;
    
    /**
     * Compatibility constructor.
     * @param message short message about exceptional situation;
     * @param error raw entity string;
     * @param position current position of parser;
     */
    public ECSVParseException(String message, String error, int position) {
        super(message + " - " + error + " = " + position);
        errorPosition = position;
        errorString = error;
    }
    
    /**
     * Default constructor.
     * @param message short message about exceptional situation;
     * @param error raw entity string;
     * @param position current position of parser;
     * @param field field instance from definition where error occurred;
     */
    public ECSVParseException(String message, String error, int position, ECSVFields field) {
        super(message + " - " + error + " = " + position + " on " + field.name());
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
