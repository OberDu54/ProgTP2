import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import fr.ul.miage.lucas.*;

public class Tests extends TestCase {
	
	private App app;
	
	private Baignoire baignoire;
	
	private Fuite fuite;
	
	private Robinet robinet;
	
	@Before
	public void before() {
		System.out.println("before");
		app = new App();
		baignoire = new Baignoire(1000);
		fuite = new Fuite(200, baignoire);
		robinet = new Robinet(500, baignoire);
	}
	
	@Test
	public void testVerif() {
		assertEquals(0, App.verification(1000, 500, 200));
		assertEquals(1, App.verification(400, 500, 200));
		assertEquals(2, App.verification(1000, 100, 200));
		assertEquals(3, App.verification(1000, 500, 0));
		assertEquals(4, App.verification(1000, 501, 200));
		assertEquals(5, App.verification(1000, 500, 501));
		assertEquals(6, App.verification(6000, 500, 200));
	}
	
	@Test
	public void testBaignoire() {
		baignoire = new Baignoire(1000);
		assertEquals(1000, baignoire.getCapacite());
		assertEquals(0.0, baignoire.getVolume());
		assertFalse(baignoire.estPleine());
		baignoire.remplir(500);
		assertEquals(500.0, baignoire.getVolume());
		baignoire.reinitialiser();
		assertEquals(0.0, baignoire.getVolume());
		baignoire.remplir(1000);
		assertTrue(baignoire.estPleine());
	}
}
