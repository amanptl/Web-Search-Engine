package indexing;

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
import java.util.Set;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import dictionary.Dictionary;
import engine.Crawler;
import engine.SearchEngine;
import textProcessing.TST;

public class testIndexing {
	public static Dictionary dictionary = new Dictionary();
	private Map<String, List<Tuple>> index = new HashMap<String, List<Tuple>>();
	private List<String> files = new ArrayList<String>();
	private static Set<String> marked = SearchEngine.crawler.getMarked();

	public testIndexing() throws IOException {
		System.out.println("Indexing");
		for (String url : marked) {
			indexFile(url);
		}
	}

	private void indexFile(String url) throws IOException {
		int fileNumber = files.indexOf(url);
		if (fileNumber == -1) {
			System.out.println(url);
			files.add(url);
			fileNumber = files.size() - 1;
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

	public void search(List<String> words) {
		for (String _word : words) {
			Set<String> answer = new HashSet<String>();
			String word = _word.toLowerCase();
			List<Tuple> idx = index.get(word);
			if (idx != null) {
				for (Tuple t : idx) {
					answer.add(files.get(t.fileNumber));
				}
			}
			for (String f : answer) {
				System.out.println(f);
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

	public static void main(String[] args) {

	}
}
