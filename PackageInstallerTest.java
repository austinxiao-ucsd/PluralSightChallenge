import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PackageInstallerTest {
	private static PackageInstaller p1, p2, p3, p4, p5;
	@Before
	public void setUp() {
		p1 = new PackageInstaller();
		p2 = new PackageInstaller();
		p3 = new PackageInstaller();
		p4 = new PackageInstaller();
		p5 = new PackageInstaller();
		// test1: all pairs have a dependency
		String [] t1 = {"A1: A2", "A2: A3", "A3:= A4"};
		p1.installPackage(t1);
		// test2: no dependency
		String [] t2 = {"A1: ","A2: ","A3: ","A4: "};
		p2.installPackage(t2);
		// test3: dependency is self
		String [] t3 = {"A1: A1", "A2: A2", "A3: A3"};
		p3.installPackage(t3);
		// test4: check with or without dependency
		String [] t4 = {"A1: A2", "A3: A3", "A4: A5", "A5: "};
		p4.installPackage(t4);
		// test5: check a disjoint graph
		String [] t5 = {"A1: A2", "A2: A3", "A3: A4", "A5: A6", "A6: A7"};
		p5.installPackage(t5);
	}
	@Test
	public void testParseInput(){
		System.out.println("Test: All inputs have dependency");
		// test1: all pairs have a dependency
		
		assertEquals(4, p1.packageMap.size());

		// test2: no dependency
		System.out.println("Test: inputs have no dependencies");
		
		assertEquals(4, p2.packageMap.size());		

		// test3: dependency is self
		System.out.println("Test: inputs have self as dependencies");
		String [] t3 = {"A1: A1", "A2: A2", "A3: A3"};
		p3.installPackage(t3);
		assertEquals(3, p3.packageMap.size());		

		// test4: check with or without dependency
		System.out.println("Test: input have mixed type of dependencies");
		String [] t4 = {"A1: A2", "A3: A3", "A4: A5", "A5: "};
		p4.installPackage(t4);
		assertEquals(5, p4.packageMap.size());		
	}
	
	@Test
	public void testResultLinearPath(){
		String [] res1 = {"A4", "A3", "A2", "A1"};
		for(int i = 0; i < p1.result.length; i++){
			assertEquals(res1[i], p1.result[i]);
		}
	}
	@Test
	public void testResultDisjointPath(){
		String [] t5 = {"A1: A2", "A2: A3", "A3: A4", "A5: A6", "A6: A7"};
		p5.installPackage(t5);
		assertEquals("A4",p5.result[0]);
	}


}
