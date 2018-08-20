package com.volvo.luna;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.flexible.standard.parser.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import com.volvo.luna.reader.TxtFileReader;

/**
 * Hello world!
 *
 */
public class App {

	private static final String SOURCE_DIR = "files\\source";

	private static final String INDEX_DIR = "files\\index";

	private static final String FIELD_FILENAME = "fileName";
	private static final String FIELD_FILEPATH = "filePath";
	private static final String FIELD_FILECONTENT = "fileContent";

	public static Analyzer analyzer = new EnglishAnalyzer();

	public static void testCreateIndex() {

		try {

			Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));

			IndexWriterConfig config = new IndexWriterConfig(analyzer);

			IndexWriter writer = new IndexWriter(directory, config);

			File sourceDir = new File(SOURCE_DIR);

			File[] fileList = sourceDir.listFiles();

			Document document = new Document();

			for (File file : fileList) {

				String fileName = file.getName();
				System.out.println(fileName);

				Field fileNameField = new TextField(FIELD_FILENAME, fileName, Store.YES);

				document.add(fileNameField);

				String filePath = file.getPath();

				Field filePathField = new TextField(FIELD_FILEPATH, filePath, Store.YES);

				String fileContent = TxtFileReader.readFile(file);

				Field fileContentField = new TextField(FIELD_FILECONTENT, fileContent, Store.YES);

				document.add(filePathField);
				document.add(fileContentField);

			}

			writer.addDocument(document);

			writer.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testSearchDocs(String searchingText) {

		System.out.println("Test searching started.");

		try {

			Directory directory = FSDirectory.open(Paths.get(INDEX_DIR));

			IndexReader indexReader = DirectoryReader.open(directory);

			IndexSearcher indexSearcher = new IndexSearcher(indexReader);

			QueryParser queryParser = new QueryParser(FIELD_FILENAME, analyzer);

			Query query = queryParser.parse(searchingText);

			TopDocs topDocs = indexSearcher.search(query, 5);

			System.out.println("get " + topDocs.totalHits + " docs by searching.");

			for (ScoreDoc scoreDoc : topDocs.scoreDocs) {

				Document doc = indexSearcher.doc(scoreDoc.doc);

				System.out.println(doc.get(FIELD_FILENAME) + " score: " + scoreDoc.score);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException, org.apache.lucene.queryparser.classic.ParseException {

		testCreateIndex();

		testSearchDocs("note");

		/*
		 * StandardAnalyzer standardAnalyzer = new StandardAnalyzer();
		 * 
		 * Directory directory = new RAMDirectory(); IndexWriterConfig config =
		 * new IndexWriterConfig(standardAnalyzer); // Create a writer
		 * IndexWriter writer = new IndexWriter(directory, config); Document
		 * document = new Document(); // In a real world example, content would
		 * be the actual content that // needs to be indexed. // Setting content
		 * to Hello World as an example. document.add(new TextField("content",
		 * "Hello World", Field.Store.YES)); writer.addDocument(document);
		 * document.add(new TextField("content", "Hello people",
		 * Field.Store.YES)); writer.addDocument(document); writer.close(); //
		 * Now let's try to search for Hello IndexReader reader =
		 * DirectoryReader.open(directory); IndexSearcher searcher = new
		 * IndexSearcher(reader); QueryParser parser = new
		 * QueryParser("content", standardAnalyzer); Query query =
		 * parser.parse("Hello"); TopDocs results = searcher.search(query, 5);
		 * System.out.println("Hits for Hello -->" + results.totalHits); // case
		 * insensitive search query = parser.parse("hello"); results =
		 * searcher.search(query, 5); System.out.println("Hits for hello -->" +
		 * results.totalHits); // search for a value not indexed query =
		 * parser.parse("Hi there"); results = searcher.search(query, 5);
		 * System.out.println("Hits for Hi there -->" + results.totalHits);
		 */
	}

}
