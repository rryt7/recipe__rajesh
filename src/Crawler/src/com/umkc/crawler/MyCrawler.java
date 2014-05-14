package com.umkc.crawler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.regex.Pattern;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class MyCrawler extends WebCrawler {
	private final static String path = "C:\\Users\\RajeshKannan\\Desktop\\Spring 2014\\KDM\\Project\\Indian\\";

    private final static Pattern FILTERS = Pattern.compile(".*(\\.(css|js|bmp|gif|jpe?g" 
                                                      + "|png|tiff?|mid|mp2|mp3|mp4"
                                                      + "|wav|avi|mov|mpeg|ram|m4v|pdf" 
                                                      + "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    /**
     * You should implement this function to specify whether
     * the given url should be crawled or not (based on your
     * crawling logic).
     */
    @Override
    public boolean shouldVisit(WebURL url) {
            String href = url.getURL().toLowerCase();
            return !FILTERS.matcher(href).matches() && href.startsWith("http://allrecipes.com/");
    }

    /**
     * This function is called when a page is fetched and ready 
     * to be processed by your program.
     */
    @Override
    public void visit(Page page) {          
            String url = page.getWebURL().getURL();
            System.out.println("URL: " + url);
           
           
            if (page.getParseData() instanceof HtmlParseData && (url.contains("Detail") || url.contains("detail"))) {
                    HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
                    String text = htmlParseData.getText();
                    String html = htmlParseData.getHtml();
                    List<WebURL> links = htmlParseData.getOutgoingUrls();
                    String[] urls = url.split("/");
                    String file = "bad";
                    System.out.println("urls split ---->"+urls.length);
                    for(int i =0;i<urls.length;i++){
                    	System.out.println(urls[i]);
                    	if(urls[i].equalsIgnoreCase("Recipe")){
                    		i++;
                    		file = urls[i];
                    		break;
                    	}
                    }
                    try{
                    	File txtFile = new File(path+file+".txt");
                    	
                    writeDataToFile( path+file, text, html);
                    }catch(Exception e){
                    	e.printStackTrace();
                    }
                    System.out.println("Text : " + text.length());
                    System.out.println("Html length: " + html.length());
                    System.out.println("Number of outgoing links: " + links.size());
            }
    }

	private void writeDataToFile(String file, String text, String html) throws Exception {
		
		BufferedWriter out_txt = new BufferedWriter(new FileWriter(file+".txt"));
		out_txt.write(text);
		out_txt.close();
		
		BufferedWriter out_html = new BufferedWriter(new FileWriter(file+".html"));
		out_html.write(html);
		out_html.close();
		
		
	}
}