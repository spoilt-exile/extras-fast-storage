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
package tk.freaxsoftware.extras.faststorage.reading;

import tk.freaxsoftware.extras.faststorage.exception.ECSVParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFields;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;

/**
 * Default implementation of ECSV parser.
 * @author Stanislav Nepochatov
 */
public class ECSVParser {
    
    /**
     * Group folds counter.
     */
    private Integer foldsCounter = 0;
    
    /**
     * Definition of entity.
     */
    private final ECSVDefinition defintion;
    
    /**
     * Iterator through definition fields.
     */
    private ListIterator<ECSVDefinition.ECSVFieldPrimitive> fieldsIter;
    
    /**
     * Current iterated field.
     */
    private ECSVDefinition.ECSVFieldPrimitive currentField;
    
    /**
     * Default constructor.
     * @param definition entity field definition;
     */
    public ECSVParser(ECSVDefinition definition) {
        this.defintion = definition;
        this.fieldsIter = this.defintion.getFields().listIterator();
        this.currentField = this.fieldsIter.next();
    }
    
    /**
     * Notify main parser method about special chars<br><br>
     * <<b>Statuses:</b><br>
     * <b>0</b> : ordinary char<br>
     * <b>1</b> : comma separator<br>
     * <b>2</b> : white zone begining<br>
     * <b>3</b> : white zone ending<br>
     * <b>4</b> : array begining<br>
     * <b>5</b> : array ending<br>
     * <b>6</b> : ignore comma separator<br>
     * <b>7</b> : increase index command<br>
     * @param prevCh previos char
     * @param ch current char
     * @param nextCh next char
     * @return parse code status
     */
    private Integer parseMarker(char prevCh, char ch, char nextCh) {
        switch (ch) {
            case ',':
                if (((nextCh == ECSVFormat.WHITE_ZONE_BEGIN_CHAR) || (nextCh == ECSVFormat.ARRAY_BEGIN_CHAR)) && ((prevCh == ECSVFormat.WHITE_ZONE_END_CHAR) || (prevCh == ECSVFormat.ARRAY_END_CHAR))) {
                    return 0;
                } else if ((prevCh == ECSVFormat.WHITE_ZONE_END_CHAR) || (prevCh == ECSVFormat.ARRAY_END_CHAR) && (foldsCounter == 0)) {
                    return 7;
                } else {
                    return 1;
                }
            case ECSVFormat.WHITE_ZONE_BEGIN_CHAR:
                if (foldsCounter == 0) {
                    foldsCounter = foldsCounter + 1;
                    return 2;
                } else {
                    foldsCounter = foldsCounter + 1;
                    return 0;
                }
            case ECSVFormat.WHITE_ZONE_END_CHAR:
                if (foldsCounter == 1) {
                    foldsCounter = foldsCounter - 1;
                    return 3;
                } else {
                    foldsCounter = foldsCounter - 1;
                    return 0;
                }
            case ECSVFormat.ARRAY_BEGIN_CHAR:
                return 4;
            case ECSVFormat.ARRAY_END_CHAR:
                return 5;
        }
        return 0;
    }
    
