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

import java.util.logging.Logger;
import org.mc2.leia.wikipedia.API.Period;

/**
 *
 * @author marco
 */
public class WSPeriod extends WSComposerGroup implements Period {
    private static Logger log = Logger.getLogger(WSPeriod.class.getName());
   
    private final String title;
    private final String url; 
    
    public WSPeriod(Integer id, String title, String name, String range, String url){
        super(null, id, name, range, "","period");
        
        this.title = title;
        this.url = url;

    }
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUrl(){
        
        return this.url.replace("/wiki", "https://en.wikipedia.org/wiki");
    }
    
    @Override
    public String toString(){
        
        return getId() +" - "+getTitle()+" - "+getName()+" - "+getPeriod()+" - ("+getUrl()+")";
    }

   
    
}
