/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "PUBLISHERS", schema = DB_SCHEMA)
@Audited
public class Publisher implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "PUBLISHER_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long publisherId;

    @Column(name = "UUID", unique = true, nullable = false)
    private String uuid;

    /**
     * Name of the publisher. The title of the asset is only for internal use,
     * this property should be used for name of the publisher which is displayed
     * on public pages.
     */
    @Column(name = "NAME", length = 2048, nullable = false)
    private String name;

    /**
     * The place of the publisher.
     */
    @Column(name = "PLACE", length = 2048)
    private String place;

    @OneToMany(mappedBy = "publisher")
    private List<PublicationWithPublisher> publications;

    public Publisher() {
        super();

        publications = new ArrayList<>();
    }

    public long getPublisherId() {
        return publisherId;
    }

    protected void setPublisherId(final long publisherId) {
        this.publisherId = publisherId;
    }

    public String getUuid() {
        return uuid;
    }

    protected void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(final String place) {
        this.place = place;
    }

    public List<PublicationWithPublisher> getPublications() {
        if (publications == null) {
            return null;
        } else {
            return Collections.unmodifiableList(publications);
        }
    }

    protected void addPublication(final PublicationWithPublisher publication) {
        publications.add(publication);
    }

    protected void removePublication(
        final PublicationWithPublisher publication) {
        publications.remove(publication);
    }

    protected void setPublications(
        final List<PublicationWithPublisher> publications) {
        this.publications = new ArrayList<>(publications);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 29 * hash + (int) (publisherId ^ (publisherId >>> 32));
        hash = 29 * hash + Objects.hashCode(uuid);
        hash = 29 * hash + Objects.hashCode(name);
        hash = 29 * hash + Objects.hashCode(place);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (!(obj instanceof Publisher)) {
            return false;
        }
        final Publisher other = (Publisher) obj;

        if (!other.canEqual(this)) {
            return false;
        }
        if (publisherId != other.getPublisherId()) {
            return false;
        }
        if (!Objects.equals(uuid, other.getUuid())) {
            return false;
        }
        if (!Objects.equals(name, other.getName())) {
            return false;
        }
        return Objects.equals(place, other.getPlace());
    }

    public boolean canEqual(final Object obj) {
        return obj instanceof Publisher;
    }

    public String toString(final String data) {
        return String.format("%s{ "
                                 + "publisherId = %d, "
                                 + "uuid = \"%s\", "
                                 + "name = \"%s\", "
                                 + "place = \"%s\"%s",
                             super.toString(),
                             publisherId,
                             uuid,
                             name,
                             place,
                             data);
    }

}