    /**
     * Find out if there is more separators.
     * @param restOfLine rest of parsed line;
     * @return true if rest of line contains at least one separator;
     */
    private Boolean hasMoreSeparators(String restOfLine) {
        String[] separators = new String[] {ECSVFormat.GENERIC_SEPARATOR, ECSVFormat.WHITE_ZONE_BEGIN, ECSVFormat.WHITE_ZONE_END, ECSVFormat.ARRAY_BEGIN, ECSVFormat.ARRAY_END};
        for (Integer sepIndex = 0; sepIndex < separators.length; sepIndex++) {
            if (restOfLine.contains(separators[sepIndex]) == true) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Parse entity line and return list of parsed pieces of entity for further reading.
     * @param entityLine line to parse;
     * @return arraylist string of parsed words;
     * @throws tk.freaxsoftware.extras.faststorage.reading.ParseException
     */
    public synchronized List<String> parseEntity(String entityLine) throws ECSVParseException {
        String rawEntity;
        List<String> parsed = new ArrayList(defintion.getFields().size());
        if (currentField.getField() == ECSVFields.TYPE) {
            String[] parsedType = parseKeyValue(entityLine);
            parsed.add(parsedType[0]);
            rawEntity = parsedType[1];
            moveForward();
        } else {
            rawEntity = entityLine;
        }
        Integer beginSlice = 0;
        Boolean ignoreComma = false;
        for (Integer index = 0; index < rawEntity.length(); index++) {
            char currChar = rawEntity.charAt(index);
            char nextChar = '1';
            char prevChar = '1';
            if (index > 0) {
                prevChar = rawEntity.charAt(index - 1);
            }
            if (index < rawEntity.length() - 1) {
                nextChar = rawEntity.charAt(index + 1);
            }
            switch (parseMarker(prevChar, currChar, nextChar)) {
                case 0:
                    continue;
                case 1:
                    if (ignoreComma == false) {
                        String word = rawEntity.substring(beginSlice, index);
                        if (validate(currentField.getField(), word)) {
                            parsed.add(word);
                            moveForward();
                            beginSlice = index + 1;
                        } else {
                            throw new ECSVParseException("Word validation faield, format error!", rawEntity, index, currentField.getField());
                        }
                    }
                    break;
                case 2:
                    beginSlice = index + 1;
                    ignoreComma = true;
                    break;
                case 3:
                    if (ignoreComma == true) {
                        ignoreComma = false;
                        String string = rawEntity.substring(beginSlice, index);
                        if (validate(currentField.getField(), string)) {
                            parsed.add(string);
                            moveForward();
                            beginSlice = index + 1;
                        } else {
                            throw new ECSVParseException("String validation failed, format error!", rawEntity, index, currentField.getField());
                        }
                    }
                    break;
                case 4:
                    beginSlice = index + 1;
                    ignoreComma = true;
                    break;
                case 5:
                    if (ignoreComma == true) {
                        ignoreComma = false;
                        String array = rawEntity.substring(beginSlice, index);
                        if (validate(currentField.getField(), array)) {
                            parsed.add(array);
                        } else {
                            throw new ECSVParseException("Array validation failed, format error!", rawEntity, index, currentField.getField());
                        }
                        moveForward();
                        beginSlice = index + 1;
                    }
                    break;
                case 6:
                    ignoreComma = true;
                    break;
                case 7:
                    if (ignoreComma == false) {
                        beginSlice = index + 1;
                    }
                    break;
            }
            if ((!hasMoreSeparators(rawEntity.substring(index + 1))) && (index < rawEntity.length() - 1)) {
                String word = rawEntity.substring(index + 1);
                if (validate(currentField.getField(), word)) {
                    parsed.add(word);
                } else {
                    throw new ECSVParseException("Word validation failed, format error!", rawEntity, index, currentField.getField());
                }
                moveForward();
                break;
            }
        }
        reset();
        return parsed;
    }
    
    /**
     * Move field iteration forward.
     */
    private void moveForward() {
        if (fieldsIter.hasNext()) {
            currentField = fieldsIter.next();
        }
    }
    
    /**
     * Reset iterator.
     */
    private void reset() {
        while (fieldsIter.hasPrevious()) {
            currentField = fieldsIter.previous();
        }
    }
    
    /**
     * Parse key-value struct string<br>
     * for example: "TAG:ARG"
     * @param rawString string of entity;
     * @throws ECSVParseException if there is no separator;
     * @return array with command and its arguments;
     */
    public static String[] parseKeyValue(String rawString) throws ECSVParseException {
        String[] returnedArray = new String[2];
        Integer splitIndex = -1;
        for (Integer cursorIndex = 0; cursorIndex < rawString.length(); cursorIndex++) {
            if (rawString.charAt(cursorIndex) == ':') {
                splitIndex = cursorIndex;
                break;
            }
        }
        if (splitIndex == -1) {
            throw new ECSVParseException("Given string is not key-value pair: " + rawString, rawString, splitIndex);
        } else {
            returnedArray[0] = rawString.substring(0, splitIndex);
            returnedArray[1] = rawString.substring(splitIndex + 1);
            return returnedArray;
        }
    }
    
    /**
     * Validate piece of entity against it's field.
     * @param field fields prodived by definition;
     * @param rawString raw string from stream;
     * @return true if field valid and false if not;
     */
    private Boolean validate(ECSVFields field, String rawString) {
        return true;
    }
}
