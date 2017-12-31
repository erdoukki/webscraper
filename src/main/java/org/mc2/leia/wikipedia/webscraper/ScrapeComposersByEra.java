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

import org.mc2.leia.wikipedia.webscraper.elements.WSPeriod;
import org.mc2.leia.wikipedia.webscraper.elements.WSComposersByEra;
import org.mc2.leia.wikipedia.webscraper.elements.WSComposer;
import org.mc2.leia.wikipedia.webscraper.elements.WSComposerGroup;
import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.mc2.leia.wikipedia.API.ComposerGroup;
/**
 *
 * @author marco
 */
public class ScrapeComposersByEra {
    private static Logger log = Logger.getLogger(ScrapeComposersByEra.class.getName());
    
    public static WSComposersByEra getComposersByEra() {

        try {
            Document doc = Jsoup.connect(WSComposersByEra.URL).get();
            
            String title = doc.title();
            
            if (title.endsWith(" - Wikipedia")){
                
                title= title.substring(0, title.length()-12);
            }
            
            ArrayList<WSPeriod> periods = listPeriods(doc);
            
            for (WSPeriod period : periods){
                
                //System.out.println(period.toString());
                
                listComposers(period);
                
            }
            
            return new WSComposersByEra(title, periods);
            
        } catch (IOException ex) {
            Logger.getLogger(ScrapeComposersByEra.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return new WSComposersByEra();
        }
    }
            
    public static ArrayList<WSPeriod> listPeriods(Document doc) throws UnsupportedEncodingException, IOException{
    
        ArrayList<WSPeriod> periods = new ArrayList<>();

        //System.out.println(doc.title());
        
        Element mwContentText  = doc.getElementById("mw-content-text");
        Elements verticalNavboxList = mwContentText.getElementsByClass("vertical-navbox");
        Element verticalNavbox = verticalNavboxList.first();
        Elements tbodyList = verticalNavbox.getElementsByTag("tbody");
        Element tbody = tbodyList.first();
        Elements trList = tbody.getElementsByTag("tr");
        Element tr = trList.get(1);
        Elements tbodyList2 = tr.getElementsByTag("tbody");
        Element tbody2 = tbodyList2.first();
        Elements trList2 = tbody2.getElementsByTag("tr");
        
        int id =0;
        
        for (Element tr2 : trList2) {
            
            id = id+10000;
            Elements tdList = tr2.getElementsByTag("td");
            
            Element td0 = tdList.get(0);
            Element td1 = tdList.get(1);
            
            Elements a = td0.getElementsByTag("a");
            
            String url = a.attr("href");
            String title = a.attr("title");
            String name = a.text();
            String range = td1.text();
            
            WSPeriod period = new WSPeriod(id, title, name, range, url);
            periods.add(period);

        }
        return periods;
       
    }
    
    public static void listComposers(WSPeriod period) throws IOException {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));

        Document doc = Jsoup.connect(period.getUrl()).get();

        Element mwContentText  = doc.getElementById("mw-content-text");
        Elements mwParserOutputList = mwContentText.getElementsByClass("mw-parser-output");
        Element mwParserOutput = mwParserOutputList.first();
        Elements mwParserOutputChildren = mwParserOutput.children();
        
        Integer id=period.getId();
        Integer lastId=id*10000;

        WSComposerGroup h2composerGroup=null;
        WSComposerGroup h3composerGroup=null;
        
        String lastFound="";
        
