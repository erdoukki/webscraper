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

/**
 *
 * @author marco
 */
public class Composer {
    
    private final ComposerGroup composerGroup;
    private final Integer id;
    private final String name;
    private final String alias;
    private final String title;
    private final String url;
    private final String born;
    private final String died;
    private final String nationality;
    private final String picture;
    private final String remarks;
    
    private final String notes;
    
    public Composer(ComposerGroup composerGroup, Integer id,  String name, String alias, String title, 
                    String url, String born, String died, String nationality, 
                    String picture, String remarks){
        
        this.composerGroup = composerGroup;
        this.id = id;
        this.name= name.trim();
        this.title= title.trim();
        this.url= url;
        this.born= born.trim();
        this.died= died.trim();
       
        this.picture= picture;
        this.remarks= remarks.trim();
        
        String txt="";
        
        alias= alias.trim();
        if (!alias.isEmpty() && !alias.equals(name)){
           
            if (alias.startsWith(name)){
            
                alias = alias.substring(name.length()+1);
            }
            
            if (alias.startsWith("(")){
                    alias = alias.substring(1);
            }
            
            if (alias.endsWith(")")){
                    alias = alias.substring(0, alias.length()-1);
            }
            
            txt = "aka: "+alias;
        } else {
            alias = "";
        }
        this.alias= alias;

        if (!born.isEmpty()){
            
           if (!txt.isEmpty()){
               txt = txt+" - ";
           }
           txt = txt+"born: "+born;
        }
        if (!died.isEmpty()){
            
           if (!txt.isEmpty()){
               txt = txt+" - ";
           }
           txt = txt+"died: "+died;
        }

        if (nationality.isEmpty()){
            
            nationality =  this.composerGroup.getSchool();
        }
        
        nationality=nationality.trim();
        if (!nationality.isEmpty()){
            if (!txt.isEmpty()){
                txt = txt+" - ";
            }
            txt = txt+"nationality: "+nationality;
        }
        this.nationality= nationality;
        
        if (!remarks.isEmpty()){
            
           if (!txt.isEmpty()){
               txt = txt+" - ";
           }
           txt = txt+"remarks: "+remarks;
        }
        this.notes= txt;
    }

    public Composer(ComposerGroup composerGroup, Integer id,  String name, String title, 
                    String url, String remarks){
        
        this.composerGroup = composerGroup;
        this.id = id;
        this.name= name.trim();
        this.alias= "";
        this.title= title.trim();
        this.url= url;
        this.born= "";
        this.died= "";
        this.nationality= "";
        this.picture= "";
        
        remarks=remarks.trim();
        if (!remarks.isEmpty() && !remarks.equals(name)){
            
            if (remarks.startsWith(name)){
            
                remarks = remarks.substring(name.length()+1);
            }
           
        } else {
            remarks = "";
        }
        this.remarks= remarks;
        this.notes= remarks;
    
    }
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getNotes() {
        return notes;
    }

    @Override
    public String toString(){
        
        return this.id +" - "+this.name +" - "+this.notes;
    }

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getBorn() {
        return born;
    }

    public String getDied() {
        return died;
    }

    public String getNationality() {
        return nationality;
    }

    public String getPicture() {
        return picture;
    }

    public String getRemarks() {
        return remarks;
    }

}
