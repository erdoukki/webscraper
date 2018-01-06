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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mc2.leia.wikipedia.webscraper.elements.WSComposerPicture;

/**
 *
 * @author marco
 */
public class ScrapeComposerPage {
    private static Logger log = Logger.getLogger(ScrapeComposerPage.class.getName());
   
    public static WSComposerPicture  getComposerPicture (String composerPageUrl) {
        
        String url = WSComposerPicture.WIKI_URL+composerPageUrl;
        String picture="";
        
        try {
            Document doc = Jsoup.connect(url).get();
            
            String title = doc.title();
            
            if (title.endsWith(" - Wikipedia")){
                
                title= title.substring(0, title.length()-12);
            }
            
            Element mwContentText  = doc.getElementById("mw-content-text");
            
            Elements thumbinners  = mwContentText.getElementsByClass("thumbinner");
           
            Element container=null;
            
            if (thumbinners != null){
                container  =thumbinners.first();
                
            } else {
                Elements vcards  = mwContentText.getElementsByClass("infobox vcard plainlist");
                if (vcards !=null){
                    container = vcards.first();
                }
            }
            if (container != null) {
            
                Elements imageList = container.getElementsByClass("image");
                Element image = imageList.first();
                
                if (image != null){

                    picture= WSComposerPicture.WIKI_URL+image.attr("href");
                }   

                if (picture.startsWith(WSComposerPicture.WIKI_FILE_URL)){

                    picture = ScrapeWikiFile.getPicture(picture);
                }
            }
            return new WSComposerPicture(composerPageUrl, picture);
            
        } catch (IOException ex) {
            Logger.getLogger(ScrapeComposersByEra.class.getName()).log(Level.WARNING, null, ex);
            return new WSComposerPicture(composerPageUrl,"");
        }
    }
   
}
