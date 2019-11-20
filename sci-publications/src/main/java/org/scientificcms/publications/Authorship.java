/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;
import org.librecms.assets.Person;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "AUTHORSHIPS", schema = DB_SCHEMA)
@Audited
public class Authorship implements Serializable, Comparable<Authorship> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AUTHORSHIP_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long authorshipId;

    @Column(name = "UUID", length = 36, nullable = false)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "PUBLICATION_ID")
    private Publication publication;

    @ManyToOne
    @JoinColumn(name = "AUTHOR_ID")
    private Person author;

    @Column(name = "EDITOR")
    private boolean editor;

    @Column(name = "AUTHOR_ORDER")
    private long authorOrder;

    public long getAuthorshipId() {
        return authorshipId;
    }

    protected void setAuthorshipId(final long authorshipId) {
        this.authorshipId = authorshipId;
    }

    public String getUuid() {
        return uuid;
    }

    protected void setUuid(final String uuid) {
        this.uuid = uuid;
    }

    public Publication getPublication() {
        return publication;
    }

    protected void setPublication(final Publication publication) {
        this.publication = publication;
    }

    public Person getAuthor() {
        return author;
    }

    protected void setAuthor(final Person author) {
        this.author = author;
    }

    public boolean isEditor() {
        return editor;
    }

    public void setEditor(final boolean editor) {
        this.editor = editor;
    }

    public long getAuthorOrder() {
        return authorOrder;
    }

    public void setAuthorOrder(final long authorOrder) {
        this.authorOrder = authorOrder;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (int) (authorshipId ^ (authorshipId >>> 32));
        hash = 59 * hash + Objects.hashCode(uuid);
        hash = 59 * hash + (editor ? 1 : 0);
        hash = 59 * hash + (int) (authorOrder ^ (authorOrder >>> 32));
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
        if (!(obj instanceof Authorship)) {
            return false;
        }
        final Authorship other = (Authorship) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (authorshipId != other.getAuthorshipId()) {
            return false;
        }
        if (!Objects.equals(uuid, other.getUuid())) {
            return false;
        }
        if (editor != other.isEditor()) {
            return false;
        }
        return authorOrder == other.getAuthorOrder();
    }

    public boolean canEqual(final Object obj) {
        return obj instanceof Authorship;
    }

    @Override
    public int compareTo(final Authorship other) {
        return Long.compare(authorOrder, other.getAuthorOrder());
    }

    @Override
    public final String toString() {
        return toString("");
    }

    public String toString(final String data) {

        return String.format("%s{ "
                                 + "authorshipId = %d, "
                                 + "uuid = %s, "
                                 + "author = %s " 
                                 + "editor = %b, "
                                 + "authorOrder = %d%s "
                                 + "}",
                             super.toString(),
                             authorshipId,
                             uuid,
                             Objects.toString(author),
                             editor,
                             authorOrder,
                             data);
    }

}
