package edu.iastate.cs228.h1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author
 *
 */

class WildlifeTest {

	@Test
	void test() {
		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Grass(plainTest, 0, 0);
		plainTest.grid[0][1] = new Grass(plainTest, 0, 1);
		plainTest.grid[1][0] = new Grass(plainTest, 1, 0);
		plainTest.grid[1][1] = new Badger(plainTest, 1, 1, 4); // at age 4

		Plain updated = new Plain(2);
		Wildlife.updatePlain(plainTest, updated);

		assertEquals(State.EMPTY, updated.grid[1][1].who());

	}

}