        for (Element mwParserOutputChild : mwParserOutputChildren){

            if (mwParserOutputChild.tagName().equals("h2")){
                lastFound="h2";
                id =(id/100+1)*100;
                lastId=id*10000;
                
                h3composerGroup=null;
                h2composerGroup = parseH2ComposerGroup(period, mwParserOutputChild,id);
                

                //System.out.println("- "+h2composerGroup.toString());

            }else if (mwParserOutputChild.tagName().equals("h3")){
                lastFound="h3";
                id =id+1;
                lastId=id*10000;
                
                h3composerGroup = parseH3ComposerGroup(h2composerGroup, mwParserOutputChild,id);
                
                //System.out.println("-- "+h3composerGroup.toString());

            } else if (mwParserOutputChild.tagName().equals("table") && 
                       mwParserOutputChild.hasClass("wikitable")){
                
                lastFound="table";
                
                if (h2composerGroup != null){
                    lastId = parseComposerTable(mwParserOutputChild, lastId, h2composerGroup);
                
                } else {
                    lastId =parseComposerTable(mwParserOutputChild, lastId, period);
                }

            } else if (mwParserOutputChild.tagName().equals("ul")){
                
                lastFound="ul";

                if (h3composerGroup != null){
                    lastId = parseComposerList(h3composerGroup, mwParserOutputChild, lastId);
                
                } else if (h2composerGroup != null){
                    lastId = parseComposerList(h2composerGroup, mwParserOutputChild, lastId);
                
                } else {
                    lastId =parseComposerList(period, mwParserOutputChild, lastId);
                }
            }
        }
    }

    private static WSComposerGroup parseH2ComposerGroup(WSComposerGroup parent, Element composerGroupdHeader, Integer id){
      
        
        String name="";
        String className ="";
        String idAttr="";
        WSComposerGroup out=null;
        
        Element span = parseComposerGroup(composerGroupdHeader);
        
        if (span != null){
            
            name = span.text();
            className = span.attr("class");
            idAttr = span.attr("id");

        }
        
        out = new WSComposerGroup(parent, id, name, "","", idAttr);
        return out;
           
    }
    private static WSComposerGroup parseH3ComposerGroup(WSComposerGroup parent, Element composerGroupdHeader, Integer id ){
      
        String name="";
        String className ="";
        String idAttr="";
        WSComposerGroup out=null;
        
        Element span = parseComposerGroup(composerGroupdHeader);
        
        if (span != null){
            
            name = span.text();
            className = span.attr("class");
            idAttr = span.attr("id");

        }
        out = new WSComposerGroup(parent, id, name, name, parent.getName(), idAttr);
        
        //parent.getChildren().add(out);
        
        return out;
    }
    private static Element parseComposerGroup(Element composerGroupdHeader){
      
        Elements spanList = composerGroupdHeader.getElementsByTag("span");
        
        for (Element span : spanList){
            
            if (span.hasAttr("id")&& span.hasClass("mw-headline")){
                
                return span;
            }
        }
        
        return null;
    }
    
    private static String parseTableHeader(Element composerTable){
        
        // sometime thead is transformed in the first line of tbody.
        Elements theadList = composerTable.getElementsByTag("thead");
        Element thead = theadList.first();
        
        if (thead == null){
            
            Elements tbodyList = composerTable.getElementsByTag("tbody");
            thead = tbodyList.first();
            
        }
        Elements headtrList = thead.getElementsByTag("tr");
        Element  headtr = headtrList.first();
        
        Elements thList = headtr.getElementsByTag("th");

        if (    thList.size() == 6 &&
                thList.get(0).text().equals("Name")  &&
                thList.get(1).text().equals("Born")  &&
                thList.get(2).text().equals("Died")  &&
                thList.get(3).text().equals("Nationality") &&
                thList.get(4).text().equals("Picture") &&
                thList.get(5).text().equals("Remarks")
            ){
            
                return WSComposersByEra.TABLE_MODEL_100;

                
        } else if (    thList.size() == 5 &&
                thList.get(0).text().equals("Name")  &&
                thList.get(1).text().equals("Born")  &&
                thList.get(2).text().equals("Died")  &&
                thList.get(3).text().equals("Nationality") &&
                thList.get(4).text().equals("Picture")
            ){
            
                return WSComposersByEra.TABLE_MODEL_102;

                
        } else if ( thList.size() == 4 &&
                    thList.get(0).text().equals("Name")  &&
                    thList.get(1).text().equals("Born")  &&
                    thList.get(2).text().equals("Died")  &&
                    thList.get(3).text().equals("Notes")
            ){
            
                return WSComposersByEra.TABLE_MODEL_200;
        
        } else if ( thList.size() == 5 &&
                    thList.get(0).text().equals("Name")  &&
                    thList.get(1).text().equals("Date born")  &&
                    thList.get(2).text().equals("Date died")  &&
                    thList.get(3).text().equals("Nationality")  &&
                    thList.get(4).text().equals("Comments")
            ){
            
                return WSComposersByEra.TABLE_MODEL_500;
        }
        
        return WSComposersByEra.TABLE_MODEL_UNKNOWN;
    }
    private static int parseComposerList(WSComposerGroup composerGroup, Element composerList, Integer id){

        Elements liList = composerList.getElementsByTag("li");
        Integer composerId = id;
        
        ArrayList<WSComposer> composers = new  ArrayList<>();
        
        for (Element li : liList) {
            
            Elements aList = li.getElementsByTag("a");
            Element a = aList.first();
            
            String name=a.text();
            String title=a.attr("title");
            String url=a.attr("href");
            String remarks =li.text();
            
            composerId++;
            
            WSComposer composer = new WSComposer(   composerGroup,
                                                composerId,
                                                name,  
                                                title,
                                                url,
                                                remarks );
            
            composers.add(composer);
            //System.out.println  ("-- "  + composer.toString());
        }
        
        composerGroup.getComposers().addAll(composers); 
        linkGroups(composerGroup);
        
        return composerId;
        
    }
    private static Integer parseComposerTable(Element composerTable, Integer id,  WSComposerGroup composerGroup){
        
        String model = parseTableHeader(composerTable);

        Elements tbodyList = composerTable.getElementsByTag("tbody");
        Element tbody = tbodyList.first();
        Elements trList = tbody.getElementsByTag("tr");

        Integer composerId = id;
        
        ArrayList<WSComposer> composers = new  ArrayList<>();
        
        for (Element tr : trList) {
            
            //skip headers
            Elements thList = tr.getElementsByTag("th");
            if (thList.size()>0){continue;}
                        
            composerId++;

            String name="";
            String alias="";
            String title="";
            String url="";
            String born="";
            String died="";
            String nationality ="";
            String picture ="";
            String remarks ="";

            Elements tdList = tr.getElementsByTag("td");
           
            Element tdName;
            Element tdBorn;
            Element tdDied;
            Element tdNationality;
            Element tdPicture;
            Element tdRemarks;
            
            if (tdList.size() > 0) { 
                
                tdName = tdList.get(0);
                if (tdName != null ){ 

                    Elements aList = tdName.getElementsByTag("a");
                    
                    if (!aList.isEmpty()){
                        Element a = aList.first();

                        if (a.hasAttr("href")){ url = a.attr("href");}
                        if (a.hasAttr("title")){ title = a.attr("title");}
                        name = a.text();

                        alias = parseAlias(tdName);
                    }
                }
            }
            if (tdList.size() > 1) {
                
                tdBorn = tdList.get(1);
                if (tdBorn != null ){ 

                    born = tdBorn.ownText();
                }
            }
            if (tdList.size() > 2) {
                
                tdDied = tdList.get(2);
                if (tdDied != null ){ 

                    died = tdDied.ownText();
                }
            }
            if (tdList.size() > 3) {
                
                if (model.equals(WSComposersByEra.TABLE_MODEL_100) ||
                    model.equals(WSComposersByEra.TABLE_MODEL_102) ||
                    model.equals(WSComposersByEra.TABLE_MODEL_500)) {
                    
                    tdNationality = tdList.get(3);
                    if (tdNationality != null ){ 

                        nationality = tdNationality.text();
                    }
                } else if (model.equals(WSComposersByEra.TABLE_MODEL_200)) {
                     
                    tdRemarks = tdList.get(3);
                    if (tdRemarks != null ){ 

                        remarks = tdRemarks.text();
                    }
                }
                   
            }
            if (tdList.size() > 4) {
                if (model.equals(WSComposersByEra.TABLE_MODEL_100) ||
                    model.equals(WSComposersByEra.TABLE_MODEL_102)) {

                    tdPicture = tdList.get(4);
                    if (tdPicture != null ){ 

                        Elements imageList = tdPicture.getElementsByClass("image");
                        if (imageList.size() > 0) {

                            Element image = imageList.first();
                            Elements imgList = image.getElementsByTag("img");
                            Element img = imgList.first();

                            picture = img.attr("src");
                        }
                    }
                } else if (model.equals(WSComposersByEra.TABLE_MODEL_500)) {
                     
                    tdRemarks = tdList.get(4);
                    if (tdRemarks != null ){ 

                        remarks = tdRemarks.text();
                    }
                    Elements imageList = tdRemarks.getElementsByClass("image");
                    if (imageList.size() > 0) {

                        Element image = imageList.first();
                        Elements imgList = image.getElementsByTag("img");
                        Element img = imgList.first();

                        picture = img.attr("src");
                    }
                }
            }
            
            if (tdList.size() > 5) {
                
                tdRemarks = tdList.get(5);
                if (tdRemarks != null ){ 

                    remarks = tdRemarks.text();
                }
            }
            
            WSComposer composer = new WSComposer(   composerGroup,
                                                composerId,
                                                name,
                                                alias,  
                                                title,
                                                url,
                                                born,
                                                died,
                                                nationality,
                                                picture,
                                                remarks );
            
            composers.add(composer);

            //System.out.println  ("-- "  + composer.toString());
        } 
        composerGroup.getComposers().addAll(composers); 
        linkGroups(composerGroup);
        
        return composerId;
    }
    private static void linkGroups(ComposerGroup composerGroup){

        if (composerGroup.isValid() && composerGroup.getParent() !=null){

            if (!composerGroup.getParent().getChildren().contains(composerGroup)){
                
                
                (( ArrayList<ComposerGroup>)composerGroup.getParent().getChildren()).add(composerGroup);
        
            }
            if (composerGroup.getParent().isValid() &&
                composerGroup.getParent().getParent()!=null && 
                !composerGroup.getParent().getParent().getChildren().contains(composerGroup.getParent())){

                (( ArrayList<ComposerGroup>)composerGroup.getParent().getParent().getChildren()).add(composerGroup.getParent());
            }
        }
    }
    
    private static String parseAlias(Element element){
        
        String out="";
        
        List<Node> nodes = element.childNodes();
        
        for (Node node : nodes){

           if (node instanceof Element && ((Element)node).tagName().equals("a")){
                continue;
            }
            
            if (node instanceof Element && ((Element)node).tagName().equals("span") &&
                ((Element)node).hasAttr("class") && ((Element)node).attr("class").equals("sortkey")){
                continue;
            }
            if (node instanceof Element){
                out=out+((Element)node).text();
            
            } else if (node instanceof TextNode) {
                
                out=out+((TextNode)node).text();
            }
            
        }

        return out;
    }

}
