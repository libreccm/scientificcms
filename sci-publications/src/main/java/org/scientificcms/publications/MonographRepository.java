/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class MonographRepository
    extends AbstractPublicationWithPublisherRepository<Monograph> {

    private static final long serialVersionUID = 1L;

    @Override
    public Class<Monograph> getEntityClass() {
        return Monograph.class;
    }

}
