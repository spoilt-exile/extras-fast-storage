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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;
import tk.freaxsoftware.extras.faststorage.generic.ECSVType;
import static tk.freaxsoftware.extras.faststorage.generic.ECSVType.BASIC_ECSV;
import static tk.freaxsoftware.extras.faststorage.generic.ECSVType.COMPLEX_ECSV;
import static tk.freaxsoftware.extras.faststorage.generic.ECSVType.KEY_VALUE;

/**
 * Standard parser implementation.
 * @author Stanislav Nepochatov
 * @param <E> all ECSVAble successors;
 */
public class StandardParser<E extends ECSVAble> implements Parser<E> {
    
    /**
     * Reference of given class.
     */
    private final E reference;
    
    /**
     * Entity class to build.
     */
    private final Class<E> refClass;
    
    /**
     * Default constructor.
     * @param inRefClass class of entity to parse;
     * @throws InstantiationException if creating new instances failed;
     * @throws IllegalAccessException if entity has no public default constructor (should be!);
     */
    public StandardParser(Class<E> inRefClass) throws InstantiationException, IllegalAccessException {
        refClass = inRefClass;
        reference = refClass.newInstance();
    }

    @Override
    public E readEntity(String rawEntity, Boolean extractType) throws InstantiationException, IllegalAccessException, ParseException {
//        ParseResult result = new ParseResult(reference.getEntityType(), reference.getBasicCount(), reference.getArrayCount());
//        
//        String entityString = null;
//        if (extractType && reference.getCurrentType() != ECSVType.KEY_VALUE) {
//            String[] typeExtract = this.tryExtractType(rawEntity);
//            if (typeExtract[0] != null) {
//                result.setType(typeExtract[0]);
//            }
//            entityString = typeExtract[1];
//        } else {
//            entityString = rawEntity;
//        }
//        result.setRawString(entityString);
//        E readedEntity = refClass.newInstance();
//        
//        switch (reference.getCurrentType()) {
//            case BASIC_ECSV: {
//                this.commonParseLine(result, entityString, reference.getBasicCount());
//                break;
//            }
//            case COMPLEX_ECSV: {
//                this.complexParseLine(result, entityString, reference.getBasicCount(), reference.getArrayCount());
//                break;
//            }
//            case KEY_VALUE: {
//                this.parseKeyValue(result, entityString);
//            }
//        }
//        
//        readedEntity.unPack(result);
//        
//        return readedEntity;
        //TO-DO: remake this
        return null;
    }

    @Override
    public List<E> readEntities(String rawEntities, Boolean extractType) throws InstantiationException, IllegalAccessException, ParseException {
        String[] entityLines = rawEntities.split(ECSVFormat.LINE_BREAK);
        List<E> parsedEntities = new ArrayList(entityLines.length);
        int lineCounter = 0;
        for (String entityLine: entityLines) {
            ++lineCounter;
            try {
                parsedEntities.add(this.readEntity(entityLine, extractType));
            } catch (ParseException subex) {
                ParseException pex = new ParseException("Parse error in line: " + lineCounter + " position: " + subex.getErrorPosition(), entityLine, subex.getErrorPosition());
                pex.addSuppressed(subex);
                throw pex;
            }
        }
        return parsedEntities;
    }
    
    @Override
    public List<E> readEntities(List<String> rawEntities, Boolean extractType) throws InstantiationException, IllegalAccessException, ParseException {
        List<E> parsedEntities = new ArrayList(rawEntities.size());
        int lineCounter = 0;
        for (String entityLine: rawEntities) {
            ++lineCounter;
            try {
                parsedEntities.add(this.readEntity(entityLine, extractType));
            } catch (ParseException subex) {
                ParseException pex = new ParseException("Parse error in list index: " + lineCounter + " position: " + subex.getErrorPosition(), entityLine, subex.getErrorPosition());
                pex.addSuppressed(subex);
                throw pex;
            }
        }
        return parsedEntities;
    }

