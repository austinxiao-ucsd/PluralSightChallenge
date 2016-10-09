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
		String [] expectedRes1 = {"A4", "A3", "A2", "A1"};
		tester.installPackage(test1);
		assertEquals("Check for simple linear input", expectedRes1, tester.result);
		
		// tree like valid input
		String [] test2 = {"A1: A4", "A2: A3", "A3: A4"};
		// three possible outputs
		String [] expectedRes2 = {"A4", "A1", "A3", "A2"};
		String [] expectedRes3 = {"A4", "A3", "A1", "A2"};
		String [] expectedRes4 = {"A4", "A3", "A2", "A1"};
		tester.installPackage(test2);
		boolean result = Arrays.equals(tester.result, expectedRes2) || 
				Arrays.equals(tester.result, expectedRes3) || Arrays.equals(tester.result, expectedRes4);
		assertEquals("Check for tree like input", true, result);
		
		// mixed input that could has no dependencies
		String [] test3 = {"A1: A2", "A2: ", "A3: "};
		// possible outputs
		String [] expectedRes5 = {"A2", "A1", "A3"};
		String [] expectedRes6 = {"A3", "A2", "A1"};
		String [] expectedRes7 = {"A2", "A3", "A1"};
		tester.installPackage(test3);
		
		boolean result2 = Arrays.equals(tester.result, expectedRes5) ||
				Arrays.equals(tester.result, expectedRes6) || Arrays.equals(tester.result, expectedRes7);
		assertEquals("Check for valid independent paths", true, result2);
	}
	@Test
	public void testInputWithCycles(){
		// simple cycle
		String [] test1 = {"A1: A2", "A2: A3", "A3: A1"};
		try{
			tester.installPackage(test1);
			fail("Simple cycle. Should have generated exception.");
		}
		catch(IllegalArgumentException e){
		}
		// multiple cycles
		String [] test2 = {"A1: A2", "A2: A1", "A3: A4", "A4: A3"};
		try{
			tester.installPackage(test2);
			fail("Multiple cycles. Should have generated exception.");
		}
		catch(IllegalArgumentException e){
		}
		// self cycle
		String [] test3 = {"A1: A1"};
		try{
			tester.installPackage(test3);
			fail("Self cycle. Should have generated exception.");
		}
		catch(IllegalArgumentException e){
		}
	}
	@Test
	public void testInvalidInputs(){
		// invalid string
		String [] test1 = {"A1: : A2", "A3: : A4"};
		try{
			tester.installPackage(test1);
			fail("invalid input.");
		}
		catch(IllegalArgumentException e){
		}
	}
}
