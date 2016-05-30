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
package tk.freaxsoftware.extras.faststorage.mapped;

import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;

/**
 * Mapped entity contains map of ECSV entities, allows to store 
 * lists of such entities by single key and cast result of mapping.
 * @author Nepochatov Stanislav
 * @since 3.0
 */
public interface EntityMap {
    
    /**
     * Adds entity to map. Map can't add entity if there is another 
     * type of entity with same key;
     * @param key string key for this entity, must not be null and must be unique across types;
     * @param value entity;
     */
    void add(String key, ECSVAble value);
    
    /**
     * Adds entities list to map.
     * @param key string key for this entity, must not be null and must be unique across types;
     * @param valueList list of entities;
     */
    void addList(String key, List<ECSVAble> valueList);
    
    /**
     * Get untouched value from map by key.
     * @param key key to search;
     * @return entity or null;
     */
    ECSVAble get(String key);
    
    /**
     * Get casted value from map by key.
     * @param <V> value class generic type;
     * @param key key to search;
     * @param valueClass class of entity which expected;
     * @return casted entity or null (entity not found or incorrect type);
     */
    <V extends ECSVAble> V get(String key, Class<V> valueClass);
    
    /**
     * Get list of untouched values from map by key.
     * @param key key to search;
     * @return list of entities or empty list;
     */
    List<ECSVAble> getList(String key);
    
    /**
     * Get list of casted values from map by key.
     * @param <V> value class generic type;
     * @param key key to search;
     * @param valueClass class of entity which expected;
     * @return list of casted entities or empty list (not found or incorrect type);
     */
    <V extends ECSVAble> List<V> getList(String key, Class<V> valueClass);
    
    /**
     * Get list of entities by it's internal type.
     * @param entityType internal entity type;
     * @return list of entities or empty list;
     */
    List<ECSVAble> getListByType(String entityType);
    
    /**
     * Get list of casted entities by it's internal type.
     * @param <V> value class generic type;
     * @param entityType internal entity type.
     * @param valueClass class of entity whiche expected;
     * @return list of casted entities or empty list;
     */
    <V extends ECSVAble> List<V> getListByType(String entityType, Class<V> valueClass);
    
    /**
     * Get list of entities by standard regex.
     * @param expression pattern expression;
     * @return list of entities or empty list;
     */
    List<ECSVAble> getListByRegex(String expression);
    
    /**
     * Get list of casted entities by standard regex.
     * @param <V> value class generic type.
     * @param expression pattern expression;
     * @param valueClass class of entity whiche expected;
     * @return list of casted entities or empty list;
     */
    <V extends ECSVAble> List<V> getListByRegex(String expression, Class<V> valueClass);
    
    /**
     * Check key for presense.
     * @param key string key to check;
     * @return true if key finded or false otherwise;
     */
    boolean containsKey(String key);
    
    /**
     * Check if there is a list of entities marked by key.
     * @param key string to check;
     * @return true if key represents list or false otherwise;
     */
    boolean isList(String key);
    
    /**
     * Get iterator for enties;
     * @return list iterator;
     */
    ListIterator<EntityMapEntry> listiterator();
    
    /**
     * Get set of keys;
     * @return set of strings;
     */
    Set<String> getKeys();
    
    /**
     * Remove entry or entries by key. Also remove lists.
     * @param key key to delete;
     */
    void remove(String key);
    
    /**
     * Remove certain entity within list by entity key.
     * @param key key of map entry;
     * @param entityKey key of entity to delete;
     */
    void remove(String key, Object entityKey);
    
    /**
     * Remove entities specified in list.
     */
    void removeAll(List<String> keys);
    
    /**
     * Clear map, delete all entities.
     */
    void clear();
    
    /**
     * Reads map from string and fill current instance with it.
     * @param mapString raw map string;
     */
    void readFromEcsv(String mapString);
    
    /**
     * Reads map from reader instance and fill current instance with it.
     * @param mapReader reader of raw map content;
     */
    void readFromEcsv(Reader mapReader);
    
    /**
     * Writes map entiies to string form.
     * @return raw map form;
     */
    String writeToEcsv();
    
    /**
     * Writes map entities to a writer with raw form.
     * @param writer map writer;
     */
    void writeToEcsv(Writer writer);
}
