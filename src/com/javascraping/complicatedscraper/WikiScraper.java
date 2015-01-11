package com.javascraping.complicatedscraper;

// jsoup converts HTML strings into accessible objects
// Documentation - http://jsoup.org/apidocs/
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

// For establishing a connection and retrieving information from the Web.
import java.net.*;
import java.io.*;
import java.util.Random;

public class WikiScraper 
{
	// Accessible from within the class only.
	private static Random generator;
	
	public static void scrapeTopic(String url)
	{
		String html = getUrl("http://www.wikipedia.org/" + url);
		Document doc = Jsoup.parse(html);
		// Contains a collection of jsoup Element objects.
		// Selecting all article links inside the main body.
		// [attr ~= expression] : Select all items containing the attribute values for attr that match the given regular expression.
		// Match all links that bing with /wiki/ and excludes any links that contain a succeeding colon.
		Elements links = doc.select("#mw-content-text [href~=^/wiki/((?!:).)*$]");
		
		if(links.size() == 0)
		{
			System.out.println("No links found at " + url + ". Goind back to Java!");
			scrapeTopic("/wiki/Java");
		}
		
		int r = generator.nextInt(links.size());
		System.out.println("Random link is: " + links.get(r).text() + " at url: " + links.get(r).attr("href"));
		scrapeTopic(links.get(r).attr("href"));
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
		generator = new Random(31415926);
		scrapeTopic("/wiki/Java");
	}
}
