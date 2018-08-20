package com.volvo.luna.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;


public class FileReader {
	
	public static String getFileContent(File f){
		
		InputStream is = null;
		
		try {
			Metadata metadata = new Metadata();
			metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
			is = new FileInputStream(f);
			Parser parser = new AutoDetectParser();
			
			ContentHandler handler = new BodyContentHandler();
			
			ParseContext context = new ParseContext();
			
			context.set(Parser.class, parser);
			
			parser.parse(is, handler, metadata, new ParseContext());
			
			String content = handler.toString();
			
			System.out.println(content);
			
			return content;
			
		} catch (Exception e) {
			
			return null;
			
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args){
		
		String fileName = "files\\source\\env.html";
		
		getFileContent(new File(fileName));
		
	}
	
}
