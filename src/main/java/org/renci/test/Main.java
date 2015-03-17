package org.renci.test;

import java.io.File;
import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		WordCounter wordCounter = new WordCounter();
		Folder folder = Folder.fromDirectory(new File(
				"/home/jdr0887/workspace/unc/mapseq/modules/modules"));

		long startTime;
		long endTime;

		// startTime = System.currentTimeMillis();
		// System.out.println(wordCounter.countOccurrencesOnSingleThread(folder,
		// "class"));
		// endTime = System.currentTimeMillis();
		// System.out.println(String.format("duration = %d", endTime -
		// startTime));

		startTime = System.currentTimeMillis();
		System.out.println(wordCounter.countOccurrencesInParallel(folder,
				"plugin"));
		endTime = System.currentTimeMillis();
		System.out.println(String.format("duration = %d", endTime - startTime));

	}

}
