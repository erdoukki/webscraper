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
package org.mc2.leia.wikipedia.webscraper;

import java.util.logging.Logger;

/**
 *
 * @author marco
 */
public class CopyRights {
    private static Logger log = Logger.getLogger(CopyRights.class.getName());
    
    private static final String FORMULA = "Wikipedia content provided under the terms of the";
    private static final String LICENCE = "Creative Commons BY-SA license";
    private static final String URL = "https://creativecommons.org/licenses/by-sa/3.0/";

    
    public static String getCopyrigthHtml(){
        
         return "<small>"+FORMULA+" <a href= \""+URL+"/\" target=\"_blank\">"+LICENCE+"</a></small>";
    }
    public static String getCopyrigthText(){
        
        return FORMULA+" "+LICENCE+". (See: "+URL+")";
    }
    @Override
    public  String toString(){
        
        return this.FORMULA+" "+LICENCE+". (See: "+URL+")";
    }
}
