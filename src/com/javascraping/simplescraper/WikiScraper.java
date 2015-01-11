package com.javascraping.simplescraper;

// jsoup converts HTML strings into accessible objects
// Documentation - http://jsoup.org/apidocs/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

// For establishing a connection and retrieving information from the Web.
import java.net.*;
import java.io.*;

public class WikiScraper 
{
	public static void scrapeTopic(String url)
	{
		String html = getUrl("http://www.wikipedia.org/" + url);
		Document doc = Jsoup.parse(html);
		
		// First paragraph of text in the Wikipedia article.
		String contentText = doc.select("#mw-content-text > p").first().text();
		System.out.println(contentText);
	}
	
	// Accepts an arbitrary URL and returns the raw source code of the webpage. 
	public static String getUrl(String url)
	{
		URL urlObj = null;
		
		try
		{
			urlObj = new URL(url);
		}
		catch(MalformedURLException e)
		{
			System.out.println("The url was malformed!");
			return "";
		}
		
		URLConnection urlCon = null;
		BufferedReader in = null;
		String outputText = "";
		
		try
		{
			// Open a connection.
			urlCon = urlObj.openConnection();
			// BufferedReader objects read from long streams of text.
			in = new BufferedReader(new InputStreamReader(urlCon.getInputStream()));
			String line = "";
			// The text retrieved from the BufferedReader is processed one line at a time.
			while((line = in.readLine()) != null)
			{
				outputText += line;
			}
			in.close();
		}
		catch(IOException e)
		{
			System.out.println("There was an error connecting to the URL.");
			return "";
		}
		
		return outputText;
	}
	
	public static void main(String[] args)
	{
		scrapeTopic("/wiki/Python");
	}
}
