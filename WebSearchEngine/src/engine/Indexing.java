package engine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import indexing.InvertedIndex;

public class Indexing {
	public static InvertedIndex invertedIndex;
	private static String webpageTextFolder = "Resources/webpagesText";
	private static List<File> files = new ArrayList<File>();
	private static File folder = new File(webpageTextFolder);
	
	public Indexing() throws IOException {
		readFiles(folder);
	}
	
	private static void indexFiles(List<File> fileList) throws IOException { 
		List<File> files = new ArrayList<File>();
		for(File file : fileList) {
			files.add(file);
		}
		System.out.println("Indexing webpages...");
		invertedIndex = new InvertedIndex(files);
		System.out.println("Indexing completed.");
	}
	
	public void search(List<String> query) {
		invertedIndex.search(query);
	}
	
	private static void readFiles(File folder) throws IOException {
		for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	        	readFiles(fileEntry);
	        } else {
	            files.add(new File(webpageTextFolder+"/"+fileEntry.getName()));
	        }
	    }
		indexFiles(files);
	}

	
	
	public static void main(String[] args) throws IOException {
		
	}
}
