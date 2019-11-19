/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import com.arsdigita.cms.contenttypes.ui.ArticleInJournalCreateForm;
import com.arsdigita.cms.contenttypes.ui.ArticleInJournalPropertiesStep;

import org.hibernate.envers.Audited;
import org.librecms.CmsConstants;
import org.librecms.contenttypes.AuthoringKit;
import org.librecms.contenttypes.AuthoringStep;
import org.librecms.contenttypes.ContentTypeDescription;
import org.scientificcms.publications.ArticleInJournal;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "ARTICLE_IN_COLLECTED_VOLUME_ITEMS", schema = DB_SCHEMA)
@Audited
@ContentTypeDescription(
    labelBundle = "org.scientificcms.publications.contenttypes.ArticleInJournal",
    descriptionBundle = "org.scientificcms.publications.contenttypes.ArticleInJournal"
)
@AuthoringKit(
    createComponent = ArticleInJournalCreateForm.class,
    steps = {
        @AuthoringStep(
            component = ArticleInJournalPropertiesStep.class,
            labelBundle = CmsConstants.CMS_BUNDLE,
            labelKey = "cms.contenttypes.shared.basic_properties.title",
            descriptionBundle = CmsConstants.CMS_BUNDLE,
            descriptionKey = "cms.contenttypes.shared.basic_properties"
                                 + ".description",
            order = 1
        )
    }
)
public class ArticleInJournalItem extends PublicationItem<ArticleInJournal> {

    private static final long serialVersionUID = 1L;

//    @OneToOne(cascade = {CascadeType.DETACH, 
//                         CascadeType.MERGE, 
//                         CascadeType.PERSIST, 
//                         CascadeType.REFRESH
//    })
//    @JoinColumn(name = "ARTICLE_ID")
//    private ArticleInJournal article;
//    @Override
//    public ArticleInJournal getPublication() {
//        return article;
//    }
//
//    @Override
//    protected void setPublication(final ArticleInJournal article) {
//        this.article = article;
//    }
    @Override
    public int hashCode() {
        int hash = super.hashCode();
//        hash = 67 * hash + Objects.hashCode(this.article);
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
        if (!(obj instanceof ArticleInJournalItem)) {
            return false;
        }
        final ArticleInJournalItem other
                                       = (ArticleInJournalItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(this.article, other.getPublication());
        return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof ArticleInJournalItem;
    }

//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format(", article = %s%s",
//                                            Objects.toString(article),
//                                            data));
//    }

}
