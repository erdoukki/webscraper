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
import org.mc2.webscraper.wickypedia.Query;

/**
 *
 * @author marco
 */
public class ComposersByEra extends Query {
    
    public static final String URL = "https://en.wikipedia.org/wiki/List_of_classical_music_composers_by_era";
    public static final String NAME = "Art Music";

    public static final String TABLE_MODEL_100 = "100";
    public static final String TABLE_MODEL_102 = "102";
    public static final String TABLE_MODEL_200 = "200";
    public static final String TABLE_MODEL_500 = "500";
    public static final String TABLE_MODEL_UNKNOWN= "000";
    
   
    private final ArrayList<Period> periods;
    public ComposersByEra(){
       super ("", "");
       this.periods= new ArrayList<>();
    }
    public ComposersByEra(String title, ArrayList<Period> periods){
       super (URL, title);
       this.periods= periods;
    }
    
    public String getName() {
        return NAME;
    }
        
    public ArrayList<Period> getPeriods() {
        return periods;
    }
    
    @Override
    public String toString(){
        
        return this.getTitle();
        
    }
}
