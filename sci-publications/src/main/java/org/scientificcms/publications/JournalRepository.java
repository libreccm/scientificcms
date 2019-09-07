/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.libreccm.core.AbstractEntityRepository;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
public class JournalRepository extends AbstractEntityRepository<Long, Journal>{

    private static final long serialVersionUID = 1L;

    @Override
    public Class<Journal> getEntityClass() {
        return Journal.class;
    }

    @Override
    public String getIdAttributeName() {
        return "journalId";
    }

    @Override
    public Long getIdOfEntity(final Journal entity) {
        return entity.getJournalId();
    }

    @Override
    public boolean isNew(final Journal entity) {
        return entity.getJournalId() == 0;
    }
    
}
