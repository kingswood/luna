package com.volvo.luna;

import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

public class TextAnalyzerTest {

	private static String str = "had notes-txt";

	public static void print(Analyzer analyzer) {
		StringReader reader = new StringReader(str);
		try {
			TokenStream tokenStream = analyzer.tokenStream("", reader);
			tokenStream.reset();
			CharTermAttribute term = tokenStream.getAttribute(CharTermAttribute.class);
			System.out.println("Anal：" + analyzer.getClass());
			while (tokenStream.incrementToken()) {
				System.out.print(term.toString() + "|");
			}
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Analyzer analyzer = null;
		analyzer = new StandardAnalyzer();
		print(analyzer);
		
		/*analyzer = new WhitespaceAnalyzer(Version.LUCENE_43);
		AnalyzerTest.print(analyzer);
		//
		analyzer = new SimpleAnalyzer(Version.LUCENE_43);
		AnalyzerTest.print(analyzer);

		analyzer = new CJKAnalyzer(Version.LUCENE_43);
		AnalyzerTest.print(analyzer);

		analyzer = new KeywordAnalyzer();
		AnalyzerTest.print(analyzer);

		analyzer = new StopAnalyzer(Version.LUCENE_43);
		AnalyzerTest.print(analyzer);*/
	}

}
