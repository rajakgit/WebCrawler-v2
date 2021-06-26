package com.proj.crawler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class CrawlerInvocation {
	String result = "";
	InputStream inputStream;
	String strLinks = null;
	String strSearchWord = null;
	String strOutputFile = null;

	public void getPropValues() throws IOException {

		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";

			File jarPath = new File(CrawlerInvocation.class.getProtectionDomain().getCodeSource().getLocation().getPath());
			String propertiesPath = jarPath.getParentFile().getAbsolutePath();
			System.out.println("propertiesPath" + propertiesPath);
			prop.load(new FileInputStream(propertiesPath + "/config.properties"));
			Date time = new Date(System.currentTimeMillis());

			// get the property value and print it out
			strLinks = prop.getProperty("links");
			strSearchWord = prop.getProperty("word");
			System.out.println("Search for word " + strSearchWord);
			strOutputFile = prop.getProperty("outputfile");
			System.out.println("Update result in output file " + strSearchWord);
			if (null == strOutputFile || "".equalsIgnoreCase(strOutputFile)) {
				throw new Exception("Output file is empty");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			System.exit(0);
		} finally {
			if (null != inputStream)
				inputStream.close();			
		}
	}

	public static void main(String[] args) {
		CrawlerInvocation crawler = new CrawlerInvocation();

		try {
			//Read properties file
			crawler.getPropValues();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Invoke the Crawler
		Extractor bwc = new Extractor();
		// Get the links to search
		String[] strTokens = crawler.strLinks.split(",");		
		
		for (String strLink : strTokens) {
			bwc.getPageLinks(strLink, 0);
			bwc.getArticles(crawler.strSearchWord);
			bwc.writeToFile(crawler.strOutputFile);
		}

	}

}
