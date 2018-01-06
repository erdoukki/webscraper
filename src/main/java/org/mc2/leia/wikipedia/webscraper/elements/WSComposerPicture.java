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

import org.mc2.leia.wikipedia.API.ComposerPicture;
import java.util.logging.Logger;
/**
 *
 * @author marco
 */
public class WSComposerPicture implements ComposerPicture {
    private static Logger log = Logger.getLogger(WSComposerPicture.class.getName());
    
    public static final String WIKI_URL = "https://en.wikipedia.org";
    public final static String WIKI_FILE_URL = "https://en.wikipedia.org/wiki/File:";
    
    private final String url;
    private final String picture;
    
    public WSComposerPicture( String url, String picture){
        
        
        this.url= url;
        this.picture= picture;
        
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getPicture() {
        return picture;
    }

}
