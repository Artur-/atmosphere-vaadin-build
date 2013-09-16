import java.io.File;

public abstract class JavaFileUpdater implements FileFilter {

	private File root;

	public JavaFileUpdater(File root) {
		this.root = root;
	}

	@Override
	public boolean needsProcessing(File f) {
		if (f.isDirectory()) {
			return false;
		}
		return f.getName().endsWith(".java");
	}

}
