import static org.junit.Assert.*;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class PackageInstallerTest {
	static PackageInstaller tester;
	@Before
	public void setUp() {
		tester = new PackageInstaller();
	}
	@Test
	public void testValidInput(){
		// simple valid input
		String [] test1 = {"A1: A2", "A2: A3", "A3: A4"};
		String [] res1 = {"A4", "A3", "A2", "A1"};
		tester.installPackage(test1);
		assertEquals("Check for simple linear input", res1, tester.result);
		
		// tree like valid input
		String [] test2 = {"A1: A4", "A2: A3", "A3: A4"};
		// three possible outputs
		String [] res2 = {"A4", "A1", "A3", "A2"};
		String [] res3 = {"A4", "A3", "A1", "A2"};
		String [] res4 = {"A4", "A3", "A2", "A1"};
		tester.installPackage(test2);
		boolean result = Arrays.equals(tester.result, res2) || 
				Arrays.equals(tester.result, res3) || Arrays.equals(tester.result, res4);
		assertEquals("Check for tree like input", true, result);
		
	}
}