    @Override
    public List<E> readEntities(Reader entityReader, Boolean extractType) throws InstantiationException, IllegalAccessException, ParseException {
        BufferedReader reader = null;
        if (!(entityReader instanceof BufferedReader)) {
            reader = new BufferedReader(entityReader);
        } else {
            reader = (BufferedReader) entityReader;
        }

        List<E> parsedEntities = new ArrayList();
        int lineCounter = 0;
        String entityLine = null;
        
        try {
            while (reader.ready()){
                ++lineCounter;
                entityLine = reader.readLine();
                parsedEntities.add(this.readEntity(entityLine, extractType));
            }
        } catch (ParseException subex) {
            ParseException pex = new ParseException("Parse error in line: " + lineCounter + " position: " + subex.getErrorPosition(), entityLine, subex.getErrorPosition());
            pex.addSuppressed(subex);
            throw pex;
        } catch (IOException ex) {
            ParseException pex = new ParseException("IO error occured!", null, 0);
            pex.addSuppressed(ex);
            throw pex;
        }
        return parsedEntities;
    }
    
    /**
     * Group folds counter;
     */
    private Integer foldsCounter = 0;
    
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
     * Common parse line method (without groups support).<br>
     * Using to unify parse methodic.
     * @param inputLine line to parse;
     * @param baseArrLength length of base fields in csv line;
     * @throws org.devnote.libecsvparser.parsing.ParseException if parsing failed;
     */
    private void commonParseLine(ParseResult result, String inputLine, Integer baseArrLength) throws ParseException {
        String[] baseArray = new String[baseArrLength];
        Integer beginSlice = 0;
        Integer acceptedIndex = -1;
        Boolean ignoreComma = false;
        for (Integer index = 0; index < inputLine.length(); index++) {
            char currChar = inputLine.charAt(index);
            char nextChar = '1';
            char prevChar = '1';
            if (index > 0) {
                prevChar = inputLine.charAt(index - 1);
            }
            if (index < inputLine.length() - 1) {
                nextChar = inputLine.charAt(index + 1);
            }
            switch (parseMarker(prevChar, currChar, nextChar)) {
                case 0:
                    continue;
                case 1:
                    if (ignoreComma == false) {
                        if (acceptedIndex < baseArrLength - 1) {
                            baseArray[++acceptedIndex] = inputLine.substring(beginSlice, index);
                            beginSlice = index + 1;
                        } else {
                            throw new ParseException("Basic words is more then specified, format error!", inputLine, index);
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
                        if (acceptedIndex < baseArrLength - 1) {
                            baseArray[++acceptedIndex] = inputLine.substring(beginSlice, index);
                            beginSlice = index + 1;
                        } else {
                            throw new ParseException("Basic words is more then specified, format error!", inputLine, index);
                        }
                    }
                    break;
                case 4:
                    throw new ParseException("Arrays are not supported, wrong format definition!", inputLine, index);
                case 5:
                    throw new ParseException("Arrays are not supported, wrong format definition!", inputLine, index);
                case 6:
                    ignoreComma = true;
                    break;
                case 7:
                    beginSlice = index + 1;
                    break;
            }
            if ((!hasMoreSeparators(inputLine.substring(index + 1))) && (index < inputLine.length() - 1)) {
                if (acceptedIndex < baseArrLength - 1) {
                    baseArray[++acceptedIndex] = inputLine.substring(index + 1);
                } else {
                    throw new ParseException("Basic words is more then specified, format error!", inputLine, index);
                }
                break;
            }
        }
        result.setBasics(baseArray);
        result.setValid(true);
    }
    
    /**
     * Complex parse line method (with groups support).<br>
     * Using to unify parse methodic.
     * @param inputLine line to parse
     * @param baseArrLength length of base fields in csv line
     * @param groupsCount count of additional arrays with groups parsed words
     * @return arraylist with string arrays of parsed words;
     * @throws org.devnote.libecsvparser.parsing.ParseException if parsing failed;
     */
    private void complexParseLine(ParseResult result, String inputLine, Integer baseArrLength, Integer groupsCount) throws ParseException {
        String[] baseArray = new String[baseArrLength];
        java.util.ArrayList<String[]> tempGroupArray = new java.util.ArrayList<String[]>();
        Integer beginSlice = 0;
        Integer acceptedIndex = -1;
        Boolean ignoreComma = false;
        for (Integer index = 0; index < inputLine.length(); index++) {
            char currChar = inputLine.charAt(index);
            char nextChar = '1';
            char prevChar = '1';
            if (index > 0) {
                prevChar = inputLine.charAt(index - 1);
            }
            if (index < inputLine.length() - 1) {
                nextChar = inputLine.charAt(index + 1);
            }
            switch (parseMarker(prevChar, currChar, nextChar)) {
                case 0:
                    continue;
                case 1:
                    if (ignoreComma == false) {
                        if (acceptedIndex < baseArrLength) {
                            baseArray[++acceptedIndex] = inputLine.substring(beginSlice, index);
                            beginSlice = index + 1;
                        } else {
                            throw new ParseException("Basic words is more then specified, format error!", inputLine, index);
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
                        if (acceptedIndex < baseArrLength) {
                            baseArray[++acceptedIndex] = inputLine.substring(beginSlice, index);
                            beginSlice = index + 1;
                        } else {
                            throw new ParseException("Basic words is more then specified, format error!", inputLine, index);
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
                        if (tempGroupArray.size() < groupsCount) {
                            tempGroupArray.add(inputLine.substring(beginSlice, index).split(","));
                        } else {
                            throw new ParseException("Array count is more then specified, format error!", inputLine, index);
                        }
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
            if ((!hasMoreSeparators(inputLine.substring(index + 1))) && (index < inputLine.length() - 1)) {
                if (acceptedIndex < baseArrLength) {
                    baseArray[++acceptedIndex] = inputLine.substring(index + 1);
                } else {
                    throw new ParseException("Basic words is more then specified, format error!", inputLine, index);
                }
                break;
            }
        }
        result.setBasics(baseArray);
        result.setArrays(tempGroupArray);
    }
    
    /**
     * Parse key-value struct string<br>
     * for example: "TAG:ARG"
     * @param rawString string of entity;
     * @return array with command and its arguments;
     * @throws org.devnote.libecsvparser.parsing.ParseException if given string is not key-value pair;
     */
    private void parseKeyValue(ParseResult result, String rawString) throws ParseException {
        String[] returnedArray = new String[2];
        Integer splitIndex = -1;
        for (Integer cursorIndex = 0; cursorIndex < rawString.length(); cursorIndex++) {
            if (rawString.charAt(cursorIndex) == ':') {
                splitIndex = cursorIndex;
                break;
            }
        }
        if (splitIndex == -1) {
            throw new ParseException("Given string is not key-value pair: " + rawString, rawString, splitIndex);
        } else {
            returnedArray[0] = rawString.substring(0, splitIndex);
            returnedArray[1] = rawString.substring(splitIndex + 1);
            result.setBasics(returnedArray);
        }
    }
    
    /**
     * Split first argument of csv and rest of it.<br>
     * it1,it2,it3 -> it1 and it2,it3
     * @param givenCsv csv line to split;
     * @return splited construction;
     */
    public String[] splitCsv(String givenCsv) {
        String[] result = new String[2];
        for (Integer str = 0; str < givenCsv.length(); str++) {
            if (givenCsv.charAt(str) == ',') {
                result[0] = givenCsv.substring(0, str);
                result[1] = givenCsv.substring(str + 1);
                break;
            }
        }
        return result;
    }
    
    /**
     * Try extract type of entity from it's string representation.
     * @param rawEntity raw string;
     * @return array with two strings: first is type, second is rest of string;
     */
    public String[] tryExtractType(String rawEntity) {
        ParseResult temp = new ParseResult(null, 2, 0);
        try {
            this.parseKeyValue(temp, rawEntity);
            String[] parsed = temp.getBasics();
            if (this.hasMoreSeparators(parsed[0])) {
                parsed[0] = null;
                parsed[1] = rawEntity;
                return parsed;
            } else {
                return parsed;
            }
        } catch (ParseException ex) {
            String[] raws = new String[2];
            raws[1] = rawEntity;
            return raws;
        }
    }
}
