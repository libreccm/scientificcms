/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import org.hibernate.envers.Audited;
import org.scientificcms.publications.InternetArticle;

import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "INTERNET_ARTICLE_ITEMS", schema = DB_SCHEMA)
@Audited
public class InternetArticleItem 
    extends AbstractPublicationItem<InternetArticle> {

    private static final long serialVersionUID = 1L;

    @OneToOne
    @JoinColumn(name = "EXPERTISE_ID")
    private InternetArticle expertise;

    @Override
    public InternetArticle getPublication() {
        return expertise;
    }

    @Override
    protected void setPublication(final InternetArticle internetArticle) {
        this.expertise = internetArticle;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 67 * hash + Objects.hashCode(expertise);
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
        if (!(obj instanceof InternetArticleItem)) {
            return false;
        }
        final InternetArticleItem other
                                   = (InternetArticleItem) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        return Objects.equals(this.expertise, other.getPublication());
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof InternetArticleItem;
    }

    @Override
    public String toString(final String data) {
        return super.toString(String.format(", internetArticle = %s%s",
                                            Objects.toString(expertise),
                                            data));
    }

}
