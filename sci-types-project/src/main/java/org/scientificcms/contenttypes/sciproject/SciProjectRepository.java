/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.contenttypes.sciproject;

import org.librecms.assets.Person;
import org.librecms.contentsection.ContentItemRepository;

import java.time.LocalDate;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@RequestScoped
public class SciProjectRepository extends ContentItemRepository {

    private static final long serialVersionUID = 1L;

    @Transactional(Transactional.TxType.REQUIRED)
    public List<SciProject> findWhereBeginIsBefore(final LocalDate date) {

        return getEntityManager()
            .createNamedQuery("SciProject.findWhereBeginIsBefore",
                              SciProject.class)
            .setParameter("date", date)
            .getResultList();

    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<SciProject> findWhereBeginIs(final LocalDate date) {

        return getEntityManager()
            .createNamedQuery("SciProject.findWhereBeginIs", SciProject.class)
            .setParameter("date", date)
            .getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<SciProject> findWhereBeginIsAfter(final LocalDate date) {

        return getEntityManager()
            .createNamedQuery("SciProject.findWhereBeginIsAfter",
                              SciProject.class)
            .setParameter("date", date)
            .getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<SciProject> findWhereEndIsBefore(final LocalDate date) {

        return getEntityManager()
            .createNamedQuery("SciProject.findWhereEndIsBefore",
                              SciProject.class)
            .setParameter("date", date)
            .getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<SciProject> findWhereEndIs(final LocalDate date) {
        return getEntityManager()
            .createNamedQuery("SciProject.findWhereEndIs",
                              SciProject.class)
            .setParameter("date", date)
            .getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<SciProject> findWhereEndIsAfter(final LocalDate date) {

        return getEntityManager()
            .createNamedQuery("SciProject.findWhereEndIsAfter",
                              SciProject.class)
            .setParameter("date", date)
            .getResultList();
    }

    @Transactional(Transactional.TxType.REQUIRED)
    public List<SciProject> findByMember(final Person member) {

        return getEntityManager()
            .createNamedQuery("SciProject.findByMember", SciProject.class)
            .setParameter("member", member)
            .getResultList();
    }

}
