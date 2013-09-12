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

import com.prodyna.pac.conference.ejb.facade.exception.PersistenceException;

import javax.persistence.Parameter;
import java.util.List;

/**
 * PersistenceQuery
 * <p/>
 * Author: Nicolas Moser
 * Date: 11.09.13
 * Time: 18:33
 */
public interface PersistenceQuery<T> {

	/**
	 * Set a parameter into the query.
	 *
	 * @param name
	 * 		the parameter name
	 * @param value
	 * 		the parameter value
	 *
	 * @return the query instance for chaining
	 *
	 * @throws PersistenceException
	 * 		when the parameter cannot be set into the query or does not
	 * 		exist
	 */
	PersistenceQuery<T> setParameter(String name, Object value) throws PersistenceException;

	/**
	 * Set a parameter into the query.
	 *
	 * @param name
	 * 		the parameter name
	 * @param value
	 * 		the parameter value
	 *
	 * @return the query instance for chaining
	 *
	 * @throws PersistenceException
	 * 		when the parameter cannot be set into the query or does not
	 * 		exist
	 */
	PersistenceQuery<T> setParameter(Parameter<T> name, T value) throws PersistenceException;

	/**
	 * Set the query maximum result amount.
	 *
	 * @param maxResults
	 * 		the maximum result size
	 *
	 * @return the query instance for chaining
	 *
	 * @throws PersistenceException
	 * 		when the maximum results cannot be set
	 */
	PersistenceQuery<T> setMaxResults(int maxResults) throws PersistenceException;

	/**
	 * Set the result starting row-number.
	 *
	 * @param firstResult
	 * 		the first result
	 *
	 * @return the query instance for chaining
	 *
	 * @throws PersistenceException
	 * 		when the first results cannot be set
	 */
	PersistenceQuery<T> setFirstResult(int firstResult) throws PersistenceException;

	/**
	 * Retrieves and expects exactly one single result.
	 *
	 * @return the single result
	 *
	 * @throws PersistenceException
	 * 		when no or more than one result is returned by the query
	 */
	T getSingleResult() throws PersistenceException;

	/**
	 * Retrieves a list of results.
	 *
	 * @return the result list
	 *
	 * @throws PersistenceException
	 * 		when the query is not valid
	 */
	List<T> getResultList() throws PersistenceException;

	/**
	 * Executes an update query.
	 *
	 * @return the amount of changed rows
	 *
	 * @throws PersistenceException
	 * 		when the query is not valid
	 */
	int executeUpdate() throws PersistenceException;
}
