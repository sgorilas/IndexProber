package fragsoft.indexprober;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuceneProbingTool implements ProbingTool {
	
	private static final Logger LOG = LoggerFactory.getLogger(LuceneProbingTool.class);
	
	private static final String DOCUMENT_CATCHPHRASE = "indexprobingtoolcatchphrase";
	private static final Document PRE_AGREED_DOCUMENT;
	
	private Directory indexDir;
	
	static {
		//TODO Initialize document
		PRE_AGREED_DOCUMENT = new Document();
	}
	
	public LuceneProbingTool(String indexLocation) {
		try {
			indexDir = FSDirectory.getDirectory(indexLocation);
		} catch (IOException ioe) {
			LOG.error("Error: ", ioe);
		} finally {
			if(indexDir == null) {
				throw new AssertionError("Index directory cannot be null. ");
			}
		}
	}
	
	public boolean insert() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean find() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean remove() {
		// TODO Auto-generated method stub
		return false;
	}

}
