package engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import dictionary.Dictionary;
import editDistance.Sequences;
import textProcessing.TST;
import indexing.testIndexing;

public class Suggestions {
	private static TST<?> tst;
	public Suggestions() {
		tst = testIndexing.dictionary.getTST();
	}
	
	public static List<String> getSuggestion(List<String> userWords) {
		List<String> suggestion = new ArrayList<>();
		Set<Pair> keyValuePairs = new HashSet<Pair>();
		for(String userWord : userWords) {
			for(String dictWord : tst.keys()) {
				keyValuePairs.add(new Pair(dictWord, Sequences.editDistance(dictWord, userWord)));
			}
			Queue<Pair> queue = new PriorityQueue<>();

	        for(Pair pair : keyValuePairs) {
	            queue.add(pair);
	        }
	        suggestion.add(queue.peek().text);
		}
		return suggestion;
	}

	public static void main(String[] args) {

	}
	
    
    public static class Pair implements Comparable<Pair>{
        private String text;
        private Integer distance;

        @Override
        public int hashCode() {
            return text.hashCode();
        }

        @Override
        public String toString() {
            return text + ":" + distance;
        }

        public Pair(String text, Integer distance) {
            super();
            this.text = text;
            this.distance = distance;
        }

        public String getText() {
            return text;
        }
        public void setText(String text) {
            this.text = text;
        }
        public Integer getDistance() {
            return distance;
        }
        public void setDistance(Integer distance) {
            this.distance = distance;
        }

		@Override
		public int compareTo(Pair o) {
			return this.getDistance().compareTo(o.getDistance());
		}
    }

}
