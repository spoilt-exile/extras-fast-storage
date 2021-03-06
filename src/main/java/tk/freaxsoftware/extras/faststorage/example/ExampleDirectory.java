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
import java.util.Objects;
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
    
    public static final ECSVDefinition DEFINITION = ECSVDefinition.createNew()
                .addKey(Integer.class)
                .addPrimitive(ECSVFields.PR_WORD)
                .addPrimitive(ECSVFields.PR_WORD)
                .addPrimitive(ECSVFields.PR_STRING)
                .addInternalArray(ExamplePermission.class, 
                        String.valueOf(ECSVFormat.INTERNAL_DEFAULT_SEPARATOR), 
                        ECSVFormat.INTERNAL_DEFAULT_SEPARATOR_EXPR);
    
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
        return DEFINITION;
    }

    @Override
    public void readFromECSV(EntityReader<Integer> reader) {
        this.id = reader.readKey();
        this.name = reader.readWord();
        this.parentName = reader.readWord();
        this.description = reader.readString();
        this.permissions = reader.readInternalArray(ExamplePermission.class);
    }

    @Override
    public void writeToECSV(EntityWriter<Integer> writer) {
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
    
    @Override
    public void setKey(Integer key) {
        //Do nothing
    }

    @Override
    public void update(ECSVAble<Integer> updatedEntity) {
        if (updatedEntity instanceof ExampleDirectory) {
            ExampleDirectory updatedDirectory = (ExampleDirectory) updatedEntity;
            this.setName(updatedDirectory.getName());
            this.setParentName(updatedDirectory.getParentName());
            this.setDescription(updatedDirectory.getDescription());
            this.setPermissions(updatedDirectory.getPermissions());
        }
    }

    @Override
    public String toString() {
        return "ExampleDirectory{" + "id=" + id + ", name=" + name + ", parentName=" + parentName + ", description=" + description + '}';
    }

    @Override
    public String getEntityType() {
        return TYPE;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ExampleDirectory other = (ExampleDirectory) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.parentName, other.parentName)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.permissions, other.permissions)) {
            return false;
        }
        return true;
    }
}
