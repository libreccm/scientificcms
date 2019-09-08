/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "COLLECTED_VOLUMES", schema = DB_SCHEMA)
@Audited
public class CollectedVolume extends PublicationWithPublisher {

    private static final long serialVersionUID = 1L;

    @OneToMany(mappedBy = "collectedVolume")
    private List<ArticleInCollectedVolume> articles;

    public CollectedVolume() {
        super();

        articles = new ArrayList<>();
    }

    public List<ArticleInCollectedVolume> getArticles() {
        return Collections.unmodifiableList(articles);
    }


    protected void addArticle(final ArticleInCollectedVolume article) {
        articles.add(article);
    }

    protected void removeArticle(final ArticleInCollectedVolume article) {
        articles.remove(article);
    }

    protected void setArticles(final List<ArticleInCollectedVolume> articles) {
        this.articles = new ArrayList<>(articles);
    }

    
    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 29 * hash;
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
        if (!(obj instanceof CollectedVolume)) {
            return false;
        }
        final CollectedVolume other = (CollectedVolume) obj;
        return other.canEqual(this);
    }
    
    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof CollectedVolume;
    }
    
    

}
