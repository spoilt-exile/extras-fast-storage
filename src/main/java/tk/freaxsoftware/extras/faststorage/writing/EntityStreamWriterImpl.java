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

package tk.freaxsoftware.extras.faststorage.writing;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tk.freaxsoftware.extras.faststorage.exception.EntityProcessingException;
import tk.freaxsoftware.extras.faststorage.exception.EntityStateException;
import tk.freaxsoftware.extras.faststorage.generic.ECSVAble;
import tk.freaxsoftware.extras.faststorage.generic.ECSVDefinition;
import tk.freaxsoftware.extras.faststorage.generic.ECSVFormat;

/**
 * Entity stream writer implementation.
 * @author Stanislav Nepochatov
 * @param <E> all ECSVAble successors;
 */
public class EntityStreamWriterImpl<E extends ECSVAble, K> implements EntityStreamWriter<E> {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(EntityStreamWriterImpl.class);
    
    /**
     * Entity class definition.
     */
    private ECSVDefinition entityDefinition;
    
    /**
     * Default constructor.
     * @param definition supplied entity definition;
     */
    public EntityStreamWriterImpl(ECSVDefinition definition) {
        this.entityDefinition = definition;
    }

    @Override
    public String writeEntities(List<E> entities) throws EntityProcessingException {
        StringWriter writer = new StringWriter();
        writeEntities(entities, writer);
        return writer.toString();
    }

    @Override
    public void writeEntities(List<E> entities, Writer entityWriter) throws EntityProcessingException {
        try {
            StringWriter buffer = new StringWriter();
            EntityWriterImpl<K> writer = new EntityWriterImpl<>(this.entityDefinition, buffer);
            for (E entity: entities) {
                writer.reset();
                entity.writeToECSV(writer);
                buffer.append(ECSVFormat.LINE_BREAK_CHAR);
            }
            entityWriter.write(buffer.toString());
        } catch (IOException ioex) {
            LOGGER.error("Unable to write result of writing into writer", ioex);
            throw new EntityProcessingException("Unable to write result of writing into writer", ioex);
        } catch (EntityStateException esex) {
            LOGGER.error("Unable to write entity", esex);
            throw new EntityProcessingException("Unable to write entity", esex);
        }
    }
}
