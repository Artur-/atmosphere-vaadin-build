import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class VaadinAtmospherePreprocessor {
	private static final String BASE = "/Users/artur/Documents/workspace/atmosphere/upstream";
	private static String[] dirs = new String[] { "atmosphere",
	// "atmosphere-compat", "atmosphere-extensions", "atmosphere-jsr356",
	// "atmosphere-javascript"
	};

	public static void main(String[] args) throws Exception {
		for (String dir : dirs) {
			new VaadinAtmospherePreprocessor().preprocess(new File(BASE + "/"
					+ dir));
		}
	}

	private void preprocess(File projectDir) throws Exception {
		List<FileFilter> fileFilters = new ArrayList<FileFilter>();
		List<XMLFileFilter> xmlFilters = new ArrayList<XMLFileFilter>();

		xmlFilters.add(new GroupIdFilter(projectDir));
		xmlFilters.add(new DistributionManagementFilter(projectDir));
		xmlFilters.add(new SLF4JDependencyUpdater(projectDir));
		xmlFilters.add(new GPGReleaseKeyReader(projectDir));

		fileFilters.add(new SLF4JPackageReferenceUpdater(projectDir));

		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		Collection<File> files = listRecursively(projectDir);
		for (File f : files) {
			for (FileFilter filter : fileFilters) {
				if (filter.needsProcessing(f)) {
					filter.process(f);
				}
			}
			for (XMLFileFilter filter : xmlFilters) {
				Document doc = null;
				if (filter.needsProcessing(f)) {
					if (doc == null) {
						DocumentBuilder dBuilder = dbFactory
								.newDocumentBuilder();
						doc = dBuilder.parse(f);

					}
					filter.process(f, doc);
				}
			}
		}
	}

	private Collection<File> listRecursively(File dir) {
		Collection<File> found = new ArrayList<File>();
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				found.addAll(listRecursively(f));
			} else {
				found.add(f);
			}
		}

		return found;

	}

	private Collection<File> findFiles(String filename, File dir) {
		Collection<File> found = new ArrayList<File>();
		for (File f : dir.listFiles()) {
			if (f.isDirectory()) {
				found.addAll(findFiles(filename, f));
			} else if (f.getName().equals(filename)) {
				found.add(f);
			}
		}
		return found;
	}
}
