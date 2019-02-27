package commun;


import static org.junit.Assert.assertTrue;
import org.junit.Test;
import commun.Identification;

public class IdentificationTest {
    Identification i = new Identification("j1");
    String s = "j1";

	@Test
	public void Test() throws Exception {
		assertTrue(s.equals(i.getNom()));
	}
}
