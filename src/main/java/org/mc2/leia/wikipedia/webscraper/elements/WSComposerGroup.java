/*
 * JAVA web-scaper script collections.
 *
 * Copyright (C) 2017 Marco Curti (marcoc1712 at gmail dot com).
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package org.mc2.leia.wikipedia.webscraper.elements;

import org.mc2.leia.wikipedia.API.ComposerGroup;
import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.logging.Logger;

/**
 *
 * @author marco
 */
public class WSComposerGroup implements ComposerGroup  {
    private static Logger log = Logger.getLogger(WSComposerGroup.class.getName());
    
    public static enum DISCARDED {
    
        See_also,
        Sources,
        External_links,
    };
    @JsonIgnore
    private final WSComposerGroup parent;
    private final Integer id;
    private final String name;
    private final String period;
    private final String school;
 
    private ArrayList<WSComposerGroup> children;
    private ArrayList<WSComposer> composers;
    
    private final Boolean isValid;
    
    public WSComposerGroup(WSComposerGroup parent, Integer id, String name, String period, String school, String idAttr){
        
        this.parent = parent;
        this.id = id;
        this.name = name;
        this.period = period;
        this.school = school;
        
        children = new ArrayList<>();
        composers = new ArrayList<>();
        
        isValid = ( name.isEmpty() ||
                    !discard(idAttr)
        );

    }
    private  static boolean discard(String test) {

        for (DISCARDED tobediscarded : DISCARDED.values()) {
            if (tobediscarded.name().equals(test)) {
                return true;
            }
        }

        return false;
    }
    
    @Override
    public Boolean  isValid() {
        return (isValid);
    }
    @JsonIgnore
    @Override
    public WSComposerGroup getParent() {
        return parent;
    }
    
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPeriod() {
        return period;
    }

    @Override
    public String getSchool() {
        return school;
    }
   
    @Override
    public ArrayList<? extends ComposerGroup> getChildren() {
                
        return children;
    }

    @Override
    public ArrayList<WSComposer> getComposers() {

        return composers;
    }
    @Override
    public Integer getChildrenTotal() {
                
        return children.size();
    }

    @Override
    public Integer getComposersTotal() {

        return composers.size();
    }
    
    
    @Override
    public String toString(){
        
        return this.id +" - "+this.name;
    }
}
