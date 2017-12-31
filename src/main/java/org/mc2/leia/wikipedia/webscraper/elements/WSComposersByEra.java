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

import java.util.ArrayList;
import java.util.logging.Logger;
import org.mc2.leia.wikipedia.API.ComposersByEra;
import org.mc2.leia.wikipedia.webscraper.Query;
/**
 *
 * @author marco
 */
public class WSComposersByEra extends Query implements ComposersByEra {
    private static Logger log = Logger.getLogger(WSComposersByEra.class.getName());
    
    public static final String URL = "https://en.wikipedia.org/wiki/List_of_classical_music_composers_by_era";
    public static final String NAME = "Art Music";

    public static final String TABLE_MODEL_100 = "100";
    public static final String TABLE_MODEL_102 = "102";
    public static final String TABLE_MODEL_200 = "200";
    public static final String TABLE_MODEL_500 = "500";
    public static final String TABLE_MODEL_UNKNOWN= "000";
    
   
    private final ArrayList<WSPeriod> periods;
    public WSComposersByEra(){
       super ("", "");
       this.periods= new ArrayList<>();
    }
    public WSComposersByEra(String title, ArrayList<WSPeriod> periods){
       super (URL, title);
       this.periods= periods;
    }
    
    @Override
    public String getName() {
        return NAME;
    }
        
    @Override
    public ArrayList<WSPeriod> getPeriods() {
        return periods;
    }
    
    @Override
    public String toString(){
        
        return this.getTitle();
        
    }

    @Override
    public Integer getId() {
        return 0;
    }
}
