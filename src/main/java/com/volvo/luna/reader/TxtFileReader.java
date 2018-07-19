package com.volvo.luna.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TxtFileReader {

	public static String readFile(File file) {

		StringBuffer sb = new StringBuffer();

		BufferedReader reader = null;

		try {

			reader = new BufferedReader(new FileReader(file));

			String line = null;

			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			return sb.toString();

		} catch (Exception e) {

			e.printStackTrace();

			throw new RuntimeException(e);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
