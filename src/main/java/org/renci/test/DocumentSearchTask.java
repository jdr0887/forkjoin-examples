package org.renci.test;

import java.util.concurrent.RecursiveTask;

public class DocumentSearchTask extends RecursiveTask<Long> {

	private static final long serialVersionUID = 7399799534908062055L;

	private final Document document;

	private final String searchedWord;

	public DocumentSearchTask(Document document, String searchedWord) {
		super();
		this.document = document;
		this.searchedWord = searchedWord;
	}

	@Override
	protected Long compute() {
		return WordCounter.occurrencesCount(document, searchedWord);
	}

}