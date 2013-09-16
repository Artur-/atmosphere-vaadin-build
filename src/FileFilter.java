import java.io.File;

public interface FileFilter {

	void process(File f) throws Exception;

	boolean needsProcessing(File f);

}
