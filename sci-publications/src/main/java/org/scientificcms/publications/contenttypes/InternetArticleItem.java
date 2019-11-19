/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import com.arsdigita.cms.contenttypes.ui.InternetArticleCreateForm;
import com.arsdigita.cms.contenttypes.ui.InternetArticlePropertiesStep;

import org.hibernate.envers.Audited;
import org.librecms.CmsConstants;
import org.librecms.contenttypes.AuthoringKit;
import org.librecms.contenttypes.AuthoringStep;
import org.librecms.contenttypes.ContentTypeDescription;
import org.scientificcms.publications.InternetArticle;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "INTERNET_ARTICLE_ITEMS", schema = DB_SCHEMA)
@Audited
@ContentTypeDescription(
    labelBundle = "org.scientificcms.publications.contenttypes.InternetArticle",
    descriptionBundle
        = "org.scientificcms.publications.contenttypes.InternetArticle"
)
@AuthoringKit(
    createComponent = InternetArticleCreateForm.class,
    steps = {
        @AuthoringStep(
            component = InternetArticlePropertiesStep.class,
            labelBundle = CmsConstants.CMS_BUNDLE,
            labelKey = "cms.contenttypes.shared.basic_properties.title",
            descriptionBundle = CmsConstants.CMS_BUNDLE,
            descriptionKey = "cms.contenttypes.shared.basic_properties"
                                 + ".description",
            order = 1
        )
    }
)
public class InternetArticleItem extends PublicationItem<InternetArticle> {

    private static final long serialVersionUID = 1L;

//    @OneToOne(cascade = {CascadeType.DETACH,
//                         CascadeType.MERGE,
//                         CascadeType.PERSIST,
//                         CascadeType.REFRESH
//    })
//    @JoinColumn(name = "EXPERTISE_ID")
//    private InternetArticle expertise;
//
//    @Override
//    public InternetArticle getPublication() {
//        return expertise;
//    }
//
//    @Override
//    protected void setPublication(final InternetArticle internetArticle) {
//        this.expertise = internetArticle;
//    }
    @Override
    public int hashCode() {
        int hash = super.hashCode();
//        hash = 67 * hash + Objects.hashCode(expertise);
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
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(this.expertise, other.getPublication());
        return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof InternetArticleItem;
    }

//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format(", internetArticle = %s%s",
//                                            Objects.toString(expertise),
//                                            data));
//    }
}
