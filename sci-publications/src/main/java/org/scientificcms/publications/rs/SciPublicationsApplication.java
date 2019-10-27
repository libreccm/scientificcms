/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.scientificcms.publications.rs;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author <a href="mailto:jens.pelzetter@googlemail.com">Jens Pelzetter</a>
 */
@ApplicationPath("/scipublications")
public class SciPublicationsApplication extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        
        final Set<Class<?>> classes = new HashSet<>();
        
        classes.add(Journals.class);
        classes.add(Publishers.class);
        classes.add(Publications.class);
        classes.add(SeriesRs.class);
        
        return classes;
        
    }
    
    
}
