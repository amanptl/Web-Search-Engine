package engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import textProcessing.In;
import java.util.Queue;
import textProcessing.StdOut;

public class WCrawler {
	static String regex = "(http|ftp|https)://([\\w_-]+(?:(?:\\.[\\w_-]+)+))([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?";
//	Queue<String> queue = new Queue<String>();
	Queue<String> queue = new LinkedList<>();
	public static Set<String> marked = new HashSet<String>();

	public WCrawler(String root) throws Exception {
		System.setProperty("sun.net.client.defaultConnectTimeout", "500");
		System.setProperty("sun.net.client.defaultReadTimeout", "1000");

		String s = root;
//		queue.enqueue(s);
		queue.add(s);

		marked.add(s);
		// breadth first search crawl of web
		while (marked.size() <= 50) {
//			String v = queue.dequeue();
			String v = queue.poll();
//			System.out.println(v);
			HttpURLConnection connection = null;
			URL urlObj = null;
			InputStream inputStream = null;
			Document doc = null;
			try {
				urlObj = new URL(v);
				connection = (HttpURLConnection) urlObj.openConnection();
				connection.addRequestProperty("User-Agent",
						"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
				connection.setConnectTimeout(1000);
				connection.setReadTimeout(1000);
				connection.connect();
				inputStream = connection.getInputStream();
			} catch (MalformedURLException e) {
				continue;
			} catch (IOException e) {
				continue;
			}
			
			try {
				doc = Jsoup.parse(inputStream, null, urlObj.getProtocol() + "://" + urlObj.getHost() + "/");
				Elements linksOnPage = doc.select("a[href]");
				int count = 0;
				for (Element page : linksOnPage) {
					String w = page.attr("abs:href");
					Pattern pattern = Pattern.compile(regex);
					Matcher matcher = pattern.matcher(w);
					while (matcher.find() && count<10) {
						String f = matcher.group();
						if (!marked.contains(f)) {
							marked.add(f);
							queue.add(f);
							count++;
						}
					}
				}
			}catch (IOException e) {
				continue;
			}

		}
		System.out.println("Marked size:" + marked.size());
	}

	public static Set<String> getMarked() {
		return marked;
	}

	public static void main(String[] args) throws Exception {
		WCrawler c = new WCrawler("https://www.charusat.ac.in/");
		Set<String> m = getMarked();
	}
}
