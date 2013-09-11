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

package com.prodyna.pac.conference.ejb.facade.datatypes;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Talk
 * <p/>
 * Author: Nicolas Moser
 * Date: 06.09.13
 * Time: 17:37
 */
@Entity
public class Talk implements Datatype {

    private Long id;

    private Long version;

    private String name;

    private String description;

    private Integer duration;

    private Room room;

    private Conference conference;

    private List<TalkSpeaker> speakers;

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
    @Column(nullable = false)
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
     * Getter for the talks name.
     *
     * @return the name of the talk
     */
    @Column(nullable = false)
    public String getName() {
        return name;
    }

    /**
     * Setter for the talks name.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the talk description.
     *
     * @return the description
     */
    @Column(nullable = true)
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the talk description.
     *
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the duration in minutes.
     *
     * @return the duration in minutes
     */
    @Column(nullable = false)
    public Integer getDuration() {
        return duration;
    }

    /**
     * Setter for the duration in minutes.
     *
     * @param duration the duration to set
     */
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * Getter for the room.
     *
     * @return the room
     */
    @JoinColumn
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, optional = false)
    public Room getRoom() {
        return room;
    }

    /**
     * Setter for the room.
     *
     * @param room the room to set
     */
    public void setRoom(Room room) {
        this.room = room;
    }

    /**
     * Getter for the conference.
     *
     * @return the conference
     */
    @JoinColumn
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.EAGER, optional = false)
    public Conference getConference() {
        return conference;
    }

    /**
     * Setter for the conference.
     *
     * @param conference the conference to set
     */
    public void setConference(Conference conference) {
        this.conference = conference;
    }

    /**
     * Getter for the speakers list.
     *
     * @return the list of speakers
     */
    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = false, cascade = {CascadeType.ALL})
    @JoinColumn
    public List<TalkSpeaker> getSpeakers() {
        if (speakers == null) {
            speakers = new ArrayList<TalkSpeaker>();
        }
        return speakers;
    }

    /**
     * Setter for the speakers.
     * <p/>
     * Must not be invoked by clients since it is a persistence provider operation.
     *
     * @param speakers the speakers to set
     */
    void setSpeakers(List<TalkSpeaker> speakers) {
        this.speakers = speakers;
    }
}
