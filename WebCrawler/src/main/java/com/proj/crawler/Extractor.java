package com.proj.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Logger;

public class Extractor {
    private static final int MAX_DEPTH = 2;
	private HashSet<String> links;
    private List<List<String>> articles;
    
    public Extractor() {
        links = new HashSet<String>();
        articles = new ArrayList<List<String>>();
    }

    /**
     * This makes an HTTP request and then gathers up all the links on the page.
     * The searched links will be stored in Hashet
     * 
     * @param URL - The URL to search for
     * @param depth - Depth to which need to perform search
     */
    public void getPageLinks(String URL,int depth,Logger logger) {
        if (!links.contains(URL) && (depth < MAX_DEPTH) && !URL.isEmpty()) {
        	logger.info(">> Depth: " + depth + " [" + URL + "]");
            try {
                Document document = Jsoup.connect(URL).get();
                Elements otherLinks = document.select("a[href]");
                depth++;
                // Iterate over the links and get the sub links with max depth of 2
                for (Element page : otherLinks) {
                    if (links.add(URL)) {
                        getPageLinks(page.attr("abs:href"), depth, logger);
                    }
                }
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
        }
    }

    /**
     * Iterate over and search for the word in the links
     * Store value of matched title and URL in an Arraylist
     * 
     * @param strWordSearch - The word to search for in the articles
     */
    public void getArticles(String strWordSearch,Logger logger) {
        links.forEach(x -> {
            Document document;
            try {
                document = Jsoup.connect(x).get();
                String strText = document.body().text();
                Elements articleLinks = document.select("a[href]");             
                for (Element article : articleLinks) {
                	//Only retrieve the titles of the articles that contain Search
                    if (article.text().toLowerCase().contains(strWordSearch.toLowerCase())) {
                    	ArrayList<String> temporary = new ArrayList<>();
                        temporary.add(article.text()); //The title of the article
                        temporary.add(article.attr("abs:href")); //The URL of the article
                        articles.add(temporary);
                    }
                }
            } catch (IOException e) {
                logger.severe(e.getMessage());
            }
        });
    }

    /**
     * To store the search results in a file
     * 
     * @param filename - Filename where the search results are stored
     */    
    public void writeToFile(String filename,Logger logger) {
        FileWriter writer;
        try {
            writer = new FileWriter(filename);
            articles.forEach(a -> {
                try {
                    String temp = "- Title: " + a.get(0) + " (link: " + a.get(1) + ")\n";
                    //display to console
                    //logger.info(temp);
                    //save to file
                    writer.write(temp);
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            });
            writer.close();
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
    
}