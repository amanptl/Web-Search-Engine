package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import dictionary.Dictionary;
import engine.Crawler;
import engine.SearchEngine;
import textProcessing.TST;

public class testIndexing {
//	public static Dictionary dictionary = new Dictionary();
	private static Map<String, List<Tuple>> index = new HashMap<String, List<Tuple>>();
	private static List<String> urls = new ArrayList<String>();
	static List<String> query = new ArrayList<String>();
	

	public testIndexing() throws IOException {
		Set<String> marked = WCrawler.getMarked();
		System.out.println("Size of marked:" +marked.size());
		System.out.println("Indexing");
		for (String url : marked) {
			indexFile(url);
		}
			System.out.println("Indexing Complete");
	}

	private void indexFile(String url) throws IOException {
		int fileNumber = urls.indexOf(url);
		if (fileNumber == -1) {
			System.out.println(url);
			urls.add(url);
			fileNumber = urls.size() - 1;
		}

		int pos = 0;
		URL urlObj = new URL(url);
		HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
		connection.addRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
		connection.setConnectTimeout(5000);
//		connection.setReadTimeout(5000);
		InputStream inputStream = null;
		Document doc = null;
		try {
			connection.connect();
			inputStream = connection.getInputStream();
			doc = Jsoup.parse(inputStream, null, urlObj.getProtocol() + "://" + urlObj.getHost() + "/");
			StringTokenizer stringTokenizer = new StringTokenizer(doc.text());
			String _word;
			while (stringTokenizer.hasMoreTokens()) {
				_word = stringTokenizer.nextToken();
				
				String word = _word.toLowerCase();
//				dictionary.putWord(word);
				System.out.println(word);
				pos++;
				List<Tuple> indexList = index.get(word);
				if (indexList == null) {
					indexList = new LinkedList<Tuple>();
					index.put(word, indexList);
				}
				
				indexList.add(new Tuple(fileNumber, pos));
			}
		} catch (IOException e) {
//			inputStream = connection.getErrorStream();
		}
	}

	public static void search(List<String> words) {
		for (String _word : words) {
			Set<String> answer = new HashSet<String>();
			String word = _word.toLowerCase();
			List<Tuple> idx = index.get(word);
			if (idx != null) {
				for (Tuple t : idx) {
					answer.add(urls.get(t.fileNumber));
				}
			}
			for (String f : answer) {
				System.out.println(f);//TODO TFIDF
			}
		}
	
	}

	private class Tuple {
		private int fileNumber;
		private int position;

		public Tuple(int fileno, int position) {
			this.fileNumber = fileno;
			this.position = position;
		}
	}

	public static void main(String[] args) throws IOException {
//			urls.add("http://www.uwindsor.ca/parking-services/");
//			urls.add("https://www.charusat.ac.in/");
//			testIndexing tI = new testIndexing(urls);
//			Scanner in = new Scanner(System.in);
//			while (true) {
//				query.clear();
//				System.out.println("Search: ");
//				String q = in.nextLine();
//				queryTokenizer(q);
//				search(query);
//			}
	}
	
	private static void queryTokenizer(String queryString) {
		StringTokenizer stringTokenizer = new StringTokenizer(queryString);
		List<String> checkWords = new ArrayList<String>();
		String word;
		List<String> corrected = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {
			word = stringTokenizer.nextToken();
			query.add(word);
		}
	}
}
