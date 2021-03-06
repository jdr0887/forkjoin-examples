package com.kiluet.test;

import java.util.concurrent.ForkJoinPool;

public class WordCounter {

	private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

	public static String[] wordsIn(String line) {
		return line.trim().split("(\\s|\\p{Punct})+");
	}

	public static Long occurrencesCount(Document document, String searchedWord) {
		long count = 0;
		for (String line : document.getLines()) {
			for (String word : wordsIn(line)) {
				if (searchedWord.equals(word)) {
					count = count + 1;
				}
			}
		}
		return count;
	}

	public static Long countOccurrencesOnSingleThread(Folder folder,
			String searchedWord) {
		long count = 0;
		for (Folder subFolder : folder.getSubFolders()) {
			count = count
					+ countOccurrencesOnSingleThread(subFolder, searchedWord);
		}
		for (Document document : folder.getDocuments()) {
			count = count + occurrencesCount(document, searchedWord);
		}
		return count;
	}

	public static Long countOccurrencesInParallel(Folder folder, String searchedWord) {
		return forkJoinPool.invoke(new FolderSearchTask(folder, searchedWord));
	}

}
