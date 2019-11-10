package indexing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import textProcessing.TST;;

public class InvertedIndex {

	public InvertedIndex(List<File> files) throws IOException {
		for (int i = 0; i < files.size(); i++) {
			indexFile(files.get(i));
		}
	}

	private Map<String, List<Tuple>> index = new HashMap<String, List<Tuple>>();
	private List<String> files = new ArrayList<String>();
	private TST tst = new TST();

	private void indexFile(File file) throws IOException {
		int fileNumber = files.indexOf(file.getPath());
		if (fileNumber == -1) {
			files.add(file.getPath());
			fileNumber = files.size() - 1;
		}

		int pos = 0;
		BufferedReader reader = new BufferedReader(new FileReader(file));

		for (String line = reader.readLine(); line != null; line = reader.readLine()) {
			for (String _word : line.split("\\W+")) {
				String word = _word.toLowerCase();
				pos++;
				List<Tuple> indexList = index.get(word);
				if (indexList == null) {
					indexList = new LinkedList<Tuple>();
					index.put(word, indexList);
				}
				indexList.add(new Tuple(fileNumber, pos));
			}
		}
//		System.out.println("Indexed " + file.getPath() + " " + pos + " words");
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
//			System.out.print(word);

			for (String f : answer) {
				System.out.print(f);
				System.out.println();
			}

			System.out.println("");
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
