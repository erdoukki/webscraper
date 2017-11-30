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
package org.mc2.webscraper.wickypedia.elements;

import java.util.ArrayList;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *
 * @author marco
 */
public class ComposerGroup {
    
    
    public static enum DISCARDED {
    
        See_also,
        Sources,
        External_links,
    };
    @JsonIgnore
    private final ComposerGroup parent;
    private final Integer id;
    private final String name;
    private final String period;
    private final String school;
 
    private ArrayList<ComposerGroup> children;
    private ArrayList<Composer> composers;
    
    private final Boolean isValid;
    
    public ComposerGroup(ComposerGroup parent, Integer id, String name, String period, String school, String idAttr){
        
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
    
    public Boolean  isValid() {
        return (isValid);
    }
    @JsonIgnore
    public ComposerGroup getParent() {
        return parent;
    }
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPeriod() {
        return period;
    }

    public String getSchool() {
        return school;
    }
   
    public ArrayList<ComposerGroup> getChildren() {
                
        return children;
    }

    public ArrayList<Composer> getComposers() {

        return composers;
    }
    public Integer getChildrenTotal() {
                
        return children.size();
    }

    public Integer getComposersTotal() {

        return composers.size();
    }
    
    
    @Override
    public String toString(){
        
        return this.id +" - "+this.name;
    }
}
