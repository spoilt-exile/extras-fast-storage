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

import java.util.Arrays;
import java.util.List;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;
import tk.freaxsoftware.extras.faststorage.generic.ECSVType;
import tk.freaxsoftware.extras.faststorage.packing.Packer;
import tk.freaxsoftware.extras.faststorage.packing.StandardPacker;
import tk.freaxsoftware.extras.faststorage.parsing.OldStaticParser;
import tk.freaxsoftware.extras.faststorage.parsing.ParseResult;
import tk.freaxsoftware.extras.faststorage.parsing.Parser;
import tk.freaxsoftware.extras.faststorage.parsing.StandardParser;

/**
 * Example directory entity.
 * @author Stanislav Nepochatov
 */
public class ExampleDirectory implements ECSVAble {
    
    private String name;
    
    private String parentName;
    
    private String description;
    
    private String[] marks;
    
    private List<ExamplePermission> permissions;

    @Override
    public String getEntityType() {
        //Using for type key short name of class, but any string will handle.
        return this.getClass().getSimpleName();
    }

    @Override
    public int getBasicCount() {
        //This example has three basic fields: name, parentName and description.
        return 3;
    }

    @Override
    public int getArrayCount() {
        //This example has two array-type fileds: marks and permissions. Permissions is also key-value pair.
        return 2;
    }

    @Override
    public ECSVType getCurrentType() {
        //Example has arrays, so it's complex entity.
        return ECSVType.COMPLEX_ECSV;
    }

    @Override
    public void pack(StringBuffer buffer, Boolean appendType) {
        buffer.append(this.packToString(appendType));
    }

    @Override
    public String packToString(Boolean appendType) {
        //Example: ExampleDirectory:Root,null,{Root directory},[hide,red],[Tom:false,Joe:false]
        Packer<ExamplePermission> packer = new StandardPacker();
        return (appendType ? getEntityType() : "") + ECSVFormat.KEY_VALUE_SEPARATOR + getName() + ECSVFormat.GENERIC_SEPARATOR + getParentName() + ECSVFormat.GENERIC_SEPARATOR
                + ECSVFormat.WHITE_ZONE_BEGIN + getDescription() + ECSVFormat.WHITE_ZONE_END + ECSVFormat.GENERIC_SEPARATOR
                + OldStaticParser.renderGroup(getMarks()) + ECSVFormat.GENERIC_SEPARATOR + ECSVFormat.ARRAY_BEGIN
                + packer.packEntities(getPermissions(), false) + ECSVFormat.ARRAY_END;
    }

    @Override
    public void unPack(ParseResult result) {
        setName(result.getBasics()[0]);
        setParentName(result.getBasics()[1]);
        setDescription(result.getBasics()[2]);
        setMarks(result.getArrays().get(0));
        
        //Parsing permission enteties. Parsing should be handle out of this class, this code for example of parsing.
        List<String> permStrings = Arrays.asList(result.getArrays().get(1));
        try {
            Parser<ExamplePermission> permParser = new StandardParser(ExamplePermission.class);
            setPermissions(permParser.readEntities(permStrings, false));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
    public List<ExamplePermission> getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(List<ExamplePermission> permissions) {
        this.permissions = permissions;
    }

    /**
     * Example permission entry.
     */
    public static class ExamplePermission implements ECSVAble {
        
        private String userName;
        
        private Boolean mayAcess;

        public ExamplePermission() {
        }

        @Override
        public String getEntityType() {
            //Cause key-value pair can't use type prefix.
            return null;
        }

        @Override
        public int getBasicCount() {
            //Only two words.
            return 2;
        }

        @Override
        public int getArrayCount() {
            //No arrays.
            return 0;
        }

        @Override
        public ECSVType getCurrentType() {
            return ECSVType.KEY_VALUE;
        }

        @Override
        public void pack(StringBuffer buffer, Boolean appendType) {
            buffer.append(this.packToString(appendType));
        }

        @Override
        public String packToString(Boolean appendType) {
            //Example: Tom:false
            return getUserName() + ECSVFormat.KEY_VALUE_SEPARATOR + getMayAcess().toString();
        }

        @Override
        public void unPack(ParseResult result) {
            this.setUserName(result.getBasics()[0]);
            //Non-string value recovery.
            this.setMayAcess((Boolean) Boolean.parseBoolean(result.getBasics()[1]));
        }

        /**
         * @return the userName
         */
        public String getUserName() {
            return userName;
        }

        /**
         * @param userName the userName to set
         */
        public void setUserName(String userName) {
            this.userName = userName;
        }

        /**
         * @return the mayAcess
         */
        public Boolean getMayAcess() {
            return mayAcess;
        }

        /**
         * @param mayAcess the mayAcess to set
         */
        public void setMayAcess(Boolean mayAcess) {
            this.mayAcess = mayAcess;
        }
    }    
}
