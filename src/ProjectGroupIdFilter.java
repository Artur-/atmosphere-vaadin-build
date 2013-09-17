import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * Updates project groupId from org.atmosphere to
 * com.vaadin.external.atmosphered
 * 
 */
public class ProjectGroupIdFilter extends PomXmlFilter {

	public static final String GROUP_ID_PREFIX = "com.vaadin.external.atmosphere";

	public ProjectGroupIdFilter(File root) {
		super(root);
	}

	@Override
	public void process(File f, Document d) throws Exception {
		updateGroupId((Element) findNode(d, "/project/groupId"));
		updateGroupId((Element) findNode(d, "/project/parent/groupId"));

		updateFile(f, d);
	}

	private void updateGroupId(Element e) {
		if (e == null) {
			return;
		}

		String groupId = e.getTextContent();
		if (!"org.atmosphere".equals(groupId)
				&& !GROUP_ID_PREFIX.equals(groupId)) {
			return;
			// throw new IllegalStateException("Unexpected groupId: " +
			// groupId);
		}
		e.setTextContent(GROUP_ID_PREFIX);

	}

}