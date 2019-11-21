package engine;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import indexing.InvertedIndexing;

public class SearchEngine {
	private static InvertedIndexing index;
	public static Crawler crawler;
	private static List<String> query = new ArrayList<String>();
	private static long sTime;
	private static long eTime;
	private static Suggestions suggestions;

	private static void startIndexing() throws IOException {
		print("Indexing started...");
		sTime = System.currentTimeMillis();
		index = new InvertedIndexing();
		eTime = System.currentTimeMillis();
		print("Indexing complete.");
//		print("Indexing Time: " + calculateCPUTime(sTime, eTime) + "ms.");

	}

	private static void initializeSuggestions() {
		suggestions = new Suggestions();
	}

	private static void getQuery(String rootUrl, String query) throws IOException {
		System.out.println("Searching "+query+" in "+rootUrl);
		crawler = new Crawler(rootUrl);
		startIndexing();
		initializeSuggestions();
		queryTokenizer(query);
		
		searchQuery();

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
				query.add(word);
				System.out.print(word + "  ");
			}
			print("");
		}
	}

	public static void main(String[] args) throws IOException {
		getQuery("https://www.charusat.ac.in/", "studen life");
		
		
	}

	private static void queryTokenizer(String queryString) {
		StringTokenizer stringTokenizer = new StringTokenizer(queryString);
		List<String> checkWords = new ArrayList<String>();
		String word;
		List<String> corrected = new ArrayList<String>();
		while (stringTokenizer.hasMoreTokens()) {
			word = stringTokenizer.nextToken();
			if (!index.dictionary.searchWord(word))
				checkWords.add(word);
		}
		if(checkWords.size()!=0)
			suggestWords(checkWords);
		
		
	}

	private static void print(Object object) {
		System.out.println(object);
	}

	private static long calculateCPUTime(long s, long e) {
		return e - s;
	}

}
