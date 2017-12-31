/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.leia.wikipedia.webscraper;

import static org.mc2.leia.wikipedia.webscraper.ScrapeComposersByEra.getComposersByEra;
import org.junit.Test;
import org.mc2.leia.wikipedia.API.Composer;
import org.mc2.leia.wikipedia.API.ComposerGroup;
import org.mc2.leia.wikipedia.API.ComposersByEra;
import org.mc2.leia.wikipedia.API.Period;


/**
 *
 * @author marco
 */
public class testComposersByEra extends UnitTest {

    @Test
    public void testComposersByEra() {
                
        ComposersByEra composersByEra = getComposersByEra();
            
        System.out.println(composersByEra.getTitle());
        System.out.println(composersByEra.getCopyrigthText());

        for (Period period : composersByEra.getPeriods()){

           printChildren(period, "-");

        }

    }
    private void printChildren(ComposerGroup group, String inline){
        
        System.out.println(inline+" "+group.toString());
        
        if (!group.getComposers().isEmpty()) {
            
            for (Composer composer : group.getComposers()){
                
                //if (!composer.getBorn().isEmpty()) {continue;}
                System.out.println(inline+"- "+composer.toString());
                                               
            }
            
        }
        if (!group.getChildren().isEmpty()) {
            inline = inline +"-";
            for (ComposerGroup child : group.getChildren()){
                printChildren(child,inline);
            }
        }
    }
}
