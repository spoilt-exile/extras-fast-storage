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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import tk.freaxsoftware.extras.faststorage.exception.EntityStoreException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;

/**
 * Entity map implementation.
 * @author Stanislav Nepochatov
 */
public class DefaultEntityMap implements EntityMap {
    
    private List<EntityMapEntry> entries;
    
    private Map<String, Integer> entriesMap;
    
    public DefaultEntityMap() {
        entries = new ArrayList<>();
        entriesMap = new HashMap<>();
    }

    @Override
    public void add(String key, ECSVAble value) {
        EntityMapEntry entry = getEntryByKeys(key, value.getKey());
        if (entry == null) {
            Integer keyCount = entriesMap.containsKey(key) ? entriesMap.get(key) : 0;
            EntityMapEntry newEntry = new DefaultEntityMapEntry(key, value);
            entries.add(newEntry);
            entriesMap.put(key, keyCount++);
        } else {
            entry.update(value);
        }
    }

    @Override
    public void addList(String key, List<ECSVAble> valueList) {
        for (ECSVAble value: valueList) {
            add(key, value);
        }
    }

    @Override
    public ECSVAble get(String key) {
        for (EntityMapEntry entry: entries) {
            if (entry.getKey().equals(key)) {
                return entry.getEntity();
            }
        }
        return null;
    }

    @Override
    public <V extends ECSVAble> V get(String key, Class<V> valueClass) {
        for (EntityMapEntry entry: entries) {
            if (entry.getKey().equals(key)) {
                return entry.getEntity(valueClass);
            }
        }
        return null;
    }

    @Override
    public List<ECSVAble> getList(String key) {
        Integer keyCount = entriesMap.containsKey(key) ? entriesMap.get(key) : 0;
        if (keyCount > 0) {
            List<ECSVAble> valueList = new ArrayList<>(keyCount);
            for (EntityMapEntry entry: entries) {
                if (entry.getKey().equals(key)) {
                    valueList.add(entry.getEntity());
                }
            }
            return valueList;
        } 
        return Collections.EMPTY_LIST;
    }

    @Override
    public <V extends ECSVAble> List<V> getList(String key, Class<V> valueClass) {
        Integer keyCount = entriesMap.containsKey(key) ? entriesMap.get(key) : 0;
        if (keyCount > 0) {
            List<V> valueList = new ArrayList<>(keyCount);
            for (EntityMapEntry entry: entries) {
                if (entry.getKey().equals(key)) {
                    V castedEntity = entry.getEntity(valueClass);
                    if (castedEntity != null) {
                        valueList.add(castedEntity);
                    }
                }
            }
            return valueList;
        } 
        return Collections.EMPTY_LIST;
    }

    @Override
    public boolean containsKey(String key) {
        return entriesMap.containsKey(key);
    }

    @Override
    public boolean isList(String key) {
        Integer keyCount = entriesMap.containsKey(key) ? entriesMap.get(key) : 0;
        return keyCount > 1;
    }

    @Override
    public ListIterator<EntityMapEntry> listiterator() {
        return entries.listIterator();
    }

    @Override
    public Set<String> getKeys() {
        return entriesMap.keySet();
    }

    @Override
    public void remove(String key) {
        ListIterator<EntityMapEntry> entryIter = entries.listIterator();
        while (entryIter.hasNext()) {
            EntityMapEntry entry = entryIter.next();
            if (entry.getKey().equals(key)) {
                entryIter.remove();
            }
        }
    }

    @Override
    public void remove(String key, Object entityKey) {
        ListIterator<EntityMapEntry> entryIter = entries.listIterator();
        while (entryIter.hasNext()) {
            EntityMapEntry entry = entryIter.next();
            if (entry.getKey().equals(key) && entry.isParsed() && entry.getEntity().getKey().equals(key)) {
                entryIter.remove();
            }
        }
    }

    @Override
    public void removeAll() {
        entries = new ArrayList<>();
        entriesMap = new HashMap<>();
    }

    @Override
    public void readFromEcsv(String mapString) {
        StringReader reader = new StringReader(mapString);
        readFromEcsv(reader);
    }

    @Override
    public void readFromEcsv(Reader mapReader) {
        try {
            BufferedReader reader = new BufferedReader(mapReader);
            while (reader.ready()) {
                EntityMapEntry entry = new DefaultEntityMapEntry(reader.readLine());
                if (entry.isParsed()) {
                    EntityMapEntry oldEntry = getEntryByKeys(entry.getKey(), entry.getEntity().getKey());
                    if (oldEntry == null) {
                        Integer keyCount = entriesMap.containsKey(entry.getKey()) ? entriesMap.get(entry.getKey()) : 0;
                        entries.add(entry);
                        entriesMap.put(entry.getKey(), keyCount++);
                    } else {
                        entry.update(entry.getEntity());
                    }
                } else {
                    Integer keyCount = entriesMap.containsKey(entry.getKey()) ? entriesMap.get(entry.getKey()) : 0;
                    entries.add(entry);
                    entriesMap.put(entry.getKey(), keyCount++);
                }
            }
        } catch (IOException ioex) {
            throw new EntityStoreException("unable to read map from reader", ioex);
        }
    }

    @Override
    public String writeToEcsv() {
        StringWriter writer = new StringWriter();
        writeToEcsv(writer);
        return writer.toString();
    }

    @Override
    public void writeToEcsv(Writer writer) {
        for (EntityMapEntry entry: entries) {
            entry.writeToEcsv(writer);
        }
    }
    
    /**
     * Find map entry by entry key and entity key inside it. Method will ignore unparsed entries.
     * @param entryKey key of map entry;
     * @param entityKey key of entity inside entry;
     * @return finded entry or null;
     */
    private EntityMapEntry getEntryByKeys(String entryKey, Object entityKey) {
        for (EntityMapEntry entry: entries) {
            if (entry.getKey().equals(entryKey) && entry.isParsed() && entityKey.equals(entry.getEntity().getKey())) {
                return entry;
            }
        }
        return null;
    } 
}
