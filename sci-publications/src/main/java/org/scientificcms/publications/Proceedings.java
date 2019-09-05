/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications;

import org.hibernate.envers.Audited;
import org.librecms.assets.Organization;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import static org.scientificcms.publications.SciPublicationsConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Table(name = "PROCEEDINGS", schema = DB_SCHEMA)
@Audited
public class Proceedings extends PublicationWithPublisher {

    private static final long serialVersionUID = 1L;

    @Column(name = "NAME_OF_CONFERENCE", length = 2048)
    private String nameOfConference;

    @Column(name = "PLACE_OF_CONFERENCE", length = 2048)
    private String placeOfConference;

    @Column(name = "START_DATE")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORGANIZER_ID")
    private Organization organizer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "proceedings")
    private List<InProceedings> papers;

    public Proceedings() {
        super();

        papers = new ArrayList<>();
    }

    public String getNameOfConference() {
        return nameOfConference;
    }

    public void setNameOfConference(final String nameOfConference) {
        this.nameOfConference = nameOfConference;
    }

    public String getPlaceOfConference() {
        return placeOfConference;
    }

    public void setPlaceOfConference(final String placeOfConference) {
        this.placeOfConference = placeOfConference;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public Organization getOrganizer() {
        return organizer;
    }

    public void setOrganizer(final Organization organizer) {
        this.organizer = organizer;
    }

    public List<InProceedings> getPapers() {
        return Collections.unmodifiableList(papers);
    }

    protected void addPaper(final InProceedings paper) {
        papers.add(paper);
    }

    protected void removePaper(final InProceedings paper) {
        papers.remove(paper);
    }

    protected void setPapers(final List<InProceedings> papers) {
        this.papers = new ArrayList<>(papers);
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 97 * hash + Objects.hashCode(nameOfConference);
        hash = 97 * hash + Objects.hashCode(placeOfConference);
        hash = 97 * hash + Objects.hashCode(startDate);
        hash = 97 * hash + Objects.hashCode(endDate);
        hash = 97 * hash + Objects.hashCode(organizer);
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
        if (!(obj instanceof Proceedings)) {
            return false;
        }
        final Proceedings other = (Proceedings) obj;
        if (!other.canEqual(this)) {
            return false;
        }
        if (!Objects.equals(nameOfConference, other.getNameOfConference())) {
            return false;
        }
        if (!Objects.equals(placeOfConference, other.getPlaceOfConference())) {
            return false;
        }
        if (!Objects.equals(startDate,
                            other.getStartDate())) {
            return false;
        }
        if (!Objects.equals(endDate, other.getEndDate())) {
            return false;
        }
        return Objects.equals(organizer, other.getOrganizer());
    }

    @Override
    public boolean canEqual(final Object obj) {

        return obj instanceof Proceedings;
    }

    @Override
    public String toString(final String data) {

        return super.toString(String.format("nameOfConference = \"%s\", "
                              + "placeOfConference = \"%s\", "
                              + "startDate = \"%s\", "
                              + "endDate = \"%s\", "
                              + "organizer = %s, "
                              + "papers = %s%s",
                          nameOfConference,
                          placeOfConference,
                          Objects.toString(startDate),
                          Objects.toString(endDate),
                          Objects.toString(organizer),
                          Objects.toString(papers),
                          data)
        );
    }

}
