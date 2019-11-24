package engine;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import indexing.testIndexing;

public class SearchEngine {
	private static testIndexing index;
	public static WCrawler crawler;
	private static String rootUrl;
	private static List<String> query = new ArrayList<String>();
	private static long sTime;
	private static long eTime;
	private static Suggestions suggestions;

	private static void startIndexing() throws IOException {
		print("Indexing started...");
		sTime = System.currentTimeMillis();
		index = new testIndexing();
		eTime = System.currentTimeMillis();
		print("Indexing complete.");
//		print("Indexing Time: " + calculateCPUTime(sTime, eTime) + "ms.");

	}

	private static void initializeSuggestions() {
		suggestions = new Suggestions();
	}

	private static void startCrawl() throws Exception {
		crawler = new WCrawler(rootUrl);
	}

	private static void preProcess() throws Exception {
		startCrawl();
		startIndexing();
		initializeSuggestions();
	}

	private static void getQuery() throws IOException {
		String choice = "y";
		Scanner in = new Scanner(System.in);
		while (true) {
			System.out.println("Search: ");
			String q = in.nextLine();
			queryTokenizer(q);
			searchQuery();
		}
//		in.close();
		
//		queryTokenizer("student");
//		searchQuery();
	}

	private static void searchQuery() {
		sTime = System.currentTimeMillis();
		print("Search results:");
		index.search(query);
		eTime = System.currentTimeMillis();
		print("Search Time: " + calculateCPUTime(sTime, eTime) + "ms.");
	}

	private static void suggestWords(List<String> words) {
		sTime = System.currentTimeMillis();
		List<String> suggestions = Suggestions.getSuggestion(words);
		eTime = System.currentTimeMillis();
		if (words.size() != 0) {
			print("Did you mean: ");
			for (String word : suggestions) {
				System.out.print(word + "  ");
			}
			print("");
		}
	}

	public static void main(String[] args) throws Exception {
		rootUrl = "http://www.uwindsor.ca/";
		preProcess();
		getQuery();

	}

	private static void queryTokenizer(String queryString) {
		StringTokenizer stringTokenizer = new StringTokenizer(queryString);
		List<String> checkWords = new ArrayList<String>();
		String word;
		List<String> corrected = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {
			word = stringTokenizer.nextToken();
			query.add(word);
			if (!index.dictionary.searchWord(word))
				checkWords.add(word);
		}
//		if (checkWords.size() != 0)
//			suggestWords(checkWords);
//			

	}

	private static void print(Object object) {
		System.out.println(object);
	}

	private static long calculateCPUTime(long s, long e) {
		return e - s;
	}

}
