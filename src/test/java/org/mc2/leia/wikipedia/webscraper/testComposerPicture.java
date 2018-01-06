/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.leia.wikipedia.webscraper;

import org.junit.Test;
import org.mc2.leia.wikipedia.webscraper.elements.WSComposerPicture;


/**
 *
 * @author marco
 */
public class testComposerPicture extends UnitTest {
    
    private String wikipediaUrl = "https://en.wikipedia.org";
    private String composerUrl = "/wiki/Josef_Rheinberger"; 
   // private String composerUrl = "/wiki/Charles_Dancla";
    
            
    @Test
    public void testComposerPicture(){
    
        testComposerPicture("/wiki/Josef_Rheinberger"); // thumb.
        testComposerPicture("/wiki/Charles_Dancla"); // vcard.
        testComposerPicture("/wiki/Otfrid_of_Weissenburg"); //wiki_file
        
    }
    
    
    public void testComposerPicture(String url) {
        
        //String url = wikipediaUrl+composerUrl;
        
        WSComposerPicture composerPicture = ScrapeComposerPage.getComposerPicture(url);
            
        System.out.println(composerPicture.getUrl() + " - "+composerPicture.getPicture());

    }
    
}
