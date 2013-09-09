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

package com.prodyna.pac.conference.ejb.model;

import javax.persistence.*;

/**
 * Speaker
 * <p/>
 * Author: Nicolas Moser
 * Date: 06.09.13
 * Time: 17:36
 */
@Entity
public class Speaker implements Datatype {

    private Long id;

    private Long version;

    private String name;

    private String desciption;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Override
    public Long getId() {
        return this.id;
    }

    /**
     * Setter for the entity identifier.
     *
     * @param id the ID to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Version
    @Override
    public Long getVersion() {
        return version;
    }

    /**
     * Setter for the entity version.
     *
     * @param version the version to set
     */
    public void setVersion(Long version) {
        this.version = version;
    }

    /**
     * Getter for the speakers name.
     *
     * @return name of the speaker
     */
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    /**
     * Setter for the speakers name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the speakers profile description.
     *
     * @return the profile description
     */
    public String getDesciption() {
        return desciption;
    }

    /**
     * Setter for the speakers profile description.
     *
     * @param desciption the description to set
     */
    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }
}
