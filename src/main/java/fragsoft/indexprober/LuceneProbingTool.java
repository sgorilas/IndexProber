package fragsoft.indexprober;

import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LuceneProbingTool implements ProbingTool {

	private static final Logger LOG = LoggerFactory.getLogger(LuceneProbingTool.class);
	private Directory indexDir;

	/**
	 * Creates and returns an instance of the pre-agreed document.
	 * 
	 * @return an instance of the pre-agreed document.
	 */
	public static final Document createPreAgreedDocument() {
		Document preAgreedDocument = new Document();
		Field field = new Field(ProbingTool.DOCUMENT_FIELD,
				ProbingTool.DOCUMENT_CATCHPHRASE, Field.Store.NO,
				Field.Index.ANALYZED);
		preAgreedDocument.add(field);

		return preAgreedDocument;
	}

	public LuceneProbingTool(Directory indexDir) {
		this.indexDir = indexDir;
	}

	public LuceneProbingTool(String indexLocation) {
		try {
			indexDir = FSDirectory.getDirectory(indexLocation);
		} catch (IOException ioe) {
			LOG.error("Error: ", ioe);
		}
	}

	public boolean insert() {
		if (indexDir == null)
			throw new AssertionError("Index directory cannot be null. ");
		boolean outcome = false;
		try {
			IndexWriter writer = getIndexWriter();
			writer.addDocument(createPreAgreedDocument());
			writer.close();
			outcome = true;
		} catch (CorruptIndexException e) {
			LOG.error("Index corrupted! ", e);
		} catch (LockObtainFailedException e) {
			LOG.error("Index locked! ", e);
		} catch (IOException e) {
			LOG.error("Error: ", e);
		}

		return outcome;
	}

	public boolean find() {
		if (indexDir == null)
			throw new AssertionError("Index directory cannot be null. ");
		boolean outcome = false;
		try {
			IndexSearcher searcher = new IndexSearcher(indexDir);
			Term term = new Term(ProbingTool.DOCUMENT_FIELD,
					ProbingTool.DOCUMENT_CATCHPHRASE);
			Query query = new TermQuery(term);
			int hits = searcher.search(query, 1).totalHits;
			if (hits == 1) {
				outcome = true;
			}
		} catch (CorruptIndexException e) {
			LOG.error("Index corrupted! ", e);
		} catch (LockObtainFailedException e) {
			LOG.error("Index locked! ", e);
		} catch (IOException e) {
			LOG.error("Error: ", e);
		}

		return outcome;
	}

	public boolean remove() {
		if (indexDir == null)
			throw new AssertionError("Index directory cannot be null. ");
		boolean outcome = false;
		try {
			IndexWriter writer = getIndexWriter();
			writer.deleteDocuments(new Term(ProbingTool.DOCUMENT_FIELD,
					ProbingTool.DOCUMENT_CATCHPHRASE));
			writer.close();
			outcome = true;
		} catch (CorruptIndexException e) {
			LOG.error("Index corrupted! ", e);
		} catch (LockObtainFailedException e) {
			LOG.error("Index locked! ", e);
		} catch (IOException e) {
			LOG.error("Error: ", e);
		}

		return outcome;
	}

	private IndexWriter getIndexWriter() throws CorruptIndexException,
			LockObtainFailedException, IOException {
		return new IndexWriter(indexDir, new StandardAnalyzer(), false,
				IndexWriter.MaxFieldLength.LIMITED);
	}

}
