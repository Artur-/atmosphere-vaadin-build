package com.vaadin.external.atmosphere.build.filefilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class AbstractFileFilter implements FileFilter {

	private File root;

	public AbstractFileFilter(File root) {
		this.root = root;
	}

	protected void writeFile(File f, String string) throws IOException {
		FileWriter writer = new FileWriter(f);
		writer.write(string);
		writer.close();
	}

	protected String readFile(File f) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(f));
		int bytesRead;
		char[] buf = new char[60000];
		StringBuilder contents = new StringBuilder();
		while ((bytesRead = reader.read(buf)) > 0) {
			contents.append(buf, 0, bytesRead);
		}
		reader.close();
		return contents.toString();
	}
}
