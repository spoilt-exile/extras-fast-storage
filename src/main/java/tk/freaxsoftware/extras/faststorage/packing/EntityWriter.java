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

import java.util.Date;
import java.util.List;
import java.util.Map;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.EntityReference;

/**
 * ECSV entity writer helper interface.
 * @author Stanislav Nepochatov
 * @param <K> entity key type generic;
 */
public interface EntityWriter<K> {
    
    /**
     * Writes entity type attribute to ECSV stream.
     * @param type entity type;
     */
    void writeType(String type);
    
    /**
     * Writes entity key value to ECSV stream.
     * @param key entity unique key;
     */
    void writeKey(K key);
    
    /**
     * Writes single word in stream.
     * @param word single word;
     */
    void writeWord(String word);
    
    /**
     * Writes escaped string in stream.
     * @param string simple string;
     */
    void writeString(String string);
    
    /**
     * Writes boolean value in stream.
     * @param bool boolean value;
     */
    void writeBoolean(Boolean bool);
    
    /**
     * Writes integer value in stream.
     * @param integer integer value;
     */
    void writeInteger(Integer integer);
    
    /**
     * Writes float value in stream.
     * @param flt float value;
     */
    void writeFloat(Float flt);
    
    /**
     * Writes date in stream.
     * @param date date value;
     */
    void writeDate(Date date);
    
    /**
     * Writes entity reference in stream.
     * @param <R> entity reference type generic;
     * @param reference entity reference object;
     */
    <R extends ECSVAble> void writeReference(EntityReference<R> reference);
    
    /**
     * Writes entity reference array in stream.
     * @param <R> entity reference type generic;
     * @param referenceArray list of references;
     */
    <R extends ECSVAble> void wriyeReferenceArray(List<EntityReference<R>> referenceArray);
    
    /**
     * Write primitive array in stream.
     * @param array list of primitives;
     */
    void writeArray(List array);
    
    /**
     * Write map in stream.
     * @param map map of primitives;
     */
    void writeMap(Map map);
    
    /**
     * Write internal entity to stream.
     * @param <E> entity type generic;
     * @param entity entity instance;
     */
    <E extends ECSVAble> void writeInternal(E entity);
    
    /**
     * Write internal entity array to stream.
     * @param <E> entity type generic;
     * @param entityArray entity array;
     */
    <E extends ECSVAble> void writeInternalArray(List<E> entityArray);
}
