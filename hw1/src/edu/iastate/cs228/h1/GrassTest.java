package edu.iastate.cs228.h1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author
 *
 */

class GrassTest {

	@Test
//	Condition C: otherwise,Grass.
	void conditionA() {

		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Fox(plainTest, 0, 0, 0);
		plainTest.grid[0][1] = new Fox(plainTest, 0, 1, 0);
		plainTest.grid[1][0] = new Badger(plainTest, 1, 0, 0);
		plainTest.grid[1][1] = new Grass(plainTest, 1, 0);

		assertEquals(State.GRASS, plainTest.grid[1][1].next(plainTest).who());

	}

	@Test
//	Condition B: otherwise, Rabbit if there are at least three Rabbits in the neighborhood;
	void conditionB() {
		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Rabbit(plainTest, 0, 0, 0);
		plainTest.grid[0][1] = new Rabbit(plainTest, 0, 1, 0);
		plainTest.grid[1][0] = new Rabbit(plainTest, 1, 0, 0);
		plainTest.grid[1][1] = new Grass(plainTest, 1, 0);

		assertEquals(State.RABBIT, plainTest.grid[0][0].next(plainTest).who());

	}
}