/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import com.arsdigita.cms.contenttypes.ui.GreyLiteratureCreateForm;
import com.arsdigita.cms.contenttypes.ui.GreyLiteraturePropertiesStep;

import org.hibernate.envers.Audited;
import org.librecms.CmsConstants;
import org.librecms.contenttypes.AuthoringKit;
import org.librecms.contenttypes.AuthoringStep;
import org.librecms.contenttypes.ContentTypeDescription;
import org.scientificcms.publications.GreyLiterature;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "GREY_LITERATURE_ITEMS", schema = DB_SCHEMA)
@Audited
@ContentTypeDescription(
    labelBundle = "org.scientificcms.publications.contenttypes.GreyLiterature",
    descriptionBundle
    = "org.scientificcms.publications.contenttypes.GreyLiterature"
)
@AuthoringKit(
    createComponent = GreyLiteratureCreateForm.class,
    steps = {
        @AuthoringStep(
            component = GreyLiteraturePropertiesStep.class,
            labelBundle = CmsConstants.CMS_BUNDLE,
            labelKey = "cms.contenttypes.shared.basic_properties.title",
            descriptionBundle = CmsConstants.CMS_BUNDLE,
            descriptionKey = "cms.contenttypes.shared.basic_properties"
                                 + ".description",
            order = 1
        )
    }
)
public class GreyLiteratureItem extends PublicationItem<GreyLiterature> {

    private static final long serialVersionUID = 1L;

//    @OneToOne(cascade = {CascadeType.DETACH,
//                         CascadeType.MERGE,
//                         CascadeType.PERSIST,
//                         CascadeType.REFRESH
//    })
//    @JoinColumn(name = "GREY_LITERATURE_ID")
//    private GreyLiterature greyLiterature;
//
//    @Override
//    public GreyLiterature getPublication() {
//        return greyLiterature;
//    }
//
//    @Override
//    protected void setPublication(final GreyLiterature greyLiterature) {
//        this.greyLiterature = greyLiterature;
//    }
    @Override
    public int hashCode() {
        int hash = super.hashCode();
//        hash = 67 * hash + Objects.hashCode(greyLiterature);
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
        if (!(obj instanceof GreyLiteratureItem)) {
            return false;
        }
        final GreyLiteratureItem other
                                     = (GreyLiteratureItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(this.greyLiterature, other.getPublication());
        return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof GreyLiteratureItem;
    }

//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format(", greyLiterature = %s%s",
//                                            Objects.toString(greyLiterature),
//                                            data));
//    }
}
