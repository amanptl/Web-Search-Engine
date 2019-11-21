package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PageRanker {
	private static List<String> files = new ArrayList<String>();
	private static PriorityQueue<Tuple> pQueue = new PriorityQueue<Tuple>(); 
	private static String rawString;
	
	
//	score = keyword * frequency;
	
	public PageRanker(List<String> words, List<String> files) throws IOException {
		fileReader(files);
	}
	
	private static void fileReader(List<String> files) throws IOException {
		
	}

	
	private static void scorer(int count) {
		
	}
	
	private class Tuple {
		private int fileNumber;
		private int count;

		public Tuple(int fileno, int count) {
			this.fileNumber = fileno;
			this.count = count;
		}
	}
}
