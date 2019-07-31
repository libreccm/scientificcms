/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.contenttypes.sciproject;

import org.hibernate.envers.Audited;
import org.librecms.assets.ContactableEntity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import static org.scientificcms.contenttypes.sciproject.SciProjectConstants.*;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@Entity
@Audited
@Table(name = "PROJECT_CONTACTS", schema = DB_SCHEMA)
public class Contact implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "CONTACT_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long contactId;
    
    @Column(name = "CONTACT_TYPE", length = 255, nullable = true)
    private String contactType;
    
    @Column(name = "CONTACT_ORDER")
    private long order;
    
    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private SciProject project;
    
    @ManyToOne
    @JoinColumn(name = "CONTACTABLE_ID")
    private ContactableEntity contactable;
    
}
