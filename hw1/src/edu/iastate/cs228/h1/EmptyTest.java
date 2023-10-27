package edu.iastate.cs228.h1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author
 *
 */

class EmptyTest {

	@Test
	// Condition A: Rabbit, if more than one neighboring Rabbit;
	void conditionA() {

		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Grass(plainTest, 0, 0);
		plainTest.grid[0][1] = new Grass(plainTest, 0, 1);
		plainTest.grid[1][0] = new Rabbit(plainTest, 1, 0, 0);
		plainTest.grid[1][1] = new Rabbit(plainTest, 1, 1, 0);

		assertEquals(State.RABBIT, plainTest.grid[1][1].next(plainTest).who());

	}

	@Test
	// Condition C: otherwise, Badger, if more than one neighboring Badger;
	void conditionC() {
		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Grass(plainTest, 0, 0);
		plainTest.grid[0][1] = new Badger(plainTest, 1, 0, 0);
		plainTest.grid[1][0] = new Badger(plainTest, 1, 0, 0);
		plainTest.grid[1][1] = new Badger(plainTest, 1, 1, 0);

		assertEquals(State.EMPTY, plainTest.grid[1][1].next(plainTest).who());

	}
}
