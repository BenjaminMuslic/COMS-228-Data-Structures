package edu.iastate.cs228.h1;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author
 *
 */
class AnimalTest {

	@Test
	void ageTest() {

		Plain plainTest = new Plain(1);
		plainTest.grid[0][0] = new Badger(plainTest, 0, 0, 0);
		Animal a = (Animal) plainTest.grid[0][0];
		assertEquals(0, a.myAge());
	}

}
