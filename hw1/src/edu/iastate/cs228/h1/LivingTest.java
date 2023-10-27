package edu.iastate.cs228.h1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * 
 * @author
 *
 */

class LivingTest {

	@Test
//	Condition A: Empty if the Badger is currently at age 4;
	void conditionA() {

		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Grass(plainTest, 0, 0);
		plainTest.grid[0][1] = new Grass(plainTest, 0, 1);
		plainTest.grid[1][0] = new Grass(plainTest, 1, 0);
		plainTest.grid[1][1] = new Badger(plainTest, 1, 1, 4); // at age 4

		assertEquals(State.EMPTY, plainTest.grid[1][1].next(plainTest).who());

	}

	@Test
//	Condition C: otherwise, Empty, if Badgers and Foxes together out number Rabbits in the neighborhood;
	void conditionC() {
		Plain plainTest = new Plain(2);

		plainTest.grid[0][0] = new Fox(plainTest, 0, 0, 0);
		plainTest.grid[0][1] = new Fox(plainTest, 0, 1, 0);
		plainTest.grid[1][0] = new Grass(plainTest, 1, 0);
		plainTest.grid[1][1] = new Badger(plainTest, 1, 1, 0);

		assertEquals(State.EMPTY, plainTest.grid[0][0].next(plainTest).who());

	}
}