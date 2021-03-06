package com.vaadin.external.atmosphere.build.filefilter;

import java.io.File;
import java.io.IOException;

import com.vaadin.external.atmosphere.build.Version;

public class JavascriptVersionUpdater extends AbstractFileFilter {

	public JavascriptVersionUpdater(File root) {
		super(root);
	}

	@Override
	public boolean needsProcessing(File f) {
		return (!f.isDirectory() && f.getName().endsWith(".js"));
	}

	@Override
	public void process(File f) throws Exception {
		String from = "var version = \".*-javascript\"";
		String to = "var version = \"" + Version.getVersion() + "-javascript\"";

		replace(f, from, to);
		String from2 = "version: \".*-jquery\"";
		String to2 = "version: \"" + Version.getVersion() + "-jquery\"";

		String contents = readFile(f);
		contents = contents.replaceAll(from, to);
		contents = contents.replaceAll(from2, to2);
		writeFile(f, contents);
	}

	private void replace(File f, String from, String to) throws IOException {
		String contents = readFile(f);
		String newContents = contents.replaceAll(from, to);
		if (!contents.equals(newContents)) {
		}

	}

}
