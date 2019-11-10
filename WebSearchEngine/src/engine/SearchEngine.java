package engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class SearchEngine {
	private static Indexing index;
	private static List<String> query = new ArrayList<String>();
	private static long sTime;
	private static long eTime;
	private static Dictionary dictionary;
	private static Suggestions suggestions;

	private static void startIndexing() throws IOException {
		sTime = System.currentTimeMillis();
		index = new Indexing();
		eTime = System.currentTimeMillis();
		print("Indexing Time: " + calculateCPUTime(sTime, eTime) + "ms.");

	}

	private static void initializeDictionary() throws IOException {
		dictionary = new Dictionary();
	}

	private static void initializeSuggestions() {
		suggestions = new Suggestions();
	}

	private static void getQuery() {
		String choice;
		System.out.print("Search: ");
		Scanner input = new Scanner(System.in);
		queryTokenizer(input.nextLine());
		searchQuery();
		System.out.print("Want to continue search (Y/N)? ");
		choice = input.nextLine();
		switch (choice) {
		case "Y":
		case "y":
			query.clear();
			getQuery();
		case "N":
		case "n":
			input.close();
		default:
			input.close();
		}
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
		print("Did you mean: ");
		for (String word : suggestions) {
			System.out.print(word + "  ");
		}
		print("");
		print("Suggestion Time: " + calculateCPUTime(sTime, eTime) + "ms.");
	}

	public static void main(String[] args) throws IOException {
		startIndexing();
		initializeDictionary();
		initializeSuggestions();
		getQuery();

	}

	private static void queryTokenizer(String queryString) {
		StringTokenizer stringTokenizer = new StringTokenizer(queryString);
		List<String> checkWords = new ArrayList<String>();
		String word;
		while (stringTokenizer.hasMoreTokens()) {
			word = stringTokenizer.nextToken();
			if(!dictionary.searchWord(word))
				checkWords.add(word);
			query.add(word);
		}
		suggestWords(checkWords);

	}

	private static void print(Object object) {
		System.out.println(object);
	}

	private static long calculateCPUTime(long s, long e) {
		return e - s;
	}

}
