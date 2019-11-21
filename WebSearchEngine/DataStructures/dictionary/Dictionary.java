package dictionary;

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
	private static TST<Integer> tst;
	private static int i = 0;
	
	public Dictionary() {
		tst = new TST<Integer>();
	}
	
	public static void putWord(String word) {
		if(word.length() >= 1) {
			tst.put(word, i);
			i++;
		}
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
