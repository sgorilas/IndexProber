package fragsoft.indexprober;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class IndexProberTest {
	
	private static final String INDEX_LOCATION = "C:/eudract.d/ctr/index/__ctr_doc";
	private ProbingTool probingTool;
	
	@BeforeClass
	public void init() {
		probingTool = new LuceneProbingTool(INDEX_LOCATION);
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
