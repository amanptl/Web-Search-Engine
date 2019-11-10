package engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

import javax.sound.midi.Sequence;

import textProcessing.TST;
import editDistance.Sequences;

public class Dictionary {
	private static TST<Integer> tst = new TST<Integer>();
	
	public Dictionary() throws IOException {
		File file = new File("Resources/words.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		int i = 0;
		System.out.println("Adding words to dictionary");
		while ((st = br.readLine()) != null) {
			tst.put(st, i);
			i++;
		}
		System.out.println(i+" words added.");
	}
	
	public static TST<Integer> getTST() {
		return tst;
	}
	
	public boolean searchWord(String word) {
		if(tst.contains(word)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	

	public static void main(String[] args) {

	}

}
