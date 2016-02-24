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

import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFields;
import tk.freaxsoftware.extras.faststorage.reading.EntityReader;
import tk.freaxsoftware.extras.faststorage.writing.EntityWriter;

/**
 * Example mark for directory example.
 * @author Stanislav Nepochatov
 */
public class ExamplePermission implements ECSVAble<Object> {
    
    public final static String TYPE = "PERMISSION";
    
    public final static ECSVDefinition DEFINITION = ECSVDefinition.createNew()
                .addPrimitive(ECSVFields.PR_WORD)
                .addPrimitive(ECSVFields.PR_BOOLEAN)
                .addPrimitive(ECSVFields.PR_BOOLEAN)
                .addPrimitive(ECSVFields.PR_BOOLEAN)
                .addPrimitive(ECSVFields.PR_INT);
    
    private String userName;
    
    private Boolean canRead;
    
    private Boolean canWrite;
    
    private Boolean canDelete;
    
    private Integer mode;
    
    public ExamplePermission() {}

    public ExamplePermission(String userName, Boolean canRead, Boolean canWrite, Boolean canDelete, Integer mode) {
        this.userName = userName;
        this.canRead = canRead;
        this.canWrite = canWrite;
        this.canDelete = canDelete;
        this.mode = mode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean isCanRead() {
        return canRead;
    }

    public void setCanRead(Boolean canRead) {
        this.canRead = canRead;
    }

    public Boolean isCanWrite() {
        return canWrite;
    }

    public void setCanWrite(Boolean canWrite) {
        this.canWrite = canWrite;
    }

    public Boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Boolean canDelete) {
        this.canDelete = canDelete;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    @Override
    public Object getKey() {
        return null;
    }
    

    @Override
    public void setKey(Object key) {
        //Do nothing
    }

    @Override
    public ECSVDefinition getDefinition() {
        return DEFINITION;
    }

    @Override
    public void readFromECSV(EntityReader reader) {
        this.userName = reader.readWord();
        this.canRead = reader.readBoolean();
        this.canWrite = reader.readBoolean();
        this.canDelete = reader.readBoolean();
        this.mode = reader.readInteger();
    }

    @Override
    public void writeToECSV(EntityWriter writer) {
        writer.writeWord(userName);
        writer.writeBoolean(canRead);
        writer.writeBoolean(canWrite);
        writer.writeBoolean(canDelete);
        writer.writeInteger(mode);
    }

    @Override
    public void update(ECSVAble<Object> updatedEntity) {
        //Example permission doesn't have storage, then update method may be empty.
    }    

    @Override
    public String getEntityType() {
        return TYPE;
    }
}
