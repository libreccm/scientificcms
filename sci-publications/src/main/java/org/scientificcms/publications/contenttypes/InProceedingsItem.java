/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.contenttypes;

import com.arsdigita.cms.contenttypes.ui.InProceedingsCreateForm;
import com.arsdigita.cms.contenttypes.ui.InProceedingsPropertiesStep;

import org.hibernate.envers.Audited;
import org.librecms.CmsConstants;
import org.librecms.contenttypes.AuthoringKit;
import org.librecms.contenttypes.AuthoringStep;
import org.librecms.contenttypes.ContentTypeDescription;
import org.scientificcms.publications.InProceedings;

import javax.persistence.Entity;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "INPROCEEDINGS_ITEMS", schema = DB_SCHEMA)
@Audited
@ContentTypeDescription(
    labelBundle = "org.scientificcms.publications.contenttypes.InProceedings",
    descriptionBundle
    = "org.scientificcms.publications.contenttypes.InProceedings"
)
@AuthoringKit(
    createComponent = InProceedingsCreateForm.class,
    steps = {
        @AuthoringStep(
            component = InProceedingsPropertiesStep.class,
            labelBundle = CmsConstants.CMS_BUNDLE,
            labelKey = "cms.contenttypes.shared.basic_properties.title",
            descriptionBundle = CmsConstants.CMS_BUNDLE,
            descriptionKey = "cms.contenttypes.shared.basic_properties"
                                 + ".description",
            order = 1
        )
    }
)
public class InProceedingsItem extends PublicationItem<InProceedings> {

    private static final long serialVersionUID = 1L;

//    @OneToOne(cascade = {CascadeType.DETACH,
//                         CascadeType.MERGE,
//                         CascadeType.PERSIST,
//                         CascadeType.REFRESH
//    })
//    @JoinColumn(name = "INPROCEEDINGS_ID")
//    private InProceedings inProcedings;
//
//    @Override
//    public InProceedings getPublication() {
//        return inProcedings;
//    }
//
//    @Override
//    protected void setPublication(final InProceedings inProceedings) {
//        this.inProcedings = inProceedings;
//    }
    @Override
    public int hashCode() {
        int hash = super.hashCode();
//        hash = 67 * hash + Objects.hashCode(inProcedings);
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
        if (!(obj instanceof InProceedingsItem)) {
            return false;
        }
        final InProceedingsItem other
                                    = (InProceedingsItem) obj;
//        if (!other.canEqual(this)) {
//            return false;
//        }
//        return Objects.equals(this.inProcedings, other.getPublication());
        return other.canEqual(this);
    }

    @Override
    public boolean canEqual(final Object obj) {
        return obj instanceof InProceedingsItem;
    }

//    @Override
//    public String toString(final String data) {
//        return super.toString(String.format(", inProceedings = %s%s",
//                                            Objects.toString(inProcedings),
//                                            data));
//    }
}
