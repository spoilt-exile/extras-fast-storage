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

package tk.freaxsoftware.extras.faststorage.packing;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;
import tk.freaxsoftware.extras.faststorage.generic.ECSVType;

/**
 * Standard packer implementation.
 * @author Stanislav Nepochatov
 * @param <E> all ECSVAble successors;
 */
public class StandardPacker<E extends ECSVAble> implements Packer<E> {
    
    @Override
    public String packEntities(List<E> entities, Boolean appendTypes) {
        StringBuffer buf = new StringBuffer();
        int counter = 0;
        for(E entity: entities) {
            entity.pack(buf, appendTypes);
            if (entity.getCurrentType() == ECSVType.KEY_VALUE) {
                if (counter < entities.size() - 1) {
                    buf.append(ECSVFormat.GENERIC_SEPARATOR);
                }
            } else {
                buf.append(ECSVFormat.LINE_BREAK);
            }
            ++counter;
        }
        
        return buf.toString();
    }

    @Override
    public void packEntities(List<E> entities, Boolean appendTypes, Writer entityWriter) throws IOException {
        for(E entity: entities) {
            entityWriter.write(entity.packToString(appendTypes) + ECSVFormat.LINE_BREAK);
        }
    }
    
    /**
     * Render given array to group format.
     * 
     * <p>Format: <code>'[arr1,arr2,arr3,...arrN]'</code><p>
     * @param givenGroup group to pack;
     * @return packed string;
     */
    public String packGroup(String[] givenGroup) {
        if (givenGroup == null) {
            return "[]";
        }
        String returned = "[";
        for (Integer rIndex = 0; rIndex < givenGroup.length; rIndex++) {
            returned += givenGroup[rIndex];
            if (rIndex == givenGroup.length - 1) {
                returned += "]";
            } else {
                returned += ",";
            }
        }
        return returned;
    }
    
    /**
     * Render given array to common line.
     * 
     * <p>Format: <code>'arr1,arr2,arr3,...arrN'</code><p>
     * <p>Result of this method could be returned back to 
     * array by commonParseLine method.</p>
     * @param givenCommon line to pack;
     * @return packed string;
     */
    public String packCommonLine(String[] givenCommon) {
        if (givenCommon == null) {
            return "";
        }
        String returned = "";
        for (Integer rIndex = 0; rIndex < givenCommon.length; rIndex++) {
            returned += givenCommon[rIndex];
            if (rIndex < givenCommon.length - 1) {
                returned += ",";
            }
        }
        return returned;
    }
}
