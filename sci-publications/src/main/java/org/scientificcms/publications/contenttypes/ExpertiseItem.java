/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;


import com.arsdigita.cms.contenttypes.ui.ExpertiseCreateForm;
import com.arsdigita.cms.contenttypes.ui.ExpertisePropertiesStep;

import org.hibernate.envers.Audited;
import org.librecms.CmsConstants;
import org.librecms.contenttypes.AuthoringKit;
import org.librecms.contenttypes.AuthoringStep;
import org.librecms.contenttypes.ContentTypeDescription;
import org.scientificcms.publications.Expertise;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "EXPERTISE_ITEMS", schema = DB_SCHEMA)
@Audited
@ContentTypeDescription(
    labelBundle = "org.scientificcms.publications.contenttypes.Expertise",
    descriptionBundle = "org.scientificcms.publications.contenttypes.Expertise"
)
@AuthoringKit(
    createComponent = ExpertiseCreateForm.class,
    steps = {
        @AuthoringStep(
            component = ExpertisePropertiesStep.class,
            labelBundle = CmsConstants.CMS_BUNDLE,
            labelKey = "cms.contenttypes.shared.basic_properties.title",
            descriptionBundle = CmsConstants.CMS_BUNDLE,
            descriptionKey = "cms.contenttypes.shared.basic_properties"
                                 + ".description",
            order = 1
        )
    }
)
public class ExpertiseItem extends PublicationItem<Expertise> {

    private static final long serialVersionUID = 1L;

//    @OneToOne(cascade = {CascadeType.DETACH,
//                         CascadeType.MERGE,
//                         CascadeType.PERSIST,
//                         CascadeType.REFRESH
//    })
//    @JoinColumn(name = "EXPERTISE_ID")
//    private Expertise expertise;
//
//    @Override
//    public Expertise getPublication() {
//        return expertise;
//    }
//
//    @Override
//    protected void setPublication(final Expertise expertise) {
//        this.expertise = expertise;
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
        if (!(obj instanceof ExpertiseItem)) {
            return false;
        }
        final ExpertiseItem other
                                = (ExpertiseItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(this.expertise, other.getPublication());
return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof ExpertiseItem;
    }

//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format(", expertise = %s%s",
//                                            Objects.toString(expertise),
//                                            data));
//    }

}
