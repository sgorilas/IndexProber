package fragsoft.indexprober;

public interface ProbingTool {
	
	/**
	 * Inserts a pre-agreed document into the index.
	 * 
	 * @return true if the document was successfully inserted, false otherwise.
	 */
	boolean insert();
	
	/**
	 * Searches and finds a pre-agreed document from the index.
	 * 
	 * @return true if the document was found, false otherwise.
	 */
	boolean find();
	
	/**
	 * Removes a pre-agreed document from the index.
	 * 
	 * @return true if the document was removed successfully, false otherwise.
	 */
	boolean remove();

}
