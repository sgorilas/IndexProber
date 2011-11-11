package fragsoft.indexprober;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IndexProberTest {
	
	private ProbingTool probingTool;
	
	@BeforeClass
	public void init() {
		Directory directory = new RAMDirectory();
		probingTool = new LuceneProbingTool(directory);
	}
	
	@Test()
	public void probeInsert() {
		Assert.assertTrue(probingTool.insert());
	}

	@Test(dependsOnMethods= {"probeInsert"})
	public void probeFind() {
		Assert.assertTrue(probingTool.find());
	}
	
	@Test(dependsOnMethods= {"probeFind"})
	public void probeRemove() {
		Assert.assertTrue(probingTool.remove());
	}
	
}
