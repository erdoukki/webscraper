/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wickypedia;

import org.mc2.webscraper.wickypedia.elements.ComposersByEra;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.mc2.webscraper.wickypedia.ScrapeComposersByEra.getComposersByEra;
import org.junit.Test;
import org.mc2.webscraper.wickypedia.elements.Composer;
import org.mc2.webscraper.wickypedia.elements.ComposerGroup;
import org.mc2.webscraper.wickypedia.elements.Period;

/**
 *
 * @author marco
 */
public class testComposersByEra extends UnitTest {


    @Test
    public void testComposersByEra() {
                
        try {
            ComposersByEra composersByEra = getComposersByEra();
            
            System.out.println(composersByEra.getTitle());
            System.out.println(composersByEra.getCopyrigthText());
            
            for (Period period : composersByEra.getPeriods()){
                
               printChildren(period, "-");
            
            }
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(testComposersByEra.class.getName()).log(Level.SEVERE, null, ex);
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
