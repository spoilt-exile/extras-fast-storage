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

package tk.freaxsoftware.extras.faststorage.example;

import java.util.List;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFields;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;
import tk.freaxsoftware.extras.faststorage.reading.EntityReader;

/**
 * Example directory entity.
 * @author Stanislav Nepochatov
 */
public class ExampleDirectory implements ECSVAble<Integer> {
    
    public static final String TYPE = "DIR";
    
    private Integer id;
    
    private String name;
    
    private String parentName;
    
    private String description;
    
    private List<ExamplePermission> permissions;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the parentName
     */
    public String getParentName() {
        return parentName;
    }

    /**
     * @param parentName the parentName to set
     */
    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public List<ExamplePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<ExamplePermission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public ECSVDefinition getDefinition() {
        return ECSVDefinition.createNew()
                .addPrimitive(ECSVFields.TYPE)
                .addKey(Integer.class)
                .addPrimitive(ECSVFields.PR_WORD)
                .addPrimitive(ECSVFields.PR_WORD)
                .addPrimitive(ECSVFields.PR_STRING)
                .addInternalArray(ExamplePermission.class, String.valueOf(ECSVFormat.INTERNAL_DEFAULT_SEPARATOR), ECSVFormat.INTERNAL_DEFAULT_SEPARATOR_EXPR);
    }

    @Override
    public void readFromECSV(EntityReader<Integer> reader) {
        reader.readType();
        this.id = reader.readKey();
        this.name = reader.readWord();
        this.parentName = reader.readWord();
        this.description = reader.readString();
        this.permissions = reader.readInternalArray(ExamplePermission.class);
    }

    @Override
    public void writeToECSV(EntityWriter<Integer> writer) {
        writer.writeType(TYPE);
        writer.writeKey(id);
        writer.writeWord(name);
        writer.writeWord(parentName);
        writer.writeString(description);
        writer.writeInternalArray(permissions);
    }

    @Override
    public Integer getKey() {
        return this.id;
    }
}
