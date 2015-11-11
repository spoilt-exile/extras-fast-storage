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

import java.util.Map;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFields;
import tk.freaxsoftware.extras.faststorage.packing.EntityWriter;
import tk.freaxsoftware.extras.faststorage.parsing.EntityReader;

/**
 * Example directory entity.
 * @author Stanislav Nepochatov
 */
public class ExampleDirectory implements ECSVAble {
    
    private String name;
    
    private String parentName;
    
    private String description;
    
    private String[] marks;
    
    private Map<String, Boolean> permissions;

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

    /**
     * @return the marks
     */
    public String[] getMarks() {
        return marks;
    }

    /**
     * @param marks the marks to set
     */
    public void setMarks(String[] marks) {
        this.marks = marks;
    }

    /**
     * @return the permissions
     */
    public Map<String, Boolean> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(Map<String, Boolean> permissions) {
        this.permissions = permissions;
    }

    @Override
    public ECSVDefinition getDefinition() {
        return ECSVDefinition.createNew()
                .add(ECSVFields.TYPE)
                .add(ECSVFields.PR_WORD)
                .add(ECSVFields.PR_WORD)
                .add(ECSVFields.PR_STRING)
                .add(ECSVFields.CX_ARRAY)
                .add(ECSVFields.CX_MAP);
    }

    @Override
    public void readFromECSV(EntityReader reader) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void writeToECSV(EntityWriter writer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
