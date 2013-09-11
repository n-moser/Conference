/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2013 Nicolas Moser
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.prodyna.pac.conference.ejb.beans.persistence;

import com.prodyna.pac.conference.ejb.facade.datatype.Datatype;
import com.prodyna.pac.conference.ejb.facade.exception.PersistenceException;

/**
 * PersistenceManager
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 16:50
 */
public interface PersistenceManager {


    /**
     * Persist the given Entity to the Database.
     *
     * @param entity the entity to persist
     *
     * @return the persisted entity
     *
     * @throws PersistenceException when the entity cannot be created
     */
    <T extends Datatype> T persist(T entity) throws PersistenceException;

    /**
     * Update the given Entity to the Database.
     *
     * @param entity the entity to merge
     *
     * @return the merged entity
     *
     * @throws PersistenceException when the entity cannot be merged
     */
    <T extends Datatype> T merge(T entity) throws PersistenceException;

    /**
     * Removes the given Entity from the Database.
     *
     * @param entity the entity to remove
     *
     * @return the removed entity
     *
     * @throws PersistenceException when the entity cannot be removed
     */
    <T extends Datatype> T remove(T entity) throws PersistenceException;

    /**
     * Refreshes the given Entity from the Database.
     *
     * @param entity the entity to refresh
     *
     * @return the refreshed entity
     *
     * @throws PersistenceException when the entity cannot be refreshed
     */
    <T extends Datatype> T refresh(T entity) throws PersistenceException;

    /**
     * Find the Entity with the given Type and ID from the Database.
     *
     * @param type the entity type
     * @param id   the entity id
     *
     * @return the loaded entity
     *
     * @throws PersistenceException when the entity cannot be loaded
     */
    <T extends Datatype> T find(Class<T> type, Long id) throws PersistenceException;

    /**
     * Detaches the given Entity from the Persistence Manager. Changes to the
     * entity are not synchronized to the Database.
     *
     * @param entity the entity to detach
     *
     * @return the detached entity
     *
     * @throws PersistenceException when the entity cannot be detached
     */
    <T extends Datatype> T detach(T entity) throws PersistenceException;

    /**
     * Creates a new Database Query.
     *
     * @param query the query String
     * @param type  the result type
     *
     * @return
     *
     * @throws PersistenceException
     */
    <T extends Datatype> PersistenceQuery<T> createQuery(String query, Class<T> type) throws PersistenceException;

    /**
     * Checks whether the persistence manager manages the given entity.
     *
     * @param entity the entity to check
     *
     * @return <b>true</b> if the entity is managed, <b>false</b> if not
     *
     * @throws PersistenceException when the containment cannot be checked
     */
    <T extends Datatype> boolean contains(T entity) throws PersistenceException;

    /**
     * Detaches all entities from the Persistence Manager.
     *
     * @throws PersistenceException when the entities cannot be removed
     */
    void clear() throws PersistenceException;
}
